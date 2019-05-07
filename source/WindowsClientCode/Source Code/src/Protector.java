
//package org.kamal.crypto;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

/*
 * this is AES algorithm which is used to generate session key ,encrypt and decrypt data .
 * this code is taken from the internet which reference link is given its main class.
 * 
 * */

public class Protector {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";//"AES";
    private static final int ITERATIONS = 2;
    private static final byte[] keyValue = 
        new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public static String encrypt(String value, String salt ,String newkey) throws Exception {
      	/*
    	BASE64Decoder decoder = new BASE64Decoder();
        byte[] encodedKey = decoder.decodeBuffer(newkey);
       	Key key =  new SecretKeySpec(encodedKey,0,encodedKey.length, ALGORITHM);
        */       
    	Key key =  generateKey(newkey);
      
    	System.out.println("key length generate  :" + key);
    	Cipher c = Cipher.getInstance(ALGORITHM);  
        c.init(Cipher.ENCRYPT_MODE, key);
  
    
      
        String valueToEnc = null;
        String eValue = value;
        for (int i = 0; i < ITERATIONS; i++) {
            valueToEnc = salt + eValue;
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            eValue = new BASE64Encoder().encode(encValue);
        }
        return eValue;
    }

    public static String decrypt(String value, String salt ,String newkey) throws Exception {
       /*
        BASE64Encoder encoder = new BASE64Encoder();
        String keyString = encoder.encode(key.getEncoded());
        */
    
    	Key key =  generateKey(newkey);
        
    	/*
    	BASE64Decoder decoder = new BASE64Decoder();
        byte[] encodedKey = decoder.decodeBuffer(newkey);
       	Key key =  new SecretKeySpec(encodedKey,0,encodedKey.length, ALGORITHM);
        */
       	
       	Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
  
        String dValue = null;
        String valueToDecrypt = value;
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
            byte[] decValue = c.doFinal(decordedValue);
            dValue = new String(decValue).substring(salt.length());
            valueToDecrypt = dValue;
        }
        return dValue;
    }

    private static Key generateKey(String newkey) throws Exception {
       // Key key = new SecretKeySpec(keyValue, ALGORITHM);   //old
        
    	
    	Key key = new SecretKeySpec(newkey.getBytes(), ALGORITHM);
        // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // key = keyFactory.generateSecret(new DESKeySpec(keyValue));
        return key;
    }
}