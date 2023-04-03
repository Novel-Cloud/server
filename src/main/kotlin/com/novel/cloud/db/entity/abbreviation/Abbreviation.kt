package com.novel.cloud.db.entity.abbreviation

import com.novel.cloud.db.entity.common.BaseTimeEntity
import com.novel.cloud.db.entity.member.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Abbreviation (
    content: String,
    sequence: Int,
    writer: Member,
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, length = 100)
    var content: String = content
        protected set

    @Column(nullable = false)
    var sequence: Int? = sequence

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set

    fun updateName(content: String) {
        this.content = content
    }

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

}