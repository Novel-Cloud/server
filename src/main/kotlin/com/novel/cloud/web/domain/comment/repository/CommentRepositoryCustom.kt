package com.novel.cloud.web.domain.comment.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.comment.Comment

interface CommentRepositoryCustom {

    fun findParentCommentByArtwork(artwork: Artwork): List<Comment>

}