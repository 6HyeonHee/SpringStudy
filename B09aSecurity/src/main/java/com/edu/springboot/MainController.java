package com.edu.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// guest 하위는 권한없이 누구나 접근할 수 있는 영역
	@RequestMapping("/guest/index.do")
	public String welcome1() {
		return "guest";
	}
	
	// member 하위는 ADMIN 혹은 USER 권한을 획득 후 접근 가능
	@RequestMapping("/member/index.do")
	public String welcome2() {
		return "member";
	}
	
	// admin 하위는 ADMIN 권한만 접근할 수 있는 영역으로 설정
	@RequestMapping("/admin/index.do")
	public String welcome3() {
		return "admin";
	}
}
