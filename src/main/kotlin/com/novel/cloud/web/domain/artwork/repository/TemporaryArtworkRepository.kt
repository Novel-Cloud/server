package com.novel.cloud.web.domain.artwork.repository

import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TemporaryArtworkRepository : JpaRepository<TemporaryArtwork, Long> {
    fun findByWriter(writer: Member): TemporaryArtwork?

}