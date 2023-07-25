package com.jsw.mes.mdm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is written for configuring the swagger V3
 */
@Configuration
@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class SwaggerConfiguration {

    private final Environment env;

    public SwaggerConfiguration(Environment env) {
        this.env = env;
    }


    @Bean
    public OpenAPI myOpenAPI() {


        Server localServer = new Server();
        localServer.setUrl( env.getProperty("swagger.url"));
        localServer.setDescription("Server URL in local environment");

        Contact contact = new Contact();
        contact.setEmail("ragavendhra.g@infovision.com");
        contact.setName("Ragavendhra");
        contact.setUrl("https://outlook.office365.com/mail");

        License mitLicense = new License().name("Mit License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Master Data Management API")
                .version("3.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Master Data Management APIs.").termsOfService("need url")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localServer));

    }

}
