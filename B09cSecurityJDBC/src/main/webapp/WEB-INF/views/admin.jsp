<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 스프링 시큐리티에서 제공하는 tag라이브러리를 사용하기 위한 선언 -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Admin 영역</h2>
	ADMIN 권한만 접근할 수 있습니다. <br />
	
	<!-- ADMIN권한으로 현재 페이지에 접근했을 때 access에 지정한대로
	로그인 아이디가 출력된다. -->
	<s:authorize access="hasRole('ADMIN')">
		로그인 아이디 : <s:authentication property="name"/>
	</s:authorize>
	<%@ include file="/link.jsp" %>
</body>
</html>