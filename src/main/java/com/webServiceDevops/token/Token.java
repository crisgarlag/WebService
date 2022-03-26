package com.webServiceDevops.token;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase utilizada para crear tokens
 * @author cristiangarcialagar
 *
 */
public class Token {

	public static String generateToken() {

		String caracteres = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234567890@#.:-";
		String token = "";
		for (int i = 0; i < 75; i++) {

			token += caracteres.charAt(ThreadLocalRandom.current().nextInt(0, caracteres.length()));

		}

		return token;
	}

}
