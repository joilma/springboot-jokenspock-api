package br.com.jokenspock.enums;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum JogadaEnum {

	PEDRA("PEDRA"),
	TESOURA("TESOURA"),
	PAPEL("PAPEL"),
	LAGARTO("LAGARTO"),
	SPOCK("SPOCK");

	private String nomeJogada;
	private List<JogadaEnum> minhaCriptonita;
	
	JogadaEnum (String nomeJogada) {
		this.nomeJogada = nomeJogada;
	}

	static{
		
		PEDRA.minhaCriptonita = Arrays.asList(PAPEL, SPOCK);
		TESOURA.minhaCriptonita = Arrays.asList(PEDRA, SPOCK);
		PAPEL.minhaCriptonita = Arrays.asList(LAGARTO, TESOURA);
		LAGARTO.minhaCriptonita = Arrays.asList(PEDRA, TESOURA);
		SPOCK.minhaCriptonita = Arrays.asList(PAPEL, LAGARTO);
	
	}
	
	public List<JogadaEnum> getMinhaCriptonita() {
		return minhaCriptonita;
	}

	public void setMinhaCriptonita(List<JogadaEnum> minhaCriptonita) {
		this.minhaCriptonita = minhaCriptonita;
	}

	public String getNomeJogada() {
		return nomeJogada;
	}

	public void setNomeJogada(String nomeJogada) {
		this.nomeJogada = nomeJogada;
	}
	private static final ImmutableMap<String, JogadaEnum> reverselookup = Maps
			.uniqueIndex(Arrays.asList(JogadaEnum.values()), JogadaEnum::getNomeJogada);

	public static JogadaEnum find(final String value) {
		return reverselookup.getOrDefault(value, PEDRA);
	}
	
	
}
