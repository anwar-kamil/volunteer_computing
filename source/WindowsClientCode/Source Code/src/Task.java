

/*
 * this class is used to keep the important information of each task
 * 
 * */



public class Task {

 String name ; 
 String localtaskid ; 
 String maintaskid ; 
 String timetosubmit ; 
 String algorithm_id ; 
 String algorithm_location ; 
 String Data_location ; 
 String status ; 
 String result ; 
 String errors ; 
 

 public Task(){
	 	 
 }
 
 
 
 
 
 
 
 
 
 
 
	void name_set (String name){
		this.name = name;
		} 
	
	void localtaskid_set (String localtaskid){
		this.localtaskid = localtaskid;
		} 
	void maintaskid_set (String maintaskid){
		this.maintaskid = maintaskid;
		} 
	void timetosubmit_set (String timetosubmit){
		this.timetosubmit = timetosubmit;
		} 
	void algorithm_location_set (String algorithm_location){
		this.algorithm_location = algorithm_location;
		} 
	void Data_location_set (String Data_location){
		this.Data_location = Data_location;
		} 
	void status_set (String status){
		this.status = status;
		} 
	void result_set (String result){
		this.result = result;
		} 
	
	//getters
	String name_get (){
		return name;
		} 
	
	String localtaskid_get (){
		return localtaskid;
		} 
	String maintaskid_get (){
		return maintaskid;
		} 
	String timetosubmit_get (){
		return timetosubmit;
		} 
	String algorithm_location_get (){
		return algorithm_location;
		} 
	String archtecture_type_get (){
		return Data_location;
		} 
	String status_get (){
		return status;
		} 
	String result_get (){
		return result ;
		} 
	
	String errors_get (){
		return errors ;
		} 
		
	void errors_set (String errors){
		this.errors = errors;
		}
   
    String algorithm_id_get() {
		// TODO Auto-generated method stub
		return algorithm_id;
	} 
	
    void algorithm_id_set (String algorithm_id){
		this.algorithm_id = algorithm_id;
		}











	public String Data_location_get() {
		// TODO Auto-generated method stub
		return Data_location;
	}
   
   
 
 
 
	
	
}
