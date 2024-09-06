package com.edu.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.springboot.smtp.EmailSending;
import com.edu.springboot.smtp.InfoDTO;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/emailSendMain.do")
	public String emailSendMain() {
		return "emailSendMain";
	}
	
	// 이메일 발송을 처리하는 인스턴스 자동주입
	@Autowired
	EmailSending email;
	
	// 내용 입력 후 전송했을 때 post 방식의 폼값을 받아 실제 내용 전송
	@PostMapping("/emailSendProcess.do")
	public String emailSendProcess(InfoDTO infoDTO) {
		email.myEmailSender(infoDTO);
		return "home";
	}
}
