package com.novel.cloud.web.domain.comment.controller.rq

import javax.validation.constraints.NotNull

data class DeleteCommentRq (

    @field:NotNull
    val commentId: Long,

)