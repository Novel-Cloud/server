package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundAbbreviationException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "단축어을 찾을 수 없습니다.", null)