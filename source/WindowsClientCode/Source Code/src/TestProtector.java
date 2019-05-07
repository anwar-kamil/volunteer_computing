
/*

references :: http://www.digizol.com/2009/10/java-encrypt-decrypt-jce-salt.html

setting 
 Access restriction: The type BASE64Decoder is not accessible due to restriction on required library C:\Program Files\Java\jre6\lib\rt.jar

So to avoid this do the following : 

* Go to Window-->Preferences-->Java-->Compiler-->Error/Warnings.
* Select Deprecated and Restricted API. Change it to warning.
* Change forbidden and Discouraged Reference and change it to warning. (or as your need.)


*/

public class TestProtector {
	 String salt = "this is a simple clear salt";
	 String passwordEnc ="";
     String passwordDec ="";
    
	    
	public String encrypt(String password ,String key){
	      //String password = "mypassword";
	       
	        try {            
	        passwordEnc = Protector.encrypt(password, salt,key);
	       }
	       catch (Exception e) {
		        e.printStackTrace();
		      }	   
	       // System.out.println("Salt Text : " + salt);
	       //System.out.println("Plain Text : " + password);
	       // System.out.println("Encrypted : " + passwordEnc);
	       // System.out.println("Decrypted : " + passwordDec);
	 
		 	return passwordEnc ;
	}
	
	public String decrypt(String passwordEnc ,String key){
	      //String password = "mypassword";
	       
	        try {            
	        passwordDec = Protector.decrypt(passwordEnc, salt,key);
	       }
	       catch (Exception e) {
		        e.printStackTrace();
		      }	   
	       // System.out.println("Salt Text : " + salt);
	       //System.out.println("Plain Text : " + password);
	       // System.out.println("Encrypted : " + passwordEnc);
	       // System.out.println("Decrypted : " + passwordDec);
	 
		 	return passwordDec ;
	}
	
	/*
    public static void main(String[] args) throws Exception {
        String password = "mypassword";
        String salt = "this is a simple clear salt";
       
        String passwordEnc ="";
        String passwordDec ="";
        try {            
        passwordEnc = Protector.encrypt(password, salt);
        passwordDec = Protector.decrypt(passwordEnc, salt);
       }
       catch (Exception e) {
	        e.printStackTrace();
	      }	   
        System.out.println("Salt Text : " + salt);
        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted : " + passwordEnc);
        System.out.println("Decrypted : " + passwordDec);
    }
    */
}