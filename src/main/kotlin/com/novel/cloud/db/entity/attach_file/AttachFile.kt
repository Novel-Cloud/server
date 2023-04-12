package com.novel.cloud.db.entity.attach_file

import com.novel.cloud.db.entity.BaseEntity
import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.enums.AttachFileType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class AttachFile (
    fileName: String,
    filePath: String,
    fileUidName: String,
    fileSize: Long,
    artwork: Artwork,
    attachFileType: AttachFileType
): BaseEntity() {

    @Column(length = 200, nullable = false)
    var fileName: String = fileName
        protected set

    @Column(length = 200, nullable = false)
    var fileUidName: String = fileUidName
        protected set

    @Column(length = 200, nullable = false)
    var filePath: String = filePath
        protected set

    @Column(nullable = false)
    var fileSize: Long = fileSize
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var artwork: Artwork = artwork
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var attachFileType: AttachFileType = attachFileType

    init {
        artwork.addAttachFile(this)
    }

}