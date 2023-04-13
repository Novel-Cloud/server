package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.web.config.security.jwt.JwtTokenFactory
import com.novel.cloud.web.domain.auth.controller.rq.RefreshAccessTokenRq
import com.novel.cloud.web.domain.auth.controller.rs.AccessTokenRs
import com.novel.cloud.web.domain.auth.repository.RefreshTokenRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.NotFoundMemberException
import com.novel.cloud.web.exception.NotFoundRefreshTokenException
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val findMemberService: FindMemberService,
    private val jwtTokenFactory: JwtTokenFactory,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun refreshAccessToken(rq: RefreshAccessTokenRq): AccessTokenRs {
        val refreshToken = refreshTokenRepository.findByRefreshToken(rq.refreshToken)
            ?: throw NotFoundRefreshTokenException()
        val member = findMemberService.findById(refreshToken.memberId)
            ?: throw NotFoundMemberException()
        return jwtTokenFactory.refreshAccessToken(member)
    }

}