/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.security;

import com.implicitly.dto.IdentifiedDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель передачи данных сущности <strong>Роль</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@EqualsAndHashCode
public class RoleDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8839292294470944560L;

    /**
     * Уникальный идентификатор.
     */
    private Long id;

    /**
     * Название роли.
     */
    private String name;

}
