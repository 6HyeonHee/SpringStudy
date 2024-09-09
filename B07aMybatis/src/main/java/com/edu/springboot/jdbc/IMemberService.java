package com.edu.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/*
 컨트롤러와 DAO 사이에서 매개 역할을 하는 인터페이스.
 JdbcTemplate에서는 @Service를 사용했지만 mybatis에서는 @Mapper를 사용한다.
 컨트롤러에서 인터페이스에 정의된 추상메서드를 호출하면 연결된 Mapper의 특정
 엘리먼트가 호출되어 실행되는 구조를 가진다.
 */
@Mapper
public interface IMemberService {
	
	// 회원목록
	public List<MemberDTO> select();
	// 회원등록
	public int insert(MemberDTO memberDTO);
	// 회원조회
	public MemberDTO selectOne(MemberDTO memberDTO);
	// 회원정보수정
	public int update(MemberDTO memberDTO);
	// 회원탈퇴(삭제)
	public int delete(MemberDTO memberDTO);
}
