package com.webServiceDevops.controlador;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServiceDevops.controlador.LoginController.LoginUserPayload;
import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.UserRepository;

@RestController
@RequestMapping(path = "/logout")
public class LogoutController {
	
	
	@Autowired
	private UserRepository repository;
	/**
	 * Finaliza la sesion del usuario
	 * @param usuarioLogout usuario y contraseña del usuario
	 * @return Ok si el usuario realiza el logout y not found si el usuario pasado como parametro no existe
	 */
	@PostMapping
	public ResponseEntity<Usuario> logout(@RequestBody LoginUserPayload usuarioLogout){
		
		int time = 60*60; //minutos*segundos
		Usuario currentUser = repository.findByUser(usuarioLogout.user);
		if(currentUser==null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);	
		}
		currentUser.setStartLogin(changeStartLogin(currentUser.getStartLogin(), -time));
		repository.save(currentUser);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
				
	}
	/**
	 * Permite modificar la fecha de start login para que le reste una hora
	 * @param startLogin del usuario
	 * @param time es el tiempo en segundos que se va cambiar la fecha de login
	 * @return la nueva fecha del startlogin
	 */
	public Date changeStartLogin(Date startLogin, int time) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startLogin);
		calendar.add(Calendar.SECOND, time);
		Date newStartLogin = calendar.getTime();
		
		return newStartLogin;
		
	}
	
	

}
