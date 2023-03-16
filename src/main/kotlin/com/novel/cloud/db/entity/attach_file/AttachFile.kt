package com.novel.cloud.db.entity.attach_file

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

@Entity
class AttachFile (


): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

//    @Column(length = 3000)
//    var fileName: String = fileName
//        protected set;
//
//    @Column(length = 3000)
//    var fileName: String = fileName
//        protected set;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(nullable = false)
//    var writer: Member = writer
//        protected set;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(nullable = false)
//    var artwork: Artwork = artwork
//        protected set;
//
//    init {
//        artwork.addComment(this);
//    }

}