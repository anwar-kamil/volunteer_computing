import java.io.*;
import sun.misc.BASE64Encoder;
import java.security.PublicKey;
import java.util.Scanner;

/*
This class is used to handle all file Transactions.
some functions are specific pattern dependent.
*/

public class filing {
	
	/*
	This function is used to read task information and write it on the object tasks t
	this functions is read specific pattern .
	*/	

void read_current_taskinfo_file(String filename ,tasks t) throws FileNotFoundException{
		Scanner input = new Scanner (new File(filename));
		String temp = ""; 
		  
		while(input.hasNextLine())  {
		   temp = input.nextLine();
	       if(temp.matches("(.*)currenttaskid:(.*)")){ 
			   t.currenttaskid = temp.substring(14, temp.indexOf(";")); 		
	       }
	       temp = input.nextLine();
	       if(temp.matches("(.*)maintaskid(.*)")){ 
			   t.maintaskid = temp.substring(11, temp.indexOf(";")); 		
	       }
	       temp = input.nextLine();
	       if(temp.matches("(.*)status(.*)")){ 
			   t.status = temp.substring(7, temp.indexOf(";")); 		
	       }
	  }  
	  input.close();
}

String read_ip_file(String filename ) throws FileNotFoundException{
	Scanner input = new Scanner (new File(filename));
	String temp = "" ,retun = ""; 
	  
	while(input.hasNextLine())  {
	   temp = input.nextLine();
       if(temp.matches("(.*)IP Address:(.*)")){ 
    	   retun  = temp.substring(11, temp.indexOf(";")); 		
       }
       
  }  
  input.close();

  return retun ;
}






/*
This function is used to write task information and read it from the object tasks t
this functions is write specific pattern .
*/	


void write_current_taskinfo_file(String filename ,tasks t) throws FileNotFoundException{
		
	String temp = "currenttaskid:" + t.currenttaskid + ";\n" ;
	       temp += "maintaskid:" + t.maintaskid + ";\n" ;
	       temp += "status:" + t.status + ";" ;
	       
	
   try{
         File newTextFile = new File(filename);
         FileWriter fw = new FileWriter(newTextFile);
           fw.write(temp);
           fw.close();
   
           System.out.println("file save to : " + filename ); 		    
           System.out.println("file data : " + temp ); 		    
		           
           
           
           
   	 } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} // writes the bytes

}

/*
This function is used to write current task information and read it from the object tasks t
this functions is write specific pattern .
*/	


void write_create_current_taskinfo_file(String filename ,Task ct) throws FileNotFoundException{
	
	String temp = "name:" + ct.name + ";\n" ;
	       temp += "localtaskid:" + ct.localtaskid + ";\n" ;
	       temp += "maintaskid:" + ct.maintaskid + ";\n" ;
	       temp += "timetosubmit:" + ct.timetosubmit + ";\n" ;
	       temp += "algorithm_id:" + ct.algorithm_id + ";\n" ;
	       temp += "algorithm_location:" + ct.algorithm_location + ";\n" ;
	       temp += "Data_location:" + ct.Data_location + ";\n" ;
	       temp += "status:" + ct.status + ";\n" ;
	       temp += "result:" + ct.result + ";\n" ;
	       temp += "errors:" + ct.errors + ";\n" ;
	       
   try{
         File newTextFile = new File(filename);
         FileWriter fw = new FileWriter(newTextFile);
           fw.write(temp);
           fw.close();
   
           System.out.println("file save to : " + filename ); 		    
           System.out.println("file data : " + temp ); 		    
		           
   	 } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} // writes the bytes
}


/*
This function is used to read current task information and write it on the object tasks t
this functions is write specific pattern .
*/	



void read_current_taskinfo_file(String filename ,Task ct) throws FileNotFoundException{
	Scanner input = new Scanner (new File(filename));
	String temp = ""; 
	  
	while(input.hasNextLine())  {
	   temp = input.nextLine();
       if(temp.matches("(.*)name:(.*)")){ 
		   ct.name = temp.substring(5, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)localtaskid:(.*)")){ 
		   ct.localtaskid = temp.substring(12, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)maintaskid:(.*)")){ 
		   ct.maintaskid = temp.substring(11, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)timetosubmit:(.*)")){ 
		   ct.timetosubmit = temp.substring(13, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)algorithm_id(.*)")){ 
		   ct.algorithm_id = temp.substring(12, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)algorithm_location:(.*)")){ 
		   ct.algorithm_location = temp.substring(19, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)Data_location:(.*)")){ 
		   ct.Data_location = temp.substring(14, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)status:(.*)")){ 
		   ct.status = temp.substring(7, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)result:(.*)")){ 
		   ct.result = temp.substring(7, temp.indexOf(";")); 		
       }
       temp = input.nextLine();
       if(temp.matches("(.*)errors:(.*)")){ 
		   ct.errors = temp.substring(7, temp.indexOf(";")); 		
       }
   	
   	
  }  
  input.close();
}
	
/*
This function is used to read key from file and convert it to key object mode to string 

*/	

    
String read_key_from_file(String filename) throws FileNotFoundException{
	String temp = "";
	
	try {
		  ObjectInputStream inputStream = null;
		     // Encrypt the string using the public key
		  inputStream = new ObjectInputStream(new FileInputStream(filename));
				final PublicKey publicKey = (PublicKey) inputStream.readObject();
	      System.out.println("Client public key : " + publicKey.toString() );
		    	
		
	      inputStream.close();
	      BASE64Encoder encoder = new BASE64Encoder();
	        temp = encoder.encode(publicKey.getEncoded());
	       
	      
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
return temp;
	

}


	int read_clientid_from_file(String filename) throws FileNotFoundException{
		
		Scanner input = new Scanner (new File(filename));
	    int i =0;
	 while(input.hasNextInt())  {
	       i = input.nextInt();
		 if ( isNumeric( i ) ) {
	    	  	 break;
	          }
	 }  
	  
	input.close();
	return i; 
	}
	
	void write_to_file(String filename,byte [] b) throws FileNotFoundException{
	
		 OutputStream os = new FileOutputStream(filename);
	         try {
				os.write(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // writes the bytes
	    
			
			try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	} 
	
	/*
	This function is used to algorithm  file to specific location .
	this is server protocol dependent function
	*/	

	
	void write_algo_file(String taskid,String filename ,byte [] data ){
		
		     try {
		    	 String path ="";
			     
		    if(filename.contains(".class")) {
		    	path = "../fyp/taskdata/" + taskid + "/" + filename; 
		        
		    }
		    
		    File dir = new File("taskdata/" + taskid);
		    dir.mkdir();
		    
             	 File newTextFile = new File(path);
             	 FileOutputStream out = new FileOutputStream(newTextFile);
             	 
		         out.write(data);
             	  out.close();
             	  System.out.println("file save to : " + path ); 		    
		          System.out.println("file data : " + data ); 		    
				           
		    	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // writes the bytes
	    
	} 

	/*
	This function is used to data  file to specific location .
	this is server protocol dependent function
	*/	

	void write_algo_file(String taskid,String filename ,String data ){
		
	     try {
	    	 String path ="";
			//path = "" + filename; 
	    	path = "../fyp/taskdata/" + taskid + "/" + filename; 
		    
	      
	    File dir = new File("taskdata/" + taskid);
	    dir.mkdir();
	    
        	 File newTextFile = new File(path);
        	 
	     	 FileWriter fw = new FileWriter(newTextFile);
	            fw.write(data);
	            fw.close();
	    
        	  System.out.println("file save to : " + path ); 		    
	          System.out.println("file data : " + data ); 		    
			           
	    	 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // writes the bytes
   
} 
	
	/*
	This function is used to read result file from location .
	this is server protocol dependent function
	*/	

	String read_result_file(Task t){
		 
		String temp = "";
		try {
			Scanner input =  new Scanner (new File(t.result_get()));
		
			while(input.hasNextLine())  {
				   temp += input.nextLine();
			      }
				
				input.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	return temp;
		
		
	}
	
	
	public static boolean isNumeric(int str)  
	{  
	  try  
	  {  
	    int d = (str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}
