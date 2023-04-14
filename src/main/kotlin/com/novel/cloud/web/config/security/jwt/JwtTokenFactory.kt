package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.redis.RefreshToken
import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_ISSUER
import com.novel.cloud.web.config.security.jwt.JwtProperty.MEMBER_EMAIL
import com.novel.cloud.web.domain.auth.controller.rs.AccessTokenRs
import com.novel.cloud.web.domain.auth.controller.rs.RefreshTokenRs
import com.novel.cloud.web.domain.auth.repository.RefreshTokenRepository
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.utils.DateUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class JwtTokenFactory(
    private val refreshTokenRepository: RefreshTokenRepository,
    jwtSecretProperty: JwtSecretProperty,
) {

    val SIGN_KEY = jwtSecretProperty.secret
    val REFRESH_TOKEN_VALIDITY_IN_SECONDS = jwtSecretProperty.refreshTokenValidityInSeconds
    val ACCESS_TOKEN_VALIDITY_IN_SECONDS = jwtSecretProperty.accessTokenValidityInSeconds

    fun generateJwtToken(member: Member): JwtTokenDto {
        val accessToken = generateAccessToken(member)
        val refreshToken = generateRefreshToken(member)

        return JwtTokenDto(
            accessToken = accessToken.accessToken,
            refreshToken = refreshToken.refreshToken,
            accessTokenValidity = accessToken.validity,
            refreshTokenValidity = refreshToken.validity
        )
    }

    /**
     * 엑세스 토큰 발급
     */
    fun generateAccessToken(member: Member): AccessTokenRs {
        val now = DateUtils.now()
        val expiredDate = DateUtils.addTime(now, ACCESS_TOKEN_VALIDITY_IN_SECONDS)
        val expiredLocalDateTime =
            LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault())
        val accessToken: String = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
            .compact()

        return AccessTokenRs(
            accessToken = accessToken,
            validity = expiredLocalDateTime.toString()
        )
    }

    /**
     * 리프레시 토큰 발급
     */
    fun generateRefreshToken(member: Member): RefreshTokenRs {
        val now = DateUtils.now()
        val refreshExpiredDate = DateUtils.addTime(now, REFRESH_TOKEN_VALIDITY_IN_SECONDS)
        val refreshTokenExpiredLocalDateTime =
            LocalDateTime.ofInstant(refreshExpiredDate.toInstant(), ZoneId.systemDefault())

        val refreshToken: String = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
            .setIssuedAt(now)
            .setExpiration(refreshExpiredDate)
            .compact()

        // Redis 저장
        val redisRefreshToken = RefreshToken(
            refreshToken = refreshToken,
            memberId = member.id!!
        )

        refreshTokenRepository.save(redisRefreshToken)

        return RefreshTokenRs(
            refreshToken = refreshToken,
            validity = refreshTokenExpiredLocalDateTime.toString()
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
            .setSigningKey(SIGN_KEY)
            .parseClaimsJws(token)
            .body
    }

}