package com.novel.cloud.web.domain.member.controller.rs

import com.novel.cloud.db.entity.member.Member

class FindMemberSelfRs(
    private val memberId: Long?,
    private val nickname: String?,
    private val picture: String?,
    private val email: String?
){ }