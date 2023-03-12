package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.domain.dto.JwtTokenDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component


@Component
@RequiredArgsConstructor
class JwtTokenFactory {
    fun generateJwtToken(member: Member?): JwtTokenDto? {
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
        return JwtTokenDto.builder()
            .token(token)
            .validity(expiredLocalDateTime.toString())
            .build()
    }

}