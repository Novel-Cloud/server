package com.novel.cloud.web.domain.dto

import lombok.Builder
import lombok.Getter

@Getter
@Builder
class JwtTokenDto {

    val token: String? = null;

    val validity: String? = null;

}