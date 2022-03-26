package com.webServiceDevops.controladorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.webServiceDevops.controlador.UserController;
import com.webServiceDevops.controlador.LoginController.LoginUserPayload;
import com.webServiceDevops.entidad.Usuario;

@SpringBootTest
public class UserControllerTest {
	
	
	@Autowired
	private UserController userController;
	
	private String usuarioExistente= "asdf";
	private String password = "password1";
	private String usuarioInexistente= "4598485";
	
	@Test
	public void nuevoUsuarioTest() throws NoSuchAlgorithmException {
		
		userController.deleteUser(usuarioExistente);
		
		ResponseEntity<Usuario> httpStatus = userController.addNewUser(new Usuario(usuarioExistente,password));
		assertEquals(HttpStatus.CREATED, httpStatus.getStatusCode());
		
	}
	
	
	@Test
	public void nuevoUsuarioExistenteTest() throws NoSuchAlgorithmException {
				
		assertThrows(DataIntegrityViolationException.class,()->{
			userController.addNewUser(new Usuario(usuarioExistente, password));
			
		});		
	}
	
	@Test
	public void borrarUsuarioInexistenteTest() throws NoSuchAlgorithmException {
		
		ResponseEntity<Usuario> httpResponse = userController.deleteUser(usuarioInexistente);
			
		assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
		
	}
	
	
}
