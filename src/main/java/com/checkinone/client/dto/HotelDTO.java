package com.checkinone.client.dto;

public class HotelDTO {
	
	private Long id;

	private String nome;
	
	private String endereco;
	
	private String telefone;
	
	private FuncionarioDTO gerente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public FuncionarioDTO getGerente() {
		return gerente;
	}

	public void setGerente(FuncionarioDTO gerente) {
		this.gerente = gerente;
	}
}
