package com.novel.cloud.web.domain.auth.controller.rq

import javax.validation.constraints.NotEmpty

data class RefreshAccessTokenRq (

    @field:NotEmpty
    val refreshToken: String

)