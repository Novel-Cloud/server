package com.novel.cloud.web.config.security.detail

import com.novel.cloud.db.entity.member.Member
import java.io.Serializable

data class SessionUser(
    private val member: Member
): Serializable {
    val nickname = member.nickname;
    val email = member.email;
    val picture  = member.picture;
}
