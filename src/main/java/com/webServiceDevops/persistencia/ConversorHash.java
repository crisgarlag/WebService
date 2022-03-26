package com.webServiceDevops.persistencia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilizada para cifrar la contraseña que se almacenara en la bbbdd
 * @author cristiangarcialagar
 *
 */
public class ConversorHash {
    /**
     * Metodo para convertir las password a resumen hash
     * @param pass la contraseña a convertir en resumen hash
     * @return el resumen hash
     * @throws NoSuchAlgorithmException
     */
    public static String convertirAHash(String pass) throws NoSuchAlgorithmException {

        byte[] bytePass = pass.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(bytePass);
        byte[] hash = md.digest();
        String passHash = new String(hash);

        return passHash;
    }

}
