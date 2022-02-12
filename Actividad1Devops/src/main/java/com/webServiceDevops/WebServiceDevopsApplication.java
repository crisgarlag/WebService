package com.webServiceDevops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class WebServiceDevopsApplication {
	

	public static void main(String[] args) {

		System.out.println("Iniciando contexto Spring");

		SpringApplication.run(WebServiceDevopsApplication.class, args);

	}

}
