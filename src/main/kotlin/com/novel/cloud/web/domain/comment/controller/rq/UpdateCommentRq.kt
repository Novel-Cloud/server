package com.novel.cloud.web.domain.comment.controller.rq

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UpdateCommentRq (

    @field:NotNull
    val commentId: Long,

    @field:NotEmpty
    val content: String

)