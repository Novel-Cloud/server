package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArtworkRepositoryCustom {

    fun findArtworkList(pageable: Pageable): Page<Artwork>

}