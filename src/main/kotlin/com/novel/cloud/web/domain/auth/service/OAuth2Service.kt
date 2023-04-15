package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.web.domain.auth.controller.rq.OAuthRq
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.exception.AuthenticationException
import com.novel.cloud.web.exception.HttpRequestFailedException
import org.json.simple.parser.ParseException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import javax.transaction.Transactional

@Service
@Transactional
class OAuth2Service(
    private val oAuth2GoogleService: OAuth2GoogleService,
) {

    fun loginOAuth2(rq: OAuthRq): JwtTokenDto {
        val code = rq.code

        try {
            return oAuth2GoogleService.getToken(code)
        } catch (e: HttpClientErrorException) {
            throw HttpRequestFailedException(e.statusCode, e.responseBodyAsString)
        } catch (e: ParseException) {
            throw AuthenticationException()
        }
    }

}