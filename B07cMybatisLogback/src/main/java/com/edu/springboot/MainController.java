package com.edu.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.springboot.jdbc.IMemberService;
import com.edu.springboot.jdbc.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	
	/*
	 서비스 인터페이스를 통해 Mapper의 메서드를 호출하므로 여기서
	 자동주입 받아서 준비한다. 해당 인터페이스는 @Mapper 어노테이션이
	 부착되어 있으므로 컨테이너가 시작될떄 자동으로 빈이 생성된다.
	 */
	@Autowired
	IMemberService dao;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// 회원목록
	@RequestMapping("/list.do")
	public String member2(Model model, MemberDTO memberDTO) {
		
		System.out.println(memberDTO.getSearchField());
		System.out.println(memberDTO.getSearchKeyword());
		
		model.addAttribute("memberList", dao.select(memberDTO));
		return "list";
	}
	
	// 회원등록
	@GetMapping("/regist.do")
	public String member1() {
		// 등록페이지를 포워드만 처리
		return "regist";
	}
	@PostMapping("/regist.do")
	public String member6(HttpServletRequest req) {
		/*
		 회원등록 폼에서 전송한 파라미터를 request 내장객체를 통해 저장한다.
		 개별적으로 전달받은 파라미터를 insert()메서드로 전달한다.
		 */
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		String name = req.getParameter("name");
		
		int result = dao.insert(id, pass, name);
		if(result==1) System.out.println("입력되었습니다");
		return "redirect:list.do";
	}
	// 회원 수정
	@GetMapping("/edit.do")
	public String member3(HttpServletRequest req, MemberDTO memberDTO, Model model) {
		// 폼값을 request 내장객체로 받은 후 전달한다.
		memberDTO = dao.selectOne(req.getParameter("id"));
		model.addAttribute("dto", memberDTO);
		return "edit";
	}
	@PostMapping("/edit.do")
	public String member7(HttpServletRequest req) {
		
		// 폼값을 request 내장객체를 통해 받는다.
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		String name = req.getParameter("name");
		
		// 폼값을 한꺼번에 DAO로 전달하기 위해 Map을 선언한다..
		Map<String, String> paramMap = new HashMap<String, String>();
		// 파라미터를 각 Key에 저장한다.
		paramMap.put("m_id", id);
		paramMap.put("m_pass", pass);
		paramMap.put("m_name", name);

		int result = dao.update(paramMap);
		if(result==1) System.out.println("수정되었습니다.");
		return "redirect:list.do";
	}
	
	// 회원삭제
//	@RequestMapping("/delete.do")
//	public String member4(HttpServletRequest req) {
//		String id = req.getParameter("id");
//		int result = dao.delete(id);
//		if(result==1) System.out.println("삭제되었습니다.");
//		return "redirect:list.do";
//	}
	
	// 비동기 방식으로 회원 삭제
	@RequestMapping("/delete.do")
	/* 컨트롤러에서 반환하는 String은 View의 경로를 의미하지만, 이 어노테이션이
	 부착되면 반환하는 문자열을 웹브라우저에 즉시 출력한다. 
	 또한 Map을 반환하면 JSON객체형식, List를 반환하면 JSON배열 형식으로 출력하게 된다. */
	@ResponseBody
	public Map<String, String> member4(HttpServletRequest req) {
		String id = req.getParameter("id");
		int result = dao.delete(id);
		
		// 콜백데이터 생성을 위해 Map 인스턴스 선언
		Map<String, String> map = new HashMap<>();
		// 성공여부에 따라 콜백데이터 처리
		if(result==1) {
			System.out.println("삭제되었습니다.");
			map.put("result", "success");
		} else {
			System.out.println("삭제실패.");
			map.put("result", "fail");
		}
		// Map을 반환한다. 그러면 화면상에는 JSON객체가 출력된다.
		return map;
	}
}
