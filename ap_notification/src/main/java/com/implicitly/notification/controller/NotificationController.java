/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.notification.controller;

import com.implicitly.dto.notification.NotificationDTO;
import com.implicitly.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Реализация {@link RestController}, обслуживающая запросы к сервису уведомлений.
 *
 * @author Emil Murzakaev.
 */
@RestController
@RequiredArgsConstructor
public class NotificationController {

    /**
     * {@link NotificationService}
     */
    private final NotificationService notificationService;

    /**
     * Создание уведомления.
     *
     * @param notification данные уведомления.
     */
    @PostMapping(value = "/notification", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createNotification(NotificationDTO notification) {
        notificationService.createNotification(notification);
    }

    /**
     * Получение списка уведомлений текущего пользователя.
     *
     * @return список уведомлений в виде {@link NotificationDTO}.
     */
    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<NotificationDTO> getNotifications() {
        return notificationService.getNotifications();
    }

}
