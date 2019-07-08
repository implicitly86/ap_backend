/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import com.implicitly.exception.Error;
import com.implicitly.exception.ErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ControllerAdvice}.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@ControllerAdvice
public class AdviceController {

    /**
     * Обработка исключения {@link AuthenticationException}.
     *
     * @param ex {@link BadCredentialsException}.
     * @return список {@link ErrorResponse}.
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleAuthException(AuthenticationException ex) {
        ErrorResponse errorResponse = null;
        if (ex instanceof UsernameNotFoundException) {
            errorResponse = process(Error.USER_NOT_FOUND, ex);
        }
        if (ex instanceof BadCredentialsException) {
            errorResponse = process(Error.INCORRECT_CREDENTIALS, ex);
        }
        if (ex instanceof LockedException) {
            errorResponse = process(Error.USER_IS_BLOCKED, ex);
        }
        if (errorResponse == null) {
            errorResponse = process(Error.AUTHENTICATION_FAILURE, ex);
        }
        return Collections.singletonList(errorResponse);
    }

    /**
     * Обработка исключения {@link ErrorException}.
     *
     * @param ex {@link ErrorException}.
     * @return список {@link ErrorResponse}.
     */
    @ExceptionHandler(value = ErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleException(ErrorException ex) {
        return ex.getErrors().stream().map(it -> process(it, ex)).collect(Collectors.toList());
    }

    /**
     * Обработка исключения {@link Exception}.
     *
     * @param ex {@link Exception}.
     * @return список {@link ErrorResponse}.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public List<ErrorResponse> handleException(Exception ex) {
        return Collections.singletonList(process(Error.INTERNAL_ERROR, ex));
    }

    /**
     * Генерация сообщения об ошибке.
     *
     * @param error {@link Error}.
     * @param ex    исключение
     * @return {@link ErrorResponse}.
     */
    private ErrorResponse process(Error error, Exception ex) {
        return ErrorResponse.of(error.getCode(), error.getMessage(), error.getData().toJavaList(), ExceptionUtils.getStackTrace(ex));
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
         * Дополнительные данные.
         */
        private List<String> data;
        /**
         * Стектрейс ошибки.
         */
        private String cause;

    }

}
