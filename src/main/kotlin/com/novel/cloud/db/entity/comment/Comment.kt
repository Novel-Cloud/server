package com.novel.cloud.db.entity.comment

import com.novel.cloud.db.entity.artwork.Artwork
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
import javax.persistence.OneToMany


@Entity
class Comment(
    content: String,
    writer: Member,
    artwork: Artwork,
    parent: Comment?
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    @Column(length = 3000)
    var content: String = content
        protected set;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var artwork: Artwork = artwork
        protected set;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private val parent: Comment? = parent

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private val mutableChildren: MutableList<Comment> = mutableListOf();
    val children: List<Comment> get() = mutableChildren.toList();

    init {
        artwork.addComment(this);
    }

}


