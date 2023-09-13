package com.bessisebzemeyve.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${application.version}")
    private String applicationVersion;

    @Value("${application.tittle}")
    private String applicationTittle;
    @Bean
    OpenAPI customerOpenAPI() {
        return new OpenAPI().info(new Info().title(applicationTittle)
                .version(applicationVersion)
                .contact(new Contact().email("muratcanbastug1@gmail.com")));
    }
}
