package com.example.secondhandcar.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI secondHandCarOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("二手车评估系统API")
                        .description("二手车评估管理系统API文档")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("二手车评估系统文档")
                        .url("https://github.com/example/secondhandcar"));
    }
} 