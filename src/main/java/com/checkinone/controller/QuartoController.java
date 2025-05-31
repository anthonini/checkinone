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

import com.checkinone.client.dto.HotelDTO;
import com.checkinone.client.dto.QuartoDTO;
import com.checkinone.client.dto.QuartoDTO.StatusQuarto;
import com.checkinone.client.dto.QuartoDTO.TipoQuarto;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/quarto")
public class QuartoController extends AbstractController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("quarto/list");
		
		List<QuartoDTO> quartos = restClient.get()
	            .uri("/quartos")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
        mv.addObject("quartos", quartos);
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(QuartoDTO quarto, ModelMap model) {
		return form(quarto, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		QuartoDTO quarto = restClient.get()
	            .uri("/quartos/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(quarto, model);
	}
	
	@PostMapping({"/cadastrar"})
	public ModelAndView cadastrar(QuartoDTO quarto, ModelMap model, RedirectAttributes redirect) {		
		try {
			restClient.post()
		            .uri("/quartos")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(quarto)
		            .retrieve()
		            .toEntity(QuartoDTO.class);
			
			addMensagemSucesso(redirect, "Quarto salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(quarto, model);
		}
		
		return new ModelAndView("redirect:/quarto");
	}
	
	@PostMapping({"/{id}"})
	public ModelAndView alterar(QuartoDTO quarto, ModelMap model, RedirectAttributes redirect) {		
		try {
			restClient.put()
		            .uri("/quartos/" + quarto.getId())
		            .attributes(clientRegistrationId("checkinone"))
		            .body(quarto)
		            .retrieve()
		            .toEntity(QuartoDTO.class);
			
			addMensagemSucesso(redirect, "Quarto salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(quarto, model);
		}
		
		return new ModelAndView("redirect:/quarto");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return restClient.delete()
		            .uri("/quartos/" + id)
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .toBodilessEntity();
		} catch (RestClientResponseException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ModelAndView form(QuartoDTO quarto, ModelMap model) {
		List<HotelDTO> hoteis = restClient.get()
	            .uri("/hoteis")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		hoteis.sort(Comparator.comparing(h -> h.getNome()));
		model.addAttribute("quarto", quarto);
		model.addAttribute("hoteis", hoteis);
		model.addAttribute("tipos", TipoQuarto.values());
		model.addAttribute("listaStatus", StatusQuarto.values());
		
		return new ModelAndView("quarto/form");
	}
}
