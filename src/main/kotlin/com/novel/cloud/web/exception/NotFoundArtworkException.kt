package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundArtworkException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "작품을 찾을 수 없습니다.", null)