package com.novel.cloud.web.domain.abbreviation.controller.rq

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UpdateAbbreviationRq (

    @field:NotNull
    val shortcutId: Long,

    @field:NotEmpty
    val content: String

)