package com.checkinone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author Anthonini
 *
 */
@Configuration
public class SecurityConfig {
	
	@Value("${application.auth-server.logout-url}")
	private String logoutUrl;

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
     		.authorizeHttpRequests(authz -> authz
     			.requestMatchers("/images/**", "/layout/**", "/login/**").permitAll()
     			.anyRequest().authenticated()
     		)
     		.logout(l -> l.addLogoutHandler(this::logoutHandler))
			.oauth2Login(o -> o.loginPage("/login"));
        
        return http.build();
    }
	
	private void logoutHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			response.sendRedirect(logoutUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
