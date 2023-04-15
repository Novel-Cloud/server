package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.BaseEntity
import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.db.enums.ArtworkType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Artwork(
    title: String,
    content: String,
    writer: Member,
    artworkType: ArtworkType,
    tags: List<Tag>,
) : BaseEntity() {

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false, length = 10000)
    var content: String = content
        protected set

    @Column(nullable = false)
    var view: Long = 0
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var artworkType: ArtworkType = artworkType

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "artwork_tag_assoc",
        joinColumns = [JoinColumn(name = "artwork_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")],
    )
    protected val mutableTags: MutableList<Tag> = tags.toMutableList()
    val tags: List<Tag> get() = mutableTags.toList()

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableComments: MutableList<Comment> = mutableListOf()
    val comments: List<Comment> get() = mutableComments.toList()

    @Column(length = 200, nullable = true)
    var thumbnail: String? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableAttachFiles: MutableList<AttachFile> = mutableListOf()
    val attachFiles: List<AttachFile> get() = mutableAttachFiles.toList()

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableBookmarks: MutableList<Bookmark> = mutableListOf()
    val bookmarks: List<Bookmark> get() = mutableBookmarks.toList()

    init {
        writer.writeArtwork(this)
    }

    fun updateThumbnail(thumbnail: String) {
        this.thumbnail = thumbnail
    }

    fun addView() {
        this.view++
    }

    fun addComment(comment: Comment) {
        mutableComments.add(comment)
    }

    fun addAttachFile(attachFile: AttachFile) {
        mutableAttachFiles.add(attachFile)
    }

    fun addBookmark(bookmark: Bookmark) {
        mutableBookmarks.add(bookmark)
    }

}