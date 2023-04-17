package com.novel.cloud.web.utils

import com.novel.cloud.web.exception.FileException
import org.springframework.web.multipart.MultipartFile

object FileValidateUtils {

    fun supportedFileValidationCheck(
        multipartFile: MultipartFile?
    ) {
        if (multipartFile == null || multipartFile.isEmpty) {
            throw FileException("파일이 비어있습니다.")
        }
        val contentType = multipartFile.contentType
        if (!isSupportedContentType(contentType)) {
            throw FileException("PNG, JPG, GIF만 허용됩니다.")
        }
    }

    fun supportedFileValidationCheck(
        multipartFiles: List<MultipartFile>
    ) {
        multipartFiles.map { multipartFile ->
            if (multipartFile.isEmpty) {
                throw FileException("파일이 비어있습니다.")
            }
            val contentType = multipartFile.contentType
            if (!isSupportedContentType(contentType)) {
                throw FileException("PNG, JPG, GIF만 허용됩니다.")
            }
        }

    }

    private fun isSupportedContentType(contentType: String?): Boolean {
        return contentType == "image/png"
                || contentType == "image/jpg"
                || contentType == "image/jpeg"
                || contentType == "image/gif"
    }

}