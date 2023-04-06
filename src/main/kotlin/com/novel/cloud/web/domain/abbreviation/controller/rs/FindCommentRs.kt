package com.novel.cloud.web.domain.abbreviation.controller.rs

import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.dto.MemberDto
import com.novel.cloud.web.utils.DateUtils

data class FindCommentRs(
    val commentId: Long? = null,
    val content: String? = null,
    val createdDate: String? = null,
    val editable: Boolean? = null,
    val deletable: Boolean? = null,
    val parentId: Long? = null,
    val writer: MemberDto? = null,
    val replyList: List<FindCommentRs>? = null,
){
    companion object {
        fun create(comment: Comment, member: Member): FindCommentRs {
            val formattedDate = DateUtils.formatDateYYYYMMDD(comment.createdDate)
            val writerId = comment.writer.id
            val editAndDeletable = getEditAndDeletable(writerId, member)
            val parentId = getParentId(comment)
            val replyList = getReplyList(comment, member)

            return FindCommentRs(
                commentId = comment.id,
                content = comment.content,
                createdDate = formattedDate,
                editable = editAndDeletable,
                deletable = editAndDeletable,
                parentId = parentId,
                writer = getWriter(comment.writer),
                replyList = replyList
            )
        }

        private fun getEditAndDeletable(writerId: Long?, member: Member): Boolean? {
            return member.id?.equals(writerId)
        }

        private fun getWriter(member: Member): MemberDto {
            return MemberDto.create(member)
        }

        private fun getParentId(comment: Comment): Long? {
            val parent = comment.parent
            parent?.let {
                return parent.id
            }
            return null
        }

        private fun getReplyList(
            comment: Comment,
            member: Member,
        ): List<FindCommentRs> {
            val children = comment.children
            return children.map { child ->
                    create(
                        child,
                        member
                    )
            }.toList()
        }

    }

}