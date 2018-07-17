/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.domain.User;
import com.implicitly.persistence.UserRepository;
import com.implicitly.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link UserDetailsService}.
 *
 * @author Emil Murzakaev.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * {@link UserRepository}
     */
    private final UserRepository userRepository;

    /**
     * Конструктор.
     *
     * @param userRepository {@link UserRepository}
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Поиск пользователя по имени.
     *
     * @param username имя пользователя.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email : " + username);
        }
        return UserPrincipal.create(user);
    }

    /**
     * Поиск пользователя по уникальному идентификатору.
     *
      @param id уникальный идентификатор пользователя.
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id : " + id);
        }
        return UserPrincipal.create(user);
    }

}
