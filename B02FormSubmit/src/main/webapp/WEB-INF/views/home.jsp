<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>스프링 부트 프로젝트(static 및 form submit)</h2>
	<ul>
		<li><a href="/">루트</a></li>
	</ul>
	
	<!-- static 하위에 있는 이미지는 이와같이 출력할 수 있다.
	만약 이미지가 views 하위에 저장되어 있다면 엑박이 출력될 것이다. -->
	<img src="/images/SpringBoot.png" alt="이미지 표시">
	
	<!-- Spring Boot 프레임워크에서는 HTML, CSS, JS, 이미지와 같은
	정적파일을 static 디렉토리 하위에 저장해야한다. 정적파일이란 JSP와
	같이 동적으로 변할 수 없는 파일을 말한다. -->
	<h2>정적 파일</h2>
	<ul>
		<li><a href="./index.html">index.html</a></li>
		<li><a href="./sub/sub.html">sub.html</a></li>
	</ul>
	
	<!-- 
	View에 대한 매핑을 하기 위해서는 제일 먼저 요청명을 생성한다.
	 -->
	<h2>View 매핑</h2>
	<ul>
		<li><a href="./index.do">index.do</a></li>
		<li><a href="./sub.do">sub.do</a></li>
	</ul>
	
	<!-- 
	스프링 부트에서 폼값을 받을 수 있는 4가지 방법을 알아보기 위해 아래와 같이
	요청명을 먼저 결정한다. 쿼리스트링을 통해 파라미터를 전달하므로 get방식이 요청이된다.
	 -->
	<h2>폼값 전송</h2>
	<ul>
		<li><a href="form1.do?name=손오공&age=11">내장객체</a></li>
		<li><a href="form2.do?name=저팔계&age=22">어노테이션</a></li>
		<li><a href="form3.do?name=사오정&age=33">커맨드객체</a></li>
		<li><a href="form4/삼장법사/44">경로변수</a></li>
	</ul>
	
	<h2>퀴즈</h2>
	<li><a href="./RegiFormValidate.html">회원가입</a></li>
	<li><a href="./MemberLogin.html">로그인</a></li>
</body>
</html>