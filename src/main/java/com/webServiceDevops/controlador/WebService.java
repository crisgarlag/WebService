package com.webServiceDevops.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webServiceDevops.persistencia.DaoFichero;

@RestController
public class WebService {

	@Autowired
	DaoFichero daoFichero;
	
	
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> añadirCadena(@RequestBody String cadena) {

		daoFichero.escribirFichero(cadena);
		

			return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> ContarCadena(@RequestBody String cadena) {

		String numeroCadenas= daoFichero.contarCadenasConPalabra(cadena);
		
		if(numeroCadenas!=null)

			return new ResponseEntity<String>(numeroCadenas, HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

}
