package com.edu.springboot.jdbc;

import lombok.Data;

// musthave 계정의 member 테이블의 컬럼과 동일하게 작성
@Data
public class MemberDTO {

	private String id;
	private String pass;
	private String name;
	private String regidate;
}
