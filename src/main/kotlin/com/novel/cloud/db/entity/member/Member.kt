package com.novel.cloud.db.entity.member

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.db.entity.artwork.Artwork
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table


@Entity
@Table(name = "`member`")
class Member (
    email: String,
    nickname: String,
    picture: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(nullable = false)
    var nickname: String = nickname
        protected set

    @Column(nullable = false)
    var picture: String = picture
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "writer")
    protected val mutableArtworks: MutableList<Artwork> = mutableListOf();
    val artworks: List<Artwork> get() = mutableArtworks.toList()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "writer")
    protected val mutableAbbreviation: MutableList<Abbreviation> = mutableListOf();
    val abbreviations: List<Abbreviation> get() = mutableAbbreviation.toList()

    fun writeArtwork(artwork: Artwork) {
        mutableArtworks.add(artwork)
    }

    fun copy(email: String = this.email,
             nickname: String = this.nickname,
             picture: String = this.picture): Member {
        return Member(email, nickname, picture)
    }

    fun updatePicture(picture: String = this.picture) {
        this.picture = picture
    }

    fun updateNickname(nickname: String = this.nickname) {
        this.nickname = nickname
    }

}