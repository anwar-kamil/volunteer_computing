package com.app.clientapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Client extends Activity {
	static EditText et;
	static String globalString = null;
	Button b;
	TextView tv;
	String message;
	public static String IP = "10.11.9.104";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		et = (EditText) findViewById(R.id.editText1);
		b = (Button) findViewById(R.id.b);

		//Checking whether the file exists or not....
		File logFile = new File(Environment.getExternalStorageDirectory().toString(), "config.txt");
		if(!logFile.exists()) {
		     try {
				logFile.createNewFile();
				BufferedWriter output = null;
				output = new BufferedWriter(new FileWriter(logFile));
				output.write("0");
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		b.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
	                message = et.getText().toString();
	                 String str = exec();
	                 et.setText(str);
		               
	                Thread TaskRun = new Thread(new TaskThread());
		            Thread regRun = new Thread(new RegThread()); //Thread created 
	                Thread statRun = new Thread(new StaticAttThread());
	                Thread RandRun = new Thread(new RandomAttThread());
	                regRun.start();
	                statRun.start();
	                TaskRun.start();
		            RandRun.start();
				}
		});
		
}
	
	static public int getNumber(String c)
	   {
	   char [] b = new char[200];
	   b = c.toCharArray();
	   int tot=0;
	   int inc=1;
	   boolean bool = false;
	   int[] iarr = new int[10]; int iarri=0;
	   for(int i=0; i<b.length; i++)
	    {
		 if(b[i] == ',');
		 else if(b[i]>=48 && b[i]<=57)
	   	 {
	   		iarr[iarri++]=(int)b[i]-48;
	   		bool = true;
	   	 }
	     else if (bool == true){
	    	 break;
	     }
	    }
	   for(iarri--; iarri>=0; iarri--)
	   	{
	   	int val=iarr[iarri];
	   	tot=tot+inc*val;
	   	inc*=10;
	   	}	
	   return tot;
	   }
	
	public static String readFile(String fileName){
		File logFile = new File(Environment.getExternalStorageDirectory().toString(), fileName);
		File file = logFile;
		//Read text from file
	    String line = null;
		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    line = br.readLine();
		    br.reset();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}	                			
		return line;
	}
	
	public static void writeFile(String fileName, String fileData){
		try{
			//Checking whether the file exists or not...
			File logFile = new File(Environment.getExternalStorageDirectory().toString(), fileName);
			
			//to make sure file is empty
			PrintWriter writer;
			try {
				writer = new PrintWriter(logFile);
				writer.print("");
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			//write content into files
			BufferedWriter output = null;
				output = new BufferedWriter(new FileWriter(logFile));
				output.write(fileData);
				output.close();
		} catch  (Exception e) {
        	System.out.println("No I/O");
        	System.exit(1);
        }
		
	}
	/**
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	 * @return The number of cores, or 1 if failed to get result
	 */
	public static int getNumCores() { //Method copied from http://forums.makingmoneywithandroid.com/android-development/280-%5Bhow-%5D-get-number-cpu-cores-android-device.html#pid1663
	    //Private Class to display only CPU devices in the directory listing
	    class CpuFilter implements FileFilter {
	        @Override
	        public boolean accept(File pathname) {
	            //Check if filename is "cpu", followed by a single digit number
	            if(Pattern.matches("cpu[0-9]", pathname.getName())) {
	                return true;
	            }
	            return false;
	        }      
	    }

	    try {
	        //Get directory containing CPU info
	        File dir = new File("/sys/devices/system/cpu/");
	        //Filter to only list the devices we care about
	        File[] files = dir.listFiles(new CpuFilter());
	        //Return the number of cores (virtual CPU devices)
	        return files.length;
	    } catch(Exception e) {
	        //Default to return 1 core
	        return 1;
	    }
	}
	
	public static String getInfo() {
    	StringBuffer sb = new StringBuffer();
    	if (new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq").exists()) {
        	try {
        		BufferedReader br = new BufferedReader(new FileReader(new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")));
	        	String aLine;
				while ((aLine = br.readLine()) != null) {
					sb.append(aLine + "\n");
				}
				if (br != null) {
		    		br.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
    	return sb.toString();
    }
	
	public static String getTotalRAM() {
    	String load = null;
//
		if (new File("/proc/meminfo").exists()) {
        	try {
        		BufferedReader br = new BufferedReader(new FileReader(new File("/proc/meminfo")));
	        	load = br.readLine();
				if (br != null) {
		    		br.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
    	return load;
    }
	//http://stackoverflow.com/questions/2843250/how-to-run-terminal-command-in-android-application
	
	public static String exec()
	{
		String temp = ""; 
		String buffer = "";
		try {
			String[] cmd = {"java", " ", "mnt/sdcard/T1112"};
			Process p = Runtime.getRuntime().exec(cmd);  
			
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));  
			 
			while ((temp = input.readLine()) != null)  buffer = buffer + temp + "  ";  
			input.close();  
			} catch (IOException e) {
			e.printStackTrace();
			}
		return buffer;
		
	}
	
	//Mem free function --> ref. Stackoverflow
			/*public String memfree(){
				MemoryInfo mi = new MemoryInfo();
				ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				activityManager.getMemoryInfo(mi);
				long availableMegs = mi.availMem / 1048576L;
				String mem = Long.toString(availableMegs);
				return mem;
			}*/
}


