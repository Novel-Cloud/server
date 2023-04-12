package com.novel.cloud.web.domain.abbreviation.controller.rq

import javax.validation.constraints.NotEmpty

data class UpdateAbbreviationSequenceRq (

    @field:NotEmpty
    val shortcutIdList: List<Long>

)