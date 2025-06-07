package com.checkinone.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import com.checkinone.exception.ExcecaoIntegridadeDadosRemocao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AppControllerAdvice {

	@ModelAttribute("servletPath")
	String getRequestServletPath(HttpServletRequest request) {
		return request.getServletPath();
	}

	@ExceptionHandler({ HttpClientErrorException.Forbidden.class } )
	public String handleHttpClientErrorForbiddenException(HttpClientErrorException ex, WebRequest request) {
		return "error/403";
	}
	
	@ExceptionHandler({ HttpClientErrorException.Unauthorized.class } )
	public void handleHttpClientErrorUnauthorizedException(HttpClientErrorException ex, HttpServletResponse response) throws IOException {
		response.sendRedirect("/login");
	}
	
	@ExceptionHandler({ ExcecaoIntegridadeDadosRemocao.class } )
	public ResponseEntity<Object> handleExcecaoIntegridadeDadosRemocao(ExcecaoIntegridadeDadosRemocao ex) throws IOException {
		return ResponseEntity.badRequest().body(String.format("Não é possível remover %s %s, pois já foi criado(a) %s com este(a) %s!", 
				ex.getPrefixo(), ex.getEntidadeRemovida(), ex.getEntidadeRelacionada(), ex.getEntidadeRemovida()));
	}
}
