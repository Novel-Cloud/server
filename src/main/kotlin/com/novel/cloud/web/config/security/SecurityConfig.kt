package com.novel.cloud.web.config.security

import com.novel.cloud.web.config.security.jwt.JwtOncePerRequestFilter
import com.novel.cloud.web.path.ApiPath
import com.novel.cloud.web.security.CustomOauth2SuccessHandler
import com.novel.cloud.web.security.CustomOauth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtOncePerRequestFilter: JwtOncePerRequestFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customOauth2SuccessHandler: CustomOauth2SuccessHandler,
    private val customOauth2UserService: CustomOauth2UserService
) {

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain = with(http) {

        httpBasic().disable()
        formLogin().disable()
        csrf().disable()
        cors()
        sessionManagement().apply {
            sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        authorizeRequests().apply {
            antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
            antMatchers("/resources/**", "/").permitAll() // 에러 핸들러
            antMatchers(ApiPath.ERROR_AUTH).permitAll() // 인증
            antMatchers(ApiPath.LOGIN_OAUTH2).permitAll()
            antMatchers(ApiPath.VIEW_ARTWORK).permitAll()
            antMatchers(ApiPath.ARTWORK_DETAIL).permitAll()
            antMatchers(ApiPath.MEMBER_OTHER).permitAll()
            antMatchers(ApiPath.FILE_SECURITY).permitAll()
            anyRequest().authenticated()
        }
        addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler)
        oauth2Login().apply {
            successHandler(customOauth2SuccessHandler)
            userInfoEndpoint().userService(customOauth2UserService)
        }
        return build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

}