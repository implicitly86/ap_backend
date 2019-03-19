/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils;

import com.implicitly.domain.security.User;
import com.implicitly.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Утилитарный класс, предназначенный для получения текущего аутентифицированного пользователя.
 *
 * @author Emil Murzakaev.
 */
@Component
public final class UserUtils {

    /**
     * Приватный конструктор.
     */
    private UserUtils() {
    }

    /**
     * Получение идентификатора текущего аутентифицированного пользователя.
     *
     * @return {@link Optional<Long>}
     */
    public static Optional<Long> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return Optional.ofNullable(userPrincipal.getId());
        }
        return Optional.empty();
    }

    /**
     * Получение текущего аутентифицированного пользователя.
     *
     * @return {@link Optional<User>}
     */
    public static Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return Optional.ofNullable(userPrincipal.getUser());
        }
        return Optional.empty();
    }

    /**
     * Получение деталей аутентификации текущего пользователя.
     *
     * @return {@link Optional<WebAuthenticationDetails>}
     */
    public static Optional<WebAuthenticationDetails> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            return Optional.ofNullable(details);
        }
        return Optional.empty();
    }

}
