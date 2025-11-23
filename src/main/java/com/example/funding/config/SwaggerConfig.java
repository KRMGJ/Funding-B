package com.example.funding.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local", "dev"})
public class SwaggerConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI fundingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Funding 플랫폼 API")
                        .description("Funding 플랫폼 REST API 문서입니다.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("장민규")
                                .email("example@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    /**
     * Address 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi addressApi() {
        return GroupedOpenApi.builder()
                .group("address")
                .pathsToMatch("/api/v1/shipping/**")
                .build();
    }

    /**
     * Attach 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi attachApi() {
        return GroupedOpenApi.builder()
                .group("attachment")
                .pathsToMatch("/api/v1/attach/**")
                .build();
    }

    /**
     * Auth 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    /**
     * Backing 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi backingApi() {
        return GroupedOpenApi.builder()
                .group("backing")
                .pathsToMatch("/api/v1/backing/**")
                .build();
    }

    /**
     * Category 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder()
                .group("category")
                .pathsToMatch("/api/v1/category/**")
                .build();
    }

    /**
     * Creator 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi creatorApi() {
        return GroupedOpenApi.builder()
                .group("creator")
                .pathsToMatch("/api/v1/creator/**")
                .build();
    }

    /**
     * Inquiry 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi inquiryApi() {
        return GroupedOpenApi.builder()
                .group("inquiry")
                .pathsToMatch("/api/v1/cs/inquiry/**")
                .build();
    }

    /**
     * Notice 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi noticeApi() {
        return GroupedOpenApi.builder()
                .group("notice")
                .pathsToMatch("/api/v1/cs/notice/**")
                .build();
    }

    /**
     * Notification 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi notificationApi() {
        return GroupedOpenApi.builder()
                .group("notification")
                .pathsToMatch("/api/v1/notification/**")
                .build();
    }

    /**
     * Payment 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi paymentApi() {
        return GroupedOpenApi.builder()
                .group("payment")
                .pathsToMatch("/api/v1/payment/**")
                .build();
    }

    /**
     * User 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/api/v1/user/**")
                .build();
    }

    /**
     * Project 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi projectApi() {
        return GroupedOpenApi.builder()
                .group("project")
                .pathsToMatch("/api/v1/project/**")
                .build();
    }

    /**
     * Qna 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi qnaApi() {
        return GroupedOpenApi.builder()
                .group("qna")
                .pathsToMatch("/api/v1/cs/qna/**")
                .build();
    }

    /**
     * Report 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi reportApi() {
        return GroupedOpenApi.builder()
                .group("report")
                .pathsToMatch("/api/v1/cs/report/**")
                .build();
    }

    /**
     * Settlement 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi settlementApi() {
        return GroupedOpenApi.builder()
                .group("settlement")
                .pathsToMatch("/api/v1/settlement/**")
                .build();
    }

    /**
     * Admin 영역 API 그룹
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/api/v1/admin/**")
                .build();
    }
}
