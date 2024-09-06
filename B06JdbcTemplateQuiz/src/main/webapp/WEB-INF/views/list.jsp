<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
function deleteMember(user_id){
	//alert('함수 호출..' + user_id);
	if(confirm('삭제할까요?')) {
		location.href='delete.do?id=' + user_id;
	}
}

// 삭제할 아이디는 매개변수로 전달된다.
let deletePost = function(user_id){
	// <form> 태그의 DOM을 얻어옴.
	let f = document.frm;
	// <input> 태그의 DOM을 얻어온 후 value 속성에 아이디를 설정.
	f.id.value = user_id;
	// form에 속성을 부여한다.
	f.method="post";
	f.action="delete.do";
	// 삭제에 동의하는 경우에만 submit(제출) 한다.
	if(confirm('삭제할까요?')) {
		f.submit();
	}
}
</script>
<form name="frm">
	<input type="hid-den" name="id"  />
</form>
<body>
	<h2>회원리스트</h2>
	<script>
	function formValidate(form) {
		var searchKeyword = form.searchKeyword.value.trim();
		if (searchKeyword === "") {
			alert("검색어를 입력해주세요.");
			form.searchKeyword.focus();
			return false; 
		}
		return true;
	}
	</script>
	<!-- 검색폼 -->
	<!-- 
	method : 생략하는 경우 get 방식이 디폴트
	action : 생략하는 경우 현재페이지로 폼값이 전송됨.
	 -->
	<form onsubmit="return formValidate(this);">
	<table border="1">
	<tr>
		<td>
			<select name="searchField">
				<option value="id">아이디</option>
				<option value="name">이름</option>
			</select>
			<input type="text" name="searchKeyword" />
			<input type="submit" value="검색" />
		</td>
	</tr>
	</table>
	</form>
	<table border="1">
		<tr>
			<th>아이디</th>
			<th>패스워드</th>
			<th>이름</th>
			<th>가입일</th>
			<th></th>
		</tr>
		<!-- 컨트롤러에서 Model객체에 저장한 List타입의 인스턴스를 통해
			크기만큼 반복하여 목록을 출력한다. -->
		<c:forEach items="${memberList}" var="row" varStatus="loop">
		<tr>
			<td>${ row.id }</td>
			<td>${ row.pass }</td>
			<td>${ row.name }</td>
			<td>${ row.regidate }</td>
			<!-- 퀴즈1] 삭제 버튼을 누르면 “삭제할까요?” 라고 먼저 물어본 후 삭제처리되도록 수정하시오. 
						단, 전송방식은 Post로 처리하시오. -->
			<td>
				<a href="edit.do?id=${row.id}">수정</a>
				<%-- <a href="delete.do?id=${row.id}">삭제1</a> --%>
				<a href="javascript:deleteMember('${row.id}');">삭제2</a>
				<a href="javascript:deletePost('${row.id}');">삭제3</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	<a href="regist.do">회원등록</a>
</body>
</html>