package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.exception.AuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class JwtProvider(
    private val jwtTokenFactory: JwtTokenFactory
) {
    @Throws(AuthenticationException::class)
    fun authenticate(token: String): Authentication {
        val claims = jwtTokenFactory.parseClaims(token)
        val email = claims.get(MEMBER_EMAIL, String::class.java)
        // 다른 OAuth 서비스 추가되면 사용
        // val registrationId: String = claims.get(REGISTRATION_ID, String::class.java)
        val memberContext = MemberContext.create(email)
        return UsernamePasswordAuthenticationToken(memberContext, null, null)
    }

}