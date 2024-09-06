<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원 수정</h2>
	<form action="edit.do" method="post">
	<!-- 아이디는 회원레코드에서 식별자(일련번호)로 사용되므로 수정할 수 없도록
	readonly 속성을 추가해준다. -->
	<table border="1">
		<tr>
			<th>아이디(수정불가)</th>
			<td><input type="text" name="id" value="${dto.id}" readonly /></td>
		</tr>
		<tr>
			<th>패스워드</th>
			<td><input type="text" name="pass" value="${dto.pass}" /></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" value="${dto.name}" /></td>
		</tr>
	</table>
	<input type="submit" value="전송하기" />
	</form>
</body>
</html>