package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.auth.constants.AuthConstants
import com.novel.cloud.web.domain.auth.constants.AuthConstants.AUTHORIZATION
import com.novel.cloud.web.domain.auth.constants.AuthConstants.AUTHORIZATION_CODE
import com.novel.cloud.web.domain.auth.constants.AuthConstants.BEARER
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_AUTH_URL
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_PROFILE_URL
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.exception.AuthenticationException
import lombok.RequiredArgsConstructor
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.lang.reflect.Member
import javax.transaction.Transactional


@Service
@RequiredArgsConstructor
@Transactional
class OAuth2GoogleService {

    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val googleClientId: String? = null

    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val googleClientSecret: String? = null

    @Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val googleRedirectUri: String? = null

    fun getToken(code: String?): JwtTokenDto {
        val googleAuthToken = getGoogleAuthToken(code)
        val member: Member = getMemberByGoogleToken(googleAuthToken)
        return jwtTokenFactory.generateJwtToken(member)
    }

    private fun getMemberByGoogleToken(token: String): Member {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add(AUTHORIZATION, BEARER + token)
        val httpEntity: HttpEntity<LinkedMultiValueMap<String, String>> = HttpEntity(headers)
        val attributes = getResponse(httpEntity, GOOGLE_PROFILE_URL, HttpMethod.GET) as Map<*, *>
        val oAuthAttributes: OAuthAttributes = OAuthAttributes.create(GOOGLE.getClientId(), attributes)
        val member: Member = oAuth2LoginService.saveOrUpdate(oAuthAttributes)
//        oAuth2LoginService.saveLoginLog(member)
        return member
    }

    private fun getGoogleAuthToken(code: String?): String {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        val params: LinkedMultiValueMap<String, String> = LinkedMultiValueMap()
        params.add(AuthConstants.GRANT_TYPE, AUTHORIZATION_CODE)
        params.add(AuthConstants.CLIENT_ID, googleClientId)
        params.add(AuthConstants.CLIENT_SECRET, googleClientSecret)
        params.add(AuthConstants.REDIRECT_URI, googleRedirectUri)
        params.add(AuthConstants.CODE, code)
        val httpEntity: HttpEntity<LinkedMultiValueMap<String, String>> = HttpEntity(params, headers)
        val jsonObject: JSONObject = getResponse(httpEntity, GOOGLE_AUTH_URL, HttpMethod.POST)
        return jsonObject.get(AuthConstants.ACCESS_TOKEN) as String
    }

    private fun getResponse(httpEntity: HttpEntity<LinkedMultiValueMap<String, String>>,
                            googleAuthUrl: String,
                            httpMethod: HttpMethod): JSONObject {
        return try {
            val restTemplate = RestTemplate()
            val response: ResponseEntity<String> = restTemplate.exchange(
                googleAuthUrl,
                httpMethod,
                httpEntity,
                String::class.java
            )
            val body: String? = response.body;
            val jsonParser = JSONParser()
            (jsonParser.parse(body) as JSONObject)
        } catch (e: ParseException) {
            throw AuthenticationException()
        }
    }


}