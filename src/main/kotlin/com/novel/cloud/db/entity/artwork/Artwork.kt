package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.attach_file.AttachFile
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.common.BaseTimeEntity
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Artwork(
    title: String,
    content: String,
    writer: Member,
    artworkType: ArtworkType
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;

    @Column(nullable = false)
    var title: String = title
        protected set;

    @Column(nullable = false, length = 65535)
    var content: String = content
        protected set;

    @Column(nullable = false)
    var view: Long = 0
        protected set;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var artworkType: ArtworkType = artworkType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set;

    @ElementCollection
    @CollectionTable(name = "tag")
    private val mutableTags: MutableList<Tag> = mutableListOf();
    val tags: List<Tag> get() = mutableTags.toList();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableComments: MutableList<Comment> = mutableListOf();
    val comments: List<Comment> get() = mutableComments.toList();

    @Column(length = 200, nullable = true)
    var thumbnail: String? = null;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableAttachFiles: MutableList<AttachFile> = mutableListOf();
    val attachFiles: List<AttachFile> get() = mutableAttachFiles.toList();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableBookmarks: MutableList<Bookmark> = mutableListOf();
    val bookmarks: List<Bookmark> get() = mutableBookmarks.toList();

    fun addTag(tag: Tag) {
        mutableTags.add(tag);
    }

    fun removeAllTag() {
        mutableTags.clear()
    }

    fun updateThumbnail(thumbnail: String) {
        this.thumbnail = thumbnail
    }

    fun addView() {
        this.view++
    }

    fun addComment(comment: Comment) {
        mutableComments.add(comment);
    }

    fun addAttachFile(attachFile: AttachFile) {
        mutableAttachFiles.add(attachFile);
    }

    fun addBookmark(bookmark: Bookmark) {
        mutableBookmarks.add(bookmark);
    }

    // artwork 생성시 작가의 작품 목록에 추가
    init {
        writer.writeArtwork(this);
    }

}