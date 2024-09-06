package com.edu.springboot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.springboot.jdbc.BoardDTO;
import com.edu.springboot.jdbc.IBoardService;
import com.edu.springboot.jdbc.ParameterDTO;

import jakarta.servlet.http.HttpServletRequest;
import utils.PagingUtil;

@Controller
public class MainController {
	// DAO 호출을 위한 빈 자동 주입. 이 인터페이스를 통해 Mapper를 호출한다.
	@Autowired
	IBoardService dao;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@Value("#{boardprops['board.pageSize']}") 
	int pageSize;
	@Value("#{boardprops['board.blockPage']}") 
	int blockPage;
	
	@RequestMapping("/list.do")
	public String boardList(Model model, HttpServletRequest req,
			ParameterDTO parameterDTO) {
		/* 매개변수는 View로 전달할 데이터 저장을 위한 Model, 요청을 받아
		 처리가힉 위한 request내장객체, 커멘드객체로 사용할 DTO를 추가한다. */
		
		// 게시물의 갯수 카운트(검색어가 있는 경우 DTO 객체에 자동으로 저장됨)
		int totalCount = dao.getTotalCount(parameterDTO);
		
		// 페이징을 위한 설정값(하드코딩)
		// 페이지 당 출력할 게시물의 개수
//		int pageSize = 2;
		// 한 블럭당 출력할 페이지번호의 갯수
//		int blockPage = 2;
		
		int pageSize = this.pageSize;
		int blockPage = this.pageSize;
		System.out.println(this.pageSize + "-" + this.pageSize);
		
		/* 목록에 첫 진입시에는 페이지 번호가 없으므로 무조건 1로 설정하고, 파라미터로
		 전달된 페이지 번호가 있다면 받은 후 정수로 변경한다. */
		int pageNum = (req.getParameter("pageNum")==null
				|| req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		// 현재 페이지에 출력할 게시물의 구간을 계산한다.
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		// 계산의 결과는 DTO에 저장한다.
		parameterDTO.setStart(start);
		parameterDTO.setEnd(end);
		
		// View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		// DB에서 인출한 게시물의 목록을 Model에 저장
		ArrayList<BoardDTO> lists = dao.listPage(parameterDTO);
		model.addAttribute("lists", lists);
		
		// 게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
				PagingUtil.pagingImg(totalCount, pageSize, 
						blockPage, pageNum,
						req.getContextPath()+"/list.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		return "list";
	}
	
	// 입력1 : 작성페이지 매핑
	@GetMapping("/write.do")
	public String boardWriteGet(Model model) {
		return "write";
	}
	// 입력2 : 사용자가 작성한 값으로 입력 처리
	@PostMapping("/write.do")
	public String boardWritePost(Model model, HttpServletRequest req) {
		// request 내장객체를 통해 폼값을 받음
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		// 서비스 인터페이스 호출
		int result = dao.write(name, title, content);
		System.out.println("글쓰기 결과:" + result);
		return "redirect:list.do";
	}
	
	// 열람 : 파라미터로 전달되는 idx를 커멘드객체를 통해 받음.
	@RequestMapping("/view.do")
	public String boardView(Model model, BoardDTO boardDTO) {
		
		dao.visitCountPlus(boardDTO);
		boardDTO = dao.view(boardDTO);
		// 가져온 레코드에서 내용부분은 줄바꿈 처리한다.
		boardDTO.setContent(boardDTO.getContent().replace("\r\n", "<br/>"));
		// 모델에 저장 후 JSP로 포워드
		model.addAttribute("boardDTO", boardDTO);
		return "view";
	}
	
	// 수정1 : 기존 내용을 읽어와서 수정폼에 설정
	@GetMapping("/edit.do")
	public String boardEditGet(Model model, BoardDTO boardDTO) {
		// 열람에서 사용했던 메서드를 그대로 호출
		boardDTO = dao.view(boardDTO);
		model.addAttribute("boardDTO", boardDTO);
		return "edit";
	}
	// 수정2 : 사용자가 입력한 내용을 전송하여 update처리
	@PostMapping("/edit.do")
	public String boardEditPost(BoardDTO boardDTO) {
		// 수정 후 결과는 int형으로 반환.
		int result = dao.edit(boardDTO);
		System.out.println("글 수정 결과:" + result);
		// 수정이 완료되면 열람페이지로 이동. 일련번호가 파라미터로 전달됨.
		return "redirect:view.do?idx="+ boardDTO.getIdx();
	}
	
	// 삭제 : request내장객체를 통해 폼값 받음
	@PostMapping("/delete.do")
	public String boardDeletePost(HttpServletRequest req) {
		int result = dao.delete(req.getParameter("idx"));
		System.out.println("글삭제결과:" + result);
		// 삭제가 완료되면 목록으로 이동한다.
		return "redirect:list.do";
	}

}
