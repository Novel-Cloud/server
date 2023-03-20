package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.service.ArtworkService
import com.novel.cloud.web.domain.file.service.FileService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@Tag(name="작품")
@RestController
class ArtworkController(
    private val artworkService: ArtworkService,
    private val fileService: FileService
) {

    @Operation(summary = "작품 등록")
    @PostMapping(ApiPath.ARTWORK)
    fun createArtwork(@AuthenticationPrincipal memberContext: MemberContext,
                      @Validated @RequestPart(value = "rq") rq: CreateArtworkRq,
                      @RequestPart(value = "files") files: List<MultipartFile>,
                      @RequestPart(value = "thumbnail") thumbnail: MultipartFile) {
        val artwork = artworkService.createArtwork(memberContext, rq)
        fileService.uploadFile(memberContext, artwork, files, thumbnail);
    }



}