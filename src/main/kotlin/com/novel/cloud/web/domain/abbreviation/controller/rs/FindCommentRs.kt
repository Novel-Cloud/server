package com.novel.cloud.web.domain.abbreviation.controller.rs

import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.dto.MemberDto

data class FindCommentRs (
    val writer: MemberDto? = null,
    val commentId: Long? = null,
    val content: String? = null,
    val createdDate: String? = null,
    val editable: Boolean? = null,
    val deletable: Boolean? = null,
    val parentId: Long? = null,
    val replyList: List<FindCommentRs>? = null
){
    companion object {
        fun create(comment: Comment, member: Member): FindCommentRs {
            return FindCommentRs(

            )
        }
    }

}