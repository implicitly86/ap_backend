/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Данные пользователя.
 *
 * @author Emil Murzakaev.
 */
@Getter
@Setter
@Builder
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
