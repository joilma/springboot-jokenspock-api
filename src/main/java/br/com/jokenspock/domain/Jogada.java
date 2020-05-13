package br.com.jokenspock.domain;

import br.com.jokenspock.enums.JogadaEnum;

public class Jogada {
	
	private String nomeJogador;
	
	private JogadaEnum jogada;

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public JogadaEnum getJogada() {
		return jogada;
	}

	public void setJogada(JogadaEnum jogada) {
		this.jogada = jogada;
	}
}
