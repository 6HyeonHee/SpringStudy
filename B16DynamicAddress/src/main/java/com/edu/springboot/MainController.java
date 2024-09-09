package com.edu.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.springboot.jdbc.AddressDTO;
import com.edu.springboot.jdbc.IAddressService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

	// Mapper 호출을 위한 자동 주입
	@Autowired
	IAddressService dao;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	/*
	 동적 셀렉트 페이지로 진입시에 시·도 부분은 밀 SELECT한 후
	 Mapper에서 <select> 태그에 포함시켜 놓아야 한다.
	 */
	@GetMapping("/dynamicAddress.do")
	public String address1(Model model) {
		model.addAttribute("sidoLists", dao.selectSido());
		return "address";
	}
	
	/*
	 선택한 시·도를 parameter로 전달하면 DTO가 받은 후 Mapper를 호출한다.
	 반환된 결과는 List이므로 이를 JSON 객체로 출력하기 위해 Map 컬렉션을
	 생성한 후 result 키값에 List를 추가해준다.
	 */
	@GetMapping("/getGugun.do")
	@ResponseBody
	public Map<String, Object> address2(AddressDTO addressDTO) {
		List<AddressDTO> gugunLists = dao.selectGugun(addressDTO);
		Map<String, Object> maps = new HashMap<>();
		maps.put("result", gugunLists);
		return maps;
	}
}
