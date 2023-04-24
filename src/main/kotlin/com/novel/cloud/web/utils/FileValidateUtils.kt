package com.novel.cloud.web.utils

import com.novel.cloud.web.exception.FileException
import org.springframework.web.multipart.MultipartFile

object FileValidateUtils {

    fun imageValidationCheck(
        multipartFile: MultipartFile?
    ) {
        if (multipartFile == null || multipartFile.isEmpty) {
            throw FileException("이미지 파일이 비어있습니다.")
        }
        if (multipartFile.size > 33554432) {
            throw FileException("이미지 파일의 크기가 32MB를 넘습니다.")
        }

        val contentType = multipartFile.contentType
        if (!isSupportedContentType(contentType)) {
            throw FileException("PNG, JPG, GIF만 허용됩니다.")
        }
    }

    fun imageValidationCheck(
        multipartFiles: List<MultipartFile>
    ) {
        val totalFileSize = multipartFiles.sumOf { it.size }
        if (totalFileSize > 209715200) {
            throw FileException("전체 이미지 파일의 크기가 200MB를 넘습니다.")
        }

        multipartFiles.map { multipartFile ->
            val contentType = multipartFile.contentType
            if (!isSupportedContentType(contentType)) {
                throw FileException("PNG, JPG, GIF만 허용됩니다.")
            }
        }
    }

    fun profileImageValidationCheck(profile: MultipartFile?) {
        if (profile == null || profile.isEmpty) {
            throw FileException("프로필 사진이 비어있습니다.")
        }
        if (profile.size > 5242880) {
            throw FileException("프로필 사진의 크기가 5MB를 넘습니다.")
        }
        val contentType = profile.contentType
        if (!isSupportedContentType(contentType)) {
            throw FileException("PNG, JPG, GIF만 허용됩니다.")
        }
    }

    private fun isSupportedContentType(contentType: String?): Boolean {
        return contentType == "image/png"
                || contentType == "image/jpg"
                || contentType == "image/jpeg"
                || contentType == "image/gif"
    }

}