package br.com.jokenspock.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jokenspock.enums.MensagemValidacao;
import br.com.jokenspock.exception.BusinessException;
import br.com.jokenspock.representation.EntradaJogadaRepresentation;
import br.com.jokenspock.representation.JogadaRepresentation;
import br.com.jokenspock.util.Utils;

@Repository
public class JokenSpockRepository {
	
	public JokenSpockRepository(){
		
	}

	public EntradaJogadaRepresentation buscaJogadas() throws JsonParseException, JsonMappingException, IOException{

		ObjectMapper mapper = new ObjectMapper();

		InputStream arquivo = Utils.getResourceFileAsInputStream("jogadores.json");

		if(arquivo == null){
			return null;
		}

		EntradaJogadaRepresentation jogadas = mapper.readValue(arquivo, EntradaJogadaRepresentation.class);

		return jogadas;

	}

	public void gravaJogadas(EntradaJogadaRepresentation jogadas) throws JsonParseException, JsonMappingException, IOException{

		ObjectMapper mapper = new ObjectMapper();

		File file = Utils.createFile("jogadores.json");

		mapper.writeValue(file, jogadas);

	}

	public Integer removeJogadas(EntradaJogadaRepresentation jogadas) throws JsonParseException, JsonMappingException, IOException{

		int contador = 0;

		EntradaJogadaRepresentation jogadasExistentes = this.buscaJogadas();

		if(jogadasExistentes == null){

			throw new BusinessException(MensagemValidacao.ERRO_CONSULTA);

		}

		for(JogadaRepresentation jog : jogadas.getListaJogadas()){

			if(jogadasExistentes.getListaJogadas().contains(jog)){

				jogadasExistentes.getListaJogadas().remove(jog);

				contador++;
			}

		}

		this.gravaJogadas(jogadasExistentes);

		return contador;
	}

}
