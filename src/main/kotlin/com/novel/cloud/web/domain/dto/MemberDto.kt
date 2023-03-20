package com.novel.cloud.web.domain.dto

import com.novel.cloud.db.entity.member.Member

data class MemberDto(
    val memberId: Long? = null,
    val nickname: String? = null,
    val picture: String? = null,
    val email: String? = null
){
    companion object {
        fun create(member: Member?): MemberDto {
            return MemberDto(
                memberId = member?.id,
                nickname = member?.nickname,
                picture = member?.picture,
                email = member?.email
            )
        }
    }
}