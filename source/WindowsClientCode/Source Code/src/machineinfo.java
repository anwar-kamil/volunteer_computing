import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileStore;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 This class is use to retrieves the static and random attributes using the system APIs
 this class also use pattern to extract specific values. 
*/

public class machineinfo {
 
	
	
	machineinfo(){}
	
	/*
	 * this function is use to extract the disk storage of computer in Giga Bytes 
	 * */
	void findfreememory(basic_info info) throws Exception{
		int Total = 0 , avaliable = 0 ;  
		 
		NumberFormat nf = NumberFormat.getNumberInstance();
		for (Path root : FileSystems.getDefault().getRootDirectories())
		{
		    try
		    {
		    	FileStore store = Files.getFileStore(root);
		        avaliable +=  store.getUsableSpace() / (1024 * 1024 * 1024);
		        Total += store.getTotalSpace() / (1024 * 1024 * 1024);
		    }
		    catch (FileSystemException e)
		    {
		       // System.out.println("error querying space: " + e.toString());
		    }
		}
		
		info.totalmemory = Integer.toString(Total);
		info.Availiablememory = Integer.toString(avaliable);
	}
	
   	
	/*
	 * this function is to get the current computer load in percentage
	 * */
	void getcpuload(){
		try {
			String line;
			String dosCMD="wmic cpu get loadpercentage";//DIR C:\\
			String prg="C:\\windows\\system32\\" + "cmd.exe /C ";
			Process p = Runtime.getRuntime().exec(prg+dosCMD );
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				
				if(line.length() == 0){}
				else {
					i++;
					if (i%2 == 0){
						line = "45       ";
						System.out.print("percent : ");
						line = line.trim();
						loadusage[j]  = Integer.parseInt(line);
						j++;
						char [] st = line.toCharArray();
						
						int k = st[0];
						k -=48;
						int l = st[1]; 
						l -=48;
						k *=10 + l;
						System.out.println( st);
						System.out.println( k);
					     
					}
				    				
				}
				
          }
			input.close();
			}
			catch (Exception err) {
			err.printStackTrace();
			}
		
	}
	
	/*
	 * this function is use to get the system information .
	 * its usually take minutes to give values
	 * */
	void getmachineinfo(basic_info info){
	
		 	
		try {
			String line ,str = new String();
			
			String dosCMD="systeminfo";//DIR C:\\
			String prg="C:\\windows\\system32\\"+"cmd.exe /C ";
			Process p = Runtime.getRuntime().exec(prg+dosCMD );
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			  int i=0;  				
				
			
			while ((line = input.readLine()) != null) {
				str = str.concat(i + line +"\n") ;
				i++;
				getinfo(line,info);
				}
			
			input.close();
			}
			catch (Exception err) {
			err.printStackTrace();
			}
	
		
		
		
	}
	
	/*
	 * this function is to get the number of cores
	 * */
	String getcpucores(){
		try {
			String line;
			String dosCMD="wmic cpu get NumberOfCores";//DIR C:\\
			String prg="C:\\windows\\system32\\"+"cmd.exe /C ";
			Process p = Runtime.getRuntime().exec(prg+dosCMD );
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				
				if(line.length() == 0){}
				else {
					i++;
					if (i%2 == 0){
						//System.out.print("cores : " + line);
				        return line ; 
					}
				    				
				}
	      }
			
			input.close();
			}
			catch (Exception err) {
			err.printStackTrace();
			}
		return null;
		
	}
	
	
	/* this function is use to Extract specific information from the systeminfo command result
	 * */
	
	void getinfo(String line,basic_info info){
	
		if(line.matches("(.*) OS Name :(.*)")){ 
			info.operating_system = line.substring(10, 50); 		
			  //System.out.println(" OS :" + line);
			info.operating_system = System.getProperty("os.name");
			
		}
		if(line.matches("(.*)GenuineIntel(.*)")){ 
			info.speed_in_hardz = "s" + line.substring(80, 87); 		
			
		}
		
		
		
		//22 to 35
		if(line.matches("(.*)Total Physical Memory:(.*)")){
			//totalmemory
	          info.totalmemory = "M" + line.substring(22, 35); 		
			  info.noofcores = "C" + getcpucores();  
			  try {
				findfreememory(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(line.matches("(.*)OS Configuration:(.*)")){
			//check about parameter 
			//operating system
			
			
		}
		
		//13 to 42
		if(line.matches("(.*)System Type:(.*)")){
			//archtecture (32 or 64)
			  info.archtecture_type = line.substring(27, 30); 		
			  if(info.archtecture_type.startsWith("X86")){
				  
				  info.archtecture_type = "32 bit";
			  }
			  else 	  info.archtecture_type = "64 bit";
				}	
		
		if(line.matches("(.*)Registered Owner:(.*)")){
			
		}	
				
	}
	
	
	/*
	 * this function is use to calculate the average information of load
	 * 
	 * */
	double get_average(){
			return average;
	}
	
	void average (){
		int sum=0;
		 
		for (int i=0; i<loadusage.length;i++)
		   sum += loadusage[i];
		
		average = sum / loadusage.length ;
				
	}
	
	public 
	int [] loadusage = new int[29]; int i =0 ,j=0 ,average = 0; 
	private static final Pattern INT_PATTERN = Pattern.compile("\\d+",
		    Pattern.DOTALL);
	
	
}
