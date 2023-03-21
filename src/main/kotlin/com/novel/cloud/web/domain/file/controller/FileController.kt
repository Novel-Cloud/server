package com.novel.cloud.web.domain.file.controller

import com.novel.cloud.web.domain.file.service.FileService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "이미지")
@RestController
class FileController(
    private val fileService: FileService
) {

    @Operation(summary = "이미지 불러오기")
    @GetMapping(ApiPath.VIEW_IMG)
    fun downloadImage (@PathVariable fileUidName: String): ResponseEntity<ByteArrayResource> {
        return fileService.downloadImage(fileUidName)
    }

}