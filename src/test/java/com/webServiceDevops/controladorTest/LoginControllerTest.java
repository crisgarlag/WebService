package com.webServiceDevops.controladorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.webServiceDevops.controlador.LoginController;
import com.webServiceDevops.controlador.LoginController.LoginUserPayload;
import com.webServiceDevops.controlador.UserController;
import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.UserRepository;

@SpringBootTest
public class LoginControllerTest {
	
	@Autowired
	private LoginController login;	
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserController user;
	
	private String UsuarioTest= "loginUsuarioTest";
	private String password="pass";
	private String UsuarioInexistenteTest= "4598485";
	private String passwordErronea="passErronea";
	
	@Test
	public void usuarioInexistenteLoginTest() throws NoSuchAlgorithmException {
		
		LoginUserPayload usuarioLogin = new LoginUserPayload(UsuarioInexistenteTest, password);
		ResponseEntity<Usuario> httpResponse=  login.login(usuarioLogin);
		
		assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
	}
	
	@Test
	public void usuarioPasswordErroneaLoginTest() throws NoSuchAlgorithmException {
		
		Usuario currentUser = repository.findByUser(UsuarioTest);
		if(currentUser==null) {
			user.addNewUser(new Usuario(UsuarioTest, password));
		}
		LoginUserPayload usuarioLogin = new LoginUserPayload(UsuarioTest, passwordErronea);
		ResponseEntity<Usuario> httpResponse=  login.login(usuarioLogin);
		assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
	}
	
	@Test
	public void usuarioNuloLoginTest() throws NoSuchAlgorithmException {
	
		assertThrows(NullPointerException.class, ()->{
			login.login(null);
		});
	}
	
	@Test
	public void checkLoginTokenInvalidoTest() {
		
			
		assertFalse(login.checkLogin("as", UsuarioTest));
		
	}

}
