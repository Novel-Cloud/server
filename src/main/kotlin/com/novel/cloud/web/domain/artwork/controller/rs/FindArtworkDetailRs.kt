package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.dto.AttachFileDto
import com.novel.cloud.web.domain.dto.MemberDto
import com.novel.cloud.web.domain.dto.TagDto
import com.novel.cloud.web.utils.DateUtils

data class FindArtworkDetailRs (
    val artworkId: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val view: Long? = null,
    val artworkType: ArtworkType? = null,
    val writer: MemberDto? = null,
    val likes: Int? = null,
    val likeYn: Boolean? = null,
    val tags: List<TagDto>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFileDto>? = null,
    val createDate: String? = null
){
    companion object {

        fun create(artwork: Artwork, bookmarkYn: Boolean): FindArtworkDetailRs {
            val writer = getWriter(artwork.writer)
            val attachFiles = getAttachFiles(artwork.attachFiles)
            val likes = getLikes(artwork)
            val tags = getTags(artwork.tags)
            val formattedDate = DateUtils.formatDateYYYYMMDD(artwork.createdDate) ?: ""
            return FindArtworkDetailRs(
                artworkId = artwork.id,
                title = artwork.title,
                content = artwork.content,
                view = artwork.view,
                artworkType = artwork.artworkType,
                writer = writer,
                likes = likes,
                likeYn = bookmarkYn,
                tags = tags,
                thumbnail = artwork.thumbnail,
                attachFiles = attachFiles,
                createDate = formattedDate
            )
        }

        private fun getWriter(member: Member): MemberDto {
            return MemberDto.create(member)
        }

        private fun getLikes(artwork: Artwork): Int {
            return artwork.bookmarks.size
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