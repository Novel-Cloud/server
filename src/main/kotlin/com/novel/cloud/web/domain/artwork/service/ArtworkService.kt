package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkService (
    private val artworkRepository: ArtworkRepository
) {



}