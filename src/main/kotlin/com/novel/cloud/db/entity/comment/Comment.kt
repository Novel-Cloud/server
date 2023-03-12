package com.novel.cloud.db.entity.comment

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.common.BaseTimeEntity
import com.novel.cloud.db.entity.member.Member
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Comment(
    content: String,
    writer: Member,
    artwork: Artwork
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

    init {
        artwork.addComment(this);
    }

}


