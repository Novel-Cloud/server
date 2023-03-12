package com.novel.cloud.web.domain.auth.controller.rq

import lombok.Getter
import javax.validation.constraints.NotEmpty

@Getter
class OAuthRq {

    @NotEmpty
    val code: String? = null;

}