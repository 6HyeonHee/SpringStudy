<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원등록</h2>
	<!-- 등록폼에서 post로 전송시 컨트롤러에서 insert 처리를 한다. -->
	<form action="regist.do" method="post">
	<table border="1">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="id" value="" /></td>
		</tr>
		<tr>
			<th>패스워드</th>
			<td><input type="text" name="pass" value="" /></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" value="" /></td>
		</tr>
	</table>
	<input type="submit" value="전송하기" />
	</form>
</body>
</html>