/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.notification.service;

import com.implicitly.dto.notification.NotificationDTO;

import java.util.List;

/**
 * Сервис уведомлений.
 *
 * @author Emil Murzakaev.
 */
public interface NotificationService {

    /**
     * Создание уведомления.
     *
     * @param notification данные уведомления.
     */
    void createNotification(NotificationDTO notification);

    /**
     * Получение списка уведомлений текущего пользователя.
     *
     * @return список уведомлений в виде {@link NotificationDTO}.
     */
    List<NotificationDTO> getNotifications();

}
