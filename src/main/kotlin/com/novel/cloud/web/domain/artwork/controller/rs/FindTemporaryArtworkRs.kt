package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import java.time.LocalDateTime

data class FindTemporaryArtworkRs (
    val temporaryArtworkId: Long? = null,
    val createdDate: LocalDateTime? = null,
    val content: String? = null
){
    companion object {

        fun create(temporaryArtwork: TemporaryArtwork): FindTemporaryArtworkRs {
            return FindTemporaryArtworkRs(
                temporaryArtworkId = temporaryArtwork.id,
                createdDate = temporaryArtwork.createdDate,
                content = temporaryArtwork.content
            )
        }

    }

}