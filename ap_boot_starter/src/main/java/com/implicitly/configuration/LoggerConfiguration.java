package com.implicitly.configuration;

import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Emil Murzakaev.
 */
@Configuration
public class LoggerConfiguration {

    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";

    @Value("${elk.host:localhost}")
    String host;

    @Value("${elk.port:5044}")
    String port;

    @Value("${spring.application.name:-}")
    String name;

    @Bean
    public LogstashTcpSocketAppender logstashAppender() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName(LOGSTASH_APPENDER_NAME);
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(host + ":" + port);
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(loggerContext);
        encoder.setIncludeContext(true);
        encoder.setFindAndRegisterJacksonModules(false);
        encoder.setCustomFields("{\"appname\":\"" + name + "\"}");
        encoder.start();
        logstashTcpSocketAppender.setEncoder(encoder);
        logstashTcpSocketAppender.start();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(logstashTcpSocketAppender);
        return logstashTcpSocketAppender;
    }

}
