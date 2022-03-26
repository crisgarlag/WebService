package com.webServiceDevops.controladorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.webServiceDevops.controlador.UserController;
import com.webServiceDevops.controlador.LoginController;
import com.webServiceDevops.controlador.LoginController.LoginUserPayload;
import com.webServiceDevops.controlador.LogoutController;
import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.UserRepository;

@SpringBootTest
public class LogoutControllerTest {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserController user;
	@Autowired
	private LogoutController logout;
	@Autowired
	private LoginController login;
	private String UsuarioTest= "loginUsuarioTest";
	private String password="pass";
	private String UsuarioInexistenteTest= "4598485";
	
	@Test
	public void logoutUsuarioValidoTest() throws NoSuchAlgorithmException {
		
		Usuario currentUser = repository.findByUser(UsuarioTest);
		if(currentUser==null) {
			user.addNewUser(new Usuario(UsuarioTest, password));
		}
		LoginUserPayload usuarioPrueba = new LoginUserPayload(UsuarioTest, password);
		login.login(usuarioPrueba);
		ResponseEntity<Usuario> httpResponse = logout.logout(usuarioPrueba);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
		
	}
	
	@Test
	public void logoutUsuarioNoExistenteTest() throws NoSuchAlgorithmException {
		
		user.deleteUser(UsuarioInexistenteTest);
		LoginUserPayload usuarioLogoutTest = new LoginUserPayload(UsuarioInexistenteTest, password);
		ResponseEntity<Usuario> httpResponse = logout.logout(usuarioLogoutTest);
		
		assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
		
	}
	
	@Test
	public void logoutUsuarioNullTest() throws NoSuchAlgorithmException {
			
		assertThrows(NullPointerException.class, ()->{
			logout.logout(null);
		});
		
	}
	
	
	
	
}
