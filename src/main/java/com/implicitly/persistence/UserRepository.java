/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.persistence;

import com.implicitly.domain.User;
import org.springframework.stereotype.Component;

/**
 * Маппер для сущности {@link User} - пользователь.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * Поиск пользователя по имени.
     */
    User findByName(String userName);

}
