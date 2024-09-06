package com.edu.springboot.restboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class BoardRestController {

	// JDBC 작업을 위해 Bean 자동 주입
	@Autowired
	IBoardService dao;
	
	// 페이징을 위한 설정값(properties 파일) - 아래 하드코딩된 부분을 바꾼 부분
	@Value("#{myprops['board.pageSize']}")
	private int pageSize; // 한 페이지당 게시물 수
	
	@GetMapping("/restBoardList.do")
	public List<BoardDTO> restBoardList(ParameterDTO parameterDTO) {
		// 한 페이지에 출력할 게시물의 수(하드코딩)
//		int pageSize = 10;
		// 페이지 번호
		int pageNum = parameterDTO.getPageNum() == null ? 
				1 : Integer.parseInt(parameterDTO.getPageNum());
		// 게시물의 구간 계산
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		// DTO에 저장
		parameterDTO.setStart(start);
		parameterDTO.setEnd(end);
		// DAO의 메서드 호출
		List<BoardDTO> boardList = dao.list(parameterDTO);
		// List를 즉시 반환해서 JSON 배열 형식으로 출력한다.
		return boardList;
	}
	
	@GetMapping("/restBoardSearch.do")
	public List<BoardDTO> restBoardSearch(HttpServletRequest req,
			ParameterDTO parameterDTO) {
		// 검색어는 스페이스로 구분하여 2개 이상 전송되므로 별도로 처리한다.
		if (req.getParameter("searchWord") != null) {
			// 전송된 검색어를 스페이스를 통해 split한다.
			String[] sTxtArray = req.getParameter("searchWord")
					.split(" ");
			// 저장된 모든 데이터를 삭제한다.
			parameterDTO.getSearchWord().clear();
			// 앞에서 반환된 String 배열의 크기만큼 반복
			for (String str : sTxtArray) {
				// 각 검색어를 하나씩 추가한다.
				System.out.println(str);
				parameterDTO.getSearchWord().add(str);
			}
		}
		// Mapper의 search 메서드를 호출한다.
		List<BoardDTO> searchList = dao.search(parameterDTO);
		return searchList;
	}
	
	@GetMapping("/restBoardView.do")
	public BoardDTO restBoardView(ParameterDTO parameterDTO) {
		BoardDTO boardDTO = dao.view(parameterDTO);
		return boardDTO;
	}
	
	
	// 내 버젼
//	@PostMapping("/restBoardWrite.do")
//	public List<BoardDTO> restBoardWritePost(BoardDTO boardDTO) {
//		
//		int result = dao.write(boardDTO);
//		System.out.println("글쓰기 결과 :" + result);
//		
//		ParameterDTO parameterDTO = new ParameterDTO(); 
//		int pageNum = parameterDTO.getPageNum() == null ? 
//				1 : Integer.parseInt(parameterDTO.getPageNum());
//		// 게시물의 구간 계산
//		int start = (pageNum - 1) * pageSize + 1;
//		int end = pageNum * pageSize;
//		// DTO에 저장
//		parameterDTO.setStart(start);
//		parameterDTO.setEnd(end);
//		// DAO의 메서드 호출
//		List<BoardDTO> boardList = dao.list(parameterDTO);
//		// List를 즉시 반환해서 JSON 배열 형식으로 출력한다.
//		return boardList;
//	}
	// 선생님 버젼
	@PostMapping("/restBoardWrite.do")
	public Map<String, Integer> restBoardWrite(BoardDTO boardDTO) {
		
		int result = dao.write(boardDTO);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
}
