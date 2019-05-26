/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.implicitly.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Реализация {@link AuthenticationEntryPoint}.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * {@link AuthenticationEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("unauthorized request : {} {}", request.getMethod(), request.getRequestURL());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = ErrorResponse.of(Error.UNAUTHORIZED.getCode(), Error.UNAUTHORIZED.getMessage(), null);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    /**
     * Модель, содержащая информацию об ошибке.
     */
    @Data
    @AllArgsConstructor(staticName = "of")
    private static class ErrorResponse {

        /**
         * Код ошибки.
         */
        private String code;
        /**
         * Сообщение об ошибке.
         */
        private String message;
        /**
         * Стектрейс ошибки.
         */
        private String cause;

    }

}
