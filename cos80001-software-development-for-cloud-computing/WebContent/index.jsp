<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cos80001: Carl Chen's Photo Upload Page</title>
</head>
<body>
<form action="upLoadPhoto" method=POST enctype="multipart/form-data"><h2><font color="green">Welcome to Carl's Cloud Album!</font></h2><h2>Upload photo</h2>

Describe your photo:<br>
<input type="text" name="des" size="50"><br>

<input type="file" name="carlupload" accept="image/*">
<input type="submit" value="Upload Photo" name="submit">
</form>

<br>
<a href="getBucketList">Click to see all uploaded photos</a>
</body>
</html>