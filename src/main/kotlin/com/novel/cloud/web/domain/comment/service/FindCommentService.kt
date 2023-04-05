package com.novel.cloud.web.domain.comment.service

import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rs.FindCommentRs
import com.novel.cloud.web.domain.comment.repository.CommentRepository
import com.novel.cloud.web.exception.NotFoundCommentException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindCommentService(
    private val commentRepository: CommentRepository,
) {

    fun findByIdOrElseThrow(parentId: Long): Comment {
        return commentRepository.findById(parentId)
            .orElseThrow{ NotFoundCommentException() }
    }

    fun findCommentByArtworkId(memberContext: MemberContext, artworkId: Long): List<FindCommentRs> {
    }

}

