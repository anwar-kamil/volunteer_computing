package com.app.clientapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.SystemClock;

public class TaskThread implements Runnable{
	
			String serverName = Client.IP;
		    int port = 6002;

	public void run(){
		    try
		    {
		      Socket client = new Socket(serverName, port);
		      
			  OutputStream outToServer1 = client.getOutputStream();
		      DataOutputStream out1 = new DataOutputStream(outToServer1);

		      InputStream inFromServer1 = client.getInputStream();
		      DataInputStream in1 = new DataInputStream(inFromServer1);
				
				String ClientId = Client.readFile("config.txt");
				out1.writeUTF(ClientId);
				
				out1.writeUTF("1");//asking for fetching the task
				String TaskId = in1.readUTF();
				
				String TaskFileName = in1.readUTF();
				String TaskFile = in1.readUTF();
				Client.writeFile(TaskFileName, TaskFile);
				 
				String DataFileName = in1.readUTF();
				String DataFile = in1.readUTF();
				Client.writeFile(DataFileName, DataFile);
				
				String ResultFileName = in1.readUTF();
				
				client.close();

				ExecuteTask(TaskId);
			
				SendResultFile(ResultFileName, TaskId);
		    }catch(IOException e)
		      {
		       e.printStackTrace();
		      }
		}
		
		private void SendResultFile(String ResultFileName, String TaskId){
			Socket client;
			try {
			client = new Socket(serverName, port);
			OutputStream outToServer1 = client.getOutputStream();
		    DataOutputStream out1 = new DataOutputStream(outToServer1);
			
			String ClientId = Client.readFile("config.txt");
			out1.writeUTF(ClientId);
				
			out1.writeUTF("2");//asking for fetching the task
			
			out1.writeUTF(TaskId);
			
			out1.writeUTF(ResultFileName);
			out1.writeUTF(Client.readFile(ResultFileName));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		      
		}
		
		public static void ExecuteTask(String TaskId){
			try {
			TaskId = TaskId + ".apk";
			String[] cmd = {"adb", " ", "install", " ", "-s", " ", "T1111.apk"};
			
			Runtime.getRuntime().exec(cmd);  
			} catch (Exception e) {
			//e.printStackTrace();
			//Client.textfield.setText("There is a problem.");
			}
			long i = Client.getNumber(TaskId);
			SystemClock.sleep(i);
			
			
		}
}
