package com.novel.cloud.web.domain.dto

import com.novel.cloud.db.entity.tag.Tag

data class TagDto(
    val tagId: Long? = null,
    val content: String? = null,
) {
    companion object {
        fun create(tag: Tag): TagDto {
            return TagDto(
                tagId = tag.id,
                content = tag.content
            )
        }
    }
}