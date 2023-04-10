package com.novel.cloud.web.domain.artwork.controller.rq

import javax.validation.constraints.NotNull

class PaginationRq (

    @field:NotNull
    val page: Int = 0,

    @field:NotNull
    val size: Int = 0,

)