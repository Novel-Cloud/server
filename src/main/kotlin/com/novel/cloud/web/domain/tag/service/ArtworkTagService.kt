package com.novel.cloud.web.domain.tag.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.web.domain.tag.repository.ArtworkTagRepository
import com.novel.cloud.web.exception.NotFoundTagException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkTagService (
    private val artworkTagRepository: ArtworkTagRepository
) {

    fun createTags(member: Member, tags: Set<String>): Set<Tag> {
        return tags.map { content ->
            // 이미 있는 태그일 경우 참조만
            val tag = findByContentOrElseNull(content) ?: Tag(
                content = content,
                writer = member
            )
            artworkTagRepository.save(tag)
            tag
        }.toSet()
    }

    private fun findByContentOrElseNull(content: String): Tag? {
        return artworkTagRepository.findByContent(content)
            .orElse(null)
    }

    fun findTagByIdOrThrow(tagId: Long): Tag {
        return artworkTagRepository.findById(tagId)
            .orElseThrow { NotFoundTagException() }
    }

}