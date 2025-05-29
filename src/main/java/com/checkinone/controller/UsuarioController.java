package com.checkinone.controller;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.checkinone.client.dto.PermissaoDTO;
import com.checkinone.client.dto.UsuarioDTO;
import com.checkinone.controller.message.Erro;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends AbstractController {

	@Autowired
	public RestClient restClient;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("usuario/list");
		
		List<UsuarioDTO> usuarios = restClient.get()
	            .uri("/usuarios")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
        mv.addObject("usuarios", usuarios);
		
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastro(UsuarioDTO usuario, ModelMap model) {
		return form(usuario, model);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id, ModelMap model) {
		UsuarioDTO usuario = restClient.get()
	            .uri("/usuarios/"+id)
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		return form(usuario, model);
	}
	
	@PostMapping({"/cadastrar", "/{id}"})
	public ModelAndView alterar(UsuarioDTO usuario, ModelMap model, RedirectAttributes redirect) {
		usuario.getPermissoes().removeIf(p -> p.getId() == null);
		
		try {
			restClient.post()
		            .uri("/usuarios")
		            .attributes(clientRegistrationId("checkinone"))
		            .body(usuario)
		            .retrieve()
		            .toEntity(UsuarioDTO.class);
			
			addMensagemSucesso(redirect, "Usuário salvo com sucesso!");
		} catch (RestClientResponseException e) {			
			addMensagensErroValidacao(model, e.getResponseBodyAs(Erro.class));
			return form(usuario, model);
		}
		
		return new ModelAndView("redirect:/usuario");
	}
	
	private ModelAndView form(UsuarioDTO usuario, ModelMap model) {
		List<PermissaoDTO> permissoes = restClient.get()
	            .uri("/permissoes")
	            .attributes(clientRegistrationId("checkinone"))
	            .retrieve()
	            .body(new ParameterizedTypeReference<>() {});
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("permissoes", permissoes);
		
		return new ModelAndView("usuario/form");
	}
}
