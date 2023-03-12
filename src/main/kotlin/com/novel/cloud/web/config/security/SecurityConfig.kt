package com.novel.cloud.web.config.security

import com.novel.cloud.web.config.security.detail.CustomMemberOauth2Service
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableWebSecurity
class SecurityConfig(
   private val customMemberOauth2Service: CustomMemberOauth2Service
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
                it.userInfoEndpoint().userService(customMemberOauth2Service);
            }

    }

}