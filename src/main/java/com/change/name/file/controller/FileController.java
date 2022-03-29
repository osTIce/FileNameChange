package com.change.name.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.change.name.file.service.ChangeFileNameDTO;
import com.change.name.file.service.ChangeFileNameService;

import oracle.jdbc.internal.OracleConnection.XSSessionModeFlag;

@Controller
public class FileController {

	@Resource(name = "changeFileNameService")
	private ChangeFileNameService changeFileNameService;
	
	// 홈 화면
	@GetMapping({ "/home.do", "/" })
	public String home() {
		return "index";
	}

	// 엑셀 파일 통해 이름 변경 화면
	@GetMapping("/file/excelchange.do")
	public String excelChange() {
		return "excelChange";
	}

	// 엑셀 서식 파일 다운로드
	@ResponseBody 
	@GetMapping("/file/excelfiledown.do")
	public void excelfiledown(HttpServletRequest request) {

		// ExcelNameChange.xlsx 원본 파일 경로
		String oldPath = request.getSession().getServletContext().getRealPath("/file");
		System.out.println("FileController-excelfiledown = oldPath: "+oldPath);

		// 사용하는 사용자 이름 확인
		String userName = System.getProperty("user.name");
		System.out.println("FileController-excelfiledown = userName: "+userName);

		// ExcelNameChange.xlsx 원본 파일 다운 받는 새로운 경로 // Downloads로 하드코딩하여 고정
		String newPath = "C:/Users/" + userName + "/Downloads";
		System.out.println("FileController-excelfiledown = newPath: "+newPath);

		// 원본 파일 이름 및 새로운 파일 이름 하드코딩
		final String fileName = "ExcelNameChange.xlsx";

		File file = new File(oldPath + File.separator + fileName);
		byte[] b = new byte[4096];
		try {

			// 해당 파일 읽어오는 것 (복사)
			FileInputStream fis = new FileInputStream(file);

			// 해당 파일로 생성하는 것 (붙여넣기)
			FileOutputStream fos = new FileOutputStream(newPath + File.separator + fileName);
			int read = 0;

			// -1이 아닐 때까지 반복문 실행, -1이 되면 더 이상 읽을 내용이 없다는 것
			while ((read = fis.read(b)) != -1) {

				// b 매개변수 즉 4096 바이트 단위로
				// 0부터 끝까지 읽어 새로운 파일로 생성.
				fos.write(b, 0, read);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 엑셀 서식 파일 작성 후 완료된 엑셀 파일 업로드
	@ResponseBody
	@PostMapping("/file/exceluploadfile.do")
	public void exceluploadfile(MultipartHttpServletRequest mRequest, HttpServletRequest request, ChangeFileNameDTO changeFileNameDTO) {
		
		// 해당하는 이름의 파일 객체를 가져옮.
		MultipartFile uploadFile = mRequest.getFile("uploadFile");
		System.out.println("FileController-exceluploadfile = uploadFile :"+uploadFile);
		
		// 해당하는 파일 객체의 실제 이름을 확인
		String originFileName = uploadFile.getOriginalFilename();
		System.out.println("FileController-exceluploadfile = originFileName :"+originFileName);
		
		// 해당하는 파일 객체의 크기를 확인
		long fileSize = uploadFile.getSize();
		System.out.println("FileController-exceluploadfile = fileSize :" + fileSize);
		
		// 업로드 해야 하는 폴더의 경로
		String oldPath = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("FileController-exceluploadfile = oldPath :" + oldPath);
		
		try {
			
		File fileUpload = new File(oldPath + File.separator + originFileName);
		File fileUploadPath = new File(oldPath);
		
		// 해당하는 경로에 폴더가 존재하는지 유무 확인
		if(!fileUploadPath.exists()) {
			
			// 존재하지 않을 때 폴더 생성
			fileUploadPath.mkdir();
		}
		
		// 해당 경로로 파일 붙여넣기
		uploadFile.transferTo(fileUpload);
		
		// 파일 이름 담기
		changeFileNameDTO.setName(originFileName);
		
		// 파일 정보 DB에 담기
		int uploadCheck = changeFileNameService.fileuploadinsert(changeFileNameDTO);
		System.out.println(uploadCheck == 1 ? "FileController-exceluploadfile = fileNameCheck :upload succes" : "FileController-exceluploadfile = fileNameCheck :upload failed");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 엑셀 서식 업로드 파일 리스트 확인 화면
	@GetMapping("/file/excelfilelist.do")
	public String excelfilelist(Model model) {
		
		// 파일 리스트 불러오기
		List<ChangeFileNameDTO> file = changeFileNameService.excelfilelist();
		
		model.addAttribute("fileList", file);
		
		return "excelFileList";
	}
	
	// 파일 이름 일괄 변경
	@ResponseBody
	@PostMapping("/file/allfilechange.do")
	public void allfilenamechange(MultipartHttpServletRequest mRequest, HttpServletRequest request) {
		
		// 파일 리스트 길이 가져옮
		String fileLength = mRequest.getParameter("fileLength");
		
		// excel 파일 이름 가져옮 (trim 메서드로 공백 제거)
		String excelFileName = mRequest.getParameter("excelFileName").trim();
		System.out.println("FileController-allfilenamechange = excelFileName :"+excelFileName);
		
		// 업로드 된 폴더의 경로
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("FileController-allfilenamechange = uploadPath :" + uploadPath);
		
		// 업로드 된 폴더의 경로와 양식 엑셀 파일 이름을 합침
		String excelFileUploadPath = uploadPath + "\\" + excelFileName;
		System.out.println("FileController-allfilenamechange = excelFileUploadPath :"+ excelFileUploadPath);
		
		// 엑셀 양식 파일 읽어오기 (apache.poi 라이브러리 사용)
		try {
			FileInputStream file = new FileInputStream(excelFileUploadPath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			// 시트를 가져온다 (index 값이 0이기 때문에 0 번째 시트를 가져옮)
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			// 가져온 시트 이름 확인
			String sheetName = sheet.getSheetName();
			System.out.println("FileController-allfilenamechange-EXCEL = sheetName :"+sheetName);
			
			// 해당 시트 행 개수 확인
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("FileController-allfilenamechange-EXCEL = rows :"+rows);
			
			// 행 개수만큼 반복문 돌려서 행 값 가져오기
			for(int i = 0; i < rows; i++) {
				
				XSSFRow row = sheet.getRow(i);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * 위에는 엑셀 양식 파일 읽어오는 부분
		 * 아래는 변경하려는 파일 이름 읽어오는 부분
		 * */
		
		// 문자열 값을 숫자로 변환
		int fileLen = Integer.valueOf(fileLength);
		
		ArrayList<MultipartFile> list = new ArrayList<MultipartFile>();
		
		// formData로 전달한 데이터 ArrayList에 담기
		for(int i = 0; i < fileLen; i++) {
		
			MultipartFile uploadFile = mRequest.getFile("fileName" + i);
			list.add(uploadFile);
			
		}
		
	}
	
}
