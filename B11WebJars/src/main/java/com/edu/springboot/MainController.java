package com.edu.springboot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	/* 컨트롤러에서 매핑을 위한 메서드에 @ResponseBody 어노테이션을 붙이면
	 반환되는 값을 즉시 웹브라우저에 출력한다. 이 어노테이션이 없다면
	 View의 경로를 반환하여 포워드하게된다. */
	@RequestMapping("/json.do")
	@ResponseBody
	public String json() {
		/* 외부라이브러리인 simple-json을 통해 사용할 수 있는 클래스로
		 JSON배열과 JSON객체를 만들어준다. 사용법은 List, Map 컬렉션과
		 완전히 동일하다. */
		JSONArray arr = new JSONArray();	// 배열은 List와 동일
		JSONObject obj = new JSONObject();	// 객체는 Map과 동일
		
		// JSON 배열에 데이터 추가
		arr.add("손오공");
		arr.add("저팔계");
		arr.add("사오정");
		
		// JSON 객체에 데이터 추가
		obj.put("서유기", arr);
		obj.put("result", 1);
		
		// JSON을 String형식으로 웹브라우저에 출력한다.
		return obj.toJSONString();
	}
	
	@RequestMapping("/jsonQuiz.do")
	@ResponseBody
	public String jsonQuiz() {
		
		JSONObject objResult = new JSONObject();
		
		//취미
		JSONArray arr1 = new JSONArray();
		arr1.add("자전거");
		arr1.add("수영");
		arr1.add("축구");
		
		// 중딩친구
		JSONArray arrMidFirend = new JSONArray();
		arrMidFirend.add("손오공");
		arrMidFirend.add("저팔계");
		arrMidFirend.add("사오정");
		
		// 고딩 친구
		JSONArray arrCircle = new JSONArray();
		arrCircle.add("유비");
		arrCircle.add("관우");
		arrCircle.add("장비");
		
		JSONArray arrClass = new JSONArray();
		arrClass.add("이몽룡");
		arrClass.add("성춘향");
		
		JSONObject objHighFriend = new JSONObject();
		objHighFriend.put("circle", arrCircle);
		objHighFriend.put("class", arrClass);
		
		// Friend
		JSONObject objFriend = new JSONObject();
		objFriend.put("mid", arrMidFirend);
		objFriend.put("high", objHighFriend);
		
		//결과
		objResult.put("name", "홍길동");
		objResult.put("age", "99");
		objResult.put("hobby", arr1);
		objResult.put("friend", objFriend);
		
		return objResult.toJSONString();
		
	}
	
}
