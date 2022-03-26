	    package com.webServiceDevops.controlador;

import java.security.NoSuchAlgorithmException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webServiceDevops.entidad.Usuario;
import com.webServiceDevops.persistencia.ConversorHash;
import com.webServiceDevops.persistencia.UserRepository;



@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired Usuario usuario;

	/**
	 * Permite crear un usuario nuevo y almacenarlo en la bbdd
	 * @param nuevoUsuario que se va a crear
	 * @return Created si se crea con exito
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Usuario> addNewUser(@RequestBody Usuario nuevoUsuario) throws NoSuchAlgorithmException {

		usuario=new Usuario();
		usuario.setUser(nuevoUsuario.getUser());
		String passwordHash = ConversorHash.convertirAHash(nuevoUsuario.getPassword());
		usuario.setPassword(passwordHash);
		userRepository.save(usuario);
		
			return new ResponseEntity<Usuario>(HttpStatus.CREATED);
		
	}
	/**
	 * Permite borrar un usuario pasado en la url
	 * @param user
	 * @return Accepted si lo borra y no encontrado si el usuario a borrar no existe
	 */
	@DeleteMapping(path="/delete/{user}")
	public ResponseEntity<Usuario> deleteUser(@PathVariable("user") String user){
		
		
		Usuario usuarioDevuelto = userRepository.findByUser(user);
		if(usuarioDevuelto==null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(usuarioDevuelto.getId());
			
		
			return new ResponseEntity<Usuario>(HttpStatus.ACCEPTED);
		

	}
	
}
