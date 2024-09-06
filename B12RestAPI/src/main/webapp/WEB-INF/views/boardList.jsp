<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
// jQuery의 엔트리 포인트
$(function() {
	// 버튼이 클릭되면 함수가 실행됨
	$('#btnBoard').click(function(){
		$.ajax({
			type : 'get', // 요청방식
			url : './restBoardList.do', // 요청URL(경로)
			data : {pageNum : $('#pageNum').val()}, // 파라미터(페이지번호)
			contentType : "text/html;charset:utf-8",
			dataType : "json", // 콜백데이터타입
			// 콜백함수의 경우 외부에 별도로 정의되어있음
			success : sucCallBack,
			error : errCallBack
		});
	});
	/*
	특정 이벤트를 자동으로 실행해주는 기능의 함수로 여기서는 페이지가
	로드되면 click 이벤트가 실행된다.
	*/
	$('#btnBoard').trigger('click');
});
// 요청 성공 시 콜백함수 정의
function sucCallBack(resData){
	let tableData ="";
	/* 콜백 데이터의 크기만큼 자동으로 반복하여 JSON데이터를 파싱하고
	<tr>태그로 만들어준다. */
	$(resData).each(function(index, data){
		tableData += ""
		+"<tr>"
		+"	<td>"+data.num+"</td>"
		+"	<td>"+data.title+"</td>"
		+"	<td>"+data.id+"</td>"
		+"	<td>"+data.postdate+"</td>"
		+"	<td>"+data.visitcount+"</td>"
		+"</tr>";
	});
	/* <tbody> 태그 사이에 파싱된 내용을 삽입한다. JS의 innerHTML()
	함수와 기능이 동일하다. */
	$('#show_data').html(tableData);
}
function errCallBack(errData) {
	console.log(errData.status + ":" + errData.statusText);
}
</script>
</head>
<body>
<div class="container">
	<h2>게시판 API 활용하여 목록 출력하기</h2>
	<table class="table table-bordered">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>아이디</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<tbody id="show_data"></tbody>
	</table>
	
	<!-- 페이지번호를 선택하여 요청할 수 있는 폼 -->
	<div>
		<select id="pageNum">
		<c:forEach begin="1" end="10" var="num">
			<option value="${num}">${num}page</option>
		</c:forEach>
		</select>
		<input type="button" value="목록불러오기" id="btnBoard" />
	</div>
</div>
</body>
</html>