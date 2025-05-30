package com.checkinone.controller;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import java.util.List;
import java.util.Random;

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

import com.checkinone.client.dto.HospedeDTO;
import com.checkinone.client.dto.ReservaDTO;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/hospede")
public class HospedeController extends AbstractController {

	@Autowired
	public RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("hospede/list");
		
		List<HospedeDTO> hospedes = restClient.get()
	            .uri("/hospedes")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
        mv.addObject("hospedes", hospedes);
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(HospedeDTO hospede, ModelMap model) {
		return form(hospede, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		HospedeDTO hospede = restClient.get()
	            .uri("/hospedes/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(hospede, model);
	}
	
	@GetMapping("/{id}/reservas")
	public ModelAndView reservas(@PathVariable Long id, ModelMap model) {
		HospedeDTO hospede = restClient.get()
	            .uri("/hospedes/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		List<ReservaDTO> reservas = restClient.get()
	            .uri("/reservas/hospede/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		model.addAttribute("hospede", hospede);
		model.addAttribute("reservas", reservas);
		
		return new ModelAndView("hospede/reservas");
	}
	
	@PostMapping({"/cadastrar"})
	public ModelAndView cadastrar(HospedeDTO hospede, ModelMap model, RedirectAttributes redirect) {		
		hospede.setId(new Random().nextLong());
		try {
			restClient.post()
		            .uri("/hospedes")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(hospede)
		            .retrieve()
		            .toEntity(HospedeDTO.class);
			
			addMensagemSucesso(redirect, "Hóspede salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(hospede, model);
		}
		
		return new ModelAndView("redirect:/hospede");
	}
	
	@PostMapping({"/{id}"})
	public ModelAndView alterar(HospedeDTO hospede, ModelMap model, RedirectAttributes redirect) {		
		try {
			restClient.put()
		            .uri("/hospedes/" + hospede.getId())
		            .attributes(clientRegistrationId("checkinone"))
		            .body(hospede)
		            .retrieve()
		            .toEntity(HospedeDTO.class);
			
			addMensagemSucesso(redirect, "Hóspede salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(hospede, model);
		}
		
		return new ModelAndView("redirect:/hospede");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return restClient.delete()
		            .uri("/hospedes/" + id)
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .toBodilessEntity();
		} catch (RestClientResponseException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ModelAndView form(HospedeDTO hospede, ModelMap model) {
		model.addAttribute("hospede", hospede);			
		return new ModelAndView("hospede/form");
	}
}
