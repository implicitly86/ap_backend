/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.customer;

import com.implicitly.dto.IdentifiedDTO;
import com.implicitly.dto.security.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель передачи данных сущности <strong>Клиент</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
public class CustomerDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1501124104307193379L;

    /**
     * Уникальный идентификатор.
     */
    private Long id;

    /**
     * Название клиента.
     */
    private String name;

    /**
     * Имя клиента.
     */
    private String firstName;

    /**
     * Фамилия клиента.
     */
    private String lastName;

    /**
     * Отчество клиента.
     */
    private String middleName;

    /**
     * Адрес клиента.
     */
    private String address;

    /**
     * Телефон клиента.
     */
    private String phone;

    /**
     * Почта клиента.
     */
    private String email;

    /**
     * Тип клиента.
     */
    private CustomerType type;

    /**
     * Дата создания.
     */
    private LocalDateTime createdDate;

    /**
     * Идентификатор пользователя, создавшего запись.
     */
    private UserDTO author;

}
