package springboot.jokenspock.rest;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jokenspock.business.JokenSpockBusiness;
import br.com.jokenspock.representation.EntradaJogadaRepresentation;
import br.com.jokenspock.representation.JogadaRepresentation;
import br.com.jokenspock.representation.ResultadoRepresentation;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes=br.com.jokenspock.rest.JokenSpockController.class)
public class JokenSpockControllerUnitTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private JokenSpockBusiness jokenSpockBusinessMock;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		jokenSpockBusinessMock = Mockito.mock(JokenSpockBusiness.class);
	}
	
	@Test
	public void deveConsultarJogadasComSucesso(){
		
		try {
			
			Mockito.when(jokenSpockBusinessMock.mostraJogadasCadastradas()).thenReturn(montaRequestValido());

			mvc.perform( MockMvcRequestBuilders
					.get("/getJogadas")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
			
		}catch (Exception e) {
			System.out.println("Erro ao executar teste");
		}
	}
	
	@Test
	public void deveRealizarJogadasComSucesso(){
		
		try {
			
			Mockito.when(jokenSpockBusinessMock.jogarJokenSpock()).thenReturn(montaResponseValido("Jogador Eduardo Vitória"));

			mvc.perform( MockMvcRequestBuilders
					.get("/realizaJogada")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
			
		}catch (Exception e) {
			System.out.println("Erro ao executar teste");
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
	
	public ResultadoRepresentation montaResponseValido(String mensagem){
		
		ResultadoRepresentation response = new ResultadoRepresentation();

		response.setCodigoRetorno(HttpStatus.OK.value());

		response.setMensagem(mensagem);

		return response;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
