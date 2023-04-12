package com.novel.cloud.web.domain.bookmark.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BookmarkRepository : JpaRepository<Bookmark, Long> {

    fun findByMember(member: Member): List<Bookmark>
    fun findByMemberAndArtwork(member: Member, artwork: Artwork): Optional<Bookmark>

}