package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.web.domain.auth.controller.rq.OAuthRq
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.exception.AuthenticationException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class OAuth2Service(
    private val oAuth2GoogleService: OAuth2GoogleService
) {

    fun loginOAuth2(rq: OAuthRq): JwtTokenDto {
        val code: String = rq.code;
        try {
            return oAuth2GoogleService.getToken(code)
        } catch (e: Exception) {
            throw AuthenticationException()
        }
    }


}