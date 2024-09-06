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
let deletePost = function(user_id){
	let f = document.frm;
	f.id.value = user_id;
	f.method="post";
	f.action="delete.do";
	if(confirm('삭제할까요?')) {
		f.submit();
	}
}

</script>
<!-- jQuery의 CDN 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
function ajaxDelete(delete_id) {
	// 삭제할 아이디를 콘솔에서 확인
	console.log("삭제id", delete_id);
	if(confirm('삭제할까요?')) {
		// confirm에서 '확인'을 눌러 true가 반환되면 ajax함수 호출
		$.ajax({
			// 요청URL(혹은 경로)
			url:'delete.do',
			// 전송방식
			type : 'post',
			// 서버로 전송할 파라미터(JSON 객체 형식으로 제작해야함.)
			data : {'id' : delete_id},
			// 콜백 데이터의 형식(text, html, script, xml 등)
			dataType : 'json',
			// 요청성공시 호출할 콜백 함수 정의
			success : function(rData){
				// 서버에서 작업을 종료한 후 웹브라우저에 출력해주는 내용을 콜백받게됨.
				console.log(rData);
				// 삭제에 성공한 경우에는 alert를 띄우고 화면을 새로고침
				if(rData.result=='success') {
					alert('삭제되었습니다.');
					//location.reload();
					/* hide는 display 속성으로 숨기는 것
					remove는 엘리먼트 자체를 삭제하는 것 */
					//$('#member_' + delete_id).hide();
					$('#member_' + delete_id).remove();
				} else {
					alert('삭제실패');
				}
			},
			error : function(eData) {
				console.error(eData);
			}
		});
	}
}


function validateSearchForm() {
	let checkboxes = document.getElementsByName('searchField');
    let checkCnt = 0;
    
    for(i=0; i<checkboxes.length; i++) {
    	if(checkboxes[i].checked) {
    		checkCnt++;
    	}
    }
    if(checkCnt === 0) {
    	alert('한개 이상의 항목을 체크하세요');
    	return false;
    } else {
    	return true;
    }
}
</script>
<form name="frm">
	<input type="hidden" name="id"  />
</form>
<body>
	<h2>회원리스트</h2>
	<!-- 검색폼 추가 -->
	<form onsubmit="return validateSearchForm();">
	<table>
	<tr>
		<td>
			<!-- 검색을 위한 필드(컬럼)를 2개 이상 선택하기 위해 체크박스로 구성한다.
			폼값은 List로 전달된다. -->
			<input type="checkbox" name="searchField" value="id" /> 아이디
			<input type="checkbox" name="searchField" value="name" /> 이름
			<input type="checkbox" name="searchField" value="pass" /> 패스워드
			<!-- 검색어는 일반적인 문자열로 전송된다. -->
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
		<c:forEach items="${memberList }" var="row" varStatus="loop">
		<!-- <tr>태그에 중복되지 않는 아이디를 부여한다. -->
		<tr id="member_${row.id}">
			<td>${ row.id }</td>
			<td>${ row.pass }</td>
			<td>${ row.name }</td>
			<td>${ row.regidate }</td>
			<td>
				<a href="edit.do?id=${row.id}">수정</a>
				<%-- <a href="javascript:deletePost('${row.id}');">삭제</a> --%>
				<%-- <a href="#" onclick="deletePost('${row.id}');">삭제</a> --%>
				<a href="javascript:ajaxDelete('${row.id}');">삭제</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	<a href="regist.do">회원등록</a>
	
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	<br /><br /><br /><br /><br /><br /><br /><br />
	
	<a href="#">Top으로 바로가기</a>
	&nbsp;&nbsp;
	<a href="javascript:;">javascript 접두어쓰기</a>
	&nbsp;&nbsp;
	<a href="deletePost">잘못된 호출방식</a>
</body>
</html>