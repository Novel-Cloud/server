package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface ArtworkTagRepository: JpaRepository<Tag, Long> {}