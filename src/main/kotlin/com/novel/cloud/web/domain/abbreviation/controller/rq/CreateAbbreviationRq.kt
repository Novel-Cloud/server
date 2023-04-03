package com.novel.cloud.web.domain.abbreviation.controller.rq

import javax.validation.constraints.NotEmpty

data class CreateAbbreviationRq (

    @field:NotEmpty
    val content: String

)