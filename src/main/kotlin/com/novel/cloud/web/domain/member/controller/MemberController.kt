package com.novel.cloud.web.domain.member.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.path.ApiPath
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MemberController(private val findMemberService: FindMemberService) {

    @GetMapping(ApiPath.MEMBER_SELF)
    fun findMemberSelf(@AuthenticationPrincipal memberContext: MemberContext?): FindMemberSelfRs? {
        return findMemberService.findMemberSelf(memberContext)
    }

} 