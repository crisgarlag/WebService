package com.webServiceDevops.controladorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

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

import com.webServiceDevops.controlador.LoginController;
import com.webServiceDevops.controlador.LoginController.LoginUserPayload;
import com.webServiceDevops.controlador.UserController;
import com.webServiceDevops.controlador.WebService;
import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.DaoFichero;
import com.webServiceDevops.persistencia.UserRepository;

@SpringBootTest
class WebServiceTest {
	
	public static String URL_SERVIDOR = "http://localhost:12345";

	@Autowired
	private DaoFichero daoFichero;
	
	@Autowired
	private WebService ws;
	@Autowired
	private UserRepository repository;
	@Autowired
	private LoginController login;
	@Autowired
	private UserController user;
	
	
	@Test
	public void getContarCadenaUnaPalabraTest() throws FileNotFoundException, NoSuchAlgorithmException {
		
			
		ResponseEntity<String> httpResponse = ws.contarCadena("Hola",loginUsuarioPrueba().getUser(),loginUsuarioPrueba().getToken());
		
		assertEquals(HttpStatus.OK,httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 200 OK, si recibe una unica palabra en la cadena pasada como argumento
	 * @throws FileNotFoundException
	 */
	@Test
	public void getContarCadenaUnaPalabraUsuarioTokenInvalidosTest() throws FileNotFoundException {
		
		ResponseEntity<String> httpResponse = ws.contarCadena("Hola",null,null);
		
		assertEquals(HttpStatus.FORBIDDEN,httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 400 Bad Request, si recibe más de una palabra en la cadena pasada como argumento
	 * @throws FileNotFoundException
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void getContarCadenaMasDeUnaPalabraTest() throws FileNotFoundException, NoSuchAlgorithmException {
		
				
		ResponseEntity<String> httpResponse = ws.contarCadena("Hola Mundo",loginUsuarioPrueba().getUser(),loginUsuarioPrueba().getToken());
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
		
	}
	
	
	@Test
	public void postAñadirCadenaTest() throws NoSuchAlgorithmException {
		
	File fichero = daoFichero.getFile();
			
		if(!fichero.canWrite()) {
			fichero.setWritable(true);
		}
		
		ResponseEntity<String> httpResponse = ws.añadirCadena("Hola Mundo", loginUsuarioPrueba().getUser(),loginUsuarioPrueba().getToken());
		
		assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
		
	}
	
	
	/**
	 * El metodo debe devolver el codigo 201 Created, si la cadena se añade al fichero cadenas.txt
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void postAñadirCadenaUsuarioTokenInvalidosTest() throws NoSuchAlgorithmException {
		
	File fichero = daoFichero.getFile();
		
		if(!fichero.canWrite()) {
			fichero.setWritable(true);
		}
		
		ResponseEntity<String> httpResponse = ws.añadirCadena("Hola Mundo",null,null);
		
		assertEquals(HttpStatus.FORBIDDEN, httpResponse.getStatusCode());
		
	}
	/**
	 * El metodo debe devolver el codigo 304 Not Modified, si la cadena no se añade al fichero cadenas.txt
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void postAñadirCadenaFicheroProhibidaEscrituraTest() throws NoSuchAlgorithmException {
		
		File fichero = daoFichero.getFile();
		
		
		if(fichero.canWrite()) {
			fichero.setWritable(false);
		}
		
		ResponseEntity<String> httpResponse = ws.añadirCadena("Hola Mundo",loginUsuarioPrueba().getUser(),loginUsuarioPrueba().getToken());
		
		assertEquals(HttpStatus.NOT_MODIFIED, httpResponse.getStatusCode());
		
	}
	
	public Usuario loginUsuarioPrueba() throws NoSuchAlgorithmException {
		
		String loginUsuarioPrueba= "loginUsuarioPrueba";
		String password="pass";
		Usuario currentUser = repository.findByUser(loginUsuarioPrueba);
		if(currentUser==null) {
			user.addNewUser(new Usuario(loginUsuarioPrueba, password));
		}
		LoginUserPayload userLogin = new LoginUserPayload(loginUsuarioPrueba,password);
		login.login(userLogin);
		currentUser = repository.findByUser(userLogin.getName());
		return currentUser;
		
	}
		
}
