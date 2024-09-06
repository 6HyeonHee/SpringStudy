package com.edu.springboot;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// guest 하위는 권한없이 누구나 접근 가능
	@RequestMapping("/guest/index.do")
	public String welcome1() {
		return "guest";
	}
	// member 하위는 ADMIN 혹은 USER 권한을 획득 후 접근 가능
	@RequestMapping("/member/index.do")
	public String welcome2() {
		return "member";
	}
	// admin 하위는 ADMIN 권한만 접근 가능
	@RequestMapping("/admin/index.do")
	public String welcome3() {
		return "admin";
	}
	
	// 커스텀 로그인 페이지 매핑
	@RequestMapping("/myLogin.do")
	public String login1(Principal principal, Model model) {
		try {
			/* 스프링 시큐리티는 세션을 사용해서 로그인 정보를 저장하지만
			개발자가 직접 접근할 수 없으므로 Principal 객체를 통해
			로그인 아이디를 얻어올 수 있다. */
			String user_id = principal.getName();
			model.addAttribute("user_id", user_id);
		}
		catch (Exception e) {
			/* 최초 접근 시 로그인 정보가 없으므로 NullPointer 예외가 발생된다.
			따라서 예외 처리해야 한다. */
			System.out.println("로그인 전입니다.");
		}
		return "auth/login";
	}
	
	// 로그인 시도 중 에러가 발생하는 경우
	@RequestMapping("/myError.do")
	public String login2() {
		return "auth/error";
	}
	
	// 권한이 부족한 경우
	@RequestMapping("/denied.do")
	public String login3() {
		return "auth/denied";
	}
	
}
