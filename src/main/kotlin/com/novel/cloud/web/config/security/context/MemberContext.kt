package com.novel.cloud.web.config.security.context

class MemberContext(
    private val email: String,
    private val registrationId: String
) {
    fun create(email: String, registrationId: String): MemberContext {
        return MemberContext(email, registrationId)
    }

}