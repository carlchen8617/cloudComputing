/*
 * 
 * utility file to hold default information
 * 
 */

package au.edu.swin.student.chen;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class getClient {
	public AmazonS3 rtClient() {

		return (new AmazonS3Client(new BasicAWSCredentials(secretes.ACCESS_KEY,
				secretes.SECRET_KEY)));

	}

}
