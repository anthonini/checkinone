package com.checkinone.controller;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

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

import com.checkinone.client.dto.HospedeDTO;
import com.checkinone.client.dto.PagamentoDTO.FormaPagamento;
import com.checkinone.client.dto.PagamentoDTO.StatusPagamento;
import com.checkinone.client.dto.QuartoDTO;
import com.checkinone.client.dto.ReservaDTO;
import com.checkinone.client.dto.ReservaDTO.StatusReserva;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/reserva")
public class ReservaController extends AbstractController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("reserva/list");
		
		List<ReservaDTO> reservas = restClient.get()
	            .uri("/reservas")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
        mv.addObject("reservas", reservas);
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(ReservaDTO reserva, ModelMap model) {
		return form(reserva, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		ReservaDTO reserva = restClient.get()
	            .uri("/reservas/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(reserva, model);
	}
	
	@PostMapping({"/cadastrar"})
	public ModelAndView cadastrar(ReservaDTO reserva, ModelMap model, RedirectAttributes redirect) {
		prepararDados(reserva);
		try {
			restClient.post()
		            .uri("/reservas")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(reserva)
		            .retrieve()
		            .toEntity(ReservaDTO.class);
			
			addMensagemSucesso(redirect, "Reserva salva com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(reserva, model);
		}
		
		return new ModelAndView("redirect:/reserva");
	}
	
	@PostMapping({"/{id}"})
	public ModelAndView alterar(ReservaDTO reserva, ModelMap model, RedirectAttributes redirect) {		
		prepararDados(reserva);
		try {
			restClient.put()
		            .uri("/reservas/" + reserva.getId())
		            .attributes(clientRegistrationId("checkinone"))
		            .body(reserva)
		            .retrieve()
		            .toEntity(ReservaDTO.class);
			
			addMensagemSucesso(redirect, "Reserva salva com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(reserva, model);
		}
		
		return new ModelAndView("redirect:/reserva");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return restClient.delete()
		            .uri("/reservas/" + id)
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .toBodilessEntity();
		} catch (RestClientResponseException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ModelAndView form(ReservaDTO reserva, ModelMap model) {
		List<QuartoDTO> quartos = restClient.get()
	            .uri("/quartos")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		List<HospedeDTO> hospedes = restClient.get()
	            .uri("/hospedes")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		model.addAttribute("reserva", reserva);
		model.addAttribute("quartos", quartos);
		model.addAttribute("hospedes", hospedes);
		model.addAttribute("listaStatus", StatusReserva.values());
		model.addAttribute("formasPagamento", FormaPagamento.values());
		model.addAttribute("statusPagamento", StatusPagamento.values());
		
		return new ModelAndView("reserva/form");
	}
	
	private void prepararDados(ReservaDTO reserva) {
		if(reserva.getHospede().getId() == null) {
			reserva.setHospede(null);
		}
		if(reserva.getQuarto().getId() == null) {
			reserva.setQuarto(null);
		}
	}
}
