package com.checkinone.client.dto;

import java.time.LocalDateTime;

public class ReservaDTO {

	private Long id;	
	
	private HospedeDTO hospede;	
	
	private QuartoDTO quarto;	
	
	private PagamentoDTO pagamento;	
	
	private StatusReserva status;	
	
	private LocalDateTime dataEntrada;	
	
	private LocalDateTime dataSaida;
	
	public enum StatusReserva {
		ATIVA("Ativa", "badge-success"), 
		CANCELADA("Cancelada", "badge-danger"),
		CONCLUIDA("Concluída", "badge-primary");
		
		private String descricao;
		private String classe;
		
		private StatusReserva(String descricao, String classe) {
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

	public HospedeDTO getHospede() {
		return hospede;
	}

	public void setHospede(HospedeDTO hospede) {
		this.hospede = hospede;
	}

	public QuartoDTO getQuarto() {
		return quarto;
	}

	public void setQuarto(QuartoDTO quarto) {
		this.quarto = quarto;
	}

	public PagamentoDTO getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoDTO pagamento) {
		this.pagamento = pagamento;
	}

	public StatusReserva getStatus() {
		return status;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}
}
