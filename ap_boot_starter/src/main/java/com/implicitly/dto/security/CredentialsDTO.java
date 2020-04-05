/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.security;

import lombok.Data;

/**
 * Данные пользователя.
 *
 * @author Emil Murzakaev.
 */
@Data
public class CredentialsDTO {

    /**
     * Логин.
     */
    private String username;

    /**
     * Пароль.
     */
    private String password;

}
