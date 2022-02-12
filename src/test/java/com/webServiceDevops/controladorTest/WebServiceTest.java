package com.webServiceDevops.controladorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;


import com.webServiceDevops.controlador.WebService;
import com.webServiceDevops.persistencia.DaoFichero;

@SpringBootTest
class WebServiceTest {
	
	public static String URL_SERVIDOR = "http://localhost:12345";

	@Autowired
	private DaoFichero daoFichero;
	
	@Autowired
	private WebService ws;
	
	/**
	 * El metodo debe devolver el codigo 200 OK, si recibe una unica palabra en la cadena pasada como argumento
	 * @throws FileNotFoundException
	 */
	@Test
	public void getContarCadenaUnaPalabraTest() throws FileNotFoundException {
		
		ResponseEntity<String> httpResponse = ws.contarCadena("Hola");
		
		assertEquals(HttpStatus.OK,httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 400 Bad Request, si recibe más de una palabra en la cadena pasada como argumento
	 * @throws FileNotFoundException
	 */
	@Test
	public void getContarCadenaMasDeUnaPalabraTest() throws FileNotFoundException {
		
		ResponseEntity<String> httpResponse = ws.contarCadena("Hola Mundo");
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 201 Created, si la cadena se añade al fichero cadenas.txt
	 */
	@Test
	public void postAñadirCadenaTest() {
		
	File fichero = daoFichero.getFile();
		
		if(!fichero.canWrite()) {
			fichero.setWritable(true);
		}
		
		ResponseEntity<String> httpResponse = ws.añadirCadena("Hola Mundo");
		
		assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 304 Not Modified, si la cadena no se añade al fichero cadenas.txt
	 */
	@Test
	public void postAñadirCadenaFicheroProhibidaEscrituraTest() {
		
		File fichero = daoFichero.getFile();
		
		if(fichero.canWrite()) {
			fichero.setWritable(false);
		}
		
		ResponseEntity<String> httpResponse = ws.añadirCadena("Hola Mundo");
		
		assertEquals(HttpStatus.NOT_MODIFIED, httpResponse.getStatusCode());
		
	}
		
}
