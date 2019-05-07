import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class is used to keep the static and random attributes
*/
public class basic_info {

	 void Availiablememory_set (String Availiablememory){
			this.Availiablememory = Availiablememory;
			} 
	 String Availiablememory_get (){
			return Availiablememory;
			} 
	
	 void noofcores_set (String noofcores){
		this.noofcores = noofcores;
		} 
	
	 void speed_in_hardz_set (String speed_in_hardz){
		this.speed_in_hardz = speed_in_hardz;
		} 
	 void cpuarachtecture_set (String cpuarachtecture){
		this.cpuarachtecture = cpuarachtecture;
		} 
	 void totalmemory_set (String totalmemory){
		this.totalmemory = totalmemory;
		} 
	 void operating_system_set (String operating_system){
		this.operating_system = operating_system;
		} 
	 void archtecture_type_set (String archtecture_type){
		this.archtecture_type = archtecture_type;
		} 
	 void availibility_set (String availibility){
		this.availibility = availibility;
		} 
	 void lifespan_set (String lifespan){
		this.lifespan = lifespan;
		} 
	
	//getters
	String noofcores_get (){
		return noofcores;
		} 
	
	String speed_in_hardz_get (){
		return speed_in_hardz;
		} 
	String cpuarachtecture_get (){
		return cpuarachtecture;
		} 
	String totalmemory_get (){
		return totalmemory;
		} 
	String operating_system_get (){
		return operating_system;
		} 
	String archtecture_type_get (){
		return archtecture_type;
		} 
	String availibility_get (){
		return availibility;
		} 
	String lifespan_get (){
		return lifespan ;
		} 
	
	public void update_lifespan(){
		long temp = 0;
		try {
			temp = getSystemUptime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff = temp - Long.parseLong(lifespan) ;
		     lifespan = Long.toString(diff) ;
		
	}
	/*
	This function return the boot time in mintues using the operating System APIs
	*/
	public long getSystemUptime() throws Exception {
	    long uptime = -1;
	    String os = System.getProperty("os.name").toLowerCase();
	    if (os.contains("win")) {
	        Process uptimeProc = Runtime.getRuntime().exec("net stats srv");
	        BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            if (line.startsWith("Statistics since")) {
	                SimpleDateFormat format = new SimpleDateFormat("'Statistics since' MM/dd/yyyy hh:mm:ss a");
	                Date boottime = format.parse(line);
	                uptime = System.currentTimeMillis() - boottime.getTime();
	                uptime  =  ((uptime / (1000*60)) );
	                break;
	            }
	        }
	    } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) {
	        Process uptimeProc = Runtime.getRuntime().exec("uptime");
	        BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
	        String line = in.readLine();
	        if (line != null) {
	            Pattern parse = Pattern.compile("((\\d+) days,)? (\\d+):(\\d+)");
	            Matcher matcher = parse.matcher(line);
	            if (matcher.find()) {
	                String _days = matcher.group(2);
	                String _hours = matcher.group(3);
	                String _minutes = matcher.group(4);
	                int days = _days != null ? Integer.parseInt(_days) : 0;
	                int hours = _hours != null ? Integer.parseInt(_hours) : 0;
	                int minutes = _minutes != null ? Integer.parseInt(_minutes) : 0;
	                uptime = (minutes * 60000) + (hours * 60000 * 60) + (days * 6000 * 60 * 24);
	                uptime  =  ((uptime / (1000*60)) );
		              
	            
	            }
	        }
	    }
	    return uptime;
	}
	
	
	 String noofcores ;
	 String speed_in_hardz ;
	 String	cpuarachtecture ;
	 String	totalmemory ;
	 String	Availiablememory ;
		
	 String	operating_system ;
	 String	archtecture_type ;  
	 String availibility ;
	 String lifespan ;

		
}
