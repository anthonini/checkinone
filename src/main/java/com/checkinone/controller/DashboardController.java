package com.checkinone.controller;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;

import com.checkinone.client.dto.ReservaDTO;

@Controller
public class DashboardController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("dashboard");
		
		Long totalReservas = 0L;
		Long reservasAtivasHoje = 0L;
		Long totalQuartos = 0L;
		Long totalQuartosOcupados = 0L;
		List<ReservaDTO> reservas = null;

		try {
			totalReservas = restClient.get()
					.uri("/dashboard/total-reservas")
					.attributes(clientRegistrationId("checkinone"))
					.retrieve()
					.body(new ParameterizedTypeReference<>() {});
	
			reservasAtivasHoje = restClient.get()
					.uri("/dashboard/ativas-hoje")
					.attributes(clientRegistrationId("checkinone"))
					.retrieve()
					.body(new ParameterizedTypeReference<>() {});
	
			totalQuartos = restClient.get()
					.uri("/dashboard/total-quartos")
					.attributes(clientRegistrationId("checkinone"))
					.retrieve()
					.body(new ParameterizedTypeReference<>() {});
	
			totalQuartosOcupados = restClient.get()
					.uri("/dashboard/total-quartos-ocupados")
					.attributes(clientRegistrationId("checkinone"))
					.retrieve()
					.body(new ParameterizedTypeReference<>() {});
		
		
			reservas = restClient.get()
		            .uri("/reservas/ultimas")
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .body(new ParameterizedTypeReference<>() {});
		}catch (Exception e) {}


		mv.addObject("totalReservas", totalReservas);
		mv.addObject("reservasAtivasHoje", reservasAtivasHoje);
		mv.addObject("totalQuartos", totalQuartos);
		mv.addObject("totalQuartosOcupados", totalQuartosOcupados);
		mv.addObject("reservas", reservas);
		return mv;
	}


}
