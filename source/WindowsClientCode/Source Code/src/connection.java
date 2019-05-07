/**
 * 
 */

import java.net.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.lang.*;


/**
 * @author Administrator
 
This class is used to handle all connections to server .its need IP address to connect and by dafault the
port number is 6000 to 6004 according to serve port protocol specified.

 */
public class connection {
	
	
public    
      String serverName = "";    
      connection(){ 
    	 port = 6000 ;
     }
     void connecttosent(String  str){ 
    	     
     try
      {
         System.out.println("Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to "
                      + client.getRemoteSocketAddress());
          
         Socket client1 = new Socket();
         client1 = client;
         OutputStream	outToServer = client.getOutputStream();
		
         DataOutputStream out =
             new DataOutputStream(outToServer);

			out.writeByte(Integer.parseInt(str));

         
      }catch(IOException e)
      {
         e.printStackTrace();
      }

     }
     /*
     this function is used to sent machine information to server in string type with specified port
     and nothing to receive. this function is protcol dependent from server 
     */
     String connection_sent_recive(String machineinfo ,int port1 ){
   	  try
         {
            System.out.println("Connecting to " + serverName
                                + " on port " + port1);
            Socket client = new Socket(serverName, port1);
            System.out.println("Just connected to "
                         + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out =
                          new DataOutputStream(outToServer);
            out.writeUTF(machineinfo);
            System.out.println("i sent to  server :" + machineinfo);  
            
            InputStream inFromServer = client.getInputStream();
            DataInputStream in =
                           new DataInputStream(inFromServer);
           
            String readString = ""; 
            client.close();
            return readString;
         }catch(IOException e)
         {
            e.printStackTrace();
           return "problem in receiving";
         }
    	 
  }
    
     /*
     This function is used to sent key information to server in string type with specified port
     and receive the session key. this function is protcol dependent from server 
     */
       
     
     String key_sent_recive(String enc  ){
    	  try
          {
             System.out.println("Connecting to " + serverName
                                 + " on port " + port);
             Socket client = new Socket(serverName, port);
             System.out.println("Just connected to "
                          + client.getRemoteSocketAddress());
             OutputStream outToServer = client.getOutputStream();
             DataOutputStream out =
                           new DataOutputStream(outToServer);
             out.writeUTF(enc);
             System.out.println("i sent encrypted key to  server :" + enc);  
             
             InputStream inFromServer = client.getInputStream();
             DataInputStream in =
                            new DataInputStream(inFromServer);
            
             String temp =  in.readUTF();
             System.out.println("recieved encypted session key :" + temp);  
              client.close();
             return temp;
          }catch(IOException e)
          {
             e.printStackTrace();
            return "problem in key receiving";
          }
    	 
     }
     
     /*
     This function is used to download task algorithm file and its data and save to file 
     specified by the server and update the current task information file
     information to server in string type with specified port.
     this function is protocol dependent from server 
     */
     
     public boolean downloadalgofile(tasks t,int id,int status, int port,filing f,Task tempct) {
 		// TODO Auto-generated method stub
    	 try
         {
            System.out.println("Connecting to " + serverName
                                + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to "
                         + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out =
                          new DataOutputStream(outToServer);
            out.writeUTF(Integer.toString(id));
            out.writeUTF(Integer.toString(status));             
            
            
            System.out.println("i sent to  server id & status: " + id + " " +status);  
            
            InputStream inFromServer = client.getInputStream();
            DataInputStream in =
                           new DataInputStream(inFromServer);
           
            String taskid =  in.readUTF();
            System.out.println("taskid recieved     :" + taskid);
            
            if (!taskid.equals("T0000")) {
            
            tempct.name = taskid; 
            String str =  in.readUTF();
            System.out.println("algo filename recieved   :" + str );
              
            tempct.algorithm_location = "../fyp/taskdata/" + taskid + "/"; 
                     
            // algo file content     
              int size =Integer.parseInt(in.readUTF());
              byte b[] = new byte [size];
              in.read(b);
              // write a file
              System.out.println("algo file data recieved   :" + b );
              
              f.write_algo_file(taskid, str, b);
              
              //read data filename  
              String str1 = in.readUTF();   
              System.out.println("data filename recieved   :" + str1 );
              
              tempct.Data_location = "../fyp/taskdata/" + taskid + "/" + str1;
              
              tempct.status = "5";
              tempct.result = "../fyp/taskdata/" + taskid + "/" + "Result" + taskid + ".txt";
              tempct.errors = "../fyp/taskdata/" + taskid + "/" + "Errors" + taskid + ".txt";
              
              String strdata = in.readUTF();   
             
              //write to file
              System.out.println("data file content  recieved   :" + strdata );
              
             
              String resultfilename = in.readUTF();   
              System.out.println(" discard result filename " + resultfilename);
              f.write_algo_file(taskid, str1, strdata);
              
           //update information of taskinfo files.
         
         while (!( t.Execution.equals("0")) ){     
          System.out.println("thread is waiting for updating task information" );
          try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //sleep for 1 min
		  	
         }
          System.out.println("thread is now working to update taskinformation");
           t.currenttaskid = "1"; 
           client.close();
           return true ;  	 
            }
          client.close();
          return false ;  
            
         }catch(IOException e)
         {
            e.printStackTrace();
            return false ;  
            
         }
    	 
   }
     
     /*
     This function is used to sent result of task to server in string type with specified port
     this function is protocol dependent from server 
     */
     
 	
     public void read_result_sent(Task t,int id,int status, int port,filing f) {
  		// TODO Auto-generated method stub
     	 try
          {
             System.out.println("Connecting to " + serverName
                                 + " on port " + port);
             Socket client = new Socket(serverName, port);
             System.out.println("Just connected to "
                          + client.getRemoteSocketAddress());
             OutputStream outToServer = client.getOutputStream();
             DataOutputStream out =
                           new DataOutputStream(outToServer);
             out.writeUTF(Integer.toString(id));
             out.writeUTF(Integer.toString(status));             
             
             out.writeUTF(t.algorithm_id);  
             String temp = t.result.substring(t.result.indexOf("Res"),t.result.length());
             System.out.println(" result filename :  " + temp);
             out.writeUTF(temp);
             
             String content ="";
             content = f.read_result_file(t);
             
             out.writeUTF(content);
             System.out.println("sent result to  server  " + id + t.name);  
             client.close();
          }catch(IOException e)
          {
             e.printStackTrace();
          }
    }
  	
     /*
     This function is used to sent client id to server in string type on port 6000
     this function is protocol dependent from server 
     */
     
     
     String connection_sent_recive(String clientid){
         try
         {
            System.out.println("Connecting to " + serverName
                                + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to "
                         + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out =
                          new DataOutputStream(outToServer);
            out.writeUTF(clientid);
            System.out.println("i sent to  server :" + clientid);  
            
            InputStream inFromServer = client.getInputStream();
            DataInputStream in =
                           new DataInputStream(inFromServer);
              
            String temp =  in.readUTF();
            String readString = new String(temp); 

            System.out.println("Server says " +  readString + "  " + readString.length() );
            client.close();
            return readString;
         }catch(IOException e)
         {
            e.printStackTrace();
           return "0";
         }
    	 
     }   
     String connecttorecieve(){ 
	     
         try
          {
             System.out.println("Connecting to " + serverName
                                 + " on port " + port);
            
             InputStream inFromServer = client1.getInputStream();
            
             DataInputStream in =
                            new DataInputStream(inFromServer);
             System.out.println("Server says " + in.readUTF());
             return in.readUTF();
            }catch(IOException e)
          {
             e.printStackTrace();
          }
		return "error in recived Exception called";

         }
      
private 
	  
    int port ;
    Socket client1 ; 


}
