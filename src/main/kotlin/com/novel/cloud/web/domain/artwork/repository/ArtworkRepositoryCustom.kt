package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.web.domain.artwork.controller.rq.SearchArtworkFilterRq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArtworkRepositoryCustom {
    fun findArtworkList(pageable: Pageable): Page<Artwork>
    fun findArtworkListByTag(pageable: Pageable, tags: List<String>): Page<Artwork>
    fun findArtworkListByFilter(pageable: Pageable, filter: SearchArtworkFilterRq): Page<Artwork>
}