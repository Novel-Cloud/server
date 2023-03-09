package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.comment.Comment
import com.novel.cloud.db.entity.common.BaseTimeEntity
import com.novel.cloud.db.entity.member.Member
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
open class Artwork(
    title: String,
    content: String,
    writer: Member,
    tags: Set<Tag>,
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;

    @Column(nullable = false)
    var title: String = title
        protected set;

    @Column(nullable = false, length = 3000)
    var content: String = content
        protected set;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: Member = writer
        protected set;

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "board_tag_assoc",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")],
    )
    protected val mutableTags: MutableSet<Tag> = tags.toMutableSet();
    val tags: Set<Tag> get() = mutableTags.toSet();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private val mutableComments: MutableList<Comment> = mutableListOf();
    val comments: List<Comment> get() = mutableComments.toList();



}