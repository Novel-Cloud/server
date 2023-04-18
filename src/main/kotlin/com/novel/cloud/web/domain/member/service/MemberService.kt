package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.file.service.S3UploadService
import com.novel.cloud.web.domain.member.controller.rq.UpdateMemberNicknameRq
import com.novel.cloud.web.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val findMemberService: FindMemberService,
    private val s3UploadService: S3UploadService
) {

    fun saveOrUpdateMemberByOAuth(oAuthAttributes: OAuthAttributes): Member {
        return findMemberService.findByEmailOrElseNull(oAuthAttributes.email)
            ?: memberRepository.save(oAuthAttributes.toEntity())
    }

    /**
     * 닉네임 변경
     */
    fun updateMemberNickname(memberContext: MemberContext, rq: UpdateMemberNicknameRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        member.updateNickname(rq.nickname)
    }

    /**
     * 프로필 사진 변경
     */
    fun updateMemberPicture(memberContext: MemberContext, profile: MultipartFile) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val uploadedFileUrl = s3UploadService.upload(profile)

        member.updatePicture(uploadedFileUrl)
    }

}