package com.novel.cloud.web.domain.artwork.controller.rq

import javax.validation.constraints.NotEmpty

data class AutoSaveTemporaryArtworkRq (

    @field:NotEmpty
    val content: String

)