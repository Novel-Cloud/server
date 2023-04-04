package com.novel.cloud.web.domain.comment.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.comment.controller.rq.CreateCommentRq
import com.novel.cloud.web.domain.comment.controller.rq.DeleteCommentRq
import com.novel.cloud.web.domain.comment.service.CommentService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name="댓글")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @Operation(summary = "댓글 생성")
    @PostMapping(ApiPath.COMMENT)
    fun createComment(@AuthenticationPrincipal memberContext: MemberContext,
                      @Validated @RequestBody rq: CreateCommentRq
    ) {
        commentService.createComment(memberContext, rq)
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping(ApiPath.COMMENT)
    fun deleteAbbreviation(@AuthenticationPrincipal memberContext: MemberContext,
                           @Validated @RequestBody rq: DeleteCommentRq
    ) {
        commentService.deleteComment(memberContext, rq)
    }
//    @Operation(summary = "댓글 수정")
//    @PutMapping(ApiPath.SHORTCUT)
//    fun updateAbbreviation(@AuthenticationPrincipal memberContext: MemberContext,
//                           @Validated @RequestBody rq: UpdateAbbreviationRq
//    ) {
//        abbreviationService.updateAbbreviation(memberContext, rq)
//    }
//
//    @Operation(summary = "작품 댓글 불러오기")
//    @GetMapping(ApiPath.SHORTCUT)
//    fun findAbbreviationSelf(@AuthenticationPrincipal memberContext: MemberContext): List<FindSequenceAbbreviationRs> {
//        return findAbbreviationService.findAbbreviationSelf(memberContext)
//    }

}