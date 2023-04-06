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
    fun toggleArtworkBookmark(memberContext: MemberContext, rq: BookmarkArtworkRq) {
        val artworkId = rq.artworkId
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val artwork = findArtworkService.findByIdOrElseThrow(artworkId)

        val bookmark: Bookmark? = findBookmarkService.findByMemberAndArtworkOrElseNull(member, artwork)
        bookmark?.let {
            bookmarkRepository.delete(bookmark)
            return
        }
        val newBookmark = Bookmark(
            member = member,
            artwork = artwork
        )
        bookmarkRepository.save(newBookmark)
    }

}