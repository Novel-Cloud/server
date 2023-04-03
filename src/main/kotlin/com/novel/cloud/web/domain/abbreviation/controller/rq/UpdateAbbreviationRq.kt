package com.novel.cloud.web.domain.abbreviation.controller.rq

import javax.validation.constraints.NotNull

data class UpdateAbbreviationRq (

    @field:NotNull
    val shortcutId: Long

)