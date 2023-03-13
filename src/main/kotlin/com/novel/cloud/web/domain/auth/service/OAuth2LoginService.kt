package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@RequiredArgsConstructor
@Transactional
class OAuth2LoginService(
    private val findMemberService: FindMemberService,
    private val memberRepository: MemberRepository
) {

    fun saveOrUpdate(oAuthAttributes: OAuthAttributes): Member {
        var member: Member? = findMemberService.findByEmailOrElseNull(
            oAuthAttributes.email,
        )
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity()
            memberRepository.save(member)
//            this.saveSignUpLog(member)
        }
        member!!.update(oAuthAttributes.picture)
        return member;
    }



}