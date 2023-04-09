package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkDetailRs
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs
import com.novel.cloud.web.domain.artwork.controller.rs.FindTemporaryArtworkRs
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.endpoint.PagedResponse
import com.novel.cloud.web.endpoint.Pagination
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "작품 조회/검색")
@RestController
class FindArtworkController (
    private val findArtworkService: FindArtworkService
) {

    @Operation(summary = "임시 저장 작품 불러오기")
    @GetMapping(ApiPath.ARTWORK_SAVE)
    fun findTemporaryArtworkSelf(@AuthenticationPrincipal memberContext: MemberContext): FindTemporaryArtworkRs {
        return findArtworkService.findTemporaryArtworkSelf(memberContext)
    }

    @Operation(summary = "작품 모두 조회")
    @GetMapping(ApiPath.VIEW_ARTWORK)
    fun findAllArtwork(@AuthenticationPrincipal memberContext: MemberContext?,
                       @RequestParam(value = "page", required = true) page: Int,
                       @RequestParam(value = "size", required = true) size: Int): PagedResponse<FindArtworkRs> {
        val pagination = Pagination(page, size)
        return findArtworkService.findAllArtwork(memberContext, pagination)
    }

    @Operation(summary = "작품 상세 조회")
    @GetMapping(ApiPath.ARTWORK_DETAIL)
    fun findArtworkDetail(@AuthenticationPrincipal memberContext: MemberContext?,
                          @PathVariable artworkId: Long): FindArtworkDetailRs {
        return findArtworkService.findArtworkDetail(memberContext, artworkId)
    }

    @Operation(summary = "해시태그 검색")
    @GetMapping(ApiPath.SEARCH_TAG)
    fun findArtworkByTag(@AuthenticationPrincipal memberContext: MemberContext?,
                         @RequestParam(value = "page", required = true) page: Int,
                         @RequestParam(value = "size", required = true) size: Int,
                         @RequestParam(value="tags[]") tags: List<String>
    ): PagedResponse<FindArtworkRs> {
        val pagination = Pagination(page, size)
        return findArtworkService.findArtworkByTag(memberContext, pagination, tags)
    }

}