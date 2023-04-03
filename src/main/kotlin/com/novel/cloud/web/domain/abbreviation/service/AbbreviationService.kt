package com.novel.cloud.web.domain.abbreviation.service

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rq.CreateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.repository.AbbreviationRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AbbreviationService (
    private val findMemberService: FindMemberService,
    private val abbreviationRepository: AbbreviationRepository
) {
    fun createAbbreviation(memberContext: MemberContext, rq: CreateAbbreviationRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val abbreviation = Abbreviation(
            content = rq.content,
            sequence = 1,
            writer = member
        )
        abbreviationRepository.save(abbreviation)
    }

}