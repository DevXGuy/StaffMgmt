<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>User profile</title>
</head>
<body>


	<h1>User Profile</h1>
	<a href="LogoutServlet">Logout</a>

	<!-- IMPORT CORE JSTL LIBRARY -->
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:if test="${ProfileInfo != null}">
		<p>Welcome, ${ProfileInfo.firstName} ${ProfileInfo.lastName}</p>
		<form action="UpdateProfile" method="post">
			<table>
				<tr>
					<td>First Name</td>
					<td>Last Name</td>
					<td>Username</td>
					<td>Password</td>
				</tr>
				<tr>
					<td><input type="text" name="txtFirstName"
						value='<c:out value="${ProfileInfo.firstName }"></c:out>'></td>
					<td><input type="text" name="txtLastName"
						value='<c:out value="${ProfileInfo.lastName }"></c:out>'></td>
					<td><input type="text" name="txtUsername"
						value='<c:out value="${ProfileInfo.username }"></c:out>'></td>
					<td><input type="text" name="txtPassword"
						value='<c:out value="${ProfileInfo.password }"></c:out>'>
					</td>
				</tr>
			</table>
			<input type="submit" name="btnSubmit" value="Update Profile">
		</form>
	</c:if>
</body>
</html>