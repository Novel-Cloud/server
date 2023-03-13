package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.exception.AuthenticationException
import io.jsonwebtoken.Claims
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REGISTRATION_ID
import org.springframework.stereotype.Component


@Component
@RequiredArgsConstructor
class JwtProvider(
    private val jwtTokenFactory: JwtTokenFactory
) {
    @Throws(AuthenticationException::class)
    fun authenticate(token: String): Authentication {
        val claims: Claims = jwtTokenFactory.parseClaims(token)
        val email: String = claims.get(MEMBER_EMAIL, String::class.java)
        // TODO::일단 보류 나중에 다른 OAuth 서비스 추가되면 사용
//        val registrationId: String = claims.get(REGISTRATION_ID, String::class.java)
        val memberContext: MemberContext = MemberContext.create(email)
        return UsernamePasswordAuthenticationToken(memberContext, null, null)
    }

}