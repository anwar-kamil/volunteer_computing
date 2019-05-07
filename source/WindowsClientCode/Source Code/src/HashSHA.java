 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
/*
 * this class is used to calulate the Hash values of given data using the SHA-1 algorithm
 * this code is taken from internet which reference link is given in research paper. 
 * */

public class HashSHA 
{     
	
	public String Hash(String password){
	//	String password = "123456";
		StringBuffer sb = new StringBuffer();
         
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		    md.update(password.getBytes());
			byte byteData[] = md.digest();
			
	     	for (int i = 0; i < byteData.length; i++) {
		         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		        }
		  //  System.out.println("Hex format : " + sb.toString());
	    	
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         		
		return sb.toString();
	}
 
 
}