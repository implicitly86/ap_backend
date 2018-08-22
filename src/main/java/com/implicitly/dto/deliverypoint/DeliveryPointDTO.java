/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.deliverypoint;

import com.implicitly.dto.IdentifiedDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель передачи данных сущности <strong>Пункт отправки/доставки</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@EqualsAndHashCode
public class DeliveryPointDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2722352840344828674L;

    /**
     * Уникальный идентификатор.
     */
    private Long id;

    /**
     * Название точки доставки.
     */
    private String name;

    /**
     * Адрес точки доставки.
     */
    private String address;

    /**
     * Почтовый индекс точки доставки.
     */
    private String postcode;

    /**
     * Телефон точки доставки.
     */
    private String phone;

    /**
     * Почта точки доставки.
     */
    private String email;

}
