/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.notification.persistence;

import com.implicitly.domain.notification.Notification;
import com.implicitly.persistence.BaseRepository;

/**
 * Репозиторий доступа к сущности {@link Notification}
 *
 * @author Emil Murzakaev.
 */
public interface NotificationRepository extends BaseRepository<Notification, Long> {
}
