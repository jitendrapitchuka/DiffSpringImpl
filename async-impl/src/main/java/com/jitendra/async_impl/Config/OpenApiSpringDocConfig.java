package com.jitendra.async_impl.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
public class OpenApiSpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Local server for development");

        Contact contact = new Contact();
        contact.setName("Jitendra p");
        contact.setEmail("abc@example.com");

        Info info = new Info()
        .title("Async Email Service API")
                .version("1.0.0")
                .description("API for sending emails asynchronously")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));               
    }

}
