package com.novel.cloud.web.domain.auth.controller.rq
import javax.validation.constraints.NotEmpty

class OAuthRq {

    @field:NotEmpty
    val code: String? = null;

}