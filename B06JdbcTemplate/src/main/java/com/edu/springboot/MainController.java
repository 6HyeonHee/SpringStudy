package com.edu.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.springboot.jdbc.IMemberService;
import com.edu.springboot.jdbc.MemberDTO;

@Controller
public class MainController {
	
	/*
	 Service 역할의 인터페이스의 빈을 자동으로 주입받는다. 이를 통해
	 DAO의 메서드를 호출한다.
	 */
	@Autowired
	IMemberService dao;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// 회원목록
	@RequestMapping("/list.do")
	public String member2(Model model) {
		/*
		 DAO에서 회원레코드를 추가한 List를 반환해주면 이를 Model객체에
		 저장한 후 View로 포워드한다.
		 */
		model.addAttribute("memberList", dao.select());
		return "list";
	}
	
	// 회원 정보 추가
	/*
	 @RequestMapping 어노테이션을 통해 매핑할 때 전송방식과 상관없이
	 설정하려면 프로퍼티 없이 요청명만 기술하면 된다.
	 하지만 아래와 같이 get/post 방식으로 구분하려면 value 프로퍼티에
	 요청명을, method 프로퍼티에 전송방식을 명시하면 된다.
	 */
//	@RequestMapping(value="/regist.do", method=RequestMethod.GET)
	@GetMapping("/regist.do")
	public String member1() {
		return "regist";
	}
	
	/*
	 SpringBooter 3.x에서는 매핑시 @GetMapping/@PostMapping의 
	 사용을 권고하고 있다.
	 */
	
//	@RequestMapping(value="/regist.do", method=RequestMethod.POST)
	@PostMapping("/regist.do")
	public String member6(MemberDTO memberDTO) {
		// 전송된 폼값은 DTO로 한꺼번에 받은 후 DAO를 호출한다.
		int result = dao.insert(memberDTO);
		// insert의 결과이므로 1이면 성공, 0이면 실패로 판단
		if(result==1) System.out.println("입력되었습니다.");
		/*
		 View의 경로를 반환하는게 기본이지만, 아래와 같이 redirect:를
		 추가하면 해당 요청명으로 이동하게된다.
		 */
		return "redirect:list.do";
	}
	
	// 회원정보 수정1 : 기존 회원정보 불러오기
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String member3(MemberDTO memberDTO, Model model) {
		// 아이디를 파라미터로 받아서 회원정보를 인출한다.
		memberDTO = dao.selectOne(memberDTO);
		model.addAttribute("dto", memberDTO);
		return "edit";
	}
	// 회원정보 수정2 : 수정 처리
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String member7(MemberDTO memberDTO) {
		// 전송된 폼값은 커멘드객체를 통해 한꺼번에 받아서 DAO로 전달
		int result = dao.update(memberDTO);
		// 결과가 1이면 수정성공
		if(result==1) System.out.println("수정되었습니다.");
		// 상세보기 페이지가 업으므로 수정 후 목록으로 이동
		return "redirect:list.do";
	}
	
	@RequestMapping("/delete.do")
	public String member4(MemberDTO memberDTO) {
		int result = dao.delete(memberDTO);
		if(result==1) System.out.println("삭제되었습니다.");
		return "redirect:list.do";
	}
}
