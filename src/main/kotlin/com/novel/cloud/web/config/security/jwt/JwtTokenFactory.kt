package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_ISSUER
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.utils.DateUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class JwtTokenFactory {

    @Value("\${spring.security.jwt.token-validity-in-seconds}")
    private val TOKEN_TIME_TO_LIVE: Long = 0

    fun generateJwtToken(member: Member): JwtTokenDto {
        val now = DateUtils.now()
        val expiredDate = DateUtils.addTime(now, TOKEN_TIME_TO_LIVE)
        val expiredLocalDateTime =
            LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault())
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtProperty.SIGN_KEY))
        val token: String = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return JwtTokenDto(
                token = token,
                validity = expiredLocalDateTime.toString()
            )
    }

    private fun createJwtClaims(member: Member): Map<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims[MEMBER_EMAIL] = member.email
        return claims
    }

    fun parseClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(JwtProperty.SIGN_KEY)
            .build()
            .parseClaimsJws(token)
            .body
    }

}