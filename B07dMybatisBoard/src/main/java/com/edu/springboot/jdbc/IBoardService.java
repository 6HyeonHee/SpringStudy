package com.edu.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
 Controller와 Mybatis Mapper 사이에서 매개역할을 하는 인터페이스로
 @Mapper 어노테이션을 부착한다.
 컨트롤러는 인터페이스의 추상메서드를 호출하고, 이를 통해 Mapper의 특정
 엘리먼트가 호출되어 쿼리문이 실행되는 구조를 가지게된다.
 */
@Mapper
public interface IBoardService {
	// 목록 : 게시물의 갯수를 카운트(커멘드 객체 사용)
	public int getTotalCount(ParameterDTO parameterDTO);
	// 목록 : 한페이지에 출력할 게시물을 인출하여 반환(커맨드 객체 사용)
	public ArrayList<BoardDTO> listPage(ParameterDTO parameterDTO);
	/*
	 작성 : 폼값을 받은 후 어노테이션으로 통해 이름을 변경하여 Mapper로 전달한다.
	 	Mapper에서는 변경된 이름으로 인파라미터를 설정한다.
	 */
	public int write(@Param("_name") String name,
			@Param("_title") String title,
			@Param("_content") String content);
	// 열람(커맨드 객체 사용)
	public BoardDTO view(BoardDTO boardDTO);
	// 수정(커맨드 객체 사용)
	public int edit(BoardDTO boardDTO);
	// 삭제
	public int delete(String idx);
	// 조회수 증가
	public int visitCountPlus(BoardDTO boardDTO);
}