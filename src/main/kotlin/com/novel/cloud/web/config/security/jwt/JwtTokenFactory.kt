package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_ISSUER
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.utils.DateUtils
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@Component
@RequiredArgsConstructor
class JwtTokenFactory {

    @Value("\${spring.security.jwt.token-validity-in-seconds}")
    private val TOKEN_TIME_TO_LIVE: Long = 0

    fun generateJwtToken(member: Member): JwtTokenDto {
        val now: Date = DateUtils.now()
        val expiredDate: Date = DateUtils.addTime(now, TOKEN_TIME_TO_LIVE)
        val expiredLocalDateTime: LocalDateTime =
            LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault())
        val token: String = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, JwtProperty.SIGN_KEY)
            .compact()
        return JwtTokenDto(
                token = token,
                validity = expiredLocalDateTime.toString()
            )
    }

    private fun createJwtClaims(member: Member): Map<String, Any>? {
        val claims: MutableMap<String, Any> = HashMap()
        claims[MEMBER_EMAIL] = member.email
        return claims
    }


}