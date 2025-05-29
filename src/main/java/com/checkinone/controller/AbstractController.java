package com.checkinone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import com.checkinone.security.CheckInOneOAuth2User;

/**
 * @author Anthonini
 */
public class AbstractController implements Controller {
	
	@Autowired
	private MessageSource messageSource;
	
	protected String getMessage(String message, Object... parametros) {
		return messageSource.getMessage(message, parametros, LocaleContextHolder.getLocale());
	}
	
	public CheckInOneOAuth2User getUsuarioLogado() {
		return (CheckInOneOAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
