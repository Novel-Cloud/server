package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundRefreshTokenException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "리프레시 토큰이 만료되었거나 존재하지 않습니다", null)