/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.implicitly.security.SecurityFeignRequestInterceptor;
import com.implicitly.utils.feign.FeignPageJacksonModule;
import com.implicitly.utils.feign.FeignPageableEncoder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;

/**
 * Конфигурация {@link FeignClient}.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignConfiguration {

    /**
     * Тег используемый при логировании.
     */
    private static final String LOG_TAG = "[FEIGN_CONFIGURATION] ::";

    /**
     * Имя бина {@link #OAuthFeignRequestInterceptor}.
     */
    private static final String FEIGN_REQUEST_INTERCEPTOR = "SecurityFeignRequestInterceptor";

    /**
     * Имя бина {@link #feignPageJacksonModule}.
     */
    private static final String FEIGN_PAGE_JACKSON_MODULE = "feignPageJacksonModule";

    @PostConstruct
    public void init() {
        if (log.isInfoEnabled()) {
            log.info(
                    "{} has been initialized.",
                    LOG_TAG
            );
        }
    }

    /**
     * {@link FeignPageableEncoder}
     */
    @Bean
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FeignPageableEncoder(new SpringEncoder(messageConverters));
    }

    /**
     * Кастомный обработчик ошибок .
     *
     * @return {@link FeignErrorDecoder}
     */
    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * {@link SecurityFeignRequestInterceptor}
     */
    @Bean(name = FEIGN_REQUEST_INTERCEPTOR)
    public RequestInterceptor OAuthFeignRequestInterceptor() {
        return new SecurityFeignRequestInterceptor();
    }

    /**
     * {@link FeignPageJacksonModule}
     */
    @Bean(name = FEIGN_PAGE_JACKSON_MODULE)
    public Module feignPageJacksonModule(ObjectMapper objectMapper) {
        FeignPageJacksonModule pageModule = new FeignPageJacksonModule();
        objectMapper.registerModule(pageModule);
        return pageModule;
    }

    /**
     * Реализация {@link ErrorDecoder} для возможности кастомной обработки http статусов от других микросервисов.
     */
    private class FeignErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultErrorDecoder = new Default();

        private ObjectMapper objectMapper;

        /**
         * {@inheritDoc}
         */
        @Override
        public Exception decode(String methodKey, Response response) {
            if (HttpStatus.BAD_REQUEST.value() == response.status()) {
                /*
                List<ValidationError> errors;
                try {
                    errors = getObjectMapper()
                            .readValue(
                                    response.body().asInputStream(),
                                    getObjectMapper()
                                            .getTypeFactory()
                                            .constructCollectionType(
                                                    List.class,
                                                    ValidationError.class
                                            )
                            );
                } catch (IOException e) {
                    String msg = "Exception occurred while trying to decode response";
                    log.error(
                            "{} {}",
                            LOG_TAG,
                            msg,
                            e
                    );
                    throw new OADException(msg, e);
                }
                return new ValidationException(errors);
                */
            }

            if (HttpStatus.FORBIDDEN.value() == response.status()) {
                /*
                String message;
                try {
                    message = getObjectMapper()
                            .readValue(
                                    response.body().asInputStream(),
                                    getObjectMapper()
                                            .getTypeFactory()
                                            .constructType(Map.class)
                            ).toString();
                } catch (IOException e) {
                    String msg = "Exception occurred while trying to decode response";
                    log.error(
                            "{} {}",
                            LOG_TAG,
                            msg,
                            e
                    );
                    throw new OADException(msg, e);
                }
                throw new AccessDeniedException(message);
                */
            }

            return defaultErrorDecoder.decode(methodKey, response);
        }

        private ObjectMapper getObjectMapper() {
            if (objectMapper == null) {
                ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                this.objectMapper = objectMapper;
            }
            return objectMapper;
        }
    }

}
