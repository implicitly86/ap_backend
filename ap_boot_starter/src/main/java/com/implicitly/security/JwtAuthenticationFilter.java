/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.security;

import com.implicitly.constants.Constants;
import com.implicitly.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Реализация {@link OncePerRequestFilter} для обработки токенов авторизации в запросе.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * {@link JwtTokenProvider}
     */
    private JwtTokenProvider tokenProvider;

    /**
     * {@link CustomUserDetailsService}
     */
    private CustomUserDetailsService userDetailsService;

    /**
     * Внедрение зависимости {@link JwtAuthenticationFilter#tokenProvider}
     */
    @Autowired
    public void setTokenProvider(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Внедрение зависимости {@link JwtAuthenticationFilter#userDetailsService}
     */
    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * {@link OncePerRequestFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = userDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                SecurityTokenHolder.setToken(userDetails, jwt);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Получение токена из запроса.
     *
     * @param request запрос.
     * @return токен.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
            return bearerToken.substring(Constants.BEARER.length() + 1);
        }
        return null;
    }

}
