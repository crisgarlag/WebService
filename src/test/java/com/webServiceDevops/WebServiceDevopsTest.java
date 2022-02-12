package com.webServiceDevops;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebServiceDevopsTest {
	
	/**
	 * Comprueba que el contexto de spring se inicia 
	 */
	@Test
	public void contextLoads() {
		
		WebServiceDevopsApplication.main(new String[] {});
	}
	
}
