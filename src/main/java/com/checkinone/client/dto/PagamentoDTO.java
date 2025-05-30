package com.checkinone.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;

public class PagamentoDTO {

	private Long id;
	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	private FormaPagamento formaPagamento;	
	
	private LocalDateTime data;
	
	private StatusPagamento status;
	
	public enum FormaPagamento {
		CARTAO("Cartão"),
		PIX("Pix"),
		BOLETO("Boleto");
		
		private String descricao;
		
		private FormaPagamento(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum StatusPagamento {
		PAGO("Pago", "badge-success"),
		PROCESSANDO("Processando", "badge-warning"),
		PENDENTE("Pendente", "badge-danger");
		
		private String descricao;
		private String classe;
		
		private StatusPagamento(String descricao, String classe) {
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public StatusPagamento getStatus() {
		return status;
	}

	public void setStatus(StatusPagamento status) {
		this.status = status;
	}
}
