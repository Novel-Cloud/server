package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class JwtException (
    message: String
) : GeneralHttpException(HttpStatus.UNAUTHORIZED, message, null)