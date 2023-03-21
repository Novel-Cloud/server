package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundFileException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "파일를 찾을 수 없습니다.", null)