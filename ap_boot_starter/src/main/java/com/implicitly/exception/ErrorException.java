/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Бизнес исключение.
 *
 * @author Emil Murzakaev.
 */
@Getter
public class ErrorException extends RuntimeException {

    /**
     * Перечень ошибок.
     */
    private final List<Error> errors = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param errors сообщения об ошибках
     */
    public ErrorException(Error... errors) {
        if (errors != null) {
            Collections.addAll(this.errors, errors);
        }
    }

}
