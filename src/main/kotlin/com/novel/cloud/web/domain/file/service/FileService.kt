package com.novel.cloud.web.domain.file.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.AttachFileType
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.file.repository.FileRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.NotFoundFileException
import com.novel.cloud.web.path.ApiPath
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class FileService(
    private val findMemberService: FindMemberService,
    private val fileRepository: FileRepository,
) {

    // TODO: 예시 업로드 dir 수정
    val uploadDir = "/Users/min/projects/novel-cloud/file/img/"

    fun uploadFile(memberContext: MemberContext, artwork: Artwork, multipartFiles: List<MultipartFile>, thumbnail: MultipartFile) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        uploadImageFiles(member, artwork, multipartFiles)
        uploadThumbnail(member, artwork, thumbnail)
    }

    private fun uploadImageFiles(member: Member, artwork: Artwork, multipartFiles: List<MultipartFile>) {
        multipartFiles.map { file ->
            val originalFileName = file.originalFilename;
            val saveFileName = getSaveFileName(originalFileName);

            // 파일 저장 및 파일 정보 저장
            originalFileName?.let {
                file.transferTo(File(uploadDir + saveFileName))
                val attachFile = AttachFile(
                    fileName = originalFileName,
                    filePath = uploadDir + saveFileName,
                    fileUidName = saveFileName,
                    fileSize = file.size,
                    artwork = artwork,
                    attachFileType = AttachFileType.IMAGE
                )
                fileRepository.save(attachFile)
            }
        }
    }

    private fun uploadThumbnail(member: Member, artwork: Artwork, thumbnail: MultipartFile) {
        val originalFileName = thumbnail.originalFilename;
        val saveFileName = getSaveFileName(originalFileName);

        // 파일 저장 및 파일 정보 저장
        originalFileName?.let {
            thumbnail.transferTo(File(uploadDir + saveFileName))
            artwork.updateThumbnail(saveFileName)
            val attachFile = AttachFile(
                fileName = originalFileName,
                filePath = uploadDir + saveFileName,
                fileUidName = saveFileName,
                fileSize = thumbnail.size,
                artwork = artwork,
                attachFileType = AttachFileType.THUMBNAIL
            )
            fileRepository.save(attachFile)
        }
    }

    private fun getSaveFileName(originalFilename: String?): String {
        val extPosIndex = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)

        return UUID.randomUUID().toString() + "." + ext
    }

    fun downloadImage(fileUidName: String): ResponseEntity<ByteArrayResource> {
        val file = findAttachFileByFileUidNameOrElseThrow(fileUidName)
        val img = File(file.filePath)

        // 파일이 존재하지 않는 경우 404 Not Found 반환
        if (!img.exists()) {
            throw NotFoundFileException();
        }

        // 파일을 바이트 배열로 읽어옴
        val imageBytes = Files.readAllBytes(img.toPath())

        // 바이트 배열을 ByteArrayResource로 변환하여 반환
        val resource = ByteArrayResource(imageBytes)

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .contentLength(imageBytes.size.toLong())
            .body(resource)
    }

    private fun findAttachFileByFileUidNameOrElseThrow(fileUidName: String): AttachFile {
        return fileRepository.findAttachFileByFileUidName(fileUidName)
            .orElseThrow{ NotFoundFileException() }
    }


}