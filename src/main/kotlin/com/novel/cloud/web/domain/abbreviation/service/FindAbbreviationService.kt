package com.novel.cloud.web.domain.abbreviation.service

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rs.FindSequenceAbbreviationRs
import com.novel.cloud.web.domain.abbreviation.repository.AbbreviationRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.ListResponse
import com.novel.cloud.web.exception.NotFoundAbbreviationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindAbbreviationService(
    private val abbreviationRepository: AbbreviationRepository,
    private val findMemberService: FindMemberService,
) {

    fun findByIdOrElseThrow(abbreviationId: Long): Abbreviation {
        return abbreviationRepository.findById(abbreviationId)
            .orElseThrow { NotFoundAbbreviationException() }
    }

    fun findMyLastSequenceAbbreviation(id: Long?): Abbreviation? {
        return abbreviationRepository.findMyLastSequenceAbbreviation(id)
            .orElse(null)
    }

    private fun findSequenceAbbreviation(memberId: Long?): List<Abbreviation> {
        return abbreviationRepository.findSequenceAbbreviation(memberId)
    }

    /**
     * 내 단축어 불러오기
     */
    fun findAbbreviationSelf(memberContext: MemberContext): ListResponse<FindSequenceAbbreviationRs> {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)

        val abbreviationList = findSequenceAbbreviation(member.id)
        val findSequenceAbbreviationRsList = abbreviationList.map { abbreviation ->
            FindSequenceAbbreviationRs.create(abbreviation)
        }.toList()

        return ListResponse(
            list = findSequenceAbbreviationRsList
        )
    }

}