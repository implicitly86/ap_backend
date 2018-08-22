/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.security;

import com.implicitly.dto.IdentifiedDTO;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель передачи данных сущности <strong>Пользователь</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@EqualsAndHashCode
public class UserDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1102167282001305540L;

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Набор ролей пользователя.
     */
    private List<RoleDTO> roles;

}
