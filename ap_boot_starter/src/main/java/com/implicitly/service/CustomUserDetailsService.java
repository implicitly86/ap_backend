/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service;

import com.implicitly.constants.Constants;
import com.implicitly.domain.security.User;
import com.implicitly.persistence.security.UserRepository;
import com.implicitly.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserDetailsService}.
 *
 * @author Emil Murzakaev.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * {@link UserRepository}
     */
    private final UserRepository userRepository;

    /**
     * Поиск {@link UserDetails} по имени.
     *
     * @param username имя пользователя.
     * @return {@link UserDetails}
     */
    @Override
    @Cacheable(value = Constants.CACHE_USER_BY_NAME, key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() ->
                new UsernameNotFoundException("UserDTO not found with username or email : " + username)
        );
        return UserPrincipal.of(user);
    }

    /**
     * Поиск {@link UserDetails} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return {@link UserDetails}
     */
    @Cacheable(value = Constants.CACHE_USER_BY_ID, key = "#id")
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("UserDTO not found with id : " + id)
        );
        return UserPrincipal.of(user);
    }

}
