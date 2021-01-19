/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.exception;

import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import lombok.Getter;

/**
 * Перечисление бизнес ошибок приложения.
 *
 * @author Emil Murzakaev.
 */
@Getter
public enum Error {

    USER_NOT_FOUND("U0001", "Пользователь не найден"),
    INCORRECT_CREDENTIALS("U0002", "Неверный логин или пароль"),
    USER_IS_BLOCKED("U0003", "Пользователь заблокирован"),
    CUSTOMER_NOT_FOUND("C0001", "Клиент с заданным идентификатором не найден"),
    CUSTOMER_NOT_VALID("C0002", "Некорректные данные клиента"),
    DELIVERY_POINT_NOT_FOUND("D0001", "Пункт отправки/доставки с заданным идентификатором не найден"),
    ORDER_NOT_FOUND("O0001", "Заказ с заданным идентификатором не найден"),
    AUTHENTICATION_FAILURE("S0001", "Ошибка аутентификации"),
    UNAUTHORIZED("S0002", "Необходима авторизация"),
    INTERNAL_ERROR("I0001", "Внутренняя ошибка сервера");

    /**
     * Код ошибки.
     */
    private final String code;
    /**
     * Сообщение ошибки.
     */
    private final String message;
    /**
     * Дополнительные данные.
     */
    private Seq<String> data;

    /**
     * Конструктор.
     *
     * @param code    код ошибки.
     * @param message сообщение ошибки.
     */
    Error(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = Array.empty();
    }

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

    /**
     * Доюавление дополнительных данных.
     *
     * @param data дополнительные данные.
     */
    public void addData(Seq<String> data) {
        this.data = data;
    }

}
