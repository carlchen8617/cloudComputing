<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- JSP page displaying the photos returned by getBucketList servlet --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Uploaded photos</h1>
	<table border="1">
		<tr>
			<td width="200px"><b>photo name</b></td>
			<td><b>photo</b></td>
			<td><b>upload time</b></td>
			<td><b>Description</b></td>
		</tr>
		<c:forEach items="${photoList}" var="photo">
			<tr>
				<td width="200px">
					<p>
						<b>${photo.photoname}</b>
					</p>
				</td>
				<td><p>
						<img src="${photo.url}" alt="photo">
						${photo.url}
					</p></td>
				<td><p>${photo.modiDate}</p></td>
				<td><p>${photo.description}</p></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<a href="index.jsp">Upload more photos</a>

</body>
</html>