package com.novel.cloud.web.domain.artwork.controller.rq

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotEmpty

data class UpdateTemporaryArtworkRq (

    @field:NotNull
    val temporaryArtworkId: Long,

    @field:NotEmpty
    val content: String

)