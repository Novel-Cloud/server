package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.dto.AttachFileDto
import com.novel.cloud.web.domain.dto.MemberDto
import com.novel.cloud.web.domain.dto.TagDto

data class FindArtworkRs (
    val artworkId: Long? = null,
    val title: String? = null,
    val artworkType: ArtworkType? = null,
    val writer: MemberDto? = null,
    val likeYn: Boolean? = null,
    val tags: List<TagDto>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFileDto>? = null,
){
    companion object {

        fun create(artwork: Artwork, bookmarkYn: Boolean): FindArtworkRs {
            val writer = getWriter(artwork.writer)
            val tags = getTags(artwork.tags)
            val attachFiles = getAttachFiles(artwork.attachFiles)
            return FindArtworkRs(
                artworkId = artwork.id,
                title = artwork.title,
                artworkType = artwork.artworkType,
                writer = writer,
                likeYn = bookmarkYn,
                tags = tags,
                thumbnail = artwork.thumbnail,
                attachFiles = attachFiles
            )
        }

        private fun getWriter(member: Member): MemberDto {
            return MemberDto.create(member)
        }

        private fun getTags(tags: Set<Tag>): List<TagDto> {
            return tags.map {tag ->
                TagDto.create(tag)
            }.toList()
        }

        private fun getAttachFiles(attachFiles: List<AttachFile>): List<AttachFileDto> {
            return attachFiles.map { attachFile ->
                AttachFileDto.create(attachFile)
            }.toList()
        }

    }

}