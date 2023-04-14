package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.config.security.jwt.JwtTokenFactory
import com.novel.cloud.web.domain.auth.constants.AuthConstants
import com.novel.cloud.web.domain.auth.constants.AuthConstants.AUTHORIZATION_CODE
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_AUTH_URL
import com.novel.cloud.web.domain.auth.constants.AuthConstants.GOOGLE_PROFILE_URL
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.domain.member.service.MemberService
import com.novel.cloud.web.exception.AuthenticationException
import com.novel.cloud.web.exception.HttpRequestFailedException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import javax.transaction.Transactional

@Service
@Transactional
class OAuth2GoogleService(
    googleOAuthConfigProperties: GoogleOAuthConfigProperties,
    private val jwtTokenFactory: JwtTokenFactory,
    private val memberService: MemberService,
) {

    private val CLIENT_ID = googleOAuthConfigProperties.clientId
    private val CLIENT_SECRET = googleOAuthConfigProperties.clientSecret
    private val REDIRECT_URL = googleOAuthConfigProperties.redirectUri

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
        val attributes = getResponse(httpEntity, GOOGLE_PROFILE_URL, HttpMethod.GET).toMap()
        val oAuthAttributes = OAuthAttributes.create("google", attributes)
        return memberService.saveOrUpdateMemberByOAuth(oAuthAttributes)
    }

    private fun getGoogleAuthToken(code: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
        val params = LinkedMultiValueMap<String, String>().apply {
            add(AuthConstants.GRANT_TYPE, AUTHORIZATION_CODE)
            add(AuthConstants.CLIENT_ID, CLIENT_ID)
            add(AuthConstants.CLIENT_SECRET, CLIENT_SECRET)
            add(AuthConstants.REDIRECT_URI, REDIRECT_URL)
            add(AuthConstants.CODE, code)
        }
        val httpEntity = HttpEntity(params, headers)
        val jsonObject = getResponse(httpEntity, GOOGLE_AUTH_URL, HttpMethod.POST)
        return jsonObject[AuthConstants.ACCESS_TOKEN] as String
    }

    private fun getResponse(
        httpEntity: HttpEntity<LinkedMultiValueMap<String, String>>,
        googleAuthUrl: String,
        httpMethod: HttpMethod,
    ): JSONObject {
        val restTemplate = RestTemplate()
        val jsonParser = JSONParser()
        return try {
            val response: ResponseEntity<String> = restTemplate.exchange(
                googleAuthUrl,
                httpMethod,
                httpEntity,
                String::class.java
            )
            jsonParser.parse(response.body) as JSONObject
        } catch (e: HttpClientErrorException) {
            throw HttpRequestFailedException(e.statusCode, e.responseBodyAsString)
        } catch (e: ParseException) {
            throw AuthenticationException()
        }
    }

}