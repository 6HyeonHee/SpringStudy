package com.edu.springboot.bean1;

public class Notebook {
	// 멤버변수, 생성자, toString() 메서드 정의
	private String cpu;
	
	public Notebook(String cpu) {
		this.cpu = cpu;
	}

	@Override
	public String toString() {
		return "Notebook [cpu=" + cpu + "]";
	}
	
}
