package com.springboot.ecom;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSwaggerConfig {
    @Bean
    OpenAPI getOpenApi() {
        Info info = new Info();
        info.setTitle("API Documentation with Swagger");
        OpenAPI api = new OpenAPI();
        api.setInfo(info);
        return api;
    }
}
