/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import com.implicitly.dto.security.UserDTO;
import com.implicitly.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Реализация {@link RestController}, обслуживающая запросы к сервису {@link AdminService}.
 *
 * @author Emil Murzakaev.
 */
@RestController
public class AdminController {

    /**
     * {@link AdminService}
     */
    private final AdminService adminService;

    /**
     * Конструктор.
     *
     * @param adminService {@link AdminService}
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Получение списка всех пользователей, зарегистрированных в системе.
     *
     * @param pageable {@link Pageable}
     * @return {@link Page<UserDTO>}
     */
    @GetMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<UserDTO>> getUsers(Pageable pageable) {
        return ok(adminService.getUsers(pageable));
    }

    /**
     * Сохранение нового пользователя.
     *
     * @param user {@link UserDTO}
     * @return {@link UserDTO}
     */
    @PostMapping(value = "/admin/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user) {
        return ok(adminService.saveUser(user));
    }

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя.
     */
    @DeleteMapping(value = "/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUser(id);
        return noContent().build();
    }

}
