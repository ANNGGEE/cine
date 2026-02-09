package com.example.cine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cineOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🎬 Cine API")
                        .description("API de gestión de cine: asistentes, entradas, proyecciones y salas")
                        .version("1.0"));
    }
}
