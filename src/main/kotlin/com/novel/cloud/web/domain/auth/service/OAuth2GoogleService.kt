package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.config.security.jwt.JwtTokenFactory
import com.novel.cloud.web.domain.auth.constants.AuthConstants
import com.novel.cloud.web.domain.auth.constants.AuthConstants.AUTHORIZATION_CODE
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_AUTH_URL
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_PROFILE_URL
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.exception.AuthenticationException
import com.novel.cloud.web.exception.HttpRequestFailedException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import javax.transaction.Transactional

@Service
@Transactional
class OAuth2GoogleService(
    private val jwtTokenFactory: JwtTokenFactory,
    private val oAuth2LoginService: OAuth2LoginService
) {

    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val googleClientId: String? = null

    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val googleClientSecret: String? = null

    @Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val googleRedirectUri: String? = null

    fun getToken(code: String): JwtTokenDto {
        val googleAuthToken = getGoogleAuthToken(code)
        val member = getMemberByGoogleToken(googleAuthToken)
        return jwtTokenFactory.generateJwtToken(member)
    }

    private fun getMemberByGoogleToken(token: String): Member {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            setBearerAuth(token)
        }
        val httpEntity = HttpEntity<LinkedMultiValueMap<String, String>>(headers)
        val attributes = getResponse(httpEntity, GOOGLE_PROFILE_URL, HttpMethod.GET) as Map<String, Any>
        val oAuthAttributes = OAuthAttributes.create("google", attributes)
        return oAuth2LoginService.saveOrUpdate(oAuthAttributes)
    }

    private fun getGoogleAuthToken(code: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
        val params = LinkedMultiValueMap<String, String>().apply {
            add(AuthConstants.GRANT_TYPE, AUTHORIZATION_CODE)
            add(AuthConstants.CLIENT_ID, googleClientId)
            add(AuthConstants.CLIENT_SECRET, googleClientSecret)
            add(AuthConstants.REDIRECT_URI, googleRedirectUri)
            add(AuthConstants.CODE, code)
        }
        val httpEntity = HttpEntity(params, headers)
        val jsonObject = getResponse(httpEntity, GOOGLE_AUTH_URL, HttpMethod.POST)
        return jsonObject[AuthConstants.ACCESS_TOKEN] as String
    }

    private fun getResponse(httpEntity: HttpEntity<LinkedMultiValueMap<String, String>>,
                            googleAuthUrl: String,
                            httpMethod: HttpMethod): JSONObject {
        return try {
            val restTemplate = RestTemplate()
            val jsonParser = JSONParser()
            val response: ResponseEntity<String> = restTemplate.exchange(
                googleAuthUrl,
                httpMethod,
                httpEntity,
                String::class.java
            )
            val body: String? = response.body;
            jsonParser.parse(body) as JSONObject
        } catch (e: ParseException) {
            throw AuthenticationException()
        } catch (e: HttpClientErrorException) {
            throw HttpRequestFailedException(e.statusCode, e.responseBodyAsString)
        }
    }

}