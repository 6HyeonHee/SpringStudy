package com.edu.springboot;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springboot.myfile.IMyFileService;
import com.edu.springboot.myfile.MyFileDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import utils.MyFunctions;

@Controller
public class MainController {
	@Autowired
	IMyFileService dao;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	// 싱글파일 업로드 페이지에 대한 매핑
	@GetMapping("/fileUpload.do")
	public String fileUpload() {
		return "fileUpload";
	}
	// 싱글파일 업로드 처리
	@PostMapping("/uploadProcess.do")
	public String uploadProcess(HttpServletRequest req, Model model, MyFileDTO myFileDTO) {
		try {
			// 업로드 디렉토리의 물리적 경로를 얻어온다.
			String uploadDir = ResourceUtils
					.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로 : " + uploadDir);
			
			Part part = req.getPart("ofile");
			String partHeader = part.getHeader("content-disposition");
			System.out.println("partHeader=" + partHeader);
			String[] phArr = partHeader.split("filename=");
			String originalFileName = phArr[1].trim().replace("\"", "");
			if (!originalFileName.isEmpty()) {
				part.write(uploadDir + File.separator + originalFileName);
			}
			
			// 서버에 저장된 파일명을 UUID를 통해 생성된 이름으로 변경한다.
			String savedFileName =
					MyFunctions.renameFile(uploadDir, originalFileName);
			
			// DB입력
			myFileDTO.setOfile(originalFileName);
			myFileDTO.setSfile(savedFileName);
			int result = dao.insertFile(myFileDTO);
			if(result==1) System.out.println("입력성공");
			else System.out.println("입력실패");

			
		} catch(Exception e) {
			System.out.println("업로드 실패");
		}
		
		// View로 포워드 한다.
		return "redirect:fileUploadOk.do";
	}
	@GetMapping("/fileuploadOk.do")
	public String fileUploadOk(Model model) {
		model.addAttribute("fileRows", dao.selectFile());
		return "fileUploadOk";
	}
	
	@GetMapping("/multiFileUpload.do")
	public String multiFileUpload() {
		return "multiFileUpload";
	}
	// 파일업로드 처리
	@PostMapping("multiUploadProcess.do")
	public String multiUploadProcess(HttpServletRequest req, Model model) {
		try {
			// 물리적 경로 얻어오기
			String uploadDir = ResourceUtils
					.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로 : " + uploadDir);
			
			/*
			 파일명 저장을 위해 Map 생성. Key는 원본파일명, Value는 서버에 저장된
			 파일명을 저장한다.
			 */
			Map<String, String> saveFileMaps = new HashMap<>();
			
			// 2개 이상의 파일이므로 getParts()를 통해 폼값을 얻어온다.
			Collection<Part> parts = req.getParts();
			for(Part part : parts) {
				/* 여러 폼값 중 파일인 경우에만 업로드 처리를 위해 하위 문장을
				실행한다. 나머지는 반복문의 처음으로 돌아간다. */
				if(!part.getName().equals("ofile"))
					continue;
				
				// 헤더값을 통해 파일명을 얻어온다.
				String partHeader = part.getHeader("content-disposition");
				System.out.println("partHeader=" + partHeader);
				String[] phArr = partHeader.split("filename=");
				String originalFileName = phArr[1].trim().replace("\"", "");
				// 원본 파일명으로 서버에 저장한다.
				if(!originalFileName.isEmpty()) {
					part.write(uploadDir + File.separator + originalFileName);
				}
				// 저장된 파일명을 UUID로 생성한 새로운 파일명으로 변경한다.
				String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
				// 원본 및 변경된 파일명을 Map에 저장한다.
				saveFileMaps.put(originalFileName, savedFileName);
			}
			// View로 전달하기 위해 Model에 저장한다.
			model.addAttribute("saveFileMaps", saveFileMaps);
			model.addAttribute("title", req.getParameter("title"));
			model.addAttribute("cate", req.getParameterValues("cate"));
		} catch(Exception e) {
			System.out.println("업로드 실패");
		}
		
		return "multiFileUploadOk";
	}
	
	
	
}
