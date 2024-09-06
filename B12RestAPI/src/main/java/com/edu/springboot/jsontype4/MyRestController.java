package com.edu.springboot.jsontype4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 @ResponseBody + @Controller와 같은 형태로 RestAPI를 만들기 위한
 컨트롤러에서 주로 사용된다. 내부 메서드에서 반환하는 값은 즉시 웹브라우저에
 출력된다. (일반적인 컨트롤러에서의 반환값은 View의 경로가 된다.)
 */
@RestController
public class MyRestController {
	
	// JSON 객체
	/*
	 Map은 key와 value를 가진 구조이므로 JSON객체와 동일하다.
	 따라서 Map을 반환하면 객체형태로 출력된다.
	 */
	@GetMapping("/restApi01.do")
	public Map<String, Object> restApi01() {
		Map<String, Object> maps = new HashMap<>();
		
		maps.put("key01", "홍길동");
		maps.put("key02", "유비");
		maps.put("key03", "손오공");
		maps.put("key04", "강백호");
		maps.put("key05", "둘리");
		
		return maps;
	}
	
	// JSON 배열
	/* 배열은 List와 동일한 구조를 가진다. 요소의 접근은 인덱스를 통해 할 수 있다. */
	@GetMapping("/restApi02.do")
	public List<String> restApi02(){
		List<String> lists = new ArrayList<>();
		
		lists.add("홍길동");
		lists.add("유비");
		lists.add("손오공");
		lists.add("강백호");
		lists.add("둘리");
		
		return lists;
	}
	
	// JSON 배열을 포함한 객체
	/*
	 JSON 객체이지만 Value로 배열을 포함하는 형태로 실무에서 가장 많이 사용된다.
	 */
	@GetMapping("/restApi03.do")
	public Map<String, Object> restApi03() {
		// 데이터는 객체이므로 Map을 먼저 생성한다.
		Map<String, Object> maps = new HashMap<>();
		
		// 각 Key에는 List인스턴스가 포함된다.
		List<String> lists1 =
				new ArrayList<>(Arrays.asList("이순신", "세종대왕", "신사임당"));
		maps.put("한국의위인", lists1);
		
		List<String> lists2 =
				new ArrayList<>(Arrays.asList("유비", "관우", "장비"));
		maps.put("삼국지", lists1);
		
		List<String> lists3 =
				new ArrayList<>(Arrays.asList("손오공", "저팔계", "사오정"));
		maps.put("서유기", lists1);
		
		return maps;
	}
	
	// JSON 객체를 포함한 배열
	/* JSON 배열이지만 객체를 인자로 가진다. 게시판의 목록에서 흔히
	 사용되는 패턴이다. */
	@GetMapping("/restApi04.do")
	public List<PersonVO> restApi04(){
		List<PersonVO> lists = new ArrayList<>();
		
		PersonVO vo1 = new PersonVO("강백호", 21, "파워포워드");
		PersonVO vo2 = new PersonVO("서태웅", 21, "파워포워드");
		PersonVO vo3 = new PersonVO("송태섭", 22, "포인트가드");
		PersonVO vo4 = new PersonVO("정대만", 23, "슈팅가드");
		PersonVO vo5 = new PersonVO("채치수", 24, "센터");
		
		lists.add(vo1);
		lists.add(vo2);
		lists.add(vo3);
		lists.add(vo4);
		lists.add(vo5);
		
		return lists;
	}
}
