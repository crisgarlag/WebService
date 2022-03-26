package com.webServiceDevops.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webServiceDevops.entidad.Usuario;

/**
 * Permite interactuar con la bbdd creada para la actividad
 * @author cristiangarcialagar
 *
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

	public Usuario findByUser(String user);
	
}