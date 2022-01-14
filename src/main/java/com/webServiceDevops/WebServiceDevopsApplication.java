package com.webServiceDevops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebServiceDevopsApplication {

	public static void main(String[] args) {

		System.out.println("Iniciando contexto Spring");

		SpringApplication.run(WebServiceDevopsApplication.class, args);

	}

}
