<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02GoogleMap.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
/* 구글 맵 크기 설정*/
#map {
	width:800px; height: 600px;
}
</style>
<script>
// 구글맵 초기화 및 마커설정
function initMap(){
	// 맵의 중심위치를 설정(1번 예제에서 나온 위경도로 변경 후 확인)
	var uluru = {lat:37.563398, lng:126.9863309};
	// 구글맵 초기화(맵 출력을 위한 DOM과 Zoom레벨 설정)
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 17,
		center: uluru
	});
	// 마커 설정
	var marker = new google.maps.Marker({
		position: uluru,
		map: map
	});
}
window.onload = function() {
	initMap();
}
</script>
</head>
<body>
<div class="container">
	<h2>Google Map 띄워보기</h2>
	<div id="map"></div>
	<!-- 구글맵은 비동기 형식으로 로드된다. -->
	<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=${apiKey}"></script>
</div>
</body>
</html>