<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04SearchRadius.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
	#map{
		width:100%; height:700px;
	}
</style>

<!-- 검색하는 반경에 따른 맵의 줌 레벨 설정. 
반경 1Km인 경우 레벨 15로 지정한다. -->
<c:choose>
	<c:when test="${param.distance eq 1 }">
		<c:set var="zoomLevel" value="15" />
	</c:when>
	<c:when test="${param.distance eq 5 }">
		<c:set var="zoomLevel" value="14" />
	</c:when>
	<c:when test="${param.distance eq 10 }">
		<c:set var="zoomLevel" value="13" />
	</c:when>
	<c:otherwise>
		<c:set var="zoomLevel" value="12" />
	</c:otherwise>
</c:choose>

<script type="text/javascript">
var span;
window.onload = function(){
	span = document.getElementById("result");
	
	if(navigator.geolocation){
		span.innerHTML = "Geolocation API를 지원합니다.";
		
		var options = {	
			enableHighAccurcy:true, 
			timeout:5000,
			maximumAge:3000
		};
		navigator.geolocation.getCurrentPosition(showPosition,showError,options);
	}
	else{
		span.innerHTML = "이 브라우저는 Geolocation API를 지원하지 않습니다.";
	}	
}
var showPosition = function(position){
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	span.innerHTML = "위도:"+latitude+",경도:"+longitude;	
	
	////////////////////////////////////////////////////////////////////////
	document.getElementById("latTxt").value = latitude;
	document.getElementById("lngTxt").value = longitude;
	////////////////////////////////////////////////////////////////////////
	
	initMap(latitude, longitude) ;
}
// 구글맵 초기화를 위한 JS코드
function initMap(latVar, lngVar) {
	// 매개변수로 전달된 내 위치를 통해 지도의 중심점 설정
	var uluru = {lat: latVar, lng: lngVar};
	// 구글맵 초기화
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: ${zoomLevel}, 
		center: uluru
	});
	// 커스텀 마커 표시를 위한 객체 생성
	var marker = new google.maps.Marker({
		position: uluru,
		map: map,
		// 우리가 지정한 아이콘으로 설정
		icon: './icon/icon_me.png'
	});
	
	//////////////////////////////////////////////////////////////////////////
	// 다중마커start
	// 풍성 도움말 객체 생성
	var infowindow = new google.maps.InfoWindow();
	
	// 다중마커로 표시할 시설물 정보를 배열로 추가
	var locations = [		
		/* 텍스트로 아래와 같이 기술되어야 하므로
		forEach 태그를 통해 갯수만큼 물리적으로 반복한다.
		['명동', 37.563576, 126.983431],
		['가로수길', 37.520300, 127.023008],
		['광화문', 37.575268, 126.976896],
		['남산', 37.550925, 126.990945],
		['이태원', 37.540223, 126.994005]
		*/
		// 검색된 시설물의 갯수만큼 반복
		<c:forEach items="${searchLists }" var="row">
			['${row.hp_name }', ${row.hp_latitude }, ${row.hp_longitude }], 
		</c:forEach>
	];
	
	// 앞에서 생성한 배열의 크기만큼 반복하여 마커를 생성한다.
	/* [나만의 주석] - 여기는 있는 API를 가져다 사용한 것 !!
	let으로 선언할 시 marker는 두번 선언이 되지 않으므로 
	i만 따로 선언하면 된다. */
 	var marker, i;
	for (i=0; i<locations.length; i++) {  
		marker = new google.maps.Marker({
			id:i,
			position: new google.maps.LatLng(locations[i][1], locations[i][2]),
			map: map,
			// 시설물 표시를 위한 하늘색 아이콘으로 설정
			icon: './icon/icon_facil.png'
		});

		// 생성한 마커에 이벤트를 부착한다. 클릭 시 풍선 도움말이 띄워진다.
		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				// 풍선 도움말의 바로가기를 누르면 alert를 띄운다.
				infowindow.setContent(locations[i][0]+"<br/><a href='javascript:alert(\"병원명:"+locations[i][0]+"\");'>바로가기</a>");
				infowindow.open(map, marker);
			}
		})(marker, i));
	
		// 마커를 클릭하면 항상 지도의 중심으로 이동한다.
		if(marker)
		{
			marker.addListener('click', function() {
				map.setZoom(16);
				map.setCenter(this.getPosition());
			});
		}
	}
	// 다중마커end
	//////////////////////////////////////////////////////////////////////////
}
var showError = function(error){
	switch(error.code){
		case error.UNKNOWN_ERROR:
			span.innerHTML = "알수없는오류발생";break;
		case error.PERMISSION_DENIED:
			span.innerHTML = "권한이 없습니다";break;
		case error.POSITION_UNAVAILABLE:
			span.innerHTML = "위치 확인불가";break;
		case error.TIMEOUT:
			span.innerHTML = "시간초과";break;
	}
}
</script>
</head>
<body>
<div class="container">

	<h2>내위치기반 반경검색하기</h2>
	
	<span id="result" style="color:red; font-size:1.5em; font-weight:bold;"></span>
	<fieldset>
		<legend>검색조건 ${resultCount }</legend>		
		<form name="searchFrm">
			현재위치에서
			<!-- 현재위치 위경도 입력상자 -->
			<input type="text" id="latTxt" name="latTxt" />
			<input type="text" id="lngTxt" name="lngTxt" />
			 
			<select name="distance" id="distance">
				<option value="1" <c:if test="${param.distance==1 }">selected</c:if>>1Km</option>
				<option value="5" <c:if test="${param.distance==5 }">selected</c:if>>5Km</option>
				<option value="10" <c:if test="${param.distance==10 }">selected</c:if>>10Km</option>
				<option value="20" <c:if test="${param.distance==20 }">selected</c:if>>20Km</option>
				<option value="30" <c:if test="${param.distance==30 }">selected</c:if>>30Km</option>
				<option value="40" <c:if test="${param.distance==40 }">selected</c:if>>40Km</option>
			</select>
			반경내 시설 검색하기
			<select name="pageNum" id="pageNum">			
			<c:forEach begin="1" end="${selectNum }" var="i" varStatus="loop">
				<option value="${i }" <c:if test="${param.pageNum==i }">selected</c:if>>${i }페이지</option>			
			</c:forEach>				
			</select>
			 
			<input type="submit" value="검색하기" />
		</form>
	</fieldset>
	<div id="map"></div>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=${apiKey }"></script>
</div>
</body>
</html>

