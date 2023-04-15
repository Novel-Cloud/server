package com.novel.cloud.web.domain.comment.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.comment.controller.rs.FindCommentRs
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.domain.comment.repository.CommentRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.ListResponse
import com.novel.cloud.web.exception.NotFoundCommentException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindCommentService(
    private val commentRepository: CommentRepository,
    private val findMemberService: FindMemberService,
    private val findArtworkService: FindArtworkService,
) {

    fun findByIdOrElseThrow(parentId: Long): Comment {
        return commentRepository.findById(parentId)
            .orElseThrow { NotFoundCommentException() }
    }

    /**
     * 대댓글까지 조회
     */
    fun findCommentByArtworkId(memberContext: MemberContext?, artworkId: Long): ListResponse<FindCommentRs> {
        val member = memberContext?.let {
            findMemberService.findLoginMemberOrElseThrow(memberContext)
        }
        val artwork: Artwork = findArtworkService.findByIdOrElseThrow(artworkId)

        val commentList = commentRepository.findParentCommentByArtwork(artwork)

        val findCommentRsList = commentList.map { comment ->
            FindCommentRs.create(
                comment = comment,
                member = member
            )
        }.toList()

        return ListResponse(
            list = findCommentRsList
        )

    }

}

