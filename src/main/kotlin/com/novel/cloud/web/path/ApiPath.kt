package com.novel.cloud.web.path

object ApiPath {

    // 에러 핸들러
    const val ERROR_AUTH = "/api/v1/error"

    // 멤버
    const val MEMBER = "/api/v1/member"

    const val MEMBER_SELF = "/api/v1/member/self"

    const val MEMBER_SELF_IMG = "/api/v1/member/self/image"

    const val MEMBER_SELF_NAME = "/api/v1/member/self/nickname"

    const val MEMBER_OTHER = "/api/v1/member/profile/{memberId}"

    const val LOGIN_OAUTH2 = "/api/v1/oauth"

    const val REFRESH_TOKEN = "/api/v1/oauth/refresh"

    // 작품
    const val ARTWORK = "/api/v1/artwork"

    const val ARTWORK_SAVE = "/api/v1/artwork/save"

    const val ARTWORK_SUBMIT = "/api/v1/artwork"

    const val VIEW_ARTWORK = "/api/v1/artwork/view"

    const val ARTWORK_UPDATE = "/api/v1/artwork/update"

    const val ARTWORK_DELETE = "/api/v1/artwork/delete"

    const val ARTWORK_DETAIL = "/api/v1/artwork/detail/{artworkId}"

    // 파일
    const val FILE = "/api/v1/file"

    const val FILE_UPLOAD = "/api/v1/file/upload"

    const val VIEW_ARTWORK_IMG = "/api/v1/file/artwork/{fileUidName}"

    const val VIEW_PROFILE_IMG = "/api/v1/file/profile/{fileUidName}"

    // 단축어
    const val SHORTCUT = "/api/v1/shortcut"

    const val SHORTCUT_SEQUENCE = "/api/v1/shortcut/sequence"

    // 댓글
    const val COMMENT = "/api/v1/comment"

    const val VIEW_COMMENT = "/api/v1/comment/{artworkId}"

    // 좋아요
    const val LIKE = "/api/v1/like"

    const val TOGGLE_LIKE = "/api/v1/like"

    // 검색
    const val SEARCH = "/api/v1/search"

    const val SEARCH_TAG = "/api/v1/search/tag"

    // 태그
    const val TAG = "/api/v1/tag"


}