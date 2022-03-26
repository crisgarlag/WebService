package com.webServiceDevops.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webServiceDevops.persistencia.UserRepository;
import com.webServiceDevops.token.Token;

@Component
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String user;
	private String password;
	private String token;
	private Date startLogin;
	
	
	

	public Usuario(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	
	public Usuario() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getStartLogin() {
		return startLogin;
	}

	public void setStartLogin(Date startLogin) {
		this.startLogin = startLogin;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", password=" + password + "]";
	}

	public String loginUser() {

		startLogin = new Date();
		token = Token.generateToken();
		return token;
	}

}
