package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArtworkTagRepository: JpaRepository<Tag, Long> {

    fun findByContent(content: String): Optional<Tag>

}