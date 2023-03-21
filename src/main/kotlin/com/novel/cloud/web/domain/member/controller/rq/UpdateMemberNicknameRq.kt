package com.novel.cloud.web.domain.member.controller.rq

import javax.validation.constraints.NotEmpty

data class UpdateMemberNicknameRq (

    @field:NotEmpty
    val nickname: String

)