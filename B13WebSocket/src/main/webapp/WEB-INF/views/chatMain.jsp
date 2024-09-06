<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹 소켓 채팅</title>
</head>
<body>
	<script>
	function chatWinOpen(){
		// 채팅 대화명이 입력된 상자의 DOM을 얻어온 후..
		var id = document.getElementById("chatId");
		// 입력된 대화명이 있는지 확인
		if (id.value == ""){
			alert("대화명을 입력 후 채팅창을 열어주세요.");
			id.focus();
			return;
		}
		// 채팅창을 팝업으로 오픈한다. 이때 대화명을 파라미터로 전달한다.
		window.open("chatWindow.do?chatId=" + id.value, "", 
				"width=350, height=430");
		// 다음 창을 띄울 수 있도록 기존 입력값을 삭제한다.
		id.value = "";
	}
	</script>
	<h2>웹소켓 채팅 - 대화명 적용해서 채팅창 띄워주기</h2>
	대화명 : <input type="text" id="chatId" />
	<button onclick="chatWinOpen();">채팅 참여</button>
</body>
</html>