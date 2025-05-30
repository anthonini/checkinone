package com.checkinone.client.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

public class QuartoDTO {

	private Long id;
	
	private HotelDTO hotel;
	
	private Integer numero;
	
	private TipoQuarto tipo;
	
	private Integer capacidade;
	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorDiaria;
	
	private StatusQuarto status;
	
	public String getDescricao() {
		return (hotel != null && hotel.getNome() != null ? hotel.getNome() + " - " : "") + getNumero();
	}
	
	public static enum TipoQuarto {
		STANDARD("Standard"), 
		SUITE("Suite"),
		LUXO("Luxo");
		
		private String descricao;
		
		private TipoQuarto(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public static enum StatusQuarto {
		DISPONIVEL("Disponível", "badge-success"), 
		OCUPADO("Ocupado", "badge-danger"),
		MANUTENCAO("Manutenção", "badge-warning");
		
		private String descricao;
		private String classe;
		
		private StatusQuarto(String descricao, String classe) {
			this.descricao = descricao;
			this.classe = classe;
		}

		public String getDescricao() {
			return descricao;
		}

		public String getClasse() {
			return classe;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoQuarto getTipo() {
		return tipo;
	}

	public void setTipo(TipoQuarto tipo) {
		this.tipo = tipo;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public BigDecimal getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(BigDecimal valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public StatusQuarto getStatus() {
		return status;
	}

	public void setStatus(StatusQuarto status) {
		this.status = status;
	}
}
