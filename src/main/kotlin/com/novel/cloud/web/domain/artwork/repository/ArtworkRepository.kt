package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.Artwork
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtworkRepository : JpaRepository<Artwork, Long>, ArtworkRepositoryCustom {}