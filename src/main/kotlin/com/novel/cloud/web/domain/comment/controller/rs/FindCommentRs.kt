package com.novel.cloud.web.domain.comment.controller.rs

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
) {
    companion object {

        fun create(comment: Comment, member: Member?): FindCommentRs {
            val content = getContent(comment)
            val formattedDate = DateUtils.formatDateComment(comment.createdDate)
            val writer = getWriter(comment.writer)
            val writerId = comment.writer.id
            val editAndDeletable = getEditAndDeletable(writerId, member)
            val parentId = getParentId(comment)
            val replyList = getReplyList(comment, member)

            return FindCommentRs(
                commentId = comment.id,
                content = content,
                createdDate = formattedDate,
                editable = editAndDeletable,
                deletable = editAndDeletable,
                parentId = parentId,
                writer = writer,
                replyList = replyList
            )
        }

        private fun getContent(comment: Comment): String {
            if (comment.deleted) {
                return "삭제된 메세지입니다"
            }
            return comment.content
        }

        private fun getEditAndDeletable(writerId: Long?, member: Member?): Boolean {
            return member?.let {
                member.id?.equals(writerId)
            } ?: false
        }

        private fun getWriter(member: Member): MemberDto {
            return MemberDto.create(member)
        }

        private fun getParentId(comment: Comment): Long? {
            val parent = comment.parent
            return parent?.let {
                parent.id
            }
        }

        private fun getReplyList(
            comment: Comment,
            member: Member?,
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