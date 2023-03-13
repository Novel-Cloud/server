package com.novel.cloud.web.config.security.context

class MemberContext(
    private val email: String,
//    private val registrationId: String
) {
    companion object {
        fun create(email: String): MemberContext {
            return MemberContext(email)
        }
    }

}