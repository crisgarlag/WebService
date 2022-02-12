package com.webServiceDevops.controlador;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webServiceDevops.persistencia.DaoFichero;

@RestController
public class WebService {

	@Autowired
	DaoFichero daoFichero;

	/**
	 * Metodo usado para que el cliente solicite POST para añadir la cadena de texto al fichero creado por daoFichero.
	 * @param cadena
	 * @return Created al añadir la informacion y Not modified en caso contrario
	 */
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> añadirCadena(@RequestBody String cadena) {

		
		if(daoFichero.escribirFichero(cadena))
			return new ResponseEntity<String>(HttpStatus.CREATED);
		else
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
				
	}
	/**
	 * Metodo usado para que el cliente solicite GET para consultar la cadena de texto en el fichero creado por daoFichero.
	 * @param cadena
	 * @return El numero de cadenas y Ok si el metodo contarCadenasConPalabra devuelve distinto de null y Bad Request en caso contrario
	 * @throws FileNotFoundException 
	 */
	@GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> contarCadena(@RequestBody String cadena) throws FileNotFoundException {

		String numeroCadenas = daoFichero.contarCadenasConPalabra(cadena);

		if (numeroCadenas != null)

			return new ResponseEntity<String>(numeroCadenas, HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

}
