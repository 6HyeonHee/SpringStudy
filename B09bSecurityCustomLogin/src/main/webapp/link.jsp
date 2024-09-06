<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>바로가기</h2>
<!-- 
스프링 시큐리티를 적용했을때 로그인과 로그아웃에 대한 요청명은
아래와 같이 지정된다. 만약 개발자가 변경하고 싶다면 시큐리티
설정 파일에서 커스텀하면 된다.
 -->
<ul>
	<li><a href="/myLogin.do">Custom Login</a></li>
	<li><a href="/guest/index.do">Guest</a></li>
	<li><a href="/member/index.do">Member</a></li>
	<li><a href="/admin/index.do">Admin</a></li>
	<li><a href="/myLogout.do">Custom Logout</a></li>
</ul>