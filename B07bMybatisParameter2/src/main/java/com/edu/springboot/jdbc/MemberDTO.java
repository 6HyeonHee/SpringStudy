package com.edu.springboot.jdbc;

import java.util.List;

import lombok.Data;

@Data
public class MemberDTO {
	
	private String id;
	private String pass;
	private String name;
	private String regidate;
	
	private List<String> searchField;
	private String searchKeyword;
}
