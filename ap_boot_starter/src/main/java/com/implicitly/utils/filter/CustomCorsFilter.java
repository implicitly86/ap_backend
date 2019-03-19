/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Реализация {@link Filter} для разрешения CORS.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Component
public class CustomCorsFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        log.debug("handle request : {} {}", request.getMethod(), request.getRequestURL());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type, authorization");
        chain.doFilter(req, res);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("init cors filter");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        log.debug("destroy cors filter");
    }

}
