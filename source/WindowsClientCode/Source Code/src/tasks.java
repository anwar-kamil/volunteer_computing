import java.io.FileNotFoundException;



/*
 * this class is used to keep the current information of task
 * 
 * */


public class tasks {

	 String currenttaskid ; 
	 String maintaskid ; 
	 String status ; 
     String  Execution = "0";  // 1 for true  &  0 for false  
	 
	  
	 
		void currenttaskid_set (String currenttaskid){
			this.currenttaskid = currenttaskid;
			} 
		
		void maintaskid_set (String maintaskid){
			this.maintaskid = maintaskid;
			} 
		void status_set (String status){
			this.status = status;
			} 

		String currenttaskid_get (){
			return currenttaskid;
			} 
		
		String maintaskid_get (){
			return maintaskid;
			} 
		String status_get (){
			return status;
			} 
		 
	 
	
}
