package com.edu.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/chatMain.do")
	public String chatMain() {
		return "chatMain";
	}
	
	@RequestMapping("/chatWindow.do")
	public String chatWindow() {
		return "chatWindow";
	}
}
