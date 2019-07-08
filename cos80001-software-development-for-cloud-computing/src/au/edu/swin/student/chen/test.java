package au.edu.swin.student.chen;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileName="g", desc="h";
		String photoStr;
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://cc.cgeudtniydi4.ap-southeast-2.rds.amazonaws.com:3306/carlchenCos80001","root","12345678");  
			
			Statement stmt=con.createStatement(); 
			
			//String photoStr="INSERT INTO photoAlbum (photo, des) VALUES (\""+ fileName + "\",\"" + desc +"\")";
			
			photoStr="select des from carlchenCos80001.photoAlbum where photo ='logo.png' ";
			
			//stmt.executeUpdate(photoStr); // DML operation
			//stmt.executeQuery("INSERT INTO photoAlbum (photo, des) VALUES (222,111)"); // SELECT operation
			
			ResultSet rs=stmt.executeQuery(photoStr);  
			
			
			while(rs.next())  
			System.out.println(rs.getString("des"));  
			System.out.println("test");
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  

	}

}
