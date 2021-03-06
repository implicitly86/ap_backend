/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.implicitly.utils.gson.GsonIgnoreStrategy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Провайдер работы с токенами.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * Приватный ключ шифрования.
     */
    @Value("${security.jwtSecret}")
    private String jwtSecret;

    /**
     * Срок действия токена в мс.
     */
    @Value("${security.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    /**
     * Генерация токена.
     *
     * @param authentication {@link Authentication}.
     * @return токен.
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Gson gson = new GsonBuilder().setExclusionStrategies(new GsonIgnoreStrategy()).create();
        return Jwts.builder()
                .setSubject(gson.toJson(userPrincipal))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Получение уникального идентификатора пользователя из токена.
     *
     * @param token токен.
     * @return уникальный идентификатор пользователя.
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        UserPrincipal userPrincipal = new Gson().fromJson(claims.getSubject(), UserPrincipal.class);
        return userPrincipal.getId();
    }

    /**
     * Валидация токена.
     *
     * @param authToken токен.
     * @return true, если токен валиден, false - иначе.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
