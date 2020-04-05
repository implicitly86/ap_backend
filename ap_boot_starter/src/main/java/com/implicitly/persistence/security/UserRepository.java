/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.persistence.security;

import com.implicitly.domain.security.User;
import com.implicitly.persistence.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Репозиторий сущности {@link User}.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * Поиск пользователя по имени.
     */
    Optional<User> findByName(String userName);

}
