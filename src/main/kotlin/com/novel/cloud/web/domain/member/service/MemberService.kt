package com.novel.cloud.web.domain.member.service

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rq.UpdateMemberNicknameRq
import com.novel.cloud.web.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService (
    private val memberRepository: MemberRepository,
    private val findMemberService: FindMemberService
) {
    fun updateNickname(memberContext: MemberContext, rq: UpdateMemberNicknameRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        member.updateNickname(rq.nickname)
    }

}