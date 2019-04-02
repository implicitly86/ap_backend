package com.implicitly.security;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Компонент хранящий данные пользователя {@link UserDetails}.
 *
 * @author Emil Murzakaev.
 */
public class SecurityTokenHolder {

    private static final ThreadLocal<Pair<UserDetails, String>> tokenHolder = new ThreadLocal<>();

    public static void setToken(UserDetails user, String token) {
        Pair<UserDetails, String> userToken = tokenHolder.get();
        if (userToken == null) {
            tokenHolder.set(Pair.of(user, token));
        }
    }

    public static String getToken() {
        Pair<UserDetails, String> userToken = tokenHolder.get();
        if (userToken != null) {
            return userToken.getValue();
        }
        return null;
    }

}
