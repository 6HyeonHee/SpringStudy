<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>퀴즈] 회원가입폼에서 전송된 값</h2>
	<p>
		아이디 : ${ memberDTO.id } <br>
		비밀번호 : ${ memberDTO.pass } <br>
		이름 : ${ memberDTO.name } <br>
		성별 : ${ memberDTO.sex } <br>
		이메일 : ${ memberDTO.email } <br>
		이메일 수신여부 : ${ memberDTO.sendEmail } <br>
		우편번호 : ${ memberDTO.postcode } <br>
		주소 : ${ memberDTO.address } <br>
		핸드폰 : ${ memberDTO.phoneNum } <br>
		SMS 수신 여부 : ${ memberDTO.sendSMS } <br>
		관심분야 : ${ memberDTO.favorite } <br>
		가입경로 : ${ memberDTO.joinRoute } <br>
	</p>
</body>
</html>