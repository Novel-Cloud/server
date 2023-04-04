package com.novel.cloud.web.domain.abbreviation.controller.rs

import com.novel.cloud.db.entity.abbreviation.Abbreviation

data class FindSequenceAbbreviationRs (
    val id: Long? = null,
    val content: String? = null,
){
    companion object {
        fun create(abbreviation: Abbreviation): FindSequenceAbbreviationRs {
            return FindSequenceAbbreviationRs(
                id = abbreviation.id,
                content = abbreviation.content
            )
        }

    }

}