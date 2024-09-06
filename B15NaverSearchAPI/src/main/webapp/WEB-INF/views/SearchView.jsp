<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 API</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
$(function() {
	// 검색어 입력을 위해 키보드를 눌렀을 때 발생되는 이벤트
	$('#keyword').keydown(function(e) {
		// 엔터키가 눌러졌다면 함수를 호출
		if(e.keyCode==13){
			runAjax();
			e.preventDefault();
		} else {
			// 엔터키가 아니라 콘솔에 키코드를 출력
			console.log(e.keyCode);
		}
	});
	// 검색어 입력 후 '검색요청' 버튼을 눌렀을 때 함수를 호출
	$('#searchBtn').click(function() {
		runAjax();
	});
});

function runAjax() {
	$.ajax({
		// 요청 URL
		url : "./NaverSearchRequest.do",
		// 전송방식
		type : "get",
		// 파라미터 (검색어, 시작번호)
		data : {
			keyword : $('#keyword').val(),
			startNum : $('#startNum').val()
		},
		dataType : "json",
		success : sucFuncJson,
		error : errFunc
	});
}

// 요청 성공 시 콜백함수
function sucFuncJson(d) {
	// Naver에서 보내주는 검색결과 JSON 콘솔에 출력
	console.log("결과", d);
	var str = "";
	/*
	JSON 결과중 items항목을 반복하여 파싱한다.
	*/
	$.each(d.items, function(index, item) {
		str += "<ul>";
		str += "	<li>" + (index + 1) + "</li>		";
		str += "	<li>" + item.title + "</li>			";
		str += "	<li>" + item.description + "</li>	";
		str += "	<li>" + item.bloggername + "</li>	";
		str += "	<li>" + item.bloggerlink + "</li>	";
		str += "	<li>" + item.postdate + "</li>		";
		str += "	<li><a href='" + item.link + "'		";
		str += "	target='_blank'>바로가기</a></li> 		";
		str += "</ul>";
	});
	// 파싱된 결과를 html() 함수로 페이지에 삽입한다.
	$('#searchResult').html(str);
}

function errFunc(e){
	alert("실패: " + e.status);
}
</script>
<style>
    ul{border:2px #cccccc solid;}
    #startNum{width:100px;}
</style>
</head>
<body>
<div>
    <div>
        <form id="searchFrm">
            한 페이지에 10개씩 출력됨 <br />
            <input type="number" id="startNum" step="10" 
            			value="1">
            <input type="text" id="keyword" 
            			placeholder="검색어를 입력하세요." />
            <button type="button" id="searchBtn">검색 요청</button>
        </form>
    </div>
    <div class="row" id="searchResult">
        여기에 검색 결과가 출력됩니다.
    </div>
</div>
</body>
</html>
