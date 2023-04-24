package com.novel.cloud.web.domain.artwork.controller.rq

import com.novel.cloud.db.enums.ArtworkType
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UpdateArtworkRq (

    @field:NotNull
    val artworkId: Long,

    @field:NotEmpty
    val title: String,

    @field:NotEmpty
    val content: String,

    @field:NotNull
    val artworkType: ArtworkType,

    @field:NotNull
    val tags: List<String>

)