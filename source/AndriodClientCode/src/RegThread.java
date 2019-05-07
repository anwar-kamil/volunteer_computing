package com.app.clientapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RegThread implements Runnable {
	public void run(){
		try{
			
        	Socket sock = new Socket(Client.IP, 6000);
        	// sending to client (pwrite object)
        	//Client.globalString = Client.globalString + "Akjskjdl";
        	Client.et.setText(Client.globalString);
			String text = Client.readFile("config.txt");
			DataOutputStream ostream = new DataOutputStream(sock.getOutputStream());
			ostream.writeUTF(text);

             DataInputStream inFromServer = new DataInputStream(sock.getInputStream());
            String ServerID = inFromServer.readUTF();
            
            sock.close();
        	Client.writeFile("config.txt", ServerID);
		}
		catch(Exception e){
			
		}
	}
}
