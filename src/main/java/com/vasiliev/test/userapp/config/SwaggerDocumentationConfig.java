package com.vasiliev.test.userapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test User Management")
                .description("This API describers the User Management service, which is used to manage users, loginns in the system.  [Email](alexandrvasilievby@gmail.com) [GitHub](https://github.com/belrbeZ) ")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("https://github.com/belrbeZ")
                .version("0.2.0")
                .contact(new Contact("", "", "alexandrvasilievby@gmail.com"))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vasiliev.test.userapp.controller"))
                .build()
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
