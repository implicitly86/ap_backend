/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Бизнес исключение.
 *
 * @author Emil Murzakaev.
 */
@Getter
@RequiredArgsConstructor
public class ErrorException extends RuntimeException {

    /**
     * Перечень ошибок.
     */
    private final Error error;

}
