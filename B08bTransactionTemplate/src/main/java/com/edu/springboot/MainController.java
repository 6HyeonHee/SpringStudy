package com.edu.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.springboot.jdbc.ITicketService;
import com.edu.springboot.jdbc.PayDTO;
import com.edu.springboot.jdbc.TicketDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	
	@Autowired
	ITicketService dao;
	
	@Autowired
	TransactionTemplate transcationTemplate;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// 티켓 구매 페이지 맵핑
	@GetMapping(value="/buyTicket.do")
	public String buy1() {
		return "buy";
	}
	// 구매처리
	@PostMapping(value="/buyTicket.do")
	public String buy2(TicketDTO ticketDTO, PayDTO payDTO,
			HttpServletRequest req, Model model) {
		// 구매에 성공했을 때 포워드할 View의 경로
		String viewPath = "success";
		
		/* 템플릿을 사용하면 기존의 Status 인스턴스를 필요없으므로 삭제한다. */
		
		try {
			/*
			 템플릿 내에 익명클래스를 통해 오버라이딩 된 메서드로 모든 비즈니스 로직을
			 옮겨주면된다. 템플릿을 사용하면 commit, rollback을 별도로 기술하지
			 않아도 자동으로 트랜젝션 처리된다.
			 */
			transcationTemplate.execute(new TransactionCallbackWithoutResult() {	
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 1.DB처리1 : 구매금액에 관련된 입금처리. 구매장수 * 10000원
					payDTO.setAmount(ticketDTO.getT_count() * 10000);
					int result1 = dao.payInsert(payDTO);
					if(result1==1) System.out.println("transaction_pay 입력성공");
					
					// 2. 비즈니스 로직 처리(의도적인 에러발생 부분)
					String errFlag = req.getParameter("err_flag");
					if(errFlag!=null) {
						int money = Integer.parseInt("100원");
					}
					
					/* 3.DB처리2 : 구매한 티켓 매수에 대한 처리로 6장 이상이면 check 제약조건
					위배로 DB 에러가 발생된다. */
					int result2 = dao.ticketInsert(ticketDTO);
					if(result2 == 1) System.out.println("transaction_ticket 입력성공");
					
					// DTO를 모델에 저장
					model.addAttribute("ticketDTO", ticketDTO);
					model.addAttribute("payDTO", payDTO);
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
			viewPath="error";
		}
		return viewPath;
	}
}
