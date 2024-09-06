package com.edu.springboot.bean1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Di1Controller {

	/*
	 @ResponseBody : 컨트롤러에서 처리된 내용을 View로 전달하지 않고,
	 	즉시 출력하고 싶을 때 사용하는 어노테이션이다. String을 반환하면
	 	단순히 문자열이 출력되고, Map 혹은 List로 반환하면 JSON객체 혹은
	 	배열 형태로 출력된다.
	 */
	@RequestMapping("/di1")
	@ResponseBody
	public String home() {
		
		// Java 설정 파일을 기반으로 스프링 컨테이너를 생성한다.
		ApplicationContext context =
				new AnnotationConfigApplicationContext(BeanConfig.class);
		
		// 컨테이너에 미리 생성된 person1 빈을 주입받는다.(형변환필요)
		Person person1 = (Person) context.getBean("person1");
		// 빈의 정보를 toString()을 통해 출력한다.
		System.out.println(person1);
		
		// 두번째 인자를 통해 타입을 명시하면 주입받은 후 별도의 형변환이 필요없다.
		Person person2 = context.getBean("person2", Person.class);
		System.out.println(person2);
		
		/*
		 해당 메서드의 반환타입이 String 이므로 @ResponseBody 어노테이션이
		 없다면 View의 경로를 반환하게 되지만, 현재는 단순한 문자열을 반환하여
		 컨트롤러에서 즉시 출력한다.
		 */
		return "Dependency Injection1 (의존주입1)";
	}
}
