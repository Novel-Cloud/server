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

        fun create(member: Member?): FindMemberSelfRs {
            return FindMemberSelfRs(
                memberId = member?.id,
                nickname = member?.nickname,
                picture = member?.picture,
                email = member?.email,
                artworks = getArtworks(member?.artworks)
            )
        }

        private fun getArtworks(artworks: List<Artwork>?): List<FindArtworkRs>? {
            return artworks?.map { artwork ->
                FindArtworkRs.create(artwork)
            }?.toList()
        }

    }
}