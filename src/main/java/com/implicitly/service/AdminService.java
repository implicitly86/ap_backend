/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service;

import com.implicitly.dto.security.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис, отвечающий за выполнение административных функций.
 *
 * @author Emil Murzakaev.
 */
public interface AdminService {

    /**
     * Получение списка всех пользователей, зарегистрированных в системе.
     *
     * @param pageable {@link Pageable}
     * @return {@link Page<UserDTO>}
     */
    Page<UserDTO> getUsers(Pageable pageable);

    /**
     * Сохранение нового пользователя.
     *
     * @param user {@link UserDTO}
     * @return {@link UserDTO}
     */
    UserDTO saveUser(UserDTO user);

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя.
     */
    void deleteUser(Long id);

}
