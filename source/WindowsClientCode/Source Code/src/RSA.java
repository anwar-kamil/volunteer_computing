
//package in.javadigest.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * @author JavaDigest
 *  http://javadigest.wordpress.com/2012/08/26/rsa-encryption-example/
 */
public class RSA {

  /**
   * String to hold name of the encryption algorithm.
   */
  public static final String ALGORITHM = "RSA";

  /**
   * String to hold the name of the private key file.
   */
  public static final String PRIVATE_KEY_FILE = "../fyp/keys/private.key";

  /**
   * String to hold name of the public key file.
   */
  public static final String PUBLIC_KEY_FILE = "../fyp/keys/public.key";

  public static final String SERver_PUBLIC_KEY_FILE = "../fyp/keys/serverpublickey/public.key";

  
  
  
  
  /**
   * Generate key which contains a pair of private and public key using 1024
   * bytes. Store the set of keys in Prvate.key and Public.key files.
   * 
   * @throws NoSuchAlgorithmException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static void generateKey() {
    try {
      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
      keyGen.initialize(1024);
      final KeyPair key = keyGen.generateKeyPair();

      File privateKeyFile = new File(PRIVATE_KEY_FILE);
      File publicKeyFile = new File(PUBLIC_KEY_FILE);

      // Create files to store public and private key
      if (privateKeyFile.getParentFile() != null) {
        privateKeyFile.getParentFile().mkdirs();
      }
      privateKeyFile.createNewFile();

      if (publicKeyFile.getParentFile() != null) {
        publicKeyFile.getParentFile().mkdirs();
      }
      publicKeyFile.createNewFile();

      // Saving the Public key in a file
      ObjectOutputStream publicKeyOS = new ObjectOutputStream(
          new FileOutputStream(publicKeyFile));
      publicKeyOS.writeObject(key.getPublic());
      publicKeyOS.close();

      // Saving the Private key in a file
      ObjectOutputStream privateKeyOS = new ObjectOutputStream(
          new FileOutputStream(privateKeyFile));
      privateKeyOS.writeObject(key.getPrivate());
      privateKeyOS.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * The method checks if the pair of public and private key has been generated.
   * 
   * @return flag indicating if the pair of keys were generated.
   */
  public static boolean areKeysPresent() {

    File privateKey = new File(PRIVATE_KEY_FILE);
    File publicKey = new File(PUBLIC_KEY_FILE);

    if (privateKey.exists() && publicKey.exists()) {
        return true;
    }
      System.out.println("Client keys not present "); 
     return false;
  }

  /**
   * Encrypt the plain text using public key.
   * 
   * @param text
   *          : original plain text
   * @param key
   *          :The public key
   * @return Encrypted text
   * @throws java.lang.Exception
   */
  public static byte[] encrypt(String text, PublicKey key) {
    byte[] cipherText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // encrypt the plain text using the public key
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipherText = cipher.doFinal(text.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  /**
   * Decrypt text using private key.
   * 
   * @param text
   *          :encrypted text
   * @param key
   *          :The private key
   * @return plain text
   * @throws java.lang.Exception
   */
  public static String decrypt(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance(ALGORITHM);

      // decrypt the text using the private key
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return new String(dectyptedText);
  }

  /**
   * Test the EncryptionUntil

   */
  
  public String client_PK_plus_Enc(final String originalText) {
	  
	  //final String originalText = "Text to be encrypted ";
      
      ObjectInputStream inputStream = null;
     // Encrypt the string using the public key
       byte[] cipherText = null ;
      
      try {
    	 
    	  
    	    // Check if the pair of keys are present else generate those.
	      if (!areKeysPresent()) {
	        // Method generates a pair of keys using the RSA algorithm and stores it
	        // in their respective files
	        generateKey();
	      }

    	  
    	  
    	  
    	  
    	  
    	 inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
		final PublicKey publicKey = (PublicKey) inputStream.readObject();
	    cipherText = encrypt(originalText, publicKey);
	    
     
     } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	  return new String(cipherText) ;
  }
  
 public String Server_PK_plus_Enc(final String originalText) {
	  
	  //final String originalText = "Text to be encrypted ";
      
      ObjectInputStream inputStream = null;
     // Encrypt the string using the public key
       byte[] cipherText = null ;
      
      try {
    	 
    	 inputStream = new ObjectInputStream(new FileInputStream(SERver_PUBLIC_KEY_FILE));
		final PublicKey publicKey = (PublicKey) inputStream.readObject();
	    System.out.println("server public key : " + publicKey.toString() );
    	cipherText = encrypt(originalText, publicKey);
	    
     
     } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	  return new String(cipherText) ;
  }
 
  
  public String client_PK_minus_Dec(final String originalText) {
	  
	  

	  
	  //final String originalText = "Text to be encrypted ";
      
      ObjectInputStream inputStream = null;
     // Encrypt the string using the public key
       byte[] cipherText = originalText.getBytes() ;
       String plainText = null ;
       
      try {
    	 
    	    // Check if the pair of keys are present else generate those.
	      if (!areKeysPresent()) {
	        // Method generates a pair of keys using the RSA algorithm and stores it
	        // in their respective files
	        generateKey();
	      }

	  
    	  // Decrypt the cipher text using the private key.
          inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
          final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
           plainText = decrypt(cipherText, privateKey);
    
     
     } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	  return plainText ;
  }

  
  public static void encryptDecryptFile(String srcFileName, String destFileName, Key key, int cipherMode) throws Exception
  {

      OutputStream outputWriter = null;
      InputStream inputReader = null;
      try
      {

      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      String textLine = null;
      byte[] buf = cipherMode == Cipher.ENCRYPT_MODE? new byte[100] : new byte[128];
      int bufl;
      // init the Cipher object for Encryption…
      cipher.init(cipherMode, key);

      // start FileIO
      outputWriter = new FileOutputStream(destFileName);
      inputReader = new FileInputStream(srcFileName);
      while ( (bufl = inputReader.read(buf)) != -1)
      {

      byte[] encText = null;
      String encText1 = null;
       
      if (cipherMode == Cipher.ENCRYPT_MODE)
      {

      encText = encrypt(copyBytes(buf,bufl),(PublicKey)key);
      outputWriter.write(encText);

  }
  else
  {

      encText1 = decrypt(copyBytes1(buf,bufl),(PrivateKey)key); 
      outputWriter.write(Integer.parseInt(encText1));

  }
  }
  outputWriter.flush();
  }
  finally
  {

      try
      {

      if (outputWriter != null)
      {

      outputWriter.close(); 

  }
  if (inputReader != null)
  {

      inputReader.close(); 

  }
  }
  catch (Exception e)
  {

      // do nothing…

  }
  }
  }

  public static String copyBytes(byte[] arr, int length)
  {
    byte[] newArr = null;
    if (arr.length == length)
    {
      newArr = arr;
    }
    else
    {
      newArr = new byte[length];
      for (int i = 0; i < length; i++)
      {
        newArr[i] = (byte) arr[i];
      }
    }
    return newArr.toString();
  }

  //Read more: http://www.aviransplace.com/2004/10/12/using-rsa-encryption-with-java/3/#ixzz2VJqyyYe1
  //Follow us: @aviranm on Twitter
   
  public static byte[] copyBytes1(byte[] arr, int length)
  {
    byte[] newArr = null;
    if (arr.length == length)
    {
      newArr = arr;
    }
    else
    {
      newArr = new byte[length];
      for (int i = 0; i < length; i++)
      {
        newArr[i] = (byte) arr[i];
      }
    }
    return newArr;
  }


  
  
  
  
/*  
  public static void main(String[] args) {

    try {
      // Check if the pair of keys are present else generate those.
      if (!areKeysPresent()) {
        // Method generates a pair of keys using the RSA algorithm and stores it
        // in their respective files
        generateKey();
      }

      final String originalText = "Text to be encrypted ";
      
       ObjectInputStream inputStream = null;

      // Encrypt the string using the public key
      inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
      final PublicKey publicKey = (PublicKey) inputStream.readObject();
      final byte[] cipherText = encrypt(originalText, publicKey);
         
      
      // Decrypt the cipher text using the private key.
      inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
      final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
      final String plainText = decrypt(cipherText, privateKey);

      // Printing the Original, Encrypted and Decrypted Text
      System.out.println("Original Text: " + originalText);
      System.out.println("Encrypted Text: " + new String(cipherText));
      System.out.println("Decrypted Text: " + plainText);

    } catch (Exception e) {
      e.printStackTrace();
    }
  
  }
  */
}