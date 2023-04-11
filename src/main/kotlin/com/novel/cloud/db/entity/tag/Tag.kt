package com.novel.cloud.db.entity.tag

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.member.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "tag_content_uk",
        columnNames = ["`content`"]
    )]
)
class Tag (
    content: String,
    writer: Member,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, length = 100)
    var content: String = content
        protected set

    @Column(nullable = false)
    var usageCount: Long = 0
        protected set;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set

    fun updateUsageCount() {
        usageCount++
    }

}