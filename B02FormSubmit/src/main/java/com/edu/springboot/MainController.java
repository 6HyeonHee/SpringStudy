package com.edu.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpServletRequest;

/*
 JSP에서는 컨트롤러로 정의하기 위해 Servlet 클래스로 정의한 후
 매핑 정보를 입력하였다. Spring boot에서는 아래와 같이 @Controller
 어노테이션을 부착하면 자동으로 컨트롤러 클래스로 정의된다. 또한 사용을
 위해 별도로 인스턴스를 생성할 필요없이 스프링 컨테이너가 시작시 자동으로
 인스턴스(빈)을 생성해준다.
*/
@Controller
public class MainController {
	
	// home 경로 매핑
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	/*
	 컨트롤러에 매핑된 각 메서드에서 String을 반환하면
	 application.properties의 JSP설정에 의해 View의 경로가 조립된다.
	 "Prefix경로 + 메서드의 반환값 + suffix 경로"에 해당하는 JSP 파일을
	 찾아 포워드하게 된다. 즉 이 함수는 index.jsp 파일을 찾아 포워드한다.
	 */
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/sub.do")
	public String sub() {
		/* 만약 views 하위에 별도의 폴더가 필요하다면 이와같이 슬러쉬로
		  구분하여 작성하면 되나. sub 폴더 하위의 sub.jsp를 찾아 포워드한다. */
		return "sub/sub";
	}
	
	/*
	 1. request 내장객체를 통해 폼값을 받아 사용
	 : 컨트롤러에서는 아래와 같이 요청명에 대한 매핑을 한 후 요청을 처리할 메서드를 정의한다.
	 해당 메서드는 일반적인 방식으로 호출하는게 아니라 요청명을 통해 자동으로 호출된다.
	 따라서 메서드명은 a1, a2와 같이 네이밍해도 큰 문제는 없다. 단, 중복되지 않으면 된다.
	 */
	@RequestMapping("/form1.do")
	public String form1(HttpServletRequest httpServletRequest, Model model) {
		/*
		 이 메서드에서 요청을 처리하기 위해 request 내장객체와 Model 객체를
		 사용해야하므로, 매개변수로 미리 정의한다.
		 컨트롤러에서 요청을 처리하기 위한 함수는 함수명으로 호출하는 방식이 아니므로,
		 매개변수로 정의한 객체를 스프링 컨테이너가 자동으로 생성해주는 구조를 가지고 있다.
		 */
		String name = httpServletRequest.getParameter("name");
		String age = httpServletRequest.getParameter("age");
		
		/*
		 View로 전달할 데이터가 있는 경우 서블릿에서는 request영역에 저장한 후
		 포워드했다. Spring에서는 Model객체에 저장한 후 포워드 대신 View의
		 경로를 반환한다.
		 */
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "form/submit1";
	}
	
	/*
	 2. 어노테이션을 통해 파라미터를 받은 후 변수에 저장
	 : @RequestParam에 지정된 파라미터명으로 값을 받은 후 우측에 정의한
	 매개변수에 저장한다.
	 */
	@RequestMapping("/form2.do")
	public String form2(@RequestParam("name") String name,
			 @RequestParam("age") String age, Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "form/submit2";
	}
	
	/*
	 3. 커맨드 객체를 통해 파라미터를 한꺼번에 받아 DTO에 저장한다.
	 사용시 매개변수명은 클래스명의 첫글자를 소문자로 바꾼 이름을 사용해야한다.
	 만약 이름을 변경하고 싶다면 @ModelAtribute 어노테이션을 사용해야한다.
	 */
	@RequestMapping("/form3.do")
	public String form3(PersonDTO personDTO) {
		/*
		 요청 시 사용되는 파라미터명과 DTO의 멤버변수명을 동일하게
		 정의하면 갯수에 상관없이 setter를 통해 한꺼번에 폼값을 받아
		 저장할 수 있다. 또한 Model 객체에 자동으로 저장되므로 View로
		 전달받기 위해 별도로 저장할 필요가 없다.
		 */
		return "form/submit3";
	}
	
	/*
	 4. @PathVariable 어노테이션으로 경로형태로 전달되는 파라미터를
	 순서대로 변수에 저장한다. 단 이 경우 파라미터를 경로로 인식하므로
	 정적 리소스 사용 시 경로 설정에 주의해야한다.
	 */
	@RequestMapping("/form4/{name1}/{age1}")
	public String form4(@PathVariable("name1") String name2, 
			@PathVariable("age1") String age2, Model model) {
		
		model.addAttribute("name", name2);
		model.addAttribute("age", age2);
		
		return "form/submit4";
	}
	
	@RequestMapping("/memberRegist.do")
	public String memberRegist(MemberDTO memberDTO) {
		return "member/regist";
	}
	
}
