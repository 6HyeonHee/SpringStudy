package com.edu.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/boardList.do")
	public String boardList() {
		return "boardList";
	}
	
	@GetMapping("/boardView.do")
	public String boardView() {
		return "boardView";
	}
}
