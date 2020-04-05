/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.security;

import com.implicitly.domain.security.Role;
import com.implicitly.domain.security.User;
import com.implicitly.utils.gson.GsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Принципал пользователя.
 *
 * @author Emil Murzakaev.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPrincipal implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Пароль пользователя.
     */
    @GsonIgnore
    private String password;

    /**
     * Коллекция разрешений пользователя.
     */
    private List<Role> roles;

    /**
     * Метод создания принципала по пользователю.
     *
     * @param user пользоватеь.
     * @return принципал пользователя.
     */
    public static UserPrincipal of(User user) {
        return new UserPrincipal(user.getId(), user.getName(), user.getPassword(), user.getRoles());
    }

    /**
     * {@link UserDetails#getAuthorities()}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * {@link UserDetails#getPassword()}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * {@link UserDetails#getUsername()}
     */
    @Override
    public String getUsername() {
        return name;
    }

    /**
     * {@link UserDetails#isAccountNonExpired()}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@link UserDetails#isAccountNonLocked()}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * {@link UserDetails#isCredentialsNonExpired()}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@link UserDetails#isEnabled()}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Получение {@link User} из объекта {@link UserPrincipal}.
     *
     * @return {@link User}
     */
    public User getUser() {
        return User.builder()
                .id(id)
                .name(name)
                .password(password)
                .roles(roles)
                .build();
    }

}
