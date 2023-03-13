package com.novel.cloud.web.config.security.detail

import com.novel.cloud.web.domain.member.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class CustomMemberOauth2Service(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession
): DefaultOAuth2UserService() {

//    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
//        val oAuth2User = super.loadUser(userRequest);
//
//        val clientRegistration = userRequest.clientRegistration;
//        // TODO :: registrationId를 이용해 클라이언트 oAuth타입 구분
//        val registrationId = clientRegistration.registrationId;
//        val userNameAttributeName = clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName;
//
//        val attributes = OAuthAttributes.create(
//            registrationId,
//            userNameAttributeName,
//            oAuth2User.attributes
//        )
//
//        // TODO::memberContext
//        val user = upsert(attributes);
//        return oAuth2User;
//    }

//    fun upsert(attributes: OAuthAttributes): Member {
//        val member = memberRepository.findByEmail(attributes.email)
//            ?.copy(nickname = attributes.name, picture = attributes.picture)
//            ?: attributes.toEntity()
//        return memberRepository.save(member);
//    }


}