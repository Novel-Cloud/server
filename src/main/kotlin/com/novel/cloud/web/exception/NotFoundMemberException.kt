package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus


class NotFoundMemberException
    : GeneralHttpException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.", null)