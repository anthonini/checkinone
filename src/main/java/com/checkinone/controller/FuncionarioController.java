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
import com.checkinone.client.dto.UsuarioDTO;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController extends AbstractController {

	@Autowired
	private RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("funcionario/list");
		
		List<FuncionarioDTO> funcionarios = restClient.get()
	            .uri("/funcionarios")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
        mv.addObject("funcionarios", funcionarios);
		
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(FuncionarioDTO funcionario, ModelMap model) {
		return form(funcionario, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		FuncionarioDTO funcionario = restClient.get()
	            .uri("/funcionarios/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(funcionario, model);
	}
	
	@PostMapping({"/cadastrar"})
	public ModelAndView cadastrar(FuncionarioDTO funcionario, ModelMap model, RedirectAttributes redirect) {		
		if(funcionario.getUsuario() != null && funcionario.getUsuario().getId() == null) {
			funcionario.setUsuario(null);
		}
		
		try {
			restClient.post()
		            .uri("/funcionarios")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(funcionario)
		            .retrieve()
		            .toEntity(FuncionarioDTO.class);
			
			addMensagemSucesso(redirect, "Funcionário salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(funcionario, model);
		}
		
		return new ModelAndView("redirect:/funcionario");
	}
	
	@PostMapping({"/{id}"})
	public ModelAndView alterar(FuncionarioDTO funcionario, ModelMap model, RedirectAttributes redirect) {
		if(funcionario.getUsuario() != null && funcionario.getUsuario().getId() == null) {
			funcionario.setUsuario(null);
		}
		
		try {
			restClient.put()
		            .uri("/funcionarios/" + funcionario.getId())
		            .attributes(clientRegistrationId("checkinone"))
		            .body(funcionario)
		            .retrieve()
		            .toEntity(FuncionarioDTO.class);
			
			addMensagemSucesso(redirect, "Funcionário salvo com sucesso!");
		} catch (RestClientResponseException e) {
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(funcionario, model);
		}
		
		return new ModelAndView("redirect:/funcionario");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return restClient.delete()
		            .uri("/funcionarios/" + id)
		            .attributes(clientRegistrationId("checkinone"))
		            .retrieve()
		            .toBodilessEntity();
		} catch (RestClientResponseException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ModelAndView form(FuncionarioDTO funcionario, ModelMap model) {
		List<UsuarioDTO> usuarios = restClient.get()
	            .uri("/usuarios")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		usuarios.sort(Comparator.comparing(u -> u.getNome()));
		model.addAttribute("funcionario", funcionario);
		model.addAttribute("usuarios", usuarios);
		
		return new ModelAndView("funcionario/form");
	}
}
