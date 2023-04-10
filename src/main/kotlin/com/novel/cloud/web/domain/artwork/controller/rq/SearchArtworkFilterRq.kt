package com.novel.cloud.web.domain.artwork.controller.rq

import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.enums.ArtworkSortType
import com.novel.cloud.web.domain.enums.UploadDateType
import javax.validation.constraints.NotNull

class SearchArtworkFilterRq (

    @field:NotNull
    val search: String? = null,

    val uploadDateType: UploadDateType? = null,

    val artworkType: ArtworkType? = null,

    val sortType: ArtworkSortType? = null

)