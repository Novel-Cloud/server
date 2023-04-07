package com.novel.cloud.web.domain.bookmark.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.bookmark.repository.BookmarkRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindBookmarkService (
    private val bookmarkRepository: BookmarkRepository
) {
    fun findByMemberAndArtworkOrElseNull(member: Member, artwork: Artwork): Bookmark? {
        return bookmarkRepository.findByMemberAndArtwork(member, artwork)
            .orElse(null)
    }

    fun findByMember(member: Member): List<Bookmark> {
        return bookmarkRepository.findByMember(member)
    }


}