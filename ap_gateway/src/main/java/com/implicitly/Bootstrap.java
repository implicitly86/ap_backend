package com.implicitly;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Emil Murzakaev.
 */
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Bootstrap.class)
                .run(args);
    }

}
