package com.novel.cloud.web.domain.auth.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OAuth2LoginService(
    private val findMemberService: FindMemberService,
    private val memberRepository: MemberRepository
) {

    fun saveOrUpdate(oAuthAttributes: OAuthAttributes): Member {
        val member = findMemberService.findByEmailOrElseNull(oAuthAttributes.email)
            ?: memberRepository.save(oAuthAttributes.toEntity())
        member.update(oAuthAttributes.picture)
        return member;
    }

}