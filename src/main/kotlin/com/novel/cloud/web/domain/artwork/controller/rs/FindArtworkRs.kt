package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType

data class FindArtworkRs (
    val title: String? = null,
    val content: String? = null,
    val view: Long? = null,
    val artworkType: ArtworkType? = null,
    val writer: Member? = null,
    val tags: List<Tag>? = null,
    val comments: List<Comment>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFile>? = null,
){
    companion object {
        fun create(artwork: Artwork): FindArtworkRs {
            return FindArtworkRs(
                title = artwork.title,
                content = artwork.content,
                view = artwork.view,
                artworkType = artwork.artworkType,
                writer = artwork.writer,
                tags = artwork.tags,
                comments = artwork.comments,
                thumbnail = artwork.thumbnail,
                attachFiles = artwork.attachFiles
            )
        }
    }

}