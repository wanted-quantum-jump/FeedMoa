package com.skeleton.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request
                .requestMatchers("*").permitAll()
        );

        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement(securitySessionManagementConfigurer ->
                securitySessionManagementConfigurer.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );

        return http.build();
    }
}
