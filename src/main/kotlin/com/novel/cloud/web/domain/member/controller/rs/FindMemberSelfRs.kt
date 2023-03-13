package com.novel.cloud.web.domain.member.controller.rs

import com.novel.cloud.db.entity.member.Member

data class FindMemberSelfRs(
    val memberId: Long? = null,
    val nickname: String? = null,
    val picture: String? = null,
    val email: String? = null
){
    companion object {
        fun create(member: Member?): FindMemberSelfRs {
            return FindMemberSelfRs(
                memberId = member?.id,
                nickname = member?.nickname,
                picture = member?.picture,
                email = member?.email
            )
        }
    }
}