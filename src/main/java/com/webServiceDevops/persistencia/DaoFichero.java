package com.webServiceDevops.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;
import java.text.Collator;

import org.springframework.stereotype.Component;

@Component
public class DaoFichero {

	private File file;

	/**
	 * En el constructor se revisa si existe el fichero en caso de no existir lo
	 * crea y en caso contrario lo lee
	 * 
	 * @throws IOException
	 */
	public DaoFichero() throws IOException {

		file = new File("cadenas.txt");
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("Se ha creado el fichero");
		} else {
			leerFichero(file);
		}

	}

	/**
	 * Lee la informacion del fichero de texto que recibe como parámetro
	 * 
	 * @param fichero
	 */
	public void leerFichero(File fichero) {

		try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr);) {
			String linea = br.readLine();
			System.out.println("\nEl contenido del fichero el siguiente");
			while (linea != null) {
				System.out.println(linea);
				linea = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en el fichero de texto atributo de la clase la cadena de texto
	 * recibida como parametro
	 * 
	 * @param cadena
	 */
	public boolean escribirFichero(String cadena) {

		boolean esEscrito = true;

		try (FileWriter writer = new FileWriter(file, true);
				BufferedWriter bufferWriter = new BufferedWriter(writer);) {
			bufferWriter.write(cadena);
			bufferWriter.newLine();
			System.out.println("Se ha insertado el texto");

		} catch (IOException e) {
			System.out.println("No se ha completado la escritura");
			e.printStackTrace();
			esEscrito = false;
			return esEscrito;
		}

		return esEscrito;
	}

	/**
	 * Cuenta el numero de lineas del fichero file en las que aparece la palabra
	 * pasada como parametro. No tiene en cuenta mayusculas/minusculas, tildes y
	 * solo cuenta la primera aparicion de la palabra en la cadena
	 * 
	 * @param palabra
	 * @return devuelve el numero de palabras coincidentes
	 */
	public String contarCadenasConPalabra(String palabra) {
		String totalCadenas = null;
		int contador = 0;

		if (!unaPalabraRequest(palabra)) {
			return null;
		}

		try (Scanner lector = new Scanner(file);) {
			while (lector.hasNext()) {
				String linea = lector.nextLine();
				linea = adaptarTexto(linea);
				palabra = adaptarTexto(palabra);
				if (linea.contains(palabra)) {
					contador++;
				}

			}
		} catch (FileNotFoundException e) {

			System.out.println("El fichero no existe");
			e.printStackTrace();
		}

		totalCadenas = String.valueOf(contador);

		return totalCadenas;
	}

	/**
	 * Verifica que la cadena pasada solo contenga una palabra
	 * 
	 * @param palabra
	 * @return True si solo contiene una palabra y false si tiene mas
	 */
	public boolean unaPalabraRequest(String palabra) {

		String[] totalPalabras = palabra.split(" ");
		if (totalPalabras.length == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * El metodo normaliza el texto para remplazar los caracteres unicodes y
	 * convertirlo a minusculas.
	 * 
	 * @param texto
	 * @return texto modificado 
	 * 
	 */
	public String adaptarTexto(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		texto = texto.toLowerCase();
		return texto;
	}

}
