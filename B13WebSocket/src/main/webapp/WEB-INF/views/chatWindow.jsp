<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹 소켓 채팅</title>
</head>
<script>
/*
 페이지가 로드되면 제일 먼저 웹소켓 객체를 생성한다. 이때 사용하는 주소는
 웹소켓 설정 클래스에서 지정한 요청명을 사용해야합니다.
 localhost(127.0.0.1)로 기술하면 내 컴퓨터에서만 테스트할 수 있고,
 내부아이피를 사용하면 다른 사람이 내 컴퓨터로 접속할 수 있다.
 */
var webSocket
	= new WebSocket("ws://localhost:8586/myChatServer");

// 채팅을 위한 전역변수 생성
var chatWindow, chatMessage, chatId;

/* 채팅창이 열리면 대화창, 메세지입력상자, 대화명입력상자로 사용할 DOM을
얻어와서 전역변수에 저장한다. */
window.onload = function() {
	chatWindow = document.getElementById("chatWindow");
	chatMessage = document.getElementById("chatMessage");
	chatId = document.getElementById('chatId').value;
}

// 입력된 메세지를 서버로 전송할 때 호출한다.
function sendMessage() {
	// 입력한 메세지를 대화창에 추가한다.
	chatWindow.innerHTML += "<div class='myMsg'>" +
								chatMessage.value + "</div>"
	// 웹 소켓 서버에 메세지를 전송. 형식은 '채팅대화명 | 메세지'
	webSocket.send(chatId + '|' + chatMessage.value);
	// 다음 메세지를 즉시 입력할 수 있도록 비워준다.
	chatMessage.value = "";
	// 대화창의 스크롤을 항상 제일 아래로 내려준다.
	chatWindow.scrollTop = chatWindow.scrollHeight;
	/*
	 채팅창은 최신 대화내역이 아래에 위치하는 특성을 가지므로 스크롤이
	 위쪽에 있다면 입력한 대화내용을 확인할 수 없다.
	*/
}

// 웹소켓 서버에서 접속 종료
function disconnect() {
	webSocket.close();
}

// 메세지 입력 후 엔터키를 누르면 즉시 메세지 전송
function enterKey() {
	console.log("키 눌러짐", window.event.keyCode);
	// keyCode를 통해 입력한 키보드를 알아낸다.
	if(window.event.keyCode == 13) {
		sendMessage();
	}
}

// 웹소켓 서버에 연결되었을 때 이벤트 리스너를 통해 자동으로 호출
webSocket.onopen = function(event) {
	chatWindow.innerHTML += "웹소켓 서버에 연결되었습니다. <br />";
};

// 웹소켓 서버가 종료되었을때..
webSocket.onclose = function(event) {
	chatWindow.innerHTML += "웹소켓 서버가 종료되었습니다. <br />";
};

// 웹소켓 서버에서 에러가 발생했을때..
webSocket.onerror = function(event) {
	alert(event.data)
	chatWindow.innerHTML += "채팅 중 에러가 발생하였습니다. <br />";
};

// 웹소켓 서버가 메세지를 받았을 때..
webSocket.onmessage = function(event) {
	// 대화명과 메세지를 분리한다. 전송시  | (파이프)를 조립해서 보낸다.
	var message = event.data.split("|");
	// 앞부분은 보낸사람의 대화명
	var sender = message[0];
	// 뒷부분은 메세지
	var content = message[1];
	// 메세지가 빈값이 아닌 경우
	if (content != "") {
		// 메세지에 슬러쉬가 포함된 경우에는 명령어로 인식
		if (content.match("/")) {
			/*
			 귓속말의 경우 "/받는사람 보낼메세지"와 같이 작성된다.
			 따라서 받는사람의 아이디와 동일한 대화창에만 대화내용을
			 디스플레이한다.
			*/
			if (content.match(("/" + chatId))) {
				var temp = content.replace(("/" + chatId),
						"[귓속말] :");
				chatWindow.innerHTML += "<div>" + sender
					+ temp + "</div>";
			}
		}
		else {
			// 슬러쉬가 없다면 일반 메세지로 판단
			chatWindow.innerHTML += "<div>" + sender + " : "
					+ content + "</div>";
		}
	}
	// 스크롤바를 제일 아래로 내려준다.
	chatWindow.scrollTOp = chatWindow.scrollHight;
};
</script>
<style>
#chatWindow{ border:1px solid black; width: 270px; height: 310px;
	overflow: scroll; padding: 5px; }
#chatMessage{ width: 236px; height: 30px; }
#sendBtn{ height: 30px; position: relative; top: 2px; left: -2px }
#closeBtn{ margi-bottom: 3px; position: relative; top: 2px; left:-2px }
#chatId{ width: 158px; height: 24px; border: 1px solid #AAAAAA; 
	background-color: #EEEEEE; }
.myMsg{ text-align: right;}
</style>
<body>
	<!-- 파라미터로 전달된 대화명을 얻어와서 사용 -->
	대화명 :
	<input type="text" id="chatId" value="${ param.chatId }" readonly />
	<button id="closeBtn" onclick="disconnect();">채팅 종료</button>
	<!-- 채팅 내역이 출력되는 부분 -->
	<div id="chatWindow"></div>
	<!-- 메세지를 입력하고 전송을 위한 버튼 -->
	<div>
		<input type="text" id="chatMessage" onkeyup="enterKey();" />
		<button id="sendBtn" onclick="sendMessage();">전송</button>
	</div>
</body>
</html>