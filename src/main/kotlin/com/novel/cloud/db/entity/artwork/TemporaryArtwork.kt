package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.common.BaseTimeEntity
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class TemporaryArtwork(
    content: String,
    writer: Member,
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;

    @Column(nullable = false, length = 10000)
    var content: String = content
        protected set;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set;


    fun updateContent(content: String) {
        this.content = content
    }

}