package com.checkinone.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Anthonini
 *
 */
@Service
public class CheckInOneOAuth2UserService extends DefaultOAuth2UserService {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		DefaultOAuth2User oAuth2User = (DefaultOAuth2User) super.loadUser(userRequest);
		String userNameAttributeName = getUserNameAttributeName(userRequest);
		
		String email = (String)oAuth2User.getAttributes().get("sub");
		String nome = (String)oAuth2User.getAttributes().get(userNameAttributeName);
		
		List<GrantedAuthority> authorities = getAuthorities(oAuth2User);
		
		return new CheckInOneOAuth2User(oAuth2User, authorities, userNameAttributeName, nome, email);
	}
	
	private List<GrantedAuthority> getAuthorities(DefaultOAuth2User oAuth2User) {
		List<String> userAuthorities = new ArrayList<>(oAuth2User.getAttribute("authorities"));
		List<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
		userAuthorities.forEach(a -> authorities.add(new SimpleGrantedAuthority(a.toUpperCase())));
		
		return authorities;
	}

	private String getUserNameAttributeName(OAuth2UserRequest userRequest) {
		return userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
	}
}