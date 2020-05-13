package br.com.jokenspock.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jokenspock.business.JokenSpockBusiness;
import br.com.jokenspock.representation.EntradaJogadaRepresentation;
import br.com.jokenspock.representation.ResultadoRepresentation;

@RestController
public class JokenSpockController {

	@Autowired
	JokenSpockBusiness jokenSpockBusiness;

	@GetMapping(value = "/getJogadas")
	public ResponseEntity<EntradaJogadaRepresentation> mostraJogadas() {
		return new ResponseEntity<EntradaJogadaRepresentation>(jokenSpockBusiness.mostraJogadasCadastradas(),HttpStatus.OK);
	}

	@PostMapping(value = "/cadastraJogadas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultadoRepresentation> cadastraJogadas(@RequestBody EntradaJogadaRepresentation requestBody ){

		return new ResponseEntity<ResultadoRepresentation>(jokenSpockBusiness.cadastraJogadas(requestBody),HttpStatus.OK);

	}

	@DeleteMapping(value = "/removeJogadas" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultadoRepresentation> removeJogadas(@RequestBody EntradaJogadaRepresentation requestBody ){

		return new ResponseEntity<ResultadoRepresentation>(jokenSpockBusiness.removeJogadas(requestBody),HttpStatus.OK);

	}

	@GetMapping(value = "/realizaJogada")
	public ResponseEntity<ResultadoRepresentation> realizaJogada(){
		return new ResponseEntity<ResultadoRepresentation>(jokenSpockBusiness.jogarJokenSpock(),HttpStatus.OK);

	}
}
