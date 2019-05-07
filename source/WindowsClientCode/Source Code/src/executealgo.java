import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
This class is used to execute java based task .
*/


public class executealgo {

	void execute(Task ct){
		
		// TODO Auto-generated method stub
	 try {
	       //e.g task.class so name should be task 
	       String s= ct.name_get() ;
	       String cmd = "";
	       cmd = "cmd /c cd taskdata/" + ct.name + " & java " + s ; 	        
	       System.out.println(cmd);
	       runProcess(cmd);    
	 } catch (Exception e) {
	        e.printStackTrace();
	      }	   
  }
	    


private static void printLines(String name, InputStream ins) throws Exception {
       String line = null;
       BufferedReader in = new BufferedReader(
           new InputStreamReader(ins));
       while ((line = in.readLine()) != null) {
           System.out.println(name + " " + line);
       }
     }

     private static void runProcess(String command) throws Exception {
         System.out.println("in run process :" + command);
	      
       //  "cmd /c cd taskdata/T1111 & java T1111"
         
      /* 
         Process Pro = Runtime.getRuntime().exec(command);
		 BufferedReader input = new BufferedReader(new InputStreamReader(Pro.getInputStream()));
		  String temp = "";
		  while((temp = input.readLine())!= null) System.out.println(temp);
		  input.close();
      */   
   	  
   	   Process pro = Runtime.getRuntime().exec(command);
       printLines("new java :" + " stdout:", pro.getInputStream());
       printLines("new java :" + " stderr:", pro.getErrorStream());
       pro.waitFor();
     
       System.out.println("new java :" + " exitValue() " + pro.exitValue());
          }



	
	
	
}
