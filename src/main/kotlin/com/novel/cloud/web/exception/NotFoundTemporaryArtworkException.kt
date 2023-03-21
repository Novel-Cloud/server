package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundTemporaryArtworkException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "임시 저장된 작품을 찾을 수 없습니다.", null)