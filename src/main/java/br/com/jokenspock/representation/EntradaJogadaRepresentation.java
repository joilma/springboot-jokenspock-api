package br.com.jokenspock.representation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntradaJogadaRepresentation {
	
	@JsonProperty("listaJogadas")
	private List<JogadaRepresentation> listaJogadas;

	public List<JogadaRepresentation> getListaJogadas() {
		return listaJogadas;
	}

	public void setListaJogadas(List<JogadaRepresentation> listaJogadas) {
		this.listaJogadas = listaJogadas;
	}
}
