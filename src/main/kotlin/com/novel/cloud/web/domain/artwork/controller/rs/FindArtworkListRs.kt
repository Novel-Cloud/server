package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import javax.xml.stream.events.Comment

data class FindArtworkListRs (

    val title: String,

    val content: String,

    val artworkType: ArtworkType,

    val tags: List<String>,

    val writer: Member,

    val comments: List<Comment>,


)