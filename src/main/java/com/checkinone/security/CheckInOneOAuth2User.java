package com.checkinone.security;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 
 * @author Anthonini
 *
 */
public class CheckInOneOAuth2User extends DefaultOAuth2User {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	
	public CheckInOneOAuth2User(OAuth2User OAuth2User, String nameAttributeKey, String nome, String email) {
		super(OAuth2User.getAuthorities(), OAuth2User.getAttributes(), nameAttributeKey);
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}
}
