package com.novel.cloud.web.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns(
                CorsPatternConstant.CORS_LOCAL,
                CorsPatternConstant.CORS_LOCAL_IP)
    }
}