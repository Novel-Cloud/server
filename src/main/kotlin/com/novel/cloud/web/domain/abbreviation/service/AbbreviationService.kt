package com.novel.cloud.web.domain.abbreviation.service

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rq.CreateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.DeleteAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.UpdateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.UpdateAbbreviationSequenceRq
import com.novel.cloud.web.domain.abbreviation.repository.AbbreviationRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.AbbreviationSequenceContainException
import com.novel.cloud.web.exception.AbbreviationSequenceSizeException
import com.novel.cloud.web.exception.DoNotHavePermissionToDeleteOrUpdateAbbreviationException
import org.apache.commons.lang3.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AbbreviationService(
    private val findMemberService: FindMemberService,
    private val findAbbreviationService: FindAbbreviationService,
    private val abbreviationRepository: AbbreviationRepository,
) {
    fun createAbbreviation(memberContext: MemberContext, rq: CreateAbbreviationRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val myLastAbbreviation = findAbbreviationService.findMyLastSequenceAbbreviation(member.id);
        val sequence: Int = myLastAbbreviation?.sequence?.plus(1) ?: 1

        val abbreviation = Abbreviation(
            content = rq.content,
            sequence = sequence,
            writer = member
        )
        abbreviationRepository.save(abbreviation)
    }

    fun deleteAbbreviation(memberContext: MemberContext, rq: DeleteAbbreviationRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val shortcutId: Long = rq.shortcutId
        val abbreviation: Abbreviation = findAbbreviationService.findByIdOrElseThrow(shortcutId)
        abbreviationPermissionCheck(abbreviation, member)
        abbreviationRepository.delete(abbreviation)
    }

    private fun abbreviationPermissionCheck(abbreviation: Abbreviation, member: Member) {
        val writerId: Long? = abbreviation.writer.id
        val memberId: Long? = member.id
        if (writerId == memberId) {
            return
        }
        throw DoNotHavePermissionToDeleteOrUpdateAbbreviationException()
    }

    fun updateAbbreviation(memberContext: MemberContext, rq: UpdateAbbreviationRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val shortcutId: Long = rq.shortcutId
        val abbreviation: Abbreviation = findAbbreviationService.findByIdOrElseThrow(shortcutId)
        abbreviationPermissionCheck(abbreviation, member)
        abbreviation.updateContent(rq.content)
    }

    fun updateAbbreviationSequence(memberContext: MemberContext, rq: UpdateAbbreviationSequenceRq) {
        val abbreviationIdList = rq.shortcutIdList
        if (ObjectUtils.isEmpty(abbreviationIdList)) {
            return
        }

        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val abbreviationList = member.abbreviations

        updateAbbreviationValidationCheck(abbreviationList, abbreviationIdList)

        val portfolioMap: Map<Long?, Abbreviation> = abbreviationList.associateBy { abbreviation ->
            abbreviation.id
        }

        for (index in abbreviationIdList.indices) {
            val abbreviationId = abbreviationIdList[index]
            val abbreviation = portfolioMap[abbreviationId]
            abbreviation?.updateSequence(index)
        }

    }

    private fun updateAbbreviationValidationCheck(
        abbreviationList: List<Abbreviation>,
        abbreviationIdList: List<Long>,
    ) {
        val abbreviationIdSet = abbreviationList.map { abbreviation ->
            abbreviation.id
        }.toSet()

        if (abbreviationIdSet.size != abbreviationIdList.size) {
            throw AbbreviationSequenceSizeException()
        }

        abbreviationIdList.map { abbreviationId ->
            if (!abbreviationIdSet.contains(abbreviationId)) {
                throw AbbreviationSequenceContainException()
            }
        }
    }

}