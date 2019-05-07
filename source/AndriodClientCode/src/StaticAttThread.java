package com.app.clientapp;

import java.io.DataOutputStream;
import java.net.Socket;

public class StaticAttThread implements Runnable{
	public void run(){
		String s = new String();
    	String str = new String();
    	try{		
        	Socket sock = new Socket(Client.IP, 6001);
        	// sending to client (pwrite object)
        	s = "Client ID: " + Client.readFile("config.txt") + "\n";
        	str = Client.getTotalRAM();
        	s = s + str + "\n";
        	str = Client.getInfo();
        	s = s + "CPU MHz: " + str  + "\n";
        	str = Integer.toString(Client.getNumCores()); 
        	s = s  + "Number of cores: " +  str + "\n";
        	str = System.getProperty("os.name");
        	str = "Android: 2";
        	s = s  + "OS name: " +  str + "\n"; //Ref. http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
        	str = System.getProperty("os.arch");
        	s = s  + "CPU Architecture: " +  "32 bit" + "\n"; //Ref. htt
        	DataOutputStream ostream = new DataOutputStream(sock.getOutputStream());
			ostream.writeUTF(s);
			sock.close();
    	}
    	catch(Exception e){
    		
    	}
	}

}
