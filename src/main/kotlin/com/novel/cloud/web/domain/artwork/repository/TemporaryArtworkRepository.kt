package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TemporaryArtworkRepository: JpaRepository<TemporaryArtwork, Long> {
    fun findByWriter(writer: Member): Optional<TemporaryArtwork>

}