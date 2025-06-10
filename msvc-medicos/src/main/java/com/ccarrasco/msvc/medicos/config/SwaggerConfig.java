package com.ccarrasco.msvc.medicos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        Contact contact = new Contact();
        contact.setName("Cesar Carrrasco");
        contact.setEmail("ccarrascocarre@gmail.com");
        return new OpenAPI()
                .info(new Info()
                        .title("API - MSVC - Médicos")
                        .version("1.0.0")
                        .description("Este es el microservicio de Medicos, con el puedes realizar todas las consultas" +
                                " CRUD que necesites")
                        .contact(contact)
                        .summary("esto es una api dentro de un proyecti de MSVC")
        );
    }
}
