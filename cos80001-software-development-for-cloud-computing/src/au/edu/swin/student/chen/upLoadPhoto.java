/**
 * Servlet implementation class upLoadPhoto
 */

package au.edu.swin.student.chen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.commons.io.*;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mysql.jdbc.Connection;

@WebServlet("/upLoadPhoto")
@MultipartConfig
public class upLoadPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String me, localFileName, localFile,desc,fileName;

	// get credentials

	private AmazonS3 client = (AmazonS3) new getClient().rtClient();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public upLoadPhoto() {
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String contentType = request.getContentType();
		if ((contentType != null)
				&& (contentType.indexOf("multipart/form-data") >= 0)) {
			Part myPart = request.getPart("carlupload");
			Path p = Paths.get(myPart.getSubmittedFileName());

			 fileName = p.getFileName().toString();// get file name
			
			 desc = request.getParameter("des");
			
			

			/***
			 * This section is needed for local test Pattern pp =
			 * Pattern.compile("(.*?)(images)(.*)"); Matcher m =
			 * pp.matcher(fileName);
			 * 
			 * while (m.find()) { localFileName=m.group(3);
			 * 
			 * }
			 * 
			 * //localFile="c:\\linux\\"+localFileName;
			 * 
			 * */

			// first load the uplaoded file local drive

			File localFile = new File("/home/ec2-user/" + fileName);
			OutputStream out = new FileOutputStream(localFile);
			InputStream fileIS = myPart.getInputStream();

			// read using byte array

			int readIt = 0;
			final byte[] bytes = new byte[1024];

			while ((readIt = fileIS.read(bytes)) != -1) {
				out.write(bytes, 0, readIt);
			}

			out.close();

			try {
				// UPLOAD PDF TO S3 using actual file

				client.putObject((new PutObjectRequest(secretes.BUCKET_NAME,
						fileName, localFile))
						.withCannedAcl(CannedAccessControlList.PublicRead));

				// END UPLOAD PDF TO S3

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		/**
		 * 
		 * put photo description into database
		 * 
		 */
		
		String photoStr="INSERT INTO photoAlbum (photo, des) VALUES (\""+ fileName + "\",\"" + desc +"\")";
		
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=(Connection) DriverManager.getConnection(  
		"jdbc:mysql://cc.cgeudtniydi4.ap-southeast-2.rds.amazonaws.com:3306/carlchenCos80001","root","12345678");  
		
		Statement stmt=con.createStatement();  
		stmt.executeUpdate(photoStr); 
		
		
		/**while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
		
		*/
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		 

		System.out.println(request.getContentType());
		response.sendRedirect("getBucketList");
	}

}
