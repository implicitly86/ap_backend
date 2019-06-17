/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.notification.service.impl;

import com.implicitly.dto.notification.NotificationDTO;
import com.implicitly.notification.persistence.NotificationRepository;
import com.implicitly.notification.service.NotificationService;
import com.implicitly.utils.mapper.notification.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Emil Murzakaev.
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    /**
     * {@link NotificationRepository}
     */
    private final NotificationRepository repository;

    /**
     * {@link NotificationMapper}
     */
    private final NotificationMapper mapper;

    /**
     * Создание уведомления.
     *
     * @param notification данные уведомления.
     */
    @Override
    public void createNotification(NotificationDTO notification) {
        // TODO: implement
    }

    /**
     * Получение списка уведомлений текущего пользователя.
     *
     * @return список уведомлений в виде {@link NotificationDTO}.
     */
    @Override
    public List<NotificationDTO> getNotifications() {
        // TODO: implement
        return Collections.emptyList();
    }

}
