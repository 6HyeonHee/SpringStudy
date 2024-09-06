<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload</title>
</head>
<body>
	<h2>파일 업로드 성공</h2>
	<!-- Model에 저장된 내용을 출력한다. -->
	업로드 한 파일명 : ${originalFileName} <br>
	저장된 파일명 : ${savedFileName} <br>
	<img src="./uploads/${savedFileName}"> <br>
	제목 : ${title} <br>
	카테고리 :
	<c:forEach items="${cate}" var="row" varStatus="status">
		${row}
		<!-- varStatus 속성에서 last를 사용해서 마지막 반복에는 콤마를 출력하지 않는다. -->
		<c:if test="${status.last eq false}">,</c:if>
	</c:forEach>
</body>
</html>