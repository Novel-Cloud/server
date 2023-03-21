package com.novel.cloud.web.domain.member.service

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.file.service.FileService
import com.novel.cloud.web.domain.member.controller.rq.UpdateMemberNicknameRq
import com.novel.cloud.web.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService (
    private val memberRepository: MemberRepository,
    private val findMemberService: FindMemberService,
    private val fileService: FileService
) {

    fun updateMemberNickname(memberContext: MemberContext, rq: UpdateMemberNicknameRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        member.updateNickname(rq.nickname)
    }

    fun updateMemberPicture(memberContext: MemberContext, profile: MultipartFile) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val fileUidName = fileService.uploadProfile(profile)
        // TODO: 무조건 상수화로 수정
        member.updatePicture("http://localhost:3000/api/file/" + fileUidName)
    }

}