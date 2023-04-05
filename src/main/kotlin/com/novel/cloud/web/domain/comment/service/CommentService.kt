package com.novel.cloud.web.domain.comment.service

import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.domain.comment.controller.rq.CreateCommentRq
import com.novel.cloud.web.domain.comment.controller.rq.DeleteCommentRq
import com.novel.cloud.web.domain.comment.controller.rq.UpdateCommentRq
import com.novel.cloud.web.domain.comment.repository.CommentRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.DoNotHavePermissionToDeleteOrUpdateCommentException
import com.novel.cloud.web.exception.NotMatchedParentChildArtworkIdException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val findMemberService: FindMemberService,
    private val findArtworkService: FindArtworkService,
    private val findCommentService: FindCommentService,
    private val commentRepository: CommentRepository
) {

    fun createComment(memberContext: MemberContext, rq: CreateCommentRq) {
        val artworkId: Long = rq.artworkId
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val artwork = findArtworkService.findByIdOrElseThrow(artworkId)

        val parentId = rq.parentId
        val parent = getCommentParent(parentId)

        validationCheck(parent, artworkId)

        val comment = Comment(
            content = rq.content,
            writer = member,
            artwork = artwork,
            parent = parent
        )
        commentRepository.save(comment)
    }

    private fun getCommentParent(parentId: Long?): Comment? {
        parentId?.let {
            return findCommentService.findByIdOrElseThrow(parentId)
        }
        return null
    }

    private fun validationCheck(parent: Comment?, artworkId: Long) {
        parent?.let {
            val parentArtworkId: Long? = parent.artwork.id

            if (parentArtworkId != artworkId) {
                throw NotMatchedParentChildArtworkIdException()
            }
            // TODO: 일단 무한 대댓글이지만 나중에 깊이 제한이 생기면 댓글의 깊이 제한 validation 추가
        }
    }

    fun deleteComment(memberContext: MemberContext, rq: DeleteCommentRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val comment = findCommentService.findByIdOrElseThrow(rq.commentId)

        commentPermissionCheck(member, comment)
        comment.updateDeleted(true)
    }

    private fun commentPermissionCheck(member: Member, comment: Comment) {
        val writerId: Long? = comment.writer.id
        val memberId: Long? = member.id
        if (writerId != memberId) {
            throw DoNotHavePermissionToDeleteOrUpdateCommentException()
        }
    }

    fun updateComment(memberContext: MemberContext, rq: UpdateCommentRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val comment = findCommentService.findByIdOrElseThrow(rq.commentId)
        val content = rq.content

        commentPermissionCheck(member, comment)
        comment.updateContent(content)
    }


}