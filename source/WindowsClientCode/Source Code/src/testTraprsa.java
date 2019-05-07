import java.util.Arrays;
import java.util.Random;


public class testTraprsa {

	static  RSA Rsa = new RSA();
	static  HashSHA Hash = new HashSHA();
	static  TestProtector Session = new TestProtector();
	
	
	static String Encryption (String y,String text){
		//public key of server already embedded in client  
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		//System.out.println(randomInt);
		String tempkey ="";
		
		tempkey =  "ttttttttttqqqqqq";//Hash.Hash(Integer.toString(randomInt));
		
		System.out.println("temkey size : " +tempkey.length());
		
		//tempkey = Arrays.copyOf(tempkey.getBytes(), 32).toString();
		
		
	    y = Rsa.client_PK_plus_Enc(tempkey); //server pk required change the function
		
		String cipher = Session.encrypt(text,tempkey);
		
		
		return cipher ;
	}
	
	
	static String Decryption (String y,String Cipher){
		//public key of server already embedded in client  
	
		String x = Rsa.client_PK_plus_Enc(y); //server sk key required change function
		
		String tempkey ="";
		
		tempkey = "ttttttttttqqqqqq" ;//Hash.Hash(x);
		
		//tempkey = Arrays.copyOf(tempkey.getBytes(), 16).toString();
		System.out.println("temkey size : " +tempkey.length());
		
		
	   String plain = Session.decrypt(Cipher,tempkey);
		
		
		return plain ;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
	/*
		String Text = "helloworld" ;
		String cipher = "";
		System.out.println("plain text :" + Text);
		cipher = Session.encrypt(Text , "atifhussainrrrrr");
		System.out.println("cipher text :" + cipher);
		Text = "";
		Text = Session.decrypt(cipher, "atifhussainrrrrr");
		System.out.println("plain text :" + Text);
		
	*/
		String  y = null ,plain = "Atif the great sign please let you in the sky and see the circleness of the world";
		
		String cipher = Encryption(y,plain);
		
		System.out.println("plain Text :" + plain);
		System.out.println("y value    :" + y);
		
		System.out.println("cipher Text    :" + cipher);
		
		
		plain = "";
		
		plain = Decryption(y,cipher);
		
		
        System.out.println("plain text after Decryption :" + plain);
		
		
		
		
		
		
	}
	
}
	/*
	 
	 
import java.util.Random;

/** Generate 10 random integers in the range 0..99. */
/*
	public final class RandomInteger {
  
  public static final void main(String... aArgs){
    log("Generating 10 random integers in range 0..99.");
    
    //note a single Random object is reused here
    Random randomGenerator = new Random();
    for (int idx = 1; idx <= 10; ++idx){
      int randomInt = randomGenerator.nextInt(100);
      log("Generated : " + randomInt);
    }
    
    log("Done.");
  }
  
  private static void log(String aMessage){
    System.out.println(aMessage);
  }
}
 


Example run of this class :

Generating 10 random integers in range 0..99.
Generated : 44
Generated : 81
Generated : 69
Generated : 31
Generated : 10
Generated : 64
Generated : 74
Generated : 57
Generated : 56
Generated : 93
Done.

Example 2

This example generates random integers in a specific range.


import java.util.Random;
*/
/** Generate random integers in a certain range. */
/*
public final class RandomRange {
  
  public static final void main(String... aArgs){
    log("Generating random integers in the range 1..10.");
    
    int START = 1;
    int END = 10;
    Random random = new Random();
    for (int idx = 1; idx <= 10; ++idx){
      showRandomInteger(START, END, random);
    }
    
    log("Done.");
  }
  
  private static void showRandomInteger(int aStart, int aEnd, Random aRandom){
    if ( aStart > aEnd ) {
      throw new IllegalArgumentException("Start cannot exceed End.");
    }
    //get the range, casting to long to avoid overflow problems
    long range = (long)aEnd - (long)aStart + 1;
    // compute a fraction of the range, 0 <= frac < range
    long fraction = (long)(range * aRandom.nextDouble());
    int randomNumber =  (int)(fraction + aStart);    
    log("Generated : " + randomNumber);
  }
  
  private static void log(String aMessage){
    System.out.println(aMessage);
  }
} 



An example run of this class :

Generating random integers in the range 1..10.
Generated : 9
Generated : 3
Generated : 3
Generated : 9
Generated : 4
Generated : 1
Generated : 3
Generated : 9
Generated : 10
Generated : 10
Done.

Example 3

This example generates random floating point numbers in a Gaussian (normal) distribution.


import java.util.Random;
*/
/** 
Generate pseudo-random floating point values, with an 
approximately Gaussian (normal) distribution.

Many physical measurements have an approximately Gaussian 
distribution; this provides a way of simulating such values. 
*/
/*
public final class RandomGaussian {
  
  public static void main(String... aArgs){
    RandomGaussian gaussian = new RandomGaussian();
    double MEAN = 100.0f; 
    double VARIANCE = 5.0f;
    for (int idx = 1; idx <= 10; ++idx){
      log("Generated : " + gaussian.getGaussian(MEAN, VARIANCE));
    }
  }
    
  private Random fRandom = new Random();
  
  private double getGaussian(double aMean, double aVariance){
      return aMean + fRandom.nextGaussian() * aVariance;
  }

  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
} 

}
*/