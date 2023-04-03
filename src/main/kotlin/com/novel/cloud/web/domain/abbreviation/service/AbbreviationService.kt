package com.novel.cloud.web.domain.abbreviation.service

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rq.CreateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.DeleteAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.UpdateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.repository.AbbreviationRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.DoNotHavePermissionToDeleteOrUpdateAbbreviationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects

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

}