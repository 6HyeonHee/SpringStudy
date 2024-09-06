package com.edu.springboot.restboard;

import lombok.Data;

// 모델1 방식의 회원제 게시판에서 생성한 board테이블 사용.
@Data
public class BoardDTO {
	private String num;
	private String title;
	private String content;
	private String id;
	private java.sql.Date postdate;
	private String visitcount;
}
