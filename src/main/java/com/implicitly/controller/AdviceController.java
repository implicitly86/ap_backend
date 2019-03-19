/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import com.implicitly.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static com.implicitly.constants.Constants.BODY;
import static com.implicitly.constants.Constants.MESSAGE;

/**
 * {@link ControllerAdvice}.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@ControllerAdvice
public class AdviceController {

    /**
     * Обработка исключений возвращающих HTTP статус 500
     *
     * @param ex исключение
     * @return сообщение об ошибке
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleException(Exception ex) {
        return process(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка исключения {@link NotFoundException}
     *
     * @param ex {@link NotFoundException}
     */
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(NotFoundException ex) {
    }

    /**
     * Обработка исключения {@link AccessDeniedException}
     *
     * @param ex {@link AccessDeniedException}
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(AccessDeniedException ex) {
    }

    /**
     *
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map<String, String> handleAuthException(BadCredentialsException ex) {
        Map<String, String> result = new HashMap<>();
        result.put(MESSAGE, ex.toString());
        result.put(BODY, "");
        log.warn("Failure authentication attempt");
        return result;
    }

    /**
     * Генерация сообщения об ошибке.
     *
     * @param ex     исключение
     * @param status {@link HttpStatus}
     * @return данные  ошибки
     */
    private Map<String, String> process(Exception ex, HttpStatus status) {
        Map<String, String> result = new HashMap<>();
        result.put(MESSAGE, ex.toString());
        result.put(BODY, ExceptionUtils.getStackTrace(ex));
        log.error(ex.getMessage(), ex);
        return result;
    }

}
