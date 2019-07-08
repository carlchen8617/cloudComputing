/*
 * 
 * photo beans
 * 
 */

package au.edu.swin.student.chen;

public class photoBeans {

	private String photoname;
	private String url;
	private String modiDate;
	private String description;
	

	public photoBeans() {

	}

	public photoBeans(String photoname, String url, String modiDate, long siize, String description) {
		super();
		this.photoname = photoname;
		this.url = url;
		this.modiDate = modiDate;
		this.description = description;
	}

	public String getPhotoname() {
		return photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
