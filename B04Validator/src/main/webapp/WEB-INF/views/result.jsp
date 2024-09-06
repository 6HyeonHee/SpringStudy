<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>글쓰기 결과</h2>
	<p>
		일련번호 : ${dto.idx} <br />
		아이디 : ${dto.userid} <br />
		제목 : ${dto.title} <br />
		내용 : ${dto.content}	
	</p>
</body>

</html>