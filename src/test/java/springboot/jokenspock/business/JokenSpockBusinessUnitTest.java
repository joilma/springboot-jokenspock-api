package springboot.jokenspock.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.jokenspock.business.JokenSpockBusiness;
import br.com.jokenspock.enums.MensagemValidacao;
import br.com.jokenspock.exception.BusinessException;
import br.com.jokenspock.repository.JokenSpockRepository;
import br.com.jokenspock.representation.EntradaJogadaRepresentation;
import br.com.jokenspock.representation.JogadaRepresentation;
import br.com.jokenspock.representation.ResultadoRepresentation;

@RunWith(SpringRunner.class)
public class JokenSpockBusinessUnitTest {

	@Mock
	private JokenSpockRepository jokenSpockRepositoryMock;

	@InjectMocks
	private JokenSpockBusiness jokenSpockBusinessMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveMostrarTodasJogadasCadastradasComSucesso() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = montaRequestValido();

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(request);

		try{

			EntradaJogadaRepresentation retorno = jokenSpockBusinessMock.mostraJogadasCadastradas();

			assertTrue(retorno != null);

			assertFalse(retorno.getListaJogadas().isEmpty());

			assertTrue(retorno.getListaJogadas().size() == 2);

		}catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void deveRetornarExcecaoNaConsultaSemJogadas() throws JsonParseException, JsonMappingException, IOException{

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(null);

		try{

			jokenSpockBusinessMock.mostraJogadasCadastradas();

		}catch (BusinessException e){
			assertTrue(e.getMessage().equals(MensagemValidacao.ERRO_CONSULTA.getMensagem()));
		}catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void deveCadastrarJogadaComSucessoListaVazia() throws JsonParseException, JsonMappingException, IOException{

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(null);

		Mockito.doNothing().when(jokenSpockRepositoryMock).gravaJogadas(ArgumentMatchers.any(EntradaJogadaRepresentation.class));

		EntradaJogadaRepresentation request = montaRequestValido();

		try{

			ResultadoRepresentation response = jokenSpockBusinessMock.cadastraJogadas(request);

			assertTrue(response.getCodigoRetorno().equals(HttpStatus.OK.value()));

		}catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void deveCadastrarJogadaComSucessoListaPreviamentePreenchida() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation arquivo = new EntradaJogadaRepresentation();

		arquivo.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();;

		j1.setNomeJogada("LAGARTO");
		j1.setNomeJogador("Camila");

		arquivo.getListaJogadas().add(j1);

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(arquivo);

		Mockito.doNothing().when(jokenSpockRepositoryMock).gravaJogadas(ArgumentMatchers.any(EntradaJogadaRepresentation.class));

		EntradaJogadaRepresentation request = montaRequestValido();

		try{

			ResultadoRepresentation response = jokenSpockBusinessMock.cadastraJogadas(request);

			assertTrue(response.getCodigoRetorno().equals(HttpStatus.OK.value()));

		}catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void deveRetornarExcecaoListaJogadasVaziaNaRemocao() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(null);

		try{

			jokenSpockBusinessMock.removeJogadas(request);

			assertFalse(true);

		}catch (BusinessException e) {
			assertTrue(e.getMessage().equals(MensagemValidacao.SEM_JOGADA_ENVIADA.getMensagem()));
		}catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void deveRemoverJogadasComSucesso() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = montaRequestValido();

		Mockito.when(jokenSpockRepositoryMock.removeJogadas(ArgumentMatchers.any(EntradaJogadaRepresentation.class))).thenReturn(1);

		try{

			ResultadoRepresentation response = jokenSpockBusinessMock.removeJogadas(request);

			assertTrue(response.getCodigoRetorno().equals(HttpStatus.OK.value()));

		}catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void deveRetornarExcecaoJogadasNaoEncontradasParaRemocao() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = montaRequestValido();

		Mockito.when(jokenSpockRepositoryMock.removeJogadas(ArgumentMatchers.any(EntradaJogadaRepresentation.class))).thenReturn(0);

		try{

			jokenSpockBusinessMock.removeJogadas(request);

			assertFalse(true);

		}catch (BusinessException e) {
			assertTrue(e.getMessage().equals(MensagemValidacao.SEM_JOGADAS_REMOVIDAS.getMensagem()));
		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void deveRetornarVencedorComoSpock() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();
		JogadaRepresentation j2 = new JogadaRepresentation();

		j1.setNomeJogada("SPOCK");
		j1.setNomeJogador("Joilma");

		j2.setNomeJogada("tesoura");
		j2.setNomeJogador("Eduardo");


		request.getListaJogadas().add(j1);
		request.getListaJogadas().add(j2);

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(request);

		try{

			ResultadoRepresentation retorno = jokenSpockBusinessMock.jogarJokenSpock();
			
			assertTrue(retorno.getCodigoRetorno().equals(HttpStatus.OK.value()));

			assertTrue(retorno.getMensagem().contains("Joilma"));

		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void deveRetornarVencedorComoTesoura() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();
		JogadaRepresentation j2 = new JogadaRepresentation();

		j1.setNomeJogada("papel");
		j1.setNomeJogador("Joilma");

		j2.setNomeJogada("tesoura");
		j2.setNomeJogador("Eduardo");


		request.getListaJogadas().add(j1);
		request.getListaJogadas().add(j2);

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(request);

		try{

			ResultadoRepresentation retorno = jokenSpockBusinessMock.jogarJokenSpock();
			
			assertTrue(retorno.getCodigoRetorno().equals(HttpStatus.OK.value()));

			assertTrue(retorno.getMensagem().contains("Eduardo"));

		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void deveRetornarVencedorComoPapel() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();
		JogadaRepresentation j2 = new JogadaRepresentation();

		j1.setNomeJogada("papel");
		j1.setNomeJogador("Joilma");

		j2.setNomeJogada("pedra");
		j2.setNomeJogador("Eduardo");


		request.getListaJogadas().add(j1);
		request.getListaJogadas().add(j2);

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(request);

		try{

			ResultadoRepresentation retorno = jokenSpockBusinessMock.jogarJokenSpock();
			
			assertTrue(retorno.getCodigoRetorno().equals(HttpStatus.OK.value()));

			assertTrue(retorno.getMensagem().contains("Joilma"));

		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void deveRetornarVencedorComoLagarto() throws JsonParseException, JsonMappingException, IOException{

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();
		JogadaRepresentation j2 = new JogadaRepresentation();

		j1.setNomeJogada("spock");
		j1.setNomeJogador("Joilma");

		j2.setNomeJogada("lagarto");
		j2.setNomeJogador("Eduardo");


		request.getListaJogadas().add(j1);
		request.getListaJogadas().add(j2);

		Mockito.when(jokenSpockRepositoryMock.buscaJogadas()).thenReturn(request);

		try{

			ResultadoRepresentation retorno = jokenSpockBusinessMock.jogarJokenSpock();
			
			assertTrue(retorno.getCodigoRetorno().equals(HttpStatus.OK.value()));

			assertTrue(retorno.getMensagem().contains("Eduardo"));

		}catch (Exception e) {
			assertTrue(false);
		}
	}

	public EntradaJogadaRepresentation montaRequestValido(){

		EntradaJogadaRepresentation request = new EntradaJogadaRepresentation();

		request.setListaJogadas(new ArrayList<JogadaRepresentation>());

		JogadaRepresentation j1 = new JogadaRepresentation();
		JogadaRepresentation j2 = new JogadaRepresentation();

		j1.setNomeJogada("SPOCK");
		j1.setNomeJogador("Joilma");

		j2.setNomeJogada("papel");
		j2.setNomeJogador("Eduardo");


		request.getListaJogadas().add(j1);
		request.getListaJogadas().add(j2);

		return request;
	}
}
