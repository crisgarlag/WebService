package com.webServiceDevops.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import java.util.Scanner;
import java.text.Collator;

import org.springframework.stereotype.Component;

@Component
public class DaoFichero {

	private File file;

	public DaoFichero() throws IOException {

		file = new File("cadenas.txt");
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("Se ha creado el fichero");
		} else {
			leerFichero(file);
		}

	}

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

	public void escribirFichero(String cadena) {

		try (FileWriter writer = new FileWriter(file, true);
				BufferedWriter bufferWriter = new BufferedWriter(writer);) {
			bufferWriter.write(cadena);
			bufferWriter.newLine();
			System.out.println("Se ha insertado el texto");

		} catch (IOException e) {
			System.out.println("No se ha completado la escritura");
			e.printStackTrace();
		}
	}

	public String contarCadenasConPalabra(String palabra) {
		Collator c = Collator.getInstance(new Locale("es"));
		c.setStrength(Collator.PRIMARY);
		String totalCadenas = null;
		int contador = 0;

		if (!unaPalabraRequest(palabra)) {
			return null;
		}

		try (Scanner lector = new Scanner(file);) {
			while (lector.hasNext()) {
				String linea = lector.nextLine();
				String[] totalPalabras = linea.split(" ");
				for (int i = 0; i < totalPalabras.length; i++) {
					if (c.equals(totalPalabras[i], palabra)) {
						contador++;
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {

			System.out.println("El fichero no existe");
			e.printStackTrace();
		}

		totalCadenas = String.valueOf(contador);

		return totalCadenas;
	}

	public boolean unaPalabraRequest(String palabra) {

		String[] totalPalabras = palabra.split(" ");
		System.out.println(totalPalabras.length);
		if (totalPalabras.length == 1) {
			return true;
		} else {
			return false;
		}
	}

}
