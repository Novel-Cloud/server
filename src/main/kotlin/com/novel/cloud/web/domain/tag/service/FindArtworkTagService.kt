package com.novel.cloud.web.domain.tag.service

import com.novel.cloud.web.domain.tag.controller.rs.FindPopularTagRs
import com.novel.cloud.web.domain.tag.repository.ArtworkTagRepository
import com.novel.cloud.web.endpoint.ListResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindArtworkTagService (
    val artworkTagRepository: ArtworkTagRepository,
){

//    fun findPopularTags(): ListResponse<FindPopularTagRs> {
//        return artworkTagRepository.
//    }

}