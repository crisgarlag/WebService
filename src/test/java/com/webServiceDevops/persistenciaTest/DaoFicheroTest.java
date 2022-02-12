package com.webServiceDevops.persistenciaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.webServiceDevops.persistencia.DaoFichero;

@SpringBootTest
class DaoFicheroTest {
	
	@Autowired
	private DaoFichero daoFichero;
	
	/**
	 * Al metodo se le pasa una cadena y debe devolver true si la escribe en el fichero cadenas.txt creado
	 */
	@Test
	public void escribirFicheroTest() {
		String prueba = "Prueba";
		
		assertTrue(daoFichero.escribirFichero(prueba));		
		
	}
	/**
	 * Aunque el fichero se elimine previamente, el fichero debe darse de alta y escribir la cadena pasada como argumento, si es correcto devuelve true
	 */
	@Test
	public void escribirFicheroEliminadoTest() {
		
		String prueba = "Prueba";
		File file = new File("cadenas.txt");
		file.delete();
		
		assertTrue(daoFichero.escribirFichero(prueba));		
	}
	/**
	 * Si el metodo recibe una cadena con mas de una palabra debe devolver null, ya que no debe buscar en el texto cadenas con mas de una palabra
	 * @throws FileNotFoundException
	 */
	@Test
	public void contarCadenasconMasDeUnaPalabra() throws FileNotFoundException {
		
		String dosPalabras = "Hola Mundo";
		String resultado;
	
			resultado = daoFichero.contarCadenasConPalabra(dosPalabras);
			assertEquals(null, resultado);
	
	
	}
	/**
	 * Si la cadena contiene una palabra, el metodo devuelve true y la prueba es correcta
	 */
	@Test 
	public void unaPalabraRequestTest(){
		String palabra = "Hola";
		boolean resultado= daoFichero.unaPalabraRequest(palabra);
		 assertTrue(resultado);		
	}
	
	
	/**
	 * El metodo normaliza a nivel minusculas y eliminacion de tildes. Si la palabra devuelta por el metodo coincide con otra que cumpla con las 
	 * caracteristicas anteriormente citadas, la prueba sera correcta
	 */
	@Test
	public void adaptarTextoAMinusculasSinTildesTest() {
		String entrada = "COndición";
		String salida = "condicion";
	
		
		assertEquals(salida, daoFichero.adaptarTexto(entrada));
		
	}
	
	/**
	 * Se va a probar si al instanciar un objeto de la clase DaoFichero, este instancia otro objeto de la clase File.
	 * @throws IOException
	 */
	@Test
	public void constructorSinFicherotTest() throws IOException {
		
			daoFichero.getFile().delete();
			DaoFichero daofich= new DaoFichero();
		
	}
	
	/**
	 * Comprueba si el metodo leerFichero lanza una excepcion de la clase IOException, en ese caso el test sera correcto
	 */
	@Test
	public void leerFicheroIOExceptionTest() {
		
		File file1 = new File("C:\\file.txt");
		

		assertThrows(IOException.class, ()-> {
			daoFichero.leerFichero(file1);
		}, "Esto es un texto de excepcion");
	}
	
	
	/**
	 * Si el metodo no puede acceder al fichero debe lanzarse una excepcion de tipo FileNotFoundException, en ese caso el test sera correcto
	 */
	@Test
	public void contarCadenasConPalabraFileNotFoundTest() {
				
		assertThrows(FileNotFoundException.class, ()->{
			if(daoFichero.getFile().setReadable(false))
				daoFichero.contarCadenasConPalabra("Hola");
				
		});	
		
		daoFichero.getFile().setReadable(true);
		
	}
}
