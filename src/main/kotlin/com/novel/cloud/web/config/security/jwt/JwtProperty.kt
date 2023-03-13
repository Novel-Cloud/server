package com.novel.cloud.web.config.security.jwt

object JwtProperty {

    const val SIGN_KEY = "bssm-sign-key"
    const val MEMBER_EMAIL = "MEMBER_EMAIL"
    const val REGISTRATION_ID = "REGISTRATION_ID"
    const val MEMBER_ROLE_TYPE = "MEMBER_ROLE_TYPE"
    const val JWT_ISSUER = "BSSMH"
    const val TOKEN_TIME_TO_LIVE = 2 * 60 * 60 * 1000L
    const val JWT_EXCEPTION = "exception"
    const val TOKEN_KEY = "token"

}