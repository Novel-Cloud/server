package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes




class OAuth2LoginService {

    fun saveOrUpdate(oAuthAttributes: OAuthAttributes): Member? {
        var member: Member = findMemberService.findByEmailAndRegistrationIdOrElseNull(
            oAuthAttributes.email,
            oAuthAttributes.getRegistrationId()
        )
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity()
            memberRepository.save(member)
            this.saveSignUpLog(member)
        }
        member.update(oAuthAttributes.picture)
        return member
    }



}