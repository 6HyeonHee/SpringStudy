package com.edu.springboot.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
 컨트롤러와 DAO 사이에서 매개 역할을 하는 인터페이스.
 JdbcTemplate에서는 @Service를 사용했지만 mybatis에서는 @Mapper를 사용한다.
 컨트롤러에서 인터페이스에 정의된 추상메서드를 호출하면 연결된 Mapper의 특정
 엘리먼트가 호출되어 실행되는 구조를 가진다.
 */
@Mapper
public interface IMemberService {
	
	// 회원목록(검색X)
//	public List<MemberDTO> select();
	// 회원목록(검색O)
	public List<MemberDTO> select(MemberDTO memberDTO);
	
	// 회원등록 : request 내장객체를 통해 받은 파라미터를 전달한다.
	public int insert(String id, String pass, String name);
	
	/*
	 회원정보가져오기 : 파라미터를 받은 후 @Param 어노테이션으로 Mapper에서
	 	사용할 파라미터명을 지정한다. 즉 id로 받은 후 _id로 변경한다.
	 */
	public MemberDTO selectOne(@Param("_id") String id);
	// 수정처리 : 파라미터가 저장된 Map을 매개변수로 전달한다.
	public int update(Map<String, String> paramMap);
	// 삭제처리
	public int delete(String id);
}
