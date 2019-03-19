/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.domain.security.User;
import com.implicitly.dto.security.UserDTO;
import com.implicitly.exceptions.NotFoundException;
import com.implicitly.persistence.security.UserRepository;
import com.implicitly.service.AdminService;
import com.implicitly.utils.mapper.security.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса, отвечающего за выполнение административных функций.
 *
 * @author Emil Murzakaev.
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * {@link UserRepository}
     */
    private final UserRepository repository;
    /**
     * {@link UserMapper}
     */
    private final UserMapper mapper;
    /**
     * {@link PasswordEncoder}
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор.
     *
     * @param repository {@link UserRepository}.
     * @param mapper     {@link UserMapper}.
     */
    @Autowired
    public AdminServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Получение списка всех пользователей, зарегистрированных в системе.
     *
     * @param pageable {@link Pageable}
     * @return {@link Page<UserDTO>}
     */
    @Override
    @PreAuthorize("hasAuthority('admin')")
    public Page<UserDTO> getUsers(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    /**
     * Сохранение нового пользователя.
     *
     * @param user {@link UserDTO}
     * @return {@link UserDTO}
     */
    @Override
    @PreAuthorize("hasAuthority('admin')")
    public UserDTO saveUser(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = repository.saveAndFlush(mapper.toEntity(user));
        return mapper.toDto(result);
    }

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя.
     */
    @Override
    @PreAuthorize("hasAuthority('admin')")
    public void deleteUser(Long id) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        repository.delete(id);
    }

}
