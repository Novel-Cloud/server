package com.novel.cloud.web.domain.member.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs

data class FindMemberRs(
    val memberId: Long? = null,
    val nickname: String? = null,
    val picture: String? = null,
    val artworks: List<FindArtworkRs>? = null,
) {
    companion object {

        fun create(member: Member?, myBookmarkedArtworkIdSet: Set<Long?>): FindMemberRs {
            val artworks = getArtworks(member, myBookmarkedArtworkIdSet)
            return FindMemberRs(
                memberId = member?.id,
                nickname = member?.nickname,
                picture = member?.picture,
                artworks = artworks
            )
        }

        private fun getArtworks(member: Member?, myBookmarkedArtworkIdSet: Set<Long?>): List<FindArtworkRs> {
            return member?.artworks?.map { artwork ->
                val bookmarkYn = getBookmarkYn(artwork, myBookmarkedArtworkIdSet)
                FindArtworkRs.create(artwork, bookmarkYn)
            }?.reversed() ?: emptyList()
        }

        private fun getBookmarkYn(artwork: Artwork, myBookmarkedArtworkIdSet: Set<Long?>): Boolean {
            return myBookmarkedArtworkIdSet.contains(artwork.id)
        }

    }
}