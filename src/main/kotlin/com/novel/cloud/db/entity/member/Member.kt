package com.novel.cloud.db.entity.member

import com.novel.cloud.db.entity.artwork.Artwork
import javax.persistence.*

@Entity
@Table(name = "`member`")
class Member (
    email: String,
    nickname: String,
    picture: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;
    @Column(nullable = false, unique = true)
    var email: String = email
        protected set;

    @Column(nullable = false)
    var nickname: String = nickname
        protected set;

    @Column(nullable = false)
    var picture: String = picture
        protected set;

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "writer")
    protected val mutableArtworks: MutableList<Artwork> = mutableListOf();
    val artworks: List<Artwork> get() = mutableArtworks.toList();

    fun writeArtwork(artwork: Artwork) {
        mutableArtworks.add(artwork);
    }

    fun copy(email: String = this.email,
             nickname: String = this.nickname,
             picture: String = this.picture): Member {
        return Member(email, nickname, picture);
    }

    fun update(picture: String = this.picture) {
        this.picture = picture;
    }

}