/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Simulado
 */
public class Encripta {

    Cipher cipher;
    byte[] chave;

    public Encripta() {

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.err.print(ex);
        }
        chave = "chave de 16bytes".getBytes();

    }

    public String encriptar(String value) {

        try {
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"));
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return DatatypeConverter.printBase64Binary(encrypted);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            return null;
        }

    }

    public String desencriptar(String cifra) {

        try {
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"));
            byte[] decrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(cifra));
            return new String(decrypted);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.print(ex);
            return null;
        }

    }

    public static String criptografarSenha(String senha) {

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.err.println(e);
            return null;
        }
    }

}
