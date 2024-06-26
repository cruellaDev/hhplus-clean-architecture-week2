package com.io.hhplus.registerlecture.global.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * @link <a href="https://dukcode.github.io/spring/h2-console-with-spring-security/">참고URL</a>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @EnableWebSecurity
    @Configuration
    public static class SecurityConfiguration {
        @Bean
        @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
        public WebSecurityCustomizer configureH2ConsoleEnable() {
            return web -> web.ignoring()
                    .requestMatchers(PathRequest.toH2Console());
        }

    }
}
