package com.novel.cloud.web.domain.artwork.controller.rq

import javax.validation.constraints.NotNull

data class DeleteArtworkRq (

    @field:NotNull
    val artworkId: Long

)