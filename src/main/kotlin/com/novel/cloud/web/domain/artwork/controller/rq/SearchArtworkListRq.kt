package com.novel.cloud.web.domain.artwork.controller.rq

import javax.validation.constraints.NotNull

data class SearchArtworkListRq (

    @field:NotNull
    val pagination: PaginationRq = PaginationRq(),

    @field:NotNull
    val filter: SearchArtworkFilterRq = SearchArtworkFilterRq()

)