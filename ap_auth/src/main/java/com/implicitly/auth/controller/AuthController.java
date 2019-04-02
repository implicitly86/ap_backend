/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.auth.controller;

import com.implicitly.dto.security.CredentialsDTO;
import com.implicitly.dto.security.TokenDTO;
import com.implicitly.persistence.security.UserRepository;
import com.implicitly.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Реализация {@link RestController}, обслуживающая запросы безопасности приложения.
 *
 * @author Emil Murzakaev.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    /**
     * {@link AuthenticationManager}
     */
    private final AuthenticationManager authenticationManager;
    /**
     * {@link UserRepository}
     */
    private final UserRepository userRepository;
    /**
     * {@link PasswordEncoder}
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * {@link JwtTokenProvider}
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * Аутентификация пользователя.
     *
     * @param credentials данные пользователя.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticateUser(@Valid @RequestBody CredentialsDTO credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ok(TokenDTO.builder().accessToken(jwt).build());
    }

}
