package com.app.clientapp;

import java.io.DataOutputStream;
import java.net.Socket;

public class RandomAttThread implements Runnable{
	public void run(){
		try{		
        	Socket sock = new Socket(Client.IP, 6003);
			DataOutputStream ostream = new DataOutputStream(sock.getOutputStream());
			ostream.writeUTF("Client ID: 20\nLifeSpan: 40\nAvailabiliy: 50\nMemFree: 6000\nCPUFree: 5000");
			ostream.close();
			sock.close();
		}catch(Exception e){
			
		}
	}

}
