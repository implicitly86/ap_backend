/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto.notification;

import com.implicitly.dto.IdentifiedDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель передачи данных сущности <strong>Уведомление</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
public class NotificationDTO implements IdentifiedDTO {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3450577692365677169L;

    /**
     * Уникальный идентификатор.
     */
    private Long id;

    /**
     * Заголовок уведомления.
     */
    private String title;

    /**
     * Текст уведомления.
     */
    private String message;

    /**
     * Дата создания уведомления.
     */
    private LocalDateTime createdDate;

}
