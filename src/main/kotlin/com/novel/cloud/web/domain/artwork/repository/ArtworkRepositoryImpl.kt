package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.QArtwork.artwork
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.artwork.controller.rq.SearchArtworkFilterRq
import com.novel.cloud.web.domain.enums.ArtworkSortType
import com.novel.cloud.web.domain.enums.UploadDateType
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import java.time.LocalDateTime

class ArtworkRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ArtworkRepositoryCustom {

    override fun findArtworkList(pageable: Pageable): Page<Artwork> {
        val contents = jpaQueryFactory
            .selectFrom(artwork)
            .orderBy(artwork.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = jpaQueryFactory
            .selectFrom(artwork)

        val totalSupplier = { countQuery.fetch().size.toLong() }

        return PageableExecutionUtils.getPage(contents, pageable, totalSupplier)

    }

    override fun findArtworkListByFilter(pageable: Pageable, filter: SearchArtworkFilterRq): Page<Artwork> {
        val defaultOrder = Order.DESC
        val tags = filter.tags
        val contents = jpaQueryFactory
            .selectFrom(artwork)
            .where(
                // [1] 키워드 검색
                artwork.title.contains(filter.search),
                // [2] 날짜 검색
                uploadDateEq(filter.uploadDateType),
                // [3] 작품 분류 검색
                artworkTypeEq(filter.artworkType),
                // [4] 태그 검색
                *tags.map { tag ->
                    artwork.mutableTags.any().content.eq(tag)
                }.toTypedArray()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(
                getOrderSpecifier(filter.sortType, defaultOrder)
            )
            .fetch()

        val countQuery = jpaQueryFactory
            .selectFrom(artwork)
            .where(
                artwork.title.contains(filter.search),
                uploadDateEq(filter.uploadDateType),
                artworkTypeEq(filter.artworkType),
                *tags.map { tag ->
                    artwork.mutableTags.any().content.eq(tag)
                }.toTypedArray()
            )

        val totalSupplier = { countQuery.fetch().size.toLong() }

        return PageableExecutionUtils.getPage(contents, pageable, totalSupplier)
    }

    private fun artworkTypeEq(artworkType: ArtworkType?): BooleanExpression? {
        return artworkType?.let {
            return artwork.artworkType.eq(artworkType)
        }
    }

    private fun uploadDateEq(uploadDateType: UploadDateType?): BooleanExpression? {
        return uploadDateType?.let {
            when (uploadDateType) {
                UploadDateType.AN_HOUR_AGO -> artwork.createdDate.after(LocalDateTime.now().minusHours(1))
                UploadDateType.TODAY -> artwork.createdDate.after(LocalDateTime.now().minusDays(1))
                UploadDateType.THIS_WEEK -> artwork.createdDate.after(LocalDateTime.now().minusWeeks(1))
                UploadDateType.THIS_MONTH -> artwork.createdDate.after(LocalDateTime.now().minusMonths(1))
                UploadDateType.THIS_YEAR -> artwork.createdDate.after(LocalDateTime.now().minusYears(1))
            }
        }
    }

    private fun getOrderSpecifier(sortType: ArtworkSortType?, defaultOrder: Order): OrderSpecifier<*> {
        return sortType?.let {
            when (sortType) {
                ArtworkSortType.UPLOAD_DATE -> OrderSpecifier(Order.ASC, artwork.createdDate)
                ArtworkSortType.VIEWS -> OrderSpecifier(defaultOrder, artwork.view)
                ArtworkSortType.LIKES -> OrderSpecifier(defaultOrder, artwork.mutableBookmarks.size())
                ArtworkSortType.COMMENTS -> OrderSpecifier(defaultOrder, artwork.mutableComments.size())
            }
        } ?: OrderSpecifier(defaultOrder, artwork.createdDate)
    }

}
