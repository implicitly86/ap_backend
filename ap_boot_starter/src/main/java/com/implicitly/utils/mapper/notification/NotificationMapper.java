/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.mapper.notification;

import com.implicitly.domain.notification.Notification;
import com.implicitly.dto.notification.NotificationDTO;
import com.implicitly.utils.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link EntityMapper} для сущности {@link Notification}
 *
 * @author Emil Murzakaev.
 */
@Component
@RequiredArgsConstructor
public class NotificationMapper implements EntityMapper<Notification, NotificationDTO> {

    /**
     * Маппинг Entity -> Dto.
     *
     * @param source доменная сущность.
     * @return модель передачи данных, соответствующая доменной модели.
     */
    @Override
    public NotificationDTO toDto(Notification source) {
        if (source == null) {
            return null;
        }
        NotificationDTO target = new NotificationDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * Маппинг Dto -> Entity.
     *
     * @param source модель передачи данных, соответствующая доменной модели.
     * @return доменная сущность.
     */
    @Override
    public Notification toEntity(NotificationDTO source) {
        if (source == null) {
            return null;
        }
        Notification target = new Notification();
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
