package com.novel.cloud.web.domain.tag.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.web.domain.tag.repository.ArtworkTagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkTagService(
    private val artworkTagRepository: ArtworkTagRepository,
    private val findArtworkTagService: FindArtworkTagService,
) {

    /**
     * 태그 생성
     */
    fun createTags(member: Member, tags: Set<String>): Set<Tag> {
        return tags.map { content ->
            // 없으면 태그 생성
            val tag = findArtworkTagService.findByContentOrElseNull(content) ?: Tag(
                content = content,
                writer = member
            )
            artworkTagRepository.save(tag)
            tag.updateUsageCount()
            tag
        }.toSet()
    }

}