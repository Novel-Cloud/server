package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.config.security.jwt.JwtTokenFactory
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.domain.member.service.FindMemberService
import net.bytebuddy.asm.MemberSubstitution
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val findMemberService: FindMemberService,
    private val jwtTokenFactory: JwtTokenFactory
) {

    fun refreshToken(memberContext: MemberContext?): JwtTokenDto? {
        val member = memberContext?.let {
            findMemberService.findLoginMember(it)
        } ?: return null
        return jwtTokenFactory.generateJwtToken(member);
    }

}