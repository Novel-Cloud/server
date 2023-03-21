package com.novel.cloud.web.path

object ApiPath {

    // 에러 핸들러
    const val ERROR_AUTH = "/api/error"

    // 멤버
    const val MEMBER = "/api/member"

    const val MEMBER_SELF = "/api/member/self"

    const val MEMBER_OTHER = "/api/member/profile/{memberId}"

    const val LOGIN_OAUTH2 = "/api/oauth"

    const val REFRESH_TOKEN = "/api/oauth/token"

    // 작품
    const val ARTWORK = "/api/artwork"

    const val VIEW_ARTWORK = "/api/artwork/view"

    // 파일
    const val FILE = "/api/file"

    const val FILE_SECURITY = "/api/file/**"

    const val VIEW_IMG = "/api/file/{fileUidName}"

}