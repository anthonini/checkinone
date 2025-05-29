package com.checkinone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 * @author Anthonini
 *
 */
@Configuration
public class SecurityConfig {

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
     		.authorizeHttpRequests(authz -> authz
     			.requestMatchers("/images/**", "/layout/**", "/login/**").permitAll()
     			.anyRequest().authenticated()
     		)
			.oauth2Login(Customizer.withDefaults());
        
        return http.build();
    }
}
