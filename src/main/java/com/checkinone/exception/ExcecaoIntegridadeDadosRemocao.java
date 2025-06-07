package com.checkinone.exception;

public class ExcecaoIntegridadeDadosRemocao extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String prefixo;
	private String entidadeRemovida;
	private String entidadeRelacionada;
	
	public ExcecaoIntegridadeDadosRemocao(String prefixo, String entidadeRemovida, String entidadeRelacionada) {
		this.prefixo = prefixo;
		this.entidadeRemovida = entidadeRemovida;
		this.entidadeRelacionada = entidadeRelacionada;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public String getEntidadeRemovida() {
		return entidadeRemovida;
	}

	public String getEntidadeRelacionada() {
		return entidadeRelacionada;
	}
}
