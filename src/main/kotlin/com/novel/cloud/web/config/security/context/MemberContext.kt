package com.novel.cloud.web.config.security.context

import io.swagger.v3.oas.annotations.Hidden

@Hidden
data class MemberContext (
    val email: String,
    val registrationId: String
) {

    companion object {
        fun create(email: String): MemberContext {
            return MemberContext(email, "google")
        }
    }
}