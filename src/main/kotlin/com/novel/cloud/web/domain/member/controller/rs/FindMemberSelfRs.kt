package com.novel.cloud.web.domain.member.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs

data class FindMemberSelfRs(
    val memberId: Long? = null,
    val nickname: String? = null,
    val picture: String? = null,
    val email: String? = null,
    val artworks: List<FindArtworkRs>? = null
){
    companion object {

        fun create(member: Member?, myBookmarkedArtworkIdSet: Set<Long?>): FindMemberSelfRs {
            val artworks = getArtworks(member?.artworks, myBookmarkedArtworkIdSet)
            return FindMemberSelfRs(
                memberId = member?.id,
                nickname = member?.nickname,
                picture = member?.picture,
                email = member?.email,
                artworks = artworks
            )
        }

        private fun getArtworks(artworks: List<Artwork>?, myBookmarkedArtworkIdSet: Set<Long?>): List<FindArtworkRs>? {
            return artworks?.map { artwork ->
                val bookmarkYn = getBookmarkYn(artwork, myBookmarkedArtworkIdSet)
                FindArtworkRs.create(artwork, bookmarkYn)
            }?.toList()
        }

        private fun getBookmarkYn(artwork: Artwork, myBookmarkedArtworkIdSet: Set<Long?>): Boolean {
            return myBookmarkedArtworkIdSet.contains(artwork.id)
        }

    }
}