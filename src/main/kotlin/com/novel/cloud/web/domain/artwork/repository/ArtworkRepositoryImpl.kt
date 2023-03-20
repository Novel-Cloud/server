package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import com.novel.cloud.db.entity.artwork.QArtwork.artwork
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.support.PageableExecutionUtils

class ArtworkRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): ArtworkRepositoryCustom {
    override fun findArtworkList(pageable: Pageable): Page<Artwork> {
        val contents = jpaQueryFactory
            .selectFrom(artwork)
            .orderBy(artwork.createdDate.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // TODO: fetchCount Deprecated 리팩토링
        val countQuery = jpaQueryFactory
            .selectFrom(artwork)
            .orderBy(artwork.createdDate.desc());

        val totalSupplier = { countQuery.fetchCount() }

        return PageableExecutionUtils.getPage(contents, pageable, totalSupplier)
    }

}
