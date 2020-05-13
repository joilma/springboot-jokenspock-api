package br.com.jokenspock.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadoRepresentation {
	
	@JsonProperty("codigoRetorno")
	private Integer codigoRetorno;

	@JsonProperty("mensagem")
	private String mensagem ;

	public Integer getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(Integer codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
