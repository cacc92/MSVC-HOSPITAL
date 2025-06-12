package com.ccarrasco.msvc.medicos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APIRESTFULL - MSVC - Medicos")
                        .description("Esta es la sección donde se encuentran todos " +
                                "los enpoints de MSVC medico")
                        .version("1.0.0")

                );
    }
}
