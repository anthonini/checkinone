package com.checkinone.controller;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.checkinone.client.dto.FuncionarioDTO;
import com.checkinone.client.dto.HotelDTO;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/hotel")
public class HotelController extends AbstractController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("hotel/list");
		
		List<HotelDTO> hoteis = restClient.get()
	            .uri("/hoteis")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		hoteis.sort(Comparator.comparing(h -> h.getNome()));
        mv.addObject("hoteis", hoteis);
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(HotelDTO hotel, ModelMap model) {
		return form(hotel, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		HotelDTO hotel = restClient.get()
	            .uri("/hoteis/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(hotel, model);
	}
	
	@PostMapping({"/cadastrar"})
	public ModelAndView cadastrar(HotelDTO hotel, ModelMap model, RedirectAttributes redirect) {		
		if(hotel.getGerente() != null && hotel.getGerente().getId() == null) {
			hotel.setGerente(null);
		}
		
		try {
			restClient.post()
		            .uri("/hoteis")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(hotel)
		            .retrieve()
		            .toEntity(HotelDTO.class);
			
			addMensagemSucesso(redirect, "Hotel salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(hotel, model);
		}
		
		return new ModelAndView("redirect:/hotel");
	}
	
	@PostMapping({"/{id}"})
	public ModelAndView alterar(HotelDTO hotel, ModelMap model, RedirectAttributes redirect) {
		if(hotel.getGerente() != null && hotel.getGerente().getId() == null) {
			hotel.setGerente(null);
		}
		
		try {
			restClient.put()
		            .uri("/hoteis/" + hotel.getId())
		            .attributes(clientRegistrationId("checkinone"))
		            .body(hotel)
		            .retrieve()
		            .toEntity(HotelDTO.class);
			
			addMensagemSucesso(redirect, "Hotel salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(hotel, model);
		}
		
		return new ModelAndView("redirect:/hotel");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return restClient.delete()
		            .uri("/hoteis/" + id)
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .toBodilessEntity();
		} catch (RestClientResponseException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ModelAndView form(HotelDTO hotel, ModelMap model) {
		List<FuncionarioDTO> funcionarios = restClient.get()
	            .uri("/funcionarios")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		funcionarios.sort(Comparator.comparing(f -> f.getUsuario().getNome()));
		model.addAttribute("hotel", hotel);
		model.addAttribute("funcionarios", funcionarios);
		
		return new ModelAndView("hotel/form");
	}
}
