package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotFoundCommentException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.", null)