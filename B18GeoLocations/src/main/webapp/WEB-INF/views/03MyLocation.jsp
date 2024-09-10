<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
#map {
	width: 100%; height: 700px
}
</style>
<script type="text/javascript">
var span;
window.onload = function() {
	span = document.getElementById("result");
	
	if(navigator.geolocation) {
		span.innerHTML = "Geolocation API를 지원합니다.";
		
		var options = {
			enableHighAccuracy : true,
			timeout : 5000,
			maximumAge : 3000
		};
		
		//navigator.geolocation.getCurrentPosition(
		//		showPosition, showError, options);
		
		var watchID = 
			navigator.geolocation.watchPosition(showPosition,
				showError, options);
		//navigator.geolocation.clearWatch(watchID);
	} else {
		span.innerHTML = 
			"이 브라우저는 Geolocation API를 지원하지 않습니다.";
	}
}
	
	// 위치를 찾았을 때의 콜백 메소드
	var showPosition = function(position){
		console.log("showPosition() 콜백됨");
		// 콜백된 데이터를 통해 위도, 경도를 얻어온다.
		var latitude = position.coords.latitude;
		var longitude = position.coords.longitude;
		span.innerHTML = "위도:"+latitude+", 경도:"+longitude;
		
		initMap(latitude, longitude);
	}
	
	function initMap(latVar, lngVar){
		var uluru = {lat:latVar, lng:lngVar};
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 17,
			center: uluru
		});
		var marker = new google.maps.Marker({
			position: uluru,
			map: map
		});
	}
	
	// 위치를 찾지 못한 경우의 콜백함수
	var showError = function(error){
		switch(error.code) {
		case error.UNKNOWN_ERROR:
			span.innerHTML = "알 수 없는 오류 발생"; break;
		case error.PERMISSION_DENIED:
			span.innerHTML = "권한이 없습니다."; break;
		case error.POSITION_UNAVAILABLE:
			span.innerHTML = "위치 확인 불가"; break;
		case error.TIMEOUT:
			span.innerHTML = "시간 초과"; break;
		}
	}
</script>

</head>
<body>
<div class="container">
	<h2>내 위치를 구글맵에 표시하기</h2>
	<fieldset>
		<legend>현재위치 - 위도, 경도</legend>
		<span id="result" style="color:red; font-size: 1.5em;
			font-weight: bold;"></span>
	</fieldset>
	<div id="map"></div>
	<!-- 구글맵은 비동기 형식으로 로드된다. -->
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=${apiKey}">
	</script>
</div>
</body>
</html>