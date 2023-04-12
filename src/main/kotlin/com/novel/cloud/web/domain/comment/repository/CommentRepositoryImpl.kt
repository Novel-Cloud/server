package com.novel.cloud.web.domain.comment.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.comment.Comment
import com.querydsl.jpa.impl.JPAQueryFactory
import com.novel.cloud.db.entity.comment.QComment.comment

class CommentRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : CommentRepositoryCustom {

    override fun findParentCommentByArtwork(artwork: Artwork): List<Comment> {
        return jpaQueryFactory
            .selectFrom(comment)
            .where(
                comment.artwork.id.eq(artwork.id),
                comment.parent.id.isNull
            )
            .orderBy(comment.parent.id.asc().nullsFirst(), comment.createdDate.desc())
            .fetch()
    }

}