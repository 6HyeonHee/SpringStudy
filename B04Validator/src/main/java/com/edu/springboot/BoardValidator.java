package com.edu.springboot;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/*
 폼값 검증을 위한 클래스 제작을 위해 먼저 Validator 인터페이스를 구현한다.
 그리고 추상메서드 2개를 필수로 오버라이딩 해야한다.
 */
public class BoardValidator implements Validator {
	
	/*
	 검증을 위한 커맨드 객체의 클래스 타입을 확인하는 용도의 메서드
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// 폼값을 받기 위한 커맨드객체의 타입만 명시해주면된다.
		return BoardDTO.class.isAssignableFrom(clazz);
	}
	
	/*
	 폼값 검증을 하기 위해 3가지 방법을 사용할 수 있다.
	 1. if문을 이용한 검증
	 2. ValidationUtils 클래스를 이용한 검증
	 3. 사용자 정의 메서드를 이용한 검증
	 */
	@Override
	public void validate(Object target, Errors errors) {
		// 폼값 전체를 저장한 DTO객체는 Object 타입으로 전달되므로 형변환한다.
		BoardDTO boardDTO = (BoardDTO) target;
		
		// 폼값을 저장하고 있는 커맨드객체인지 확인(필요한 경우에 사용)
		if(supports(boardDTO.getClass()) == true) {
			System.out.println("폼값 검증에 적합한 인스턴스");
		} else {
			System.out.println("폼값 검증을 위한 인스턴스가 아님");
		}
		
		//1. 아이디 검증
		String userid = boardDTO.getUserid();
		// 만약 아이디가 null이거나 빈값이라면 아래 내용을 실행한다.
		if(userid==null || userid.trim().isEmpty()) {
			// 개발자가 콘솔에 출력하는 검증 내용
			System.out.println("아이디를 입력해주세요.");
			/*
			 폼값 검증 시 오류가 있는 경우 처리형식
			 Errors인스턴스.rejectValue(폼의 name속성, 에러 객체명, 디폴트메세지);
			 */
			errors.rejectValue("userid", "idError", "아이디 검증 실패");
		}
		
		/* 
		 2. 제목검증 : ValidationUtils 클래스의 정적 메서드를 호출하여
				빈값이거나 null 값인 경우를 검증한다.
		*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", 
				"titleError", "제목 검증 실패");
		
		/*
		 3. 내용검증 : 개발자가 정의한 메서드를 통해 검증한다.
		 */
		boolean contentValidate = myEmptyOrWhitespace(boardDTO.getContent());
		if(contentValidate == false) {
			System.out.println("내용을 입력해주세요.");
			errors.rejectValue("content", "contentError", "내용 검증 실패");
		}
	}
	
	public boolean myEmptyOrWhitespace(String value) {
		// 내용이 null이거나 길이가 0인 경우 false를 반환한다.
		if(value==null || value.trim().length()==0) {
			return false;
		} else {
			return true;
		}
	}
}
