package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class DoNotHavePermissionToDeleteOrUpdateArtworkException
    : GeneralHttpException(HttpStatus.FORBIDDEN, "댓글을 삭제하거나 수정할 수 있는 권한이 없습니다.", null)