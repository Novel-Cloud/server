package com.novel.cloud.db.entity.artwork

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Tag (

    @Column(name = "`value`", nullable = false)
    val value: String

)