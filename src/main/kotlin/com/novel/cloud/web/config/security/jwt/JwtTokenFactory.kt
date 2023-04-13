package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.redis.RedisRefreshToken
import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_ISSUER
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.domain.auth.controller.rs.AccessTokenRs
import com.novel.cloud.web.domain.auth.repository.RefreshTokenRepository
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.utils.DateUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class JwtTokenFactory(
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    @Value("\${spring.security.jwt.token-validity-in-seconds}")
    private val TOKEN_TIME_TO_LIVE: Long = 0

    @Value("\${spring.security.jwt.refresh-token-validity-in-seconds}")
    private val REFRESH_TOKEN_TIME_TO_LIVE: Long = 0

    fun generateJwtToken(member: Member): JwtTokenDto {
        val now = DateUtils.now()
        val expiredDate = DateUtils.addTime(now, TOKEN_TIME_TO_LIVE)
        val refreshExpiredDate = DateUtils.addTime(now, REFRESH_TOKEN_TIME_TO_LIVE)
        val expiredLocalDateTime =
            LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault())
        val refreshExpiredLocalDateTime = LocalDateTime.ofInstant(refreshExpiredDate.toInstant(), ZoneId.systemDefault())

        val token: String = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, JwtProperty.SIGN_KEY)
            .compact()

        val refreshToken: String = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, JwtProperty.SIGN_KEY)
            .setIssuedAt(now)
            .setExpiration(refreshExpiredDate)
            .compact()

        val redisRefreshToken = RedisRefreshToken(
            refreshToken = refreshToken,
            memberId = member.id ?: 0
        )

        refreshTokenRepository.save(redisRefreshToken)

        return JwtTokenDto(
            accessToken = token,
            refreshToken = redisRefreshToken.refreshToken,
            accessTokenValidity = expiredLocalDateTime.toString(),
            refreshTokenValidity = refreshExpiredLocalDateTime.toString()
        )
    }

    fun refreshAccessToken(member: Member): AccessTokenRs {
        val now = DateUtils.now()
        val expiredDate = DateUtils.addTime(now, TOKEN_TIME_TO_LIVE)
        val expiredLocalDateTime =
            LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault())
        val token: String = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, JwtProperty.SIGN_KEY)
            .compact()

        return AccessTokenRs(
            accessToken = token,
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
            .parser()
            .setSigningKey(JwtProperty.SIGN_KEY)
            .parseClaimsJws(token)
            .body
    }

}