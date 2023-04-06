package com.novel.cloud.web.domain.comment.controller.rq

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateCommentRq (

    @field:NotNull
    val artworkId: Long,

    @field:NotEmpty
    val content: String,

    val parentId: Long?

)