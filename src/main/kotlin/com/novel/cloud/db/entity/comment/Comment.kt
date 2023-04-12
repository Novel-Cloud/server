package com.novel.cloud.db.entity.comment

import com.novel.cloud.db.entity.BaseEntity
import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.converter.BooleanToYNConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany


@Entity
class Comment(
    content: String,
    writer: Member,
    artwork: Artwork,
    parent: Comment?,
) : BaseEntity() {

    @Column(length = 3000)
    var content: String = content
        protected set

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter::class)
    var deleted: Boolean = false
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var artwork: Artwork = artwork
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    val parent: Comment? = parent

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    val mutableChildren: MutableList<Comment> = mutableListOf()
    val children: List<Comment> get() = mutableChildren.toList()

    init {
        artwork.addComment(this)
    }

    fun addComment(comment: Comment) {
        mutableChildren.add(comment)
    }

    fun updateDeleted(deleted: Boolean) {
        this.deleted = deleted
    }

    fun updateContent(content: String) {
        this.content = content
    }

}


