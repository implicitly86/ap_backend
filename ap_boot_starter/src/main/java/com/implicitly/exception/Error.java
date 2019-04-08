/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление бизнес ошибок приложения.
 *
 * @author Emil Murzakaev.
 */
@Getter
@AllArgsConstructor
public enum Error {

    USER_NOT_FOUND("U0001", "Пользователь не найден"),
    INCORRECT_CREDENTIALS("U0002", "Неверный логин или пароль"),
    USER_IS_BLOCKED("U0003", "Пользователь заблокирован"),
    CUSTOMER_NOT_FOUND("C0001", "Клиент с заданным идентификатором не найден"),
    DELIVERY_POINT_NOT_FOUND("D0001", "Пункт отправки/доставки с заданным идентификатором не найден"),
    ORDER_NOT_FOUND("O0001", "Заказ с заданным идентификатором не найден"),
    AUTHENTICATION_FAILURE("S0001", "Ошибка аутентификации"),
    UNAUTHORIZED("S0002", "Необходима авторизация"),
    INTERNAL_ERROR("I0001", "Внутренняя ошибка сервера");

    /**
     * Код ошибки.
     */
    private String code;
    /**
     * Сообщение ошибки.
     */
    private String message;

    /**
     * Создание экземпляра {@link ErrorException}.
     *
     * @return {@link ErrorException}.
     */
    public ErrorException exception() {
        return new ErrorException(this);
    }

    /**
     * Выбросить исключение.
     *
     * @throws ErrorException .
     */
    public void throwException() {
        throw exception();
    }

    /**
     * Выбросить исключение, если условие истино.
     *
     * @throws ErrorException в случае, если условие истино.
     */
    public void throwIfTrue(boolean condition) {
        if (condition) {
            throwException();
        }
    }

    /**
     * Выбросить исключение, если условие ложно.
     *
     * @throws ErrorException в случае, если условие ложно.
     */
    public void throwIfFalse(boolean condition) {
        if (!condition) {
            throwException();
        }
    }

}
