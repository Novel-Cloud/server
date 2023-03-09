package com.novel.cloud.db.entity.member

import com.novel.cloud.db.entity.artwork.Artwork
import javax.persistence.*

@Entity
@Table(name = "`user`")
open class Member (
    email: String,
    nickname: String,

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;
    @Column(nullable = false, unique = true)
    var email: String = email
        protected set;

    @Column()
    var nickname: String = nickname
        protected set;

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "writer")
    protected val mutableArtworks: MutableList<Artwork> = mutableListOf();
    val boards: List<Artwork> get() = mutableArtworks.toList();

}