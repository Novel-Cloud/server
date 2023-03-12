package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus
class AuthenticationException
    : GeneralHttpException(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.", null)