package com.edu.springboot.bean2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 @Component를 통해 스프링 컨테이너 시작시 자동으로 빈이 생성된다.
 여기서는 별도의 이름을 지정하지 않았으므로 클래스명의 첫글자를 소문자로
 변경한 student라는 이름으로 빈이 생성된다.
 */
@Component
public class Student {
	
	/*
	 @Value 어노테이션에 지정한 값으로 멤버변수가 초기화 된다. 이 값은
	 setter를 통해 설정된다.
	 */
	@Value("이순신")
	private String name;
	
	@Value("30")
	private int age;
	
	/*
	 객체타입의 멤버변수는 @Autowired를 통해 자동으로 빈을 주입받을 수 있다.
	 이때 @Qualifier가 있으면 빈의 이름까지 지정해서 주입받는다.
	 만약 없다면 타입으로 빈을 찾아 주입받게 된다.
	 */
	@Autowired
	@Qualifier("macBook")
	private Computer notebook;

	// 생성자, getter, setter, toString() 정의
	public Student() {}
	public Student(String name, int age, Computer notebook) {
		super();
		this.name = name;
		this.age = age;
		this.notebook = notebook;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Computer getNotebook() {
		return notebook;
	}
	public void setNotebook(Computer notebook) {
		this.notebook = notebook;
	}
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", notebook=" + notebook + "]";
	}
}
