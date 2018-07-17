/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Конфигурация подключения к БД.
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Configuration
@EntityScan(basePackages = {"com.implicitly.domain"})
@EnableJpaRepositories(basePackages = {"com.implicitly.persistence"})
@EnableConfigurationProperties({DataSourceProperties.class, JpaProperties.class})
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
     * {@link JpaProperties}
     */
    private final JpaProperties jpaProperties;

    /**
     * Конструктор.
     *
     * @param dsProperties {@link DataSourceProperties}
     * @param jpaProperties {@link JpaProperties}
     */
    @Autowired
    public JpaConfiguration(DataSourceProperties dsProperties, JpaProperties jpaProperties) {
        this.dataSourceProperties = dsProperties;
        this.jpaProperties = jpaProperties;
    }

    @PostConstruct
    public void init() {
        if (log.isInfoEnabled()) {
            log.info(
                    "{} has been initialized.",
                    LOG_TAG
            );
            log.info(
                    "{} URL - {}, User - {}",
                    LOG_TAG,
                    dataSourceProperties.getUrl(),
                    dataSourceProperties.getUsername()
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
