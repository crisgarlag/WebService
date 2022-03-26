package com.webServiceDevops.controlador;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.webServiceDevops.persistencia.DaoFichero;

@RestController
@RequestMapping(path = "/service")
public class WebService {

	@Autowired
	DaoFichero daoFichero;
	@Autowired
	LoginController login;

	/**
	 * Metodo usado para que el cliente solicite POST para añadir la cadena de texto al fichero creado por daoFichero.
	 * @param cadena que se va a añadir
	 * @param usuario el usuario que va a usar el servicio
	 * @param token token valido para el uso del servicio
	 * @return forbiden si el usuario o el token son invalidos, created si se inserta el texto y not_modified si no se inserta
	 */
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> añadirCadena(@RequestBody String cadena,@RequestParam String user ,@RequestParam String token) {
		
		if(!login.checkLogin(token,user)){
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		
		if(daoFichero.escribirFichero(cadena))
			return new ResponseEntity<String>(HttpStatus.CREATED);
		else
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
				
	}
	/**
	 * Metodo usado para que el cliente solicite GET para consultar la cadena de texto en el fichero creado por daoFichero.
	 * @param cadena que se va a añadir
	 * @param usuario el usuario que va a usar el servicio
	 * @param token token valido para el uso del servicio
	 * @return forbiden si el usuario o el token son invalidos, Ok si se consulta la cadena de texto y bad_request si el numero de cadenas es null
	 */
	@GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> contarCadena(@RequestBody String cadena, @RequestParam String user ,@RequestParam String token) throws FileNotFoundException {

		String numeroCadenas = daoFichero.contarCadenasConPalabra(cadena);
		
		if(!login.checkLogin(token,user)){

			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}

		if (numeroCadenas != null)

			return new ResponseEntity<String>(numeroCadenas, HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

}
