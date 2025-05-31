package com.checkinone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Controller
public class DashboardController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("dashboard");

		Long totalReservas = restClient.get()
				.uri("/dashboard/total-reservas")
				.attributes(clientRegistrationId("checkinone"))
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		Long reservasAtivasHoje = restClient.get()
				.uri("/dashboard/ativas-hoje")
				.attributes(clientRegistrationId("checkinone"))
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		Long totalQuartos = restClient.get()
				.uri("/dashboard/total-quartos")
				.attributes(clientRegistrationId("checkinone"))
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		Long totalQuartosOcupados = restClient.get()
				.uri("/dashboard/total-quartos-ocupados")
				.attributes(clientRegistrationId("checkinone"))
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});


		mv.addObject("totalReservas", totalReservas);
		mv.addObject("reservasAtivasHoje", reservasAtivasHoje);
		mv.addObject("totalQuartos", totalQuartos);
		mv.addObject("totalQuartosOcupados", totalQuartosOcupados);
		return mv;
	}


}
