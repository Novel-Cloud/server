package com.novel.cloud.web.config.security.jwt

object JwtProperty {

    // TODO::운영환경에서 비밀키 숨기기
    const val SIGN_KEY = "testserver"
    const val MEMBER_EMAIL = "MEMBER_EMAIL"
    const val REGISTRATION_ID = "REGISTRATION_ID"
    const val JWT_ISSUER = "min"
    const val JWT_EXCEPTION = "exception"
    const val TOKEN_KEY = "token"

}