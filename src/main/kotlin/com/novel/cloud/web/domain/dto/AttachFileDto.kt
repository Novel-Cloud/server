package com.novel.cloud.web.domain.dto

import com.novel.cloud.db.entity.attach_file.AttachFile

data class AttachFileDto (
    val attachFileId: Long? = null,
    val fileName: String? = null,
    val filePath: String? = null,
    val fileSize: Long? = null
) {
    companion object {
        fun create(attachFile: AttachFile?): AttachFileDto {
            return AttachFileDto(
                attachFileId = attachFile?.id,
                fileName = attachFile?.fileName,
                filePath = attachFile?.filePath,
                fileSize = attachFile?.fileSize
            )
        }

    }
}