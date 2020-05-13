package br.com.jokenspock.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.jokenspock.domain.Jogada;
import br.com.jokenspock.enums.JogadaEnum;
import br.com.jokenspock.enums.MensagemValidacao;
import br.com.jokenspock.exception.BusinessException;
import br.com.jokenspock.repository.JokenSpockRepository;
import br.com.jokenspock.representation.EntradaJogadaRepresentation;
import br.com.jokenspock.representation.JogadaRepresentation;
import br.com.jokenspock.representation.ResultadoRepresentation;

@Service
public class JokenSpockBusiness {

	@Autowired
	private JokenSpockRepository jokenSpockRepository;

	public EntradaJogadaRepresentation mostraJogadasCadastradas(){

		try {

			EntradaJogadaRepresentation jogadas = jokenSpockRepository.buscaJogadas();

			if(jogadas == null || jogadas.getListaJogadas().isEmpty()){
				throw new BusinessException(MensagemValidacao.SEM_JOGADAS_CONSULTA);
			}

			return jogadas;

		} catch (BusinessException ex) {

			throw ex;

		} catch (Exception ex) {

			throw new BusinessException(MensagemValidacao.ERRO_CONSULTA);

		}
	}

	public ResultadoRepresentation cadastraJogadas(EntradaJogadaRepresentation requestJogadas){

		try {

			EntradaJogadaRepresentation jogadas = jokenSpockRepository.buscaJogadas();

			if(jogadas != null){

				for(JogadaRepresentation jog : requestJogadas.getListaJogadas()){

					jogadas.getListaJogadas().add(jog);
				}

			}else{

				jogadas = requestJogadas;

			}

			jokenSpockRepository.gravaJogadas(jogadas);

			return montaResponse(MensagemValidacao.SUCESSO_CADASTRO.getMensagem());

		} catch (BusinessException ex) {

			throw ex;

		} catch (Exception ex) {

			throw new BusinessException(MensagemValidacao.ERRO_CADASTRO);

		}
	}

	public ResultadoRepresentation removeJogadas(EntradaJogadaRepresentation requestJogadas){

		try {

			if(requestJogadas.getListaJogadas() == null || 
					requestJogadas.getListaJogadas().isEmpty()){

				throw new BusinessException(MensagemValidacao.SEM_JOGADA_ENVIADA);

			}

			Integer contador = jokenSpockRepository.removeJogadas(requestJogadas);

			if(contador != null && contador > 0){

				return montaResponse(contador + " jogadas removidas!");

			}else{
				
				throw new BusinessException(MensagemValidacao.SEM_JOGADAS_REMOVIDAS);
				
			}

		} catch (BusinessException ex) {

			throw ex;

		} catch (Exception ex) {

			throw new BusinessException(MensagemValidacao.ERRO_CONSULTA);

		}
	}

	public ResultadoRepresentation jogarJokenSpock(){

		try{

			EntradaJogadaRepresentation jogadas = jokenSpockRepository.buscaJogadas();

			if(jogadas.getListaJogadas() == null || 
					jogadas.getListaJogadas().isEmpty()){

				throw new BusinessException(MensagemValidacao.SEM_JOGADAS_CONSULTA);

			}

			Jogada jogadaCampea = null;

			List<Jogada> listaJogadas = new ArrayList<Jogada>();

			jogadas.getListaJogadas().stream().forEach(s -> {

				Jogada escolha = new Jogada();

				escolha.setNomeJogador(s.getNomeJogador());

				escolha.setJogada(JogadaEnum.find(s.getNomeJogada().toUpperCase()));

				listaJogadas.add(escolha);

			});

			if(!listaJogadas.isEmpty()){

				jogadaCampea = calculaVencedor(listaJogadas);

			}

			if(jogadaCampea == null){

				throw new BusinessException(MensagemValidacao.SEM_VENCEDOR);

			}

			return montaResponse("Jogador " + jogadaCampea.getNomeJogador() + " Vitória");

		} catch (BusinessException ex) {

			throw ex;

		} catch (Exception ex) {

			throw new BusinessException(MensagemValidacao.ERRO_CONSULTA);

		}

	}

	private Jogada calculaVencedor(List<Jogada> listaJogadas){

		Jogada jogadorCampeao = null;

		if(listaJogadas != null && 
				!listaJogadas.isEmpty()){

			jogadorCampeao = listaJogadas.get(0);

			for(Jogada jogada : listaJogadas){

				JogadaEnum escolhaJogador = jogada.getJogada();

				if(jogadorCampeao.getJogada().getMinhaCriptonita().contains(escolhaJogador)){

					jogadorCampeao = jogada;
				}
			}
		}

		return jogadorCampeao;
	}

	private ResultadoRepresentation montaResponse(String mensagem){

		ResultadoRepresentation response = new ResultadoRepresentation();

		response.setCodigoRetorno(HttpStatus.OK.value());

		response.setMensagem(mensagem);

		return response;

	}
}
