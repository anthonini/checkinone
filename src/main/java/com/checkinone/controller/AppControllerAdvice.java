package com.checkinone.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

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
	
	@ExceptionHandler({ HttpClientErrorException.BadRequest.class } )
	public void handleHttpClientErrorBadRequestException(HttpClientErrorException ex, HttpServletResponse response) throws IOException {
		response.sendRedirect("/login");
	}
}
