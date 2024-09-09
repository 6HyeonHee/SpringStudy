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
	public String member2(Model model) {
		/*
		 dao.select() : 서비스 인터페이스의 추상메서드를 호출한다.
		 	그러면 인터페이스와 연결된 Mapper파일에 정의된 특정 엘리먼트가 호출된다.
		 */
		model.addAttribute("memberList", dao.select());
		return "list";
	}
	
	/*
	 회원등록 : 가입폼은 get방식, 등록처리는 post방식으로 매핑한다.
	 */
	@GetMapping("/regist.do")
	public String member1() {
		// 등록페이지를 포워드만 처리
		return "regist";
	}
	@PostMapping("/regist.do")
	public String member6(MemberDTO memberDTO) {
		// 전송된 폼값을 커맨드객체를 통해 일괄적으로 받은 후 DAO의 메세드 호출
		int result = dao.insert(memberDTO);
		if(result == 1) System.out.println("입력되었습니다.");
		// 입력에 성공하면 목록으로 이동한다.
		return "redirect:list.do";
	}
	
	// 회원정보수정1 : 기존 회원정보를 가져와서 수정폼 구성
	@GetMapping("/edit.do")
	public String member3(MemberDTO memberDTO, Model model) {
		// 기존 레코드를 얻어온 후 View로 전달
		memberDTO = dao.selectOne(memberDTO);
		model.addAttribute("dto", memberDTO);
		return "edit";
	}
	// 회원정보수정2 : 수정된 내용을 통해 레코드 업데이트 처리
	@PostMapping("/edit.do")
	public String member7(MemberDTO memberDTO) {
		int result = dao.update(memberDTO);
		if(result==1) System.out.println("수정되었습니다.");
		return "redirect:list.do";
	}
	
	// 회원삭제
//	@RequestMapping("/delete.do")
//	public String member4(MemberDTO memberDTO) {
//		// 아이디를 파라미터로 받은 후 delete 메서드 호출
//		int result = dao.delete(memberDTO);
//		if(result==1) System.out.println("삭제되었습니다.");
//		return "redirect:list.do";
//	}
	
	// 비동기 방식으로 회원 삭제
	@RequestMapping("/delete.do")
	/* 컨트롤러에서 반환하는 String은 View의 경로를 의미하지만, 이 어노테이션이
	 부착되면 반환하는 문자열을 웹브라우저에 즉시 출력한다. 
	 또한 Map을 반환하면 JSON객체형식, List를 반환하면 JSON배열 형식으로 출력하게 된다. */
	@ResponseBody
	public Map<String, String> member4(MemberDTO memberDTO) {
		int result = dao.delete(memberDTO);
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
