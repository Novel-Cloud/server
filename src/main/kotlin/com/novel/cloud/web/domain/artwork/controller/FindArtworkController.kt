package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.endpoint.PagedResponse
import com.novel.cloud.web.endpoint.Pagination
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "작품")
@RestController
class FindArtworkController (
    private val findArtworkService: FindArtworkService
) {

    @Operation(summary = "작품 모두 조회")
    @GetMapping(ApiPath.ARTWORK)
    fun findAllArtwork(@AuthenticationPrincipal memberContext: MemberContext,
                       @RequestParam(value = "page", required = false) page: Int,
                       @RequestParam(value = "size", required = false) size: Int) {
        val pagination: Pagination = Pagination(
            page = page,
            size = size
        )
        return findArtworkService.findAllArtwork(memberContext, pagination)
    }

//    @Operation(summary = "작품 상세보기")
//    @GetMapping(ApiPath.ARTWORK)
//    fun findArtworkDetail(@AuthenticationPrincipal memberContext: MemberContext) {
//
//    }

}