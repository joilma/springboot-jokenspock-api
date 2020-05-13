package br.com.jokenspock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.jokenspock.enums.MensagemValidacao;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(MensagemValidacao mensagem) {
		super(mensagem.getMensagem());
	}
}

