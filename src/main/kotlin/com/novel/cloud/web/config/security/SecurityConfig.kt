package com.novel.cloud.web.config.security

import com.novel.cloud.web.config.security.jwt.JwtOncePerRequestFilter
import com.novel.cloud.web.path.ApiPath
import com.novel.cloud.web.security.CustomOauth2SuccessHandler
import com.novel.cloud.web.security.CustomOauth2UserService
import lombok.RequiredArgsConstructor
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
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtOncePerRequestFilter: JwtOncePerRequestFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customOauth2SuccessHandler: CustomOauth2SuccessHandler,
    private val customOauth2UserService: CustomOauth2UserService
) {

    @Bean
    @Throws(Exception::class)
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.httpBasic().disable()
        http.formLogin().disable()
        http.csrf().disable()
        http.cors()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
            .antMatchers("/resources/**", "/").permitAll() // 에러 핸들러
            .antMatchers(ApiPath.ERROR_AUTH).permitAll() // 인증
            .antMatchers(ApiPath.LOGIN_OAUTH2, ApiPath.REFRESH_TOKEN).permitAll()
            .anyRequest().authenticated()
        http.addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler)
        http.oauth2Login()
            .successHandler(customOauth2SuccessHandler)
            .userInfoEndpoint()
            .userService(customOauth2UserService)
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

}