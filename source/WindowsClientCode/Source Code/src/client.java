import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.jgoodies.forms.util.Utilities;

public class client  {
	
	static  int clientid = 0;
	static boolean  register = false ;
	
	static String  tm ="";
	static  connection Con = new connection() ;
	static	filing f = new filing();
	static	machineinfo  info = new machineinfo() ;
	static	basic_info   binfo = new basic_info() ;
	static	tasks t  = new tasks(); 
	static	Task ct  = new Task();
	static  Task tempct  = new Task();
	static	executealgo el = new executealgo();
		
	static  boolean waitfortask = false;
	static  String port = "6004";
	static  RSA Rsa = new RSA();
	static  HashSHA Hash = new HashSHA();
	static  TestProtector Session = new TestProtector();
	static  String Session_key = "";
	private static Pattern VALID_IPV4_PATTERN = null;
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	    
	
	 static {
		    try {
		      VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
		    } catch (PatternSyntaxException e) {
		     System.out.println("ip address is not correct ");
		    	//logger.severe("Unable to compile pattern", e);
		    }
		  }
	
	
       public static void load_run() {
		     
    	   Thread t = new Thread(new Runnable() {           
    	     public void run() { 
    	                //do stuff here
    	    	        // Thread.sleep(500);
    			  		int i=0;
    			  		//info.getmachineinfo();
    			  		while (true)
    			  		{
    			  	
    		//for proper remove below and uncomment line no 1 to 2 specify under 	 
    			  				
    			  			
    			  	  	try {
    			  		      
    			  			info.getcpuload();
    			  		 i++;
    			  	     TimeUnit.MINUTES.sleep(1); //sleep for 2 min
    			  		 if ( i == 5) { //sent to server after 30 mins
    			  		 info.j=0;
       			  	     info.average();
       			  	     try {
							info.findfreememory(binfo);
							binfo.update_lifespan();
							//binfo.lifespan = Long.toString(binfo.getSystemUptime());
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
       			  	      Con.connection_sent_recive(tm + "\n"+ String.valueOf(info.get_average()) + "\n" + binfo.Availiablememory + "\n" + binfo.lifespan, 6003);
    			  		  i=0;
    			  		      }
    			  		 } catch (InterruptedException e) {
    					         System.out.println("thread for cpu load interrupted.");
    					 }
    					    
    			  	 	}//end while
    			    	             
    	        } }
    	     );
    	        t.start();
    	    }
    	 
    	
       public static void clientregristration(){
           Thread t = new Thread(new Runnable() {           
               public void run() {
            	   boolean R = false;
            	   while(!R) {
                   //do stuff here
            	  try {
           		
            		clientid =	f.read_clientid_from_file("id.txt");
           		    System.out.println("read from file" + clientid); 
           		    
           		    
           		} catch (FileNotFoundException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
           	
           		
           		//if client id 0 then get new id
           		if (clientid == 0 )
           		{  String temp = "",encdata = "" ,Rencdata = "";
           		   
           		tm = Integer.toString(clientid);
           		   int i=0;
           		   
           		   /* hide for security
           		   if(Session_key.isEmpty()){
           			 try {
     					TimeUnit.SECONDS.sleep(60);
     					if(Session_key.isEmpty()){ System.out.print("still not have session key");}
     					
     				} catch (InterruptedException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				} //sleep for 30 min
     			  
           			   
           			  
           			   
           		   }
           		   */
           		   while (Integer.parseInt(tm) ==0 && i < 2 ){  //try 10 time to connect to server in case of failure to connect
           			 i++;  
           			 /* hide for security
           			 temp = Hash.Hash(Integer.toString(clientid));
             		 encdata = Session.encrypt(tm + "---" + temp, Session_key);
           			 */
             		tm = Con.connection_sent_recive(tm);//Rencdata for assignment & encdata parameter  for security 
             		/* hide for security
             		temp = "";     
             		temp = Session.decrypt(Rencdata, Session_key);
             		
             		*/
             /*
             		//parsing 
             		int k =0 ;
             		String temp1="";
             		while (true){
             			if (temp.charAt(k) != '-' && temp.charAt(k+1) != '-') { temp1 += temp.charAt(k); k++;}	
             			else if (temp.charAt(k) != '-') {temp1 += temp.charAt(k); k++;} 
             			else break;
             		}
             		k = k + 2 ;
             		tm = temp1;
             		
             		String givenhash = temp.substring(k, temp.length());
             		 
             	 // matching hash	
             		 temp = ""; 
             		 temp = Hash.Hash(temp1);
                  	 if ( temp.equals(givenhash) )
                  	 {}
                  	 else {
                  		//if fails to match
                  		 tm = Integer.toString(clientid);
                  	    
                  	 }
                  	 
                  	*/ 
                  	 
           		}
           		if (Integer.parseInt(tm) !=0){
           		//save new id to file
           		try {
           			f.write_to_file("id.txt" ,tm.getBytes());
           			R = true ;
           			register = true ;
           		} catch (FileNotFoundException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
           		get_sent_machineinfo_to_server();
           		waitfortask();
           		} else 
           		{
           			System.out.println("your client Registration is not complete .please restart this program");
           			System.out.println("or check your internet connection .make sure its running");
           		    
           			//System.exit(0);	
           		 try {
					TimeUnit.MINUTES.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //sleep for 30 min
			  		
				//Keytransfer() ;   hide for security 
			    
           		}
           		
               
               } 
           		R = true;
           		
            	   }
            	register = true ;   
           }
           });
           t.start();
           
       }
       
       public static void get_sent_machineinfo_to_server(){
           Thread t = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
            	// sent machine info to server only ist time
                   System.out.println("before call");	
           		info.getmachineinfo(binfo);
           		  
                   String SA = tm + "\n";
                          SA += binfo.totalmemory + "\n";
                          SA += binfo.speed_in_hardz + "\n";
                          SA += binfo.noofcores + "\n";
                         // SA += binfo.operating_system + "\n";
                          SA += "windows version" + "\n";
                          SA += binfo.archtecture_type + "\n";
                        //  SA += binfo.totalmemory + "\n";
                         // SA += binfo.Availiablememory + "\n";
                          try {
							binfo.lifespan = Long.toString(binfo.getSystemUptime());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                             
                  System.out.println(Con.connection_sent_recive(SA, 6001));	
           		
                
               } 
           });
           t.start();
       }
       
       public static void checkpendingtask(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
              
            	// now check project status
          		 //read task information from file 
          			try {
          					f.read_current_taskinfo_file("tasksinfo.txt", t);
          				} catch (FileNotFoundException e) {
          					// TODO Auto-generated catch block
          					e.printStackTrace();
          				}
          				
          			if (Integer.valueOf(t.currenttaskid) > 0) {
          				//already have a task
          				System.out.println("already have a task");
          	            //read current task data from file 
          				
          				try {
          					f.read_current_taskinfo_file("currenttaskinfo.txt", ct);
          				} catch (FileNotFoundException e) {
          					// TODO Auto-generated catch block
          					e.printStackTrace();
          				}
          	             
          		System.out.println("name : " + ct.name_get());
          		System.out.println("localtaskid  : " + ct.localtaskid_get());
          		System.out.println("maintaskid   : " + ct.maintaskid_get());
          		System.out.println("timetosubmit : " + ct.timetosubmit_get());
          		System.out.println("algorithm_id : " + ct.algorithm_id_get());
          		System.out.println("algorithm_location : " + ct.algorithm_location_get());
          		System.out.println("Data_location : " + ct.Data_location_get());
          		System.out.println("Status : " + ct.status_get());
          		System.out.println("result  : " + ct.result_get());
          		System.out.println("errors : " + ct.errors_get());
          		
          		Executetask();
               } 
          	else{
          		
          		
          		 port = "6004";
                 synchronized(port)
                 {
                  System.out.println("10 : now allow to download more task... :notifyall on :port");
                  port.notifyAll();
                 } 
          		
                
          		
          	} 
          		
               }
               
           });
           t1.start();
       }
	   
       public static void Executetask(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
           		 t.Execution = "1";
           		 synchronized(t.Execution)
                 {
           			/* 
           			synchronized(t.currenttaskid)
                    {
           			 t.currenttaskid = "0"; 
           		  	 t.currenttaskid.notify(); 
           		     System.out.println("8 :now allow to update current taskid :notify on :currenttaskid");
                   	
                    }	 
           			 
           			 */
           			t.currenttaskid = "0"; 
          		    el.execute(ct);
          		    sentresulttoserver();
          		    System.out.println("9 :now allow to sent result  to server...:notify on :Execution");
                    
          		  try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          		    
          		    //t.Execution.notify();
                 } 
           		 t.Execution = "0";
          		 
           		 
           		  
           		 
           		  
               }
           });
           t1.start();
       }
	   
       public static void sentresulttoserver(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
            	   synchronized(t.Execution)
                   {
                   System.out.println("6 :wait for result to sent...:wait on :Execution");
				   //t.Execution.wait();
				   System.out.println("7 :now submit task result....:allow on :Execution");
                   }
            	   Con.read_result_sent(ct,clientid,2,6004,f);
                   
               }
           });
           t1.start();
          
           port = "6004";
           synchronized(port)
           {
            System.out.println("faltu notify :now call to download more task...:notify on :port");
            port.notify();
           } 
           
           
       }
	   
       public static void waitfortask(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                  //do stuff here
            	 //wait for task
       			//System.out.println("current task id is : " + t.currenttaskid_get() );
       		while(true) {
              synchronized(port)
       	      {
       	       System.out.println("2 :Thread wait for permission to download more task ...:wait on :port");
       	      try {
				port.wait();
			  System.out.println("3 : now allow to download more task ...:allow on :port");
		      while (!register){
					 try {
						 System.out.println("4 : Thread is not allow to download more task ...:wait for registration variable :register ");
			       	     TimeUnit.SECONDS.sleep(1);
						 } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} //sleep for 30 min
					  		
					
				} 
			   System.out.println("5 : Thread is now able to download tasks ...:allow on registration varible : register");
				    
		      
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	      
			if ( port.equals("6004") )
			{    System.out.println("Thread ready to connect on port..." + port);
			     Downloadtask();   
			     
			   /*  try {
					port.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				*/
			}
			else  System.out.println("port not matched...:" + port);
			     
       	      
       	      
       	      
       	      }
       			
       		}  //always true
       		} 
           });
           //while(true)
           { 
                t1.start();
           }
       }
	
       
       public static void Downloadtask(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
            	   System.out.println("1 : now call to download more working..");
            	  // Integer.parseInt(port)
            	 if ( Con.downloadalgofile(t,clientid, 1,6004, f,tempct))
            	 {
            		 setcurrentTAsk();
            	 }
            	 else {
            		 // currently not have any task so sleep for 5 minutes
            		  try {
						TimeUnit.MINUTES.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //sleep for 2 min
    			  		 
            		 
            		 
            	 }
               }
           });
           t1.start();
       }
       
       public static void setcurrentTAsk(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   //do stuff here
            	   System.out.println("update current task..");
            	   try {
            	   if(t.Execution.equals("1")){
            		   System.out.println("wait for time 5 minutes for current task..executions ");
                 	  
            		   TimeUnit.MINUTES.sleep(5); //sleep for 2 min
   			  		  setcurrentTAsk();
            	     }
            	   else {
            		 //overwrite the taskinfofile.
            		
						f.write_current_taskinfo_file("tasksinfo.txt", t);
				      //now create new current task info file   
						f.write_create_current_taskinfo_file("currenttaskinfo.txt", tempct);   
            	        //now check new task  buffer
						 checkpendingtask();
						 
            	   }
            	
            		} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	
            	   
           //	if not execution  checkpendingtask();
               }
           });
           t1.start();
       }
      
       public static void Keytransfer(){
           Thread t1 = new Thread(new Runnable() {           
               public void run() { 
                   String temp ="";
            	   String encseskey = "" ,seskey = "";
            	   //do stuff here
            	   System.out.println("key Exchange..");
            	  try {
            		//  System.out.println("Client public key : " + f.read_key_from_file(Rsa.PUBLIC_KEY_FILE) );
   		           
            		  temp = Rsa.Server_PK_plus_Enc(f.read_key_from_file(Rsa.PUBLIC_KEY_FILE));
					//  System.out.println("Client public key : " + f.read_key_from_file(Rsa.PUBLIC_KEY_FILE) );
		              	 
				     	
					
					encseskey = Con.key_sent_recive(temp);
					seskey = Rsa.client_PK_minus_Dec(encseskey);
					Session_key = seskey;
            	    System.out.println("key Exchange done ");
            	    System.out.println("session key : " + Session_key);
             	     
            	  } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	   
               }
           });
           t1.start();
       }
       
       public static boolean isIpAddress(String ipAddress) {

    	    Matcher m1 = VALID_IPV4_PATTERN.matcher(ipAddress);
    	    if (m1.matches()) {
    	      return true;
    	    }
    	   return false ;    
       }   
       
       
       /**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		
		// TODO Auto-generated method stub
		// for cpu load finding
		String ip="";
		 try {
			ip =  f.read_ip_file("ip.txt") ; 
			System.out.println(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isIpAddress(ip)){
			System.out.println("Correct ip address :" + ip);
			Con.serverName = ip ;
			
			
			//Keytransfer() ; hide for security
			    load_run();  
			//check own client id
			    waitfortask();
			    clientregristration();
		        checkpendingtask();
			
			
		}
		else {
			System.out.println("not valid ip address :" + ip);
			
		}
		
		
		
	
	}

}
