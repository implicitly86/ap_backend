/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.notification;

import com.implicitly.domain.IdentifiedEntity;
import com.implicitly.utils.converter.LocalDateTimeConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Доменная модель сущности <strong>Уведомление</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Entity
@Table(name = "`notification`")
public class Notification implements IdentifiedEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7097637332692025474L;

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_gen")
    @SequenceGenerator(name = "notification_gen", sequenceName = "sq_notification", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Заголовок уведомления.
     */
    @Column(name = "title")
    private String title;

    /**
     * Текст уведомления.
     */
    @Column(name = "message")
    private String message;

    /**
     * Дата создания уведомления.
     */
    @Column(name = "createddt", nullable = false, columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

}
