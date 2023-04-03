package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class DoNotHavePermissionToDeleteAbbreviationException
    : GeneralHttpException(HttpStatus.FORBIDDEN, "단축어를 삭제할 수 있는 권한이 없습니다.", null)