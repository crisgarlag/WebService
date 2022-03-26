package com.webServiceDevops.controlador;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.ConversorHash;
import com.webServiceDevops.persistencia.UserRepository;

@RestController
@RequestMapping(path = "/login") //
public class LoginController {

	/**
	 * Clase de apoyo para recibir los datos recibidos del cliente y comparar con
	 * los datos de la bbdd
	 * 
	 * @author cristiangarcialagar
	 *
	 */
	public static class LoginUserPayload {
		String user;
		String password;

		public LoginUserPayload(String user, String password) {
			this.user = user;
			this.password = password;
		}

		public String getName() {
			return user;
		}

		public String getPassword() {
			return password;
		}

	}

	private final UserRepository repository;
	private Usuario currentUser;

	public LoginController(UserRepository repository) {
		this.repository = repository;
	}
	/**
	 * El usuario se logea y devuelve token si existe el nombre y la contraseña es correcta
	 * @param usuarioLogin objeto que contiene el usuario y contraseñas pasadas en el body de la request
	 * @return NotFound si el usuario no existe o la contraseña no es correcta y token + datos del usuario en caso de existir el usuario y ser correcta la contraseña
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Usuario> login(@RequestBody LoginUserPayload usuarioLogin) throws NoSuchAlgorithmException {

		currentUser = repository.findByUser(usuarioLogin.user);

		if (currentUser == null || !checkUserPassword(usuarioLogin.user, usuarioLogin.password)) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}

		String token = currentUser.loginUser();
		// Guardamos usuario con las propiedades token y startlogin
		repository.save(currentUser);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", token);
		return ResponseEntity.ok().headers(responseHeaders).body(currentUser);
	}
	
	/**
	 * Permite chequear para el login que la contraseña pasada para el login coincida con la almacenada en la bbdd para el usuario
	 * @param user a loguearse
 	 * @param password
	 * @return true si es correcta y false de lo contrario
	 * @throws NoSuchAlgorithmException
	 */
	public boolean checkUserPassword(String user, String password) throws NoSuchAlgorithmException {

		String passwordHash = ConversorHash.convertirAHash(password);
		currentUser = repository.findByUser(user);
		// Solo contrasto la contraseña porque el usuario es unico
		if (currentUser.getPassword().equals(passwordHash)) {

			return true;
		} else {
			return false;
		}
	}
	/**
	 * Permite comprobar si el usuario se encuentra logueado y el tiempo que lleva logueado no es superior a 1 hora
	 * @param token valido del usuario que está logueado
	 * @param user logueado
	 * @return true si el usuario se encuentra logueado y el tiempo que lleva logueado no es superior a 1 hora y false de lo contrario
	 */
	public boolean checkLogin(String token, String user) {

		Usuario currentUser = repository.findByUser(user);
		if (currentUser == null) {
			return false;
		}
		Date checkLogin = new Date();
		int time = 60 * 60;// segundos * minutos
		Long tiempoLoginValido = (checkLogin.getTime() - currentUser.getStartLogin().getTime()) / 1000;

		if (token.equals(currentUser.getToken()) && tiempoLoginValido < time)
			return true;
		else
			return false;
	}

}
