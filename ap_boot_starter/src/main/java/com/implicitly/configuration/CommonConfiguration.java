package com.implicitly.configuration;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Emil Murzakaev.
 */
@Configuration
@EnableSwagger2
public class CommonConfiguration {

    /**
     * Swagger бин для документации.
     *
     * @return {@link Docket}
     */
    @Bean(name = "swagger")
    public Docket swagger() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        val securityReferences = Collections.singletonList(
                new SecurityReference(AUTHORIZATION, authorizationScopes)
        );
        val securityContext = SecurityContext.builder()
                .securityReferences(securityReferences)
                .forPaths(PathSelectors.any())
                .build();
        val apiKeys = new ApiKey(AUTHORIZATION, AUTHORIZATION, "header");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enable(true)
                .securitySchemes(Collections.singletonList(apiKeys))
                .securityContexts(Collections.singletonList(securityContext));
    }

}
