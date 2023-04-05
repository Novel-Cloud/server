package com.novel.cloud.web.domain.comment.repository

import com.novel.cloud.db.entity.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>, CommentRepositoryCustom {}