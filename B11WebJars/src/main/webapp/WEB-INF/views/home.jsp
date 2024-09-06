<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- bootstrap 링크 -->
<link rel="stylesheet" href="/webjars/bootstrap/5.3.3/css/bootstrap.css" />
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.js"></script>
<!-- jQuery 링크 -->
<script src="/webjars/jquery/3.7.1/jquery.js"></script>
</head>
<body>
	<h2>Spring boot Project 세팅하기</h2>
	<ul>
		<li><a href="/">루트</a></li>
		<li><a href="/json.do">simple-json 라이브러리 사용하기</a></li>
		<li><a href="/jsonQuiz.do">퀴즈</a></li>
	</ul>
	
	<h2>Webjars - 부트스트랩</h2>
	<button type="button" class="btn">Basic</button>
	<button type="button" class="btn btn-primary">Primary</button>
	<button type="button" class="btn btn-secondary">Secondary</button>
	<button type="button" class="btn btn-success">Success</button>
	<button type="button" class="btn btn-info">Info</button>
	<button type="button" class="btn btn-warning">Warning</button>
	<button type="button" class="btn btn-danger">Danger</button>
	<button type="button" class="btn btn-dark">Dark</button>
	<button type="button" class="btn btn-light">Light</button>
	<button type="button" class="btn btn-link">Link</button>
	
	<h2>Webjars - JQuery</h2>
	<button type="button" id="myBtn" class="btn btn-warning">
		클릭하세요
	</button>
	<script>
	$(function() {
		$('#myBtn').click(function() {
			alert("jQuery 동작하나요?");
		});
	});
	</script>
	
	<h2>BootStrap5의 Modal 구현하기</h2>
	<button type="button" class="btn btn-primary" data-bs-toggle="modal" 
		data-bs-target="#myModal01">
    	Open modal
  	</button>

	
</body>
<!-- 전체 디자인과 상관이 없으므로 body 태그 바깥쪽에 작성해도됨. -->
<!-- Modal01 - 댓글수정폼 -->
<div class="modal fade" id="myModal01">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">댓글수정폼</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        <textarea class="form-control" style="height:200px;">내용 블라블라..!!</textarea>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
      	<button type="submit" class="btn btn-primary">수정하기</button>
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

</html>