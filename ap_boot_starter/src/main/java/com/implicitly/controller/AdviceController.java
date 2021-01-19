/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import com.implicitly.exception.Error;
import com.implicitly.exception.ErrorException;
import com.implicitly.exception.ErrorResponse;
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
    public ErrorResponse handleAuthException(AuthenticationException ex) {
        ErrorResponse errorResponse = null;
        if (ex instanceof UsernameNotFoundException) {
            errorResponse = process(Error.USER_NOT_FOUND);
        }
        if (ex instanceof BadCredentialsException) {
            errorResponse = process(Error.INCORRECT_CREDENTIALS);
        }
        if (ex instanceof LockedException) {
            errorResponse = process(Error.USER_IS_BLOCKED);
        }
        if (errorResponse == null) {
            errorResponse = process(Error.AUTHENTICATION_FAILURE);
        }
        return errorResponse;
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
    public ErrorResponse handleException(ErrorException ex) {
        return process(ex.getError());
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
    public ErrorResponse handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return process(Error.INTERNAL_ERROR, ex);
    }

    /**
     * Генерация сообщения об ошибке.
     *
     * @param error {@link Error}.
     * @return {@link ErrorResponse}.
     */
    private ErrorResponse process(Error error) {
        return ErrorResponse.of(error.getCode(), error.getMessage(), error.getData().toJavaList(), null);
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

}
