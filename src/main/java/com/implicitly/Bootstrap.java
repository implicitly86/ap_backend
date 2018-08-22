/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.val;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Загрузчик.
 *
 * @author Emil Murzakaev.
 */
@EnableSwagger2
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Bootstrap.class)
                .run(args);
    }

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
        val securityReferences = newArrayList(
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
                .securitySchemes(newArrayList(apiKeys))
                .securityContexts(newArrayList(securityContext));
    }

    /**
     * Бин конфигурации формата даты.
     *
     * @return {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        builder
                .serializers(new JsonSerializer<LocalDateTime>() {
                    @Override
                    public Class<LocalDateTime> handledType() {
                        return LocalDateTime.class;
                    }
                    @Override
                    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                            SerializerProvider serializerProvider) throws IOException {
                        jsonGenerator.writeString(formatter.format(localDateTime));
                    }
                })
                .deserializers(new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public Class<LocalDateTime> handledType() {
                        return LocalDateTime.class;
                    }
                    @Override
                    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                            throws IOException, JsonProcessingException {
                        return LocalDateTime.parse(p.getValueAsString(), formatter);
                    }
                });
        return builder.build();
    }

}
