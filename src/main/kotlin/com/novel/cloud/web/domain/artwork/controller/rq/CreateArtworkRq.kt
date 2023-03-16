package com.novel.cloud.web.domain.artwork.controller.rq

import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.enums.ArtworkType
import java.net.ContentHandler
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateArtworkRq (

    @field:NotEmpty
    val title: String,

    @field:NotEmpty
    val content: String,

    @field:NotNull
    val artworkType: ArtworkType,

    @field:NotNull
    val tags: List<String>,

    @field:NotNull
    val attachFileUids: List<String>

)