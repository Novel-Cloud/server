package com.novel.cloud.web.domain.bookmark.controller.rq

import javax.validation.constraints.NotNull

data class BookmarkArtworkRq (

    @field:NotNull
    val artworkId: Long,

)