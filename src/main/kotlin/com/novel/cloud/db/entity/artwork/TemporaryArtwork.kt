package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.BaseEntity
import com.novel.cloud.db.entity.member.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class TemporaryArtwork(
    content: String,
    writer: Member,
) : BaseEntity() {

    @Column(nullable = false, length = 10000)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set


    fun updateContent(content: String) {
        this.content = content
    }

}