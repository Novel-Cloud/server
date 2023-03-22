package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class DoNotHavePermissionToAutoSaveArtwork
    : GeneralHttpException(HttpStatus.FORBIDDEN, "임시 저장할 수 있는 권한이 없습니다.", null)