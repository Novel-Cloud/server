package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

//class ArtworkRepositoryImpl(
//    private val jpaQueryFactory: JPAQueryFactory
//): ArtworkRepositoryCustom {
//
//    @Override
//    fun findArtworkList(pageable: Pageable): Page<Artwork> {
//        val contents = jpaQueryFactory
//            .selectFrom(artwork)
//    }
//
//}
