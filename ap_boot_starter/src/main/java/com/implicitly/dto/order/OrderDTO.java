/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.order;

import com.implicitly.dto.IdentifiedDTO;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.dto.security.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель передачи данных сущности <strong>Заказ</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
public class OrderDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4635758436837920497L;

    /**
     * Уникальный идентификатор.
     */
    private Long id;

    /**
     * Штрих-код заказа.
     */
    private String barcode;

    /**
     * Клиент.
     */
    private CustomerDTO customer;

    /**
     * Пункт приема заказа.
     */
    private DeliveryPointDTO fromPoint;

    /**
     * Пункт доставки заказа.
     */
    private DeliveryPointDTO toPoint;

    /**
     * Дата создания заказа.
     */
    private LocalDateTime createdDate;

    /**
     * Пользователь, создавший запись.
     */
    private UserDTO author;

    /**
     * Дата изменения записи.
     */
    private LocalDateTime modifiedDate;

    /**
     * Пользователь, изменивший запись.
     */
    private UserDTO modifiedBy;

}
