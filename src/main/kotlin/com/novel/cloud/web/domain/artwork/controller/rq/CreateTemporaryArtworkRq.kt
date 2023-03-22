package com.novel.cloud.web.domain.artwork.controller.rq

import javax.validation.constraints.NotEmpty

data class CreateTemporaryArtworkRq (

    @field:NotEmpty
    val content: String

)