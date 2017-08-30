package com.mann.jsf_db;

public class Employee{
	 
	
	  String rollid;  
	  String name;  
	  String email;
	  String status;
	  
	  Employee(String rollid,String name,String email,String status){  
		   this.rollid=rollid;  
		   this.name=name;  
		   this.email=email; 
		   this.status=status;
		  }
	  
	 
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRollid() {
		return rollid;
	}

	public void setRollid(String rollid) {
		this.rollid = rollid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
