package com.novel.cloud.web.domain.abbreviation.controller.rq

import javax.validation.constraints.NotNull

data class CreateAbbreviationRq (

    @field:NotNull
    val content: String

)