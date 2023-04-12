package com.novel.cloud.db.entity.abbreviation

import com.novel.cloud.db.entity.BaseEntity
import com.novel.cloud.db.entity.member.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Abbreviation(
    content: String,
    sequence: Int,
    writer: Member,
) : BaseEntity() {

    @Column(nullable = false, length = 100)
    var content: String = content
        protected set

    @Column(nullable = false)
    var sequence: Int? = sequence

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

}