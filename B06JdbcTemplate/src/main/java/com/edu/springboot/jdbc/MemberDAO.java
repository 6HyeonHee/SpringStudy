package com.edu.springboot.jdbc;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

/*
 이 어노테이션도 스프링 컨테이너 시작시 자동으로 빈이 생성된다.
 @Controller, @Service와 동일한 역할을 한다.
 */
@Repository
public class MemberDAO implements IMemberService {

	/*
	 IMemberService 인터페이스를 구현했으므로 정의된 추상메서드는
	 일괄적으로 오버라이딩 해야한다. 컨트롤러에서 서비스 인터페이스를 통해
	 DAO의 각 메서드를 호출하게 된다.
	 (상속이 되면 부모의 추상메서드를 통해 오버라이딩 된 자식의 메서드를 호출할 수 있다.)
	 */
	
	/*
	 JDBC 작업을 위해 자동주입 받는다. JdbcTemplate 빈은 개발자가
	 직접 설정하지 않고, build.gradle에 의존설정이 되어있으므로
	 스프링 컨테이너가 자동으로 만들어준다.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 회원목록
	@Override
	public List<MemberDTO> select() {
		// 회원레코드를 가입일 기준으로 내림차순 정렬한 후 인출한다.
		String sql = "SELECT * FROM member ORDER BY regidate DESC";
		
		/*
		 query() 메서드를 통해 select문을 실행한다. 쿼리문 실행 후 반환되는
		 ResultSet은 RowMapper가 자동으로 반복하여 DTO에 저장하고, 이를
		 List에 추가해서 반환해준다. 즉, 레코드를 컬렉션에 저장하기 위한
		 반복적인 작업을 자동으로 수행해준다.
		 */
		return jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper<MemberDTO>(MemberDTO.class));
	}

	// 회원정보추가
	@Override
	public int insert(MemberDTO memberDTO) {
		/*
			 insert, update, delete와 같이 행의 변화가 생기는 쿼리문은
			 update() 메서드를 사용한다. 쿼리 실행 후 적용된 행의 갯수를
			 int형으로 반환한다.
		 */
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			/*
			 PreparedStatementCreator 인터페이스로 익명 클래스를 생성한 후
			 오버라이딩된 메서드 내에서 쿼리문을 실행하고 결과를 반환한다.
			 */
			@Override
			public PreparedStatement createPreparedStatement(Connection con) 
					throws SQLException {
				/*
				 인파라미터가 있는 insert쿼리문의 실행을 위해 PreparedStatement 인스턴스를
				 생성한 후 인파라미터를 설정하고 쿼리문을 실행한다.
				 */
				String sql = "INSERT INTO member (id, pass, name) "
						+ " values (?, ?, ?)";
				
				PreparedStatement psmt = con.prepareStatement(sql);
				
				psmt.setString(1, memberDTO.getId());
				psmt.setString(2, memberDTO.getPass());
				psmt.setString(3, memberDTO.getName());
				
				return psmt;
			}
		});
		// insert문 실행 후 반환받은 결과는 컨트롤러로 전달된다.
		return result;
	}

	// 회원정보수정1 : 회원정보인출
	@Override
	public MemberDTO selectOne(MemberDTO memberDTO) {
		// 인파라미터가 있는 쿼리문 작성
		String sql = "SELECT * FROM member WHERE id=?";
		/*
		 queryForObject 메서드는 반드시 하나의 결과가 있어야 하므로
		 없는 경우에는 예외가 발생하게 된다. 따라서 try~catch로 감싸는게 좋다.
		 */
		try {
			/*
		 queryForObject() 메서드는 하나의 결과를 반환하는 select
		 쿼리를 실행할 때 사용한다.
		 인자1 : 쿼리문
		 인자2 : RowMapper(인출한 ResultSet을 DTO에 자동으로 입력)
		 인자3 : 인파라미터에 설정할 값을 배열로 선언(순서대로 설정됨)
			 */
			memberDTO = jdbcTemplate.queryForObject(sql,
					new BeanPropertyRowMapper<MemberDTO>(MemberDTO.class),
					new Object[] { memberDTO.getId() });
		} catch(Exception e) {
			e.printStackTrace();
		}
		return memberDTO;
	}

	// 회원정보수정2 : 수정처리
	@Override
	public int update(MemberDTO memberDTO) {
		// 인파라미터가 있는 update 쿼리문
		String sql = "UPDATE member SET pass=?, name=? WHERE id=?";
		/*
		 PreparedStatementSetter 인터페이스의 익명클래스를 이용해서 인파라미터를
		 설정하고 쿼리문을 실행한다.
		 */
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, memberDTO.getPass());
				ps.setString(2, memberDTO.getName());
				ps.setString(3, memberDTO.getId());
			}
		});
		return result;
	}

	@Override
	public int delete(MemberDTO memberDTO) {
		String sql = "DELETE FROM member WHERE id=?";
		/* update 메서드를 통해 delete 쿼리문 실행. 인파라미터는 Object형
		 배열을 통해 설정한다. memberDTO */
		int result = jdbcTemplate.update(sql,
				new Object[] {memberDTO.getId()});
		return result;
	}
	
}
