package com.novel.cloud.web.config.security.context

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