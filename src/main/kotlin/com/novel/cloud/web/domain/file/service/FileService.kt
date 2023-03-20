package com.novel.cloud.web.domain.file.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.file.repository.FileRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class FileService (
    private val findMemberService: FindMemberService,
    private val fileRepository: FileRepository
) {

    // TODO: 예시 업로드 dir 수정
    val uploadDir = "/Users/min/projects/novel-cloud/file/img/"

    fun uploadFile(memberContext: MemberContext, artwork: Artwork, multipartFiles: List<MultipartFile>, thumbnail: MultipartFile) {
        val member = findMemberService.findLoginMemberOrElseException(memberContext)
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
                    fileSize = file.size,
                    artwork = artwork
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
            val attachFile = AttachFile(
                fileName = originalFileName,
                filePath = uploadDir + saveFileName,
                fileSize = thumbnail.size,
                artwork = artwork
            )
            fileRepository.save(attachFile)
        }

    }

    private fun getSaveFileName(originalFilename: String?): String {
        val extPosIndex = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)

        return UUID.randomUUID().toString() + "." + ext
    }

}