package com.novel.cloud.web.domain.member.service

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.file.service.FileService
import com.novel.cloud.web.domain.member.controller.rq.UpdateMemberNicknameRq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService (
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
        // TODO: 상수화 또는 다른 로직으로 수정
        member.updatePicture("http://10.150.151.237:8080/api/file/profile/$fileUidName")
    }

}