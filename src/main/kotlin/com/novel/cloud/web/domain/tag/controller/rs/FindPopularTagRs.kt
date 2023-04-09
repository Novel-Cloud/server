package com.novel.cloud.web.domain.tag.controller.rs

import com.novel.cloud.db.entity.tag.Tag

data class FindPopularTagRs (
    val tagId: Long? = null,
    val content: String? = null,
) {

    companion object {
        fun create(tag: Tag): FindPopularTagRs {
            return FindPopularTagRs(
                tagId = tag.id,
                content = tag.content
            )
        }

    }

}
