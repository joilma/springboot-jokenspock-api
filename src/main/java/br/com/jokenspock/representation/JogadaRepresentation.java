package br.com.jokenspock.representation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JogadaRepresentation implements Serializable{
	
	private static final long serialVersionUID = -2134400317899194120L;

	@JsonProperty("nomeJogador")
	private String nomeJogador;
	
	@JsonProperty("nomeJogada")
	private String nomeJogada;
	
	@Override
	public boolean equals(Object object){
		
		if(object.getClass().equals(this.getClass())){
			
			JogadaRepresentation jogada = (JogadaRepresentation) object;
			
			return (jogada.getNomeJogada().equalsIgnoreCase(this.nomeJogada) 
					&& jogada.getNomeJogador().equalsIgnoreCase(nomeJogador));
	
		}
		
		return false;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public String getNomeJogada() {
		return nomeJogada;
	}

	public void setNomeJogada(String nomeJogada) {
		this.nomeJogada = nomeJogada;
	}
	
	
}
