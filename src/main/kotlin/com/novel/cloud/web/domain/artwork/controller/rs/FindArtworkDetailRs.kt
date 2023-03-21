package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.dto.AttachFileDto
import com.novel.cloud.web.domain.dto.MemberDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FindArtworkDetailRs (
    val title: String? = null,
    val artworkType: ArtworkType? = null,
    val writer: MemberDto? = null,
    val tags: List<Tag>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFileDto>? = null,
    val createDates: String? = null
){
    companion object {
        fun create(artwork: Artwork): FindArtworkDetailRs {
            return FindArtworkDetailRs(
                title = artwork.title,
                artworkType = artwork.artworkType,
                writer = getWriter(artwork.writer),
                tags = artwork.tags,
                thumbnail = artwork.thumbnail,
                attachFiles = getAttachFiles(artwork.attachFiles),
                createDates = formatDate(artwork.createdDate)
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

        private fun formatDate(createdDate: LocalDateTime?): String? {
            return createdDate?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd."))
        }

    }

}