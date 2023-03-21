package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import org.springframework.data.jpa.repository.JpaRepository

interface TemporaryArtworkRepository: JpaRepository<TemporaryArtwork, Long> {}