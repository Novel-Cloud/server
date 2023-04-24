package com.novel.cloud.web.domain.file.controller

import com.novel.cloud.web.domain.file.service.S3UploadService
import com.novel.cloud.web.path.ApiPath
import com.novel.cloud.web.utils.FileValidateUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "이미지")
@RestController
class FileController(
    private val s3UploadService: S3UploadService
) {

    @Operation(summary = "에디터 이미지 단일 업로드")
    @PostMapping(ApiPath.FILE_UPLOAD)
    fun editorImageUpload(
        @RequestPart(value = "image") image: MultipartFile
    ): String {
        FileValidateUtils.imageValidationCheck(image)
        return s3UploadService.upload(image)
    }

}