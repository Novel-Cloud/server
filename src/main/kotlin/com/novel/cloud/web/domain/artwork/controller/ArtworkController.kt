package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.service.ArtworkService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Tag(name="작품")
@RestController
class ArtworkController(
    private val artworkService: ArtworkService
) {

    @Operation(summary = "작품 등록")
    @PostMapping(ApiPath.ARTWORK)
    fun createArtwork(@AuthenticationPrincipal memberContext: MemberContext,
                      @Validated @RequestBody rq: CreateArtworkRq) {
        artworkService.createArtwork(memberContext, rq)
    }


}