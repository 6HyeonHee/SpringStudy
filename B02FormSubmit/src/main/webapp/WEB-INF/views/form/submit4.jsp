<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>폼값전송4 : @PathVariable</h2>
	<p>
		이름 : ${ name } <br>
		나이 : ${ age }
	</p>
	
	<!-- 
	요청명이 'form4/삼장법사/44'와 같으므로 루트경로 하위 2Depth로 인식한다.
	따라서 이미지의 경로는 아래와 같이 ../을 두번 추가해야한다.
	/images/SpringBoot.png 로 해도 결과는 동일하게 나오므로 상관없다!!
	 -->
	<img src="../../images/SpringBoot.png" />
</body>
</html>