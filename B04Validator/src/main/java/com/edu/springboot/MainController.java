package com.edu.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/write.do")
	public String insert1() {
		return "write";
	}
	
	/*
	 Validator 인터페이스를 통해 폼값의 유효성 검증
	 Spring에서 사용하는 커멘드객체는 전송된 폼값을 한꺼번에 받아
	 Model 인스턴스에 저장해준다. 만약 저장되는 속성명을 변경하고 싶다면
	 @ModelAttribute 어노테이션을 사용하면 된다. 아래의 경우 boardDTO가
	 아닌 dto라는 이름으로 Model에 저장된다.
	 */
	@RequestMapping("/writeAction1.do")
	public String writeAction1(@ModelAttribute("dto") BoardDTO boardDTO,
			BindingResult result) {
		
		// 폼값 검증에 성공한 경우 포워드 할 View의 경로 설정
		String page = "result";
		System.out.println(boardDTO);
		
		// 폼값 검증을 위한 인스턴스 생성
		BoardValidator validator = new BoardValidator();
		/*
		 폼값을 저장한 DTO 및 검증결과 전달을 위한 객체를 인수로 전달한다.
		 여기서 validate()를 호출하여 검증을 진행하게 된다.
		 */
		validator.validate(boardDTO, result);
		
		// 폼값 검증에 실패한 경우 if문의 블럭 실행
		if(result.hasErrors()) {
			// 실패한 경우 재입력을 받기위해 쓰기 페이지로 포워드
			page = "write";
			/*
			 result가 폼값 검증에 대한 모든 결과값을 가지고 있다. 따라서
			 아래 부분의 출력문을 주석처리 하더라도 이 부분에서 모든 검증 결과가
			 출력된다.
			 */
			System.out.println("검증 실패 반환값1:" + result.toString());
			System.out.println("====================================");
			
			// 제목 검증에 실패한 경우 우리가 생성한 에러코드를 출력한다.
			if(result.getFieldError("title") != null) {
				System.out.println("제목 검증1(에러코드):" 
							+ result.getFieldError("title").getCode());
			}
			
			// 내용 검증에 실패한 경우 디폴트 메세지를 출력한다.
			if(result.getFieldError("content") != null) {
				System.out.println("내용 검증1(디폴트 메세지):" 
							+ result.getFieldError("content").getDefaultMessage());
			}
		}
		return page;
	}
	
	/*
	 어노테이션을 통한 검증이므로 폼값 저장을 위한 VO객체에 @Validated를 추가한다.
	 */
	@RequestMapping("/writeAction2.do")
	public String writeAction2(@ModelAttribute("dto")@Validated BoardVO boardVO,
			BindingResult result) {
		String page = "result";
		System.out.println(boardVO);
		
		/*
		 검증을 위한 클래스는 별도로 정의할 필요가 없으므로 주석처리한다.
		 */
//		BoardValidator validator = new BoardValidator();
//		validator.validate(boardVO, result);
		
		if(result.hasErrors()) {
			page = "write";
			System.out.println("검증 실패 반환값2:" + result.toString());
			
			if(result.getFieldError("title") != null) {
				System.out.println("제목 검증2(에러코드):" +
						result.getFieldError("title").getCode());
			}
			if(result.getFieldError("content") != null) {
				System.out.println("내용 검증2(디폴트메세지):" +
						result.getFieldError("content").getDefaultMessage());
			}
		}
		return page;
	}
}
