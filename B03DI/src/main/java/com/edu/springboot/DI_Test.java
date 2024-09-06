package com.edu.springboot;

class Persons {
	String name;
	int age;
	/*
	 생성자가 public이면 외부접근이 가능하므로 인스턴스를 생성할 수 있다.
	 하지만 private으로 변경하면 외부에서 인스턴스 생성을 할 수 없다.
	 */
	public Persons() {
		System.out.println("public 생성자 호출됨");
	}
}
public class DI_Test {
	/*
	 강한결합(독립성 낮음) : new를 통해 직접 인스턴스를 생성한다.
	 		이 경우 객체간의 결합도가 높기 때문에 Persons 클래스의
	 		변화에 직접적인 영향을 받게된다.
	 */
	public static void aPerson() {
		// 생성자를 private으로 변경하는 순간 에러가 발생한다.
		Persons person1 = new Persons();
		person1 .name = "손오공";
		person1.age = 10;
	}
	
	/*
	 약한결합(독립성 높음) : 미리 생성된 객체(bean)를 주입(Injection) 받아
	 		사용한다. 결합도가 낮아지기 때문에 Persons 클래스에 변화가 생기더라도
	 		직접적인 영향을 받지 않는다. 또한 코드도 간결해진다.
	 */
	public static void bPerson(Persons person2) {
		person2.name = "유비";
		person2.age = 45;
	}
	
	/*
	 따라서 DI(의존성주입)의 목적은 객체간의 독립성을 높이고, 결합도를 낮춰
	 전체적인 프로그램을 간결하게 만드는 것에 있다.
	 */

}
