/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration;

import com.implicitly.constants.Constants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * Конфигурация подключения к БД.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Configuration
@EntityScan(basePackages = {Constants.ENTITY_BASE_PACKAGE})
@EnableJpaRepositories(basePackages = {Constants.REPOSITORY_BASE_PACKAGE})
@EnableCaching
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableConfigurationProperties({DataSourceProperties.class, JpaProperties.class, RedisProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class JpaConfiguration {

    /**
     * Тег используемый при логировании.
     */
    private static final String LOG_TAG = "[DATA_SOURCE_CONFIGURATION] ::";

    /**
     * {@link DataSourceProperties}.
     */
    private final DataSourceProperties dataSourceProperties;
    /**
     * {@link RedisProperties}
     */
    private final RedisProperties redisProperties;

    /**
     * {@link PostConstruct}
     */
    @PostConstruct
    public void init() {
        if (log.isInfoEnabled()) {
            log.info(
                    "{} has been initialized",
                    LOG_TAG
            );
            log.info(
                    "{} database url : {}, database user : {}",
                    LOG_TAG,
                    dataSourceProperties.getUrl(),
                    dataSourceProperties.getUsername()
            );
            log.info(
                    "{} redis hostname : {}, port : {}",
                    LOG_TAG,
                    redisProperties.getHost(),
                    redisProperties.getPort()
            );
        }
    }

    /**
     * {@link HikariDataSource}
     */
    @Bean(name = "dataSource")
    public HikariDataSource dataSource() {
        return (HikariDataSource) dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * {@link PageableHandlerMethodArgumentResolver}
     */
    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }

    /**
     * {@link DataSourceTransactionManager}.
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * {@link SortHandlerMethodArgumentResolver}
     */
    @Bean
    public SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver() {
        return new SortHandlerMethodArgumentResolver();
    }

}
