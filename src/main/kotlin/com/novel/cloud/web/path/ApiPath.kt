package com.novel.cloud.web.path

object ApiPath {

    // 에러 핸들러
    const val ERROR_AUTH = "/api/error"

    // 멤버
    const val MEMBER = "/api/member"

    const val MEMBER_SELF = "/api/member/self"

    const val MEMBER_SELF_IMG = "/api/member/self/image"

    const val MEMBER_SELF_NAME = "/api/member/self/nickname"

    const val MEMBER_OTHER = "/api/member/profile/{memberId}"

    const val LOGIN_OAUTH2 = "/api/oauth"

    const val REFRESH_TOKEN = "/api/oauth/token"

    // 작품
    const val ARTWORK = "/api/artwork"

    const val ARTWORK_SAVE = "/api/artwork/save"

    const val ARTWORK_SUBMIT = "/api/artwork"

    const val VIEW_ARTWORK = "/api/artwork/view"

    const val ARTWORK_DETAIL = "/api/artwork/detail/{artworkId}"

    // 파일
    const val FILE = "/api/file"

    const val FILE_SECURITY = "/api/file/**"

    const val VIEW_ARTWORK_IMG = "/api/file/artwork/{fileUidName}"

    const val VIEW_PROFILE_IMG = "/api/file/profile/{fileUidName}"

    //단축어
    const val SHORTCUT = "/api/shortcut"

    const val SHORTCUT_SEQUENCE = "/api/shortcut/sequence"

    //댓글
    const val COMMENT = "/api/comment"

}