package com.novel.cloud.web.config.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

class SecurityConfig(
//   private val customUserDeatailService: CustomUserDetailService
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf {
                it.disable();
            }
            .httpBasic() {
                it.disable();
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER);
            }
            .oauth2Login(){
//                it.userInfoEndpoint().userService(customUserDetailService);
                it.defaultSuccessUrl("api/oauth/login");
                it.failureUrl("api/error");
            }

    }

}