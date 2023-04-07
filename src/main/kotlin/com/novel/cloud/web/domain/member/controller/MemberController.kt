package com.novel.cloud.web.domain.member.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rq.UpdateMemberNicknameRq
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.domain.member.service.MemberService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "멤버")
@RestController
class MemberController(
    private val memberService: MemberService,
    private val findMemberService: FindMemberService
) {

    @Operation(summary = "내 정보 불러오기")
    @GetMapping(ApiPath.MEMBER_SELF)
    fun findMemberSelf(@AuthenticationPrincipal memberContext: MemberContext): FindMemberSelfRs? {
        return findMemberService.findMemberSelf(memberContext)
    }

    @Operation(summary = "다른 멤버의 프로필 보기")
    @GetMapping(ApiPath.MEMBER_OTHER)
    fun findMemberProfile(@AuthenticationPrincipal memberContext: MemberContext,
                          @PathVariable memberId: Long): FindMemberSelfRs? {
        return findMemberService.findMemberProfile(memberContext, memberId)
    }

    @Operation(summary = "닉네임 변경")
    @PutMapping(ApiPath.MEMBER_SELF_NAME)
    fun updateMemberNickname(@AuthenticationPrincipal memberContext: MemberContext,
                             @Validated @RequestBody rq: UpdateMemberNicknameRq) {
        return memberService.updateMemberNickname(memberContext, rq)
    }

    @Operation(summary = "프로필 사진 변경")
    @PutMapping(ApiPath.MEMBER_SELF_IMG)
    fun updateMemberPicture(
        @AuthenticationPrincipal memberContext: MemberContext,
        @RequestPart(value = "profile") profile: MultipartFile,
    ) {
        return memberService.updateMemberPicture(memberContext, profile)
    }

} 