/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import static com.implicitly.constants.Constants.BODY;
import static com.implicitly.constants.Constants.MESSAGE;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
     * @param ex исключение
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
