package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.dto.AttachFileDto
import com.novel.cloud.web.domain.dto.MemberDto

data class FindArtworkRs (
    val artworkId: Long? = null,
    val title: String? = null,
    val artworkType: ArtworkType? = null,
    val writer: MemberDto? = null,
    val likeYn: Boolean? = null,
    val tags: List<Tag>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFileDto>? = null,
){
    companion object {

        fun create(artwork: Artwork, bookmarkYn: Boolean): FindArtworkRs {
            val writer = getWriter(artwork.writer)
            return FindArtworkRs(
                artworkId = artwork.id,
                title = artwork.title,
                artworkType = artwork.artworkType,
                writer = writer,
                likeYn = bookmarkYn,
                tags = artwork.tags,
                thumbnail = artwork.thumbnail,
                attachFiles = getAttachFiles(artwork.attachFiles)
            )
        }

        private fun getWriter(member: Member): MemberDto {
            return MemberDto.create(member)
        }

        private fun getAttachFiles(attachFiles: List<AttachFile>): List<AttachFileDto> {
            return attachFiles.map { attachFile ->
                AttachFileDto.create(attachFile)
            }.toList()
        }

    }

}