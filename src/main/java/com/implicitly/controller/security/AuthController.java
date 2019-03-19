/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller.security;

import com.implicitly.dto.security.CredentialsDTO;
import com.implicitly.dto.security.TokenDTO;
import com.implicitly.persistence.security.UserRepository;
import com.implicitly.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Конструктор.
     *
     * @param authenticationManager {@link AuthenticationManager}
     * @param userRepository        {@link UserRepository}
     * @param passwordEncoder       {@link PasswordEncoder}
     * @param tokenProvider         {@link JwtTokenProvider}
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

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
