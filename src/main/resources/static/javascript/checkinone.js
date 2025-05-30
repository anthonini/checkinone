var CheckinOne = CheckinOne || {};

CheckinOne.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.habilitar = function() {
		$(document).ajaxSend(function(event, jqxhr, settings){
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
}());

CheckinOne.MascaraNumeroTelefone = (function() {
	
	function MascaraNumeroTelefone() {
		this.inputPhoneNumber = $('.js-numero-telefone');
	}
	
	MascaraNumeroTelefone.prototype.habilitar = function() {
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MascaraNumeroTelefone;
}());

CheckinOne.MascaraNumeros = (function(){
	function MascaraNumeros() {
		this.preco = $('.js-preco');		
	}
	
	MascaraNumeros.prototype.habilitar = function() {
		this.preco.mask('#.##0,00', {reverse: true});
	}
	
	return MascaraNumeros;
})();

CheckinOne.MascaraCPF = (function(){
	function MascaraCPF() {
		this.preco = $('.js-cpf');		
	}
	
	MascaraCPF.prototype.habilitar = function() {
		this.preco.mask('999.999.999-99');
	}
	
	return MascaraCPF;
})();

$(function() {
	var security = new CheckinOne.Security();
	security.habilitar();
	
	var mascaraNumeroTelefone = new CheckinOne.MascaraNumeroTelefone();
	mascaraNumeroTelefone.habilitar();
	
	var mascaraNumeros = new CheckinOne.MascaraNumeros();
	mascaraNumeros.habilitar();
	
	var mascaraCPF = new CheckinOne.MascaraCPF();
	mascaraCPF.habilitar();
});