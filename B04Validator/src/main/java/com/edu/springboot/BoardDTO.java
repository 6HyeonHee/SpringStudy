package com.edu.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 롬북을 통해 멤버변수에 대한 getter, setter, 기본생성자 등을 일괄적으로
 정의할 수 있다. 또한 Object 클래스에서 제공되는 toString과 같은
 메서드도 자동으로 오버라이딩된다.
 */
@Data
// 인수를 가진 생성자를 추가한다.
@AllArgsConstructor
// 기본생성자를 추가한다.
@NoArgsConstructor
public class BoardDTO {

	private int idx;
	private String userid;
	private String title;
	private String content;
}
