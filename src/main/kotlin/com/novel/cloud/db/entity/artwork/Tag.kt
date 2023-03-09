package com.novel.cloud.db.entity.artwork

import javax.persistence.*
@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "tag_key_value_uk", columnNames = ["`key`", "`value`"])])
open class Tag (
    key: String,
    value: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;

    @Column(name = "`key`", nullable = false)
    var key: String = key
        protected set;

    @Column(name = "`value`", nullable = false)
    var value: String = value
        protected set;

}