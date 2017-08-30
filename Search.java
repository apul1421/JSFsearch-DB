package com.mann.jsf_db;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import sun.nio.cs.ext.ISCII91;

 

 

@ManagedBean(name="bean", eager=true)

public class Search {
	
	  Connection con = null;
	  PreparedStatement stmt = null;
	  Statement ps = null;
      ResultSet rs = null;
	  
	  ArrayList<Employee> display;
	  int result =0;
	  String rollid;  
	  String name;  
	  String email; 
	  String msg;
	 /* String state;
	 
	  
	  
	 
	  

	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}*/



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
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

	public ArrayList<Employee> getDisplay() {
		return display;
	}



	public void setDisplay(ArrayList<Employee> display) {
		this.display = display;
	}


public Search() {
	
	try{
 
	 Class.forName("com.mysql.jdbc.Driver");
	 con=DriverManager.getConnection("jdbc:mysql://192.168.1.4:3306/campus17","campus17","Dr58lM02");
	 
	 
	}
	
	catch(Exception e){
		
		  System.out.println(e); 		
	}
	
	show();
	//setState("Active");	
	}
	


public void status(String id,String status){
	
	if(status.equals("active")){
	
	try{
		 
		 String sql = "update empjsf set empstatus=? where rollid=?";
		 stmt = con.prepareStatement(sql);
		 
		 
		 stmt.setString(1, "deactive");  
		 stmt.setString(2, id); 		 
		 
		 result = stmt.executeUpdate();
		 show();
		 reset();
	}
	catch(Exception e){
		 
		System.out.println("Error Data : " + e.getMessage());
	}

	finally{
		 try
        {
            con.close();
            stmt.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	}
	
	else{
		try{
			 
			 String sql = "update empjsf set empstatus=? where rollid=?";
			 stmt = con.prepareStatement(sql);
			 
			 
			 stmt.setString(1, "active");  
			 stmt.setString(2, id); 		 
			 
			 result = stmt.executeUpdate();
			 show();
			 reset();
		}
		catch(Exception e){
			 
			System.out.println("Error Data : " + e.getMessage());
		}

		finally{
			 try
	        {
	            con.close();
	            stmt.close();
	        }

	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
		}
	}
	
}



public boolean add(){
	
	try{
		 
		 String sql = "INSERT INTO empjsf(rollid,empname,email) values(?,?,?)";
		 stmt = con.prepareStatement(sql);
		 
		 stmt.setString(1, this.getRollid());
		 stmt.setString(2, this.getName());  
		 stmt.setString(3, this.getEmail());  
		 
		 result = stmt.executeUpdate();
		 show();
		 reset();
	}
	 
	catch(Exception e){
		if(e.toString().matches("(.*)Duplicate entry(.*)")){
			msg="Duplicate Entry";
		System.out.println(e);  
		}
		
		else if(e.toString().matches("(.*)Incorrect integer(.*)")){
			msg="Entered Null value";
			System.out.println(e); 
		}
		
		else{
			msg="Entered Null value";
			System.out.println(e); 
		}
	}
	
	finally{
		 try
         {
             con.close();
             stmt.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
	}
	
	
	if(result == 1){  
		setMsg("Data Successfully saved");
		System.out.println("Data successfully saved");
		return true;  		
		}
	
	else{ 
		//setMsg("Some error occured");
		return false;
		}  
		  
}

public void reset(){ 
	setRollid("");
	setName("");
	setEmail("");
}



public void show(){ 
	 
	try{
		
		String sql = "select * from empjsf";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("List of Employee is");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		  		  		    
	  }
	
	catch(Exception e){
 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
}





public void searched(){
	 
	  
	 
	 if (name.isEmpty() && email.isEmpty() && !rollid.isEmpty()){
		 
		 searchid();
	 }
	  
	 
	else if(rollid.isEmpty() && email.isEmpty() && !name.isEmpty()){
		 searchname( );
	 }
	 
	 else if(rollid.isEmpty() && name.isEmpty() && !email.isEmpty()){
		 searchemail();
	 }
	 
	 else if(!name.isEmpty() && email.isEmpty() && !rollid.isEmpty()){
		 searchidname();
	 }
		 
	 else if(name.isEmpty() && !email.isEmpty() && !rollid.isEmpty()){
		 searchidemail();
	 } 
		
	 else if(!name.isEmpty() && !email.isEmpty() && rollid.isEmpty()){
		 searchnameemail();
	 }
	 else if(!name.isEmpty() && !email.isEmpty() && !rollid.isEmpty()){
		 allentry();
	 }
	 else{
		 
		 setMsg("You Have Left All Fields Empty");
		 
	
	 } 
}


public void searchid(){ 
	 
	try{
		
		String sql = "select * from empjsf WHERE rollid='"+getRollid()+"'";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			 
		 }
		 setMsg("Record Not Found");	  		    
	  }
	
	catch(Exception e){
		 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }
}


public void searchname(){ 
	 
	try{
		
		String sql = "select * from empjsf WHERE empname='"+getName()+"'";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found");		  		    
	  }
	
	catch(Exception e){
		 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }
}

 
public void searchemail(){
	
try{
		
		String sql = "select * from empjsf WHERE email='"+getEmail()+"'";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found"); 		  		    
	  }
	
	catch(Exception e){
		

		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }	
	
}

public void searchidname(){
	
try{
		
		String sql = "select * from empjsf WHERE rollid='"+getRollid()+"' AND empname='"+getName()+"'" ;
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found"); 		    
	  }
	
	catch(Exception e){
		
	 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }	
	
	
}


public void searchidemail(){
	
try{
		
		String sql = "select * from empjsf WHERE rollid='"+getRollid()+"' AND email='"+getEmail()+"'" ;
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found");  		    
	  }
	
	catch(Exception e){
		
		 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }	
	
	
}




public void searchnameemail(){
	
try{
		
		String sql = "select * from empjsf WHERE empname='"+getName()+"' AND email='"+getEmail()+"'";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			  
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found");  		    
	  }
	
	catch(Exception e){
		
		 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }	
	
	
}
	

public void allentry(){
	
try{
		
		String sql = "select * from empjsf WHERE rollid='"+getRollid()+"' AND empname='"+getName()+"' AND email='"+getEmail()+"'";
		ps = con.createStatement();
		rs = ps.executeQuery(sql);
		 display= new ArrayList<Employee>();
		 
		 while(rs.next()){
			 		 
			 setMsg("Record Found");
			 display.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		 }
		 setMsg("Record Not Found");		    
	  }
	
	catch(Exception e){
	 
		System.out.println("Error Data : " + e.getMessage());
	}
	  
	 finally{
		 
		 try
         {
             con.close();
             ps.close();
             rs.close();
         }

         catch(Exception e)
         {
             e.printStackTrace();
         }
		 
	 }	
	
	
}


}