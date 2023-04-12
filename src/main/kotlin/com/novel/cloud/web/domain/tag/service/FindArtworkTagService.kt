package com.novel.cloud.web.domain.tag.service

import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.web.domain.tag.controller.rs.FindPopularTagRs
import com.novel.cloud.web.domain.tag.repository.ArtworkTagRepository
import com.novel.cloud.web.endpoint.ListResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindArtworkTagService(
    val artworkTagRepository: ArtworkTagRepository,
) {

    fun findByContentOrElseNull(content: String): Tag? {
        return artworkTagRepository.findByContent(content)
            .orElse(null)
    }

    fun findPopularTags(): ListResponse<FindPopularTagRs> {
        val tags = artworkTagRepository.findAllByOrderByUsageCountDesc()

        val tagList = tags.map { tag ->
            FindPopularTagRs.create(tag)
        }.toList()

        return ListResponse(
            list = tagList
        )
    }

}