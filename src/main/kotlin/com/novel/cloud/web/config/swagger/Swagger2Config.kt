package com.novel.cloud.web.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Swagger2Config {

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("v1-documentation")
            .pathsToMatch("/api/**")
            .addOpenApiCustomiser(openApiCustomiser())
            .build()
    }

    @Bean
    fun springShopOpenApi(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Novel-Cloud API")
                    .description("Novel-Cloud API 명세서입니다.")
                    .version("v1.0.0")
            )
            .components(getComponents())
    }

    private fun getComponents(): Components? {
        return Components()
            .addSecuritySchemes("jwt", getJwtSecurityScheme())
    }

    private fun getJwtSecurityScheme(): SecurityScheme? {
        return SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .`in`(SecurityScheme.In.HEADER)
            .name("token")
    }

    @Bean
    fun openApiCustomiser(): OpenApiCustomiser? {
        return OpenApiCustomiser {
            it.addSecurityItem(getSecurityItem())
            it.addServersItem(getServersItem())
        }
    }

    @Bean
    fun resumeOpenApiCustomizer(): OpenApiCustomiser {
        return OpenApiCustomiser { api ->
            api
                .addSecurityItem(getSecurityItem())
                .addServersItem(getServersItem())
        }
    }

    private fun getSecurityItem(): SecurityRequirement {
        return SecurityRequirement()
            .addList("jwt")
    }

    private fun getServersItem(): Server {
        return Server().url("/")
    }

}