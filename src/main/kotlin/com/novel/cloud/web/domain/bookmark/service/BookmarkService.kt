package com.novel.cloud.web.domain.bookmark.service

import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.domain.bookmark.controller.rq.BookmarkArtworkRq
import com.novel.cloud.web.domain.bookmark.repository.BookmarkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookmarkService (
    private val findMemberService: FindMemberService,
    private val findArtworkService: FindArtworkService,
    private val findBookmarkService: FindBookmarkService,
    private val bookmarkRepository: BookmarkRepository
) {

    /**
     * 좋아요 토글
     */
    fun toggleArtworkBookmark(memberContext: MemberContext, rq: BookmarkArtworkRq) {
        val artworkId = rq.artworkId
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val artwork = findArtworkService.findByIdOrElseThrow(artworkId)

        val bookmark = findBookmarkService.findByMemberAndArtworkOrElseNull(member, artwork)

        // [1] 좋아요를 누른 경우 삭제
        bookmark?.let {
            bookmarkRepository.delete(bookmark)
            return
        }

        // [2] 좋아요를 누르지 않은 경우
        val newBookmark = Bookmark(
            member = member,
            artwork = artwork
        )
        bookmarkRepository.save(newBookmark)
    }

}