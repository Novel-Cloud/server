package com.novel.cloud.web.domain.bookmark.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.bookmark.controller.rq.BookmarkArtworkRq
import com.novel.cloud.web.domain.bookmark.service.BookmarkService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "좋아요")
@RestController
class BookmarkController(
    private val bookmarkService: BookmarkService,
) {

    @Operation(summary = "작품 좋아요 (토글)")
    @PostMapping(ApiPath.TOGGLE_LIKE)
    fun toggleArtworkBookmark(
        @AuthenticationPrincipal memberContext: MemberContext,
        @Validated @RequestBody rq: BookmarkArtworkRq,
    ) {
        bookmarkService.toggleArtworkBookmark(memberContext, rq)
    }

}