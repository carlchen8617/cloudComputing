/*
 * 
 * 
 * servlet engine to fetch S3 objects
 * 
 * 
 */

package au.edu.swin.student.chen;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class getBucketList
 */
@WebServlet("/getBucketList")
@MultipartConfig
public class getBucketList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultSet rs;
	String photoStr;
	Connection con;
	Statement stmt;

	private AmazonS3 client = (AmazonS3) new getClient().rtClient();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getBucketList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<photoBeans> photoList = new ArrayList<>();
		
		/*
		 * get to database and fetch
		 * 
		 * 
		 */
		
        
		
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		 con=(Connection) DriverManager.getConnection(  
		"jdbc:mysql://cc.cgeudtniydi4.ap-southeast-2.rds.amazonaws.com:3306/carlchenCos80001","root","12345678");  
		
		
		
		
		/**while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
		
		*/
	
		}catch(Exception e){ System.out.println(e);} 
		
		
		
		/*
		 * get to S3 and fetch
		 * 
		 */
		

		ObjectListing objs = client.listObjects(secretes.BUCKET_NAME);
		ListIterator<S3ObjectSummary> objIt = (objs.getObjectSummaries())
				.listIterator();
		

		do {
			while (objIt.hasNext()) {
				photoBeans photoBeans = new photoBeans();
				S3ObjectSummary ok = objIt.next();
				photoBeans.setPhotoname(ok.getKey());

				/*
				 * use CloudFront Distributions
				 */
				String CF = client.generatePresignedUrl(
						new GeneratePresignedUrlRequest(secretes.BUCKET_NAME,
								ok.getKey())).toString();

				CF = CF.replaceAll("carlchen.s3.amazonaws.com",
						"dtfs9yu376gmw.cloudfront.net");

				photoBeans.setUrl(CF);

				/*
				 * get from S3 directly
				 * 
				 * photoBeans.setUrl(client.generatePresignedUrl( new
				 * GeneratePresignedUrlRequest(secretes.BUCKET_NAME,
				 * ok.getKey())).toString());
				 */

				photoBeans.setModiDate(ok.getLastModified().toString());
				
				
				photoStr="select des from carlchenCos80001.photoAlbum where photo ='"+ok.getKey()+"'";
				//photoStr="select des from carlchenCos80001.photoAlbum where photo ='logo.png'";
				System.out.println(photoStr);
				
				try {
					stmt = con.createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				try {
					rs =stmt.executeQuery(photoStr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				try {
					while(rs.next())
					photoBeans.setDescription(rs.getString("des"));
					//photoBeans.setDescription("des");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				photoList.add(photoBeans);
			}

			objs = client.listNextBatchOfObjects(objs);

		} while (objs.isTruncated());
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		// Forward to photoList.jsp page with a list of photos
		request.setAttribute("photoList", photoList);
		request.getRequestDispatcher("/photoList.jsp").forward(request,
				response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
