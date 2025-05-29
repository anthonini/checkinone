package com.checkinone.controller.message;

import java.util.ArrayList;
import java.util.List;

public class Erro {

	private List<String> mensagens;
	
	public Erro() {
		this.mensagens = new ArrayList<>();
	}

	public List<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<String> mensagens) {
		this.mensagens = mensagens;
	}
}