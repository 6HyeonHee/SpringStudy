<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSON의 4가지 형식 이해하기</h2>
	<ul>
		<li><a href="/">루트</a></li>
		<li><a href="/restApi01.do">순수 객체*</a></li>
		<li><a href="/restApi02.do">순수 배열*</a></li>
		<li><a href="/restApi03.do">배열을 포함한 객체***</a></li>
		<li><a href="/restApi04.do">객체를 포함한 배열**</a></li>
	</ul>
	
	<h2>Rest API 만들어보기</h2>
	<style>
	fieldset{width:400px;}
	</style>
	<fieldset>
		<legend>페이지별 레코드 조회</legend>
		<form action="restBoardList.do">
			<select name="pageNum">
			<c:forEach begin="1" end="10" var="p">
				<option value="${p}">${p}</option>
			</c:forEach>
			</select>
			<input type="submit" value="요청하기" />
		</form>
	</fieldset>
	
	<fieldset>
		<legend>2개 이상의 단어 검색</legend>
		<form action="restBoardSearch.do">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="요청하기" />
		</form>
	</fieldset>
	
	<fieldset>
		<legend>상세 내용 조회</legend>
		<form action="restBoardView.do">
			일련번호:<input type="number" name="num" />
			<input type="submit" value="요청하기" />
		</form>
	</fieldset>
	
	<fieldset>
		<legend>[퀴즈]작성하기</legend>
		<form method="post" action="restBoardWrite.do">
			아이디:<input type="text" name="id" value="musthave"/> <br />
			제목:<input type="text" name="title" /> <br />
			내용: <textarea name="content" cols="30" rows="10"></textarea><br />
			<input type="submit" value="요청하기" />
		</form>
	</fieldset>
	
	<h2>Ajax로 게시판 구현</h2>
	<ul>
		<li><a href="/boardList.do">목록</a></li>
		<li><a href="/boardView.do">내용보기</a></li>
		<li><a href="/boardListView.do">퀴즈] 목록 + 내용보기</a></li>
	</ul>
</body>
</html>