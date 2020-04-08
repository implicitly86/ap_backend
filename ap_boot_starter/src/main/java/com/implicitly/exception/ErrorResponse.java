package com.implicitly.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Модель, содержащая информацию об ошибке.
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {

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
