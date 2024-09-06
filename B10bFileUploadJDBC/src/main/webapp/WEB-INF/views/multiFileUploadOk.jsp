<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MultiUpload Result</title>
<style>
img{max-width: 300px;}
</style>
</head>
<body>
	<h2>멀티 파일 업로드 성공</h2>
	<!-- 
	첨부한 파일의 갯수만큼 반복하여 출력한다.
	 -->
	<c:forEach items="${saveFileMaps }" var="row" varStatus="status">
		업로드 한 파일명 : ${status.count} : ${row.key} <br>
		저장된 파일명 : ${status.count} : ${row.value} <br>
		<img src="./uploads/${row.value}"> <br>
	</c:forEach>
	
	제목 : ${title} <br>
	카테고리 :
	<c:forEach items="${cate}" var="row" varStatus="status">
		${row}
		<c:if test="${status.last eq false}">,</c:if>
	</c:forEach>
</body>
</html>