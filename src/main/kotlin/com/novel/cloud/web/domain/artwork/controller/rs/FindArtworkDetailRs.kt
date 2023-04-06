package com.novel.cloud.web.domain.artwork.controller.rs

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.domain.dto.AttachFileDto
import com.novel.cloud.web.domain.dto.MemberDto
import com.novel.cloud.web.utils.DateUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FindArtworkDetailRs (
    // TODO:: COMMENT 기능 추가하면 프로퍼티 추가
    val title: String? = null,
    val content: String? = null,
    val view: Long? = null,
    val artworkType: ArtworkType? = null,
    val writer: MemberDto? = null,
    val tags: List<Tag>? = null,
    val thumbnail: String? = null,
    val attachFiles: List<AttachFileDto>? = null,
    val createDates: String? = null
){
    companion object {
        fun create(artwork: Artwork): FindArtworkDetailRs {
            val writer = getWriter(artwork.writer)
            val attachFiles = getAttachFiles(artwork.attachFiles)

            return FindArtworkDetailRs(
                title = artwork.title,
                content = artwork.content,
                view = artwork.view,
                artworkType = artwork.artworkType,
                writer = writer,
                tags = artwork.tags,
                thumbnail = artwork.thumbnail,
                attachFiles = attachFiles,
                createDates = DateUtils.formatDateYYYYMMDD(artwork.createdDate)

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