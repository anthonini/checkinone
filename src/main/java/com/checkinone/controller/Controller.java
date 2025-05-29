package com.checkinone.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.checkinone.controller.message.MensagemHolder;
import com.checkinone.controller.message.TipoMensagem;


/**
 * @author Anthonini
 */
public interface Controller {

    default void addMensagemErro(Object mensagemHolder, String mensagem) {
        MensagemHolder.getTipoMensagemHolder(mensagemHolder).adicionarMensagem(mensagemHolder, TipoMensagem.ERROR, mensagem);
    }

    default void addMensagemSucesso(Object mensagemHolder, String mensagem) {
        MensagemHolder.getTipoMensagemHolder(mensagemHolder).adicionarMensagem(mensagemHolder, TipoMensagem.SUCCESS, mensagem);
    }

    default void addMensagemInfo(Object mensagemHolder, String mensagem) {
        MensagemHolder.getTipoMensagemHolder(mensagemHolder).adicionarMensagem(mensagemHolder, TipoMensagem.INFO, mensagem);
    }

    default void addMensagensErroValidacao(Object mensagemHolder, BindingResult bindingResult) {
        for(FieldError error : bindingResult.getFieldErrors()) {
            addMensagemErro(mensagemHolder, error.getDefaultMessage());
        }
    }
}
