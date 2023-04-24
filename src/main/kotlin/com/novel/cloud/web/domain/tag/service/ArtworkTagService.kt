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
    fun createTags(member: Member, tags: List<String>): List<Tag> {
        return tags.map { content ->
            val tag = findArtworkTagService.findByContentOrElseNull(content) ?: Tag(
                content = content,
                writer = member
            )
            artworkTagRepository.save(tag)
            tag.plusUsageCount()
            tag
        }.toList()
    }

    fun removeTags(member: Member, beforeTags: List<Tag>) {
        beforeTags.map { tag ->
            if (tag.usageCount == 1L) {
                artworkTagRepository.delete(tag)
            }
            if (tag.usageCount > 1L) {
                tag.minusUsageCount()
            }
        }
    }

}