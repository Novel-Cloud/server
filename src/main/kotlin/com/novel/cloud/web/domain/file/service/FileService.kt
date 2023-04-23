package com.novel.cloud.web.domain.file.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.enums.AttachFileType
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.file.repository.FileRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class FileService(
    private val fileRepository: FileRepository,
    private val s3UploadService: S3UploadService
) {

    /**
     * 작품 이미지 업로드
     */
    fun uploadArtworkImage(
        memberContext: MemberContext,
        artwork: Artwork,
        thumbnail: MultipartFile,
        multipartFiles: List<MultipartFile>
    ) {
        uploadThumbnail(artwork, thumbnail)
        uploadImageFiles(artwork, multipartFiles)
    }

    /**
     * 썸네일 업로드
     */
    private fun uploadThumbnail(artwork: Artwork, thumbnail: MultipartFile) {
        val originalFileName = thumbnail.originalFilename

        // TODO::S3 링크 보안문제
        val saveFileName = s3UploadService.upload(thumbnail)

        // 파일 저장 및 파일 정보 저장
        originalFileName?.let {
            artwork.updateThumbnail(saveFileName)
            val attachFile = AttachFile(
                fileName = originalFileName,
                filePath = saveFileName,
                fileUidName = saveFileName,
                fileSize = thumbnail.size,
                artwork = artwork,
                attachFileType = AttachFileType.THUMBNAIL
            )
            fileRepository.save(attachFile)
        }
    }

    /**
     * 썸네일이 아닌, 이미지 파일 업로드
     */
    private fun uploadImageFiles(artwork: Artwork, multipartFiles: List<MultipartFile>) {
        multipartFiles.map { file ->
            val originalFileName = file.originalFilename

            // TODO::S3 링크 보안문제
            val saveFileName = s3UploadService.upload(file)

            // 파일 저장 및 파일 정보 저장
            originalFileName?.let {
                val attachFile = AttachFile(
                    fileName = originalFileName,
                    filePath = saveFileName,
                    fileUidName = saveFileName,
                    fileSize = file.size,
                    artwork = artwork,
                    attachFileType = AttachFileType.IMAGE
                )
                fileRepository.save(attachFile)
            }
        }
    }

    /**
     * 이미지 업데이트
     */
    fun updateArtworkImage(
        memberContext: MemberContext,
        artwork: Artwork,
        thumbnail: MultipartFile,
        multipartFiles: List<MultipartFile>
    ) {
        artwork.removeAttachFiles()
        uploadThumbnail(artwork, thumbnail)
        uploadImageFiles(artwork, multipartFiles)
    }

}