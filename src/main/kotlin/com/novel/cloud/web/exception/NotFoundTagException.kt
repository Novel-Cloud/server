package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundTagException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "태그를 찾을 수 없습니다.", null)