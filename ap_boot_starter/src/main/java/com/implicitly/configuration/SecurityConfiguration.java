/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.implicitly.exception.Error;
import com.implicitly.exception.ErrorResponse;
import com.implicitly.security.JwtAuthenticationFilter;
import com.implicitly.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Конфигурация безопасности приложения.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Тег используемый при логировании.
     */
    private static final String LOG_TAG = "[SECURITY_CONFIGURATION] ::";

    /**
     * {@link CustomUserDetailsService}
     */
    private final CustomUserDetailsService userDetailsService;
    /**
     * {@link JacksonConfiguration#objectMapper()}
     */
    private final ObjectMapper objectMapper;

    /**
     * {@link PostConstruct}
     */
    @PostConstruct
    public void init() {
        if (log.isInfoEnabled()) {
            log.info(
                    "{} has been initialized.",
                    LOG_TAG
            );
        }
    }

    /**
     * {@link WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * {@link WebSecurityConfigurerAdapter#configure(HttpSecurity)}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // formatter: off
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/")
                .permitAll()
                .antMatchers(unsecuredEndpoints())
                .permitAll()
                .anyRequest()
                .authenticated();
        // formatter: on
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Эндпоинты, доступные без аутентификации.
     *
     * @return масиив эндпоинтов.
     */
    private String[] unsecuredEndpoints() {
        return new String[]{
                "/auth/login",
                "/actuator",
                "/actuator/**",
                "/**/api-docs",
                "/swagger**",
                "/swagger-resources/**",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        };
    }

    /**
     * Конфигурация CORS.
     *
     * @return {@link CorsConfigurationSource}
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(List.of("x-requested-with", "content-type", "authorization"));
        corsConfiguration.setAllowedMethods(List.of("POST", "GET", "PUT", "OPTIONS", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.setAlwaysUseFullPath(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * {@link WebSecurityConfigurerAdapter#authenticationManagerBean()}
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * {@link JwtAuthenticationFilter}
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * {@link AccessDeniedHandler}
     */
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            ErrorResponse errorResponse = ErrorResponse.of(Error.UNAUTHORIZED.getCode(), Error.UNAUTHORIZED.getMessage());
            writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, errorResponse);
        };
    }

    /**
     * {@link AuthenticationEntryPoint}
     */
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.info("unauthorized request : {} {}", request.getMethod(), request.getRequestURL());
            ErrorResponse errorResponse = ErrorResponse.of(Error.UNAUTHORIZED.getCode(), Error.UNAUTHORIZED.getMessage());
            writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, errorResponse);
        };
    }

    @SneakyThrows
    private void writeErrorResponse(HttpServletResponse response, int statusCode, ErrorResponse error) {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), error);
    }

}
