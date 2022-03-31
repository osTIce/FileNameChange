package com.change.name.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
		
		Map<String, String> cellMap = new HashMap<String, String>();
		
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
			
			// 엑셀 앞에 추가할 이름 값 가져오기
			String frontValue = exceltypecheck(excelFileUploadPath, 1, 3);
			System.out.println("FileController-allfilenamechange-EXCEL = front name value :"+frontValue);
			
			// 엑셀 양식 뒤에 추가할 이름 가져오기
			String backValue = exceltypecheck(excelFileUploadPath, 1, 4);
			System.out.println("FileController-allfilenamechange-EXCEL = back name value :"+backValue);
			
			// 행 개수만큼 반복문 돌려서 행 값 가져오기 (i가 1부터 시작하는 이유는 0번째 index는 서식을 의미하기 때문에)
			for(int i = 1; i < rows; i++) {
				
				XSSFRow row = sheet.getRow(i);
				
				// getPhysicalNumberOfRows 함수가 row의 수를 캐치하여 리턴해주는데, 해당 값이 이상하게 리턴이 되는 부분도 있어서 row 값이 null인지 아닌지 확인해야 한다.
				if(row == null) {
					break;
				}
				
				// 해당 행 셀 개수 확인
				int cells = row.getPhysicalNumberOfCells();
				String key = "";
				String value = "";
				System.out.println("FileController-allfilenamechange-EXCEL = cells :"+cells);
				int j = 0;
				
				for(; j < cells; j++) {
					XSSFCell cell =	row.getCell(j);
					
					if(cell == null) {
						break;
					}
					
					// 원래 기존에는 타입 체크할 때 CELL.TYPE.FORMULA 식으로 작성해야하는데, poi 버전이 업데이트 되면서 아래와 같이 바뀐 듯 (정확히는 잘 모르겠음)
					// j가 0이면 map의 key값에 들어가고, j가 1이면 map의 value값에 들어감.
					if(cell.getCellType() == CellType.FORMULA) {
						if(j == 0) {
							key=cell.getCellFormula();
						}else if(j == 1) {
							value=cell.getCellFormula();
						}
					}else if(cell.getCellType() == CellType.NUMERIC) {
						if(j == 0) {
							key=cell.getNumericCellValue() + "";
						}else if(j == 1) {
							value=cell.getNumericCellValue() + "";
						}
					}else if(cell.getCellType() == CellType.STRING) {
						if(j == 0) {
							key=cell.getStringCellValue();
						}else if(j == 1) {
							value=cell.getStringCellValue();
						}
					}else if(cell.getCellType() == CellType.BLANK) {
						if(j == 0) {
							key=cell.getBooleanCellValue() + "";
						}else if(j == 1) {
							value=cell.getBooleanCellValue() + "";
						}
					}else if(cell.getCellType() == CellType.ERROR) {
						if(j == 0) {
							key=cell.getErrorCellValue() + "";
						}else if(j == 1) {
							value=cell.getErrorCellValue() + "";  
						}
					}
					
					if(key != "" & value != "") {
						
						System.out.println("FileController-allfilenamechange-EXCEL = key :"+ key);
						System.out.println("FileController-allfilenamechange-EXCEL = value :"+ value);
						
						cellMap.put(key, value);
					}
					
				}
				
			}
			
			// FileInputStream 닫기
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * 위에는 엑셀 양식 파일 읽어오는 부분
		 * 아래는 변경하려는 파일 이름 읽어오는 부분
		 * */
		
		// 문자열 값을 숫자로 변환
		int fileLen = Integer.valueOf(fileLength);
		
		ArrayList<String> list = new ArrayList<String>();
		
		// formData로 전달한 데이터 ArrayList에 담기
		for(int i = 0; i < fileLen; i++) {
		
			MultipartFile uploadFile = mRequest.getFile("fileName" + i);
			
			// 파일 이름 가져오기
			String fileName = uploadFile.getOriginalFilename();
			System.out.println("FileController-allfilenamechange-EXCEL = fileName :"+fileName);
			
			// 마지막 "."이 있는 인덱스 숫자 반환
			int idx = fileName.lastIndexOf(".");
			
			// 0 ~ idx 숫자까지 (파일 명만 가져오기)
			String fileNameChange = fileName.substring(0, idx);
			System.out.println("FileController-allfilenamechange-EXCEL = fileNameChange :"+fileNameChange);
			
			// 해당 파일 확장자 구하기
			String fileNameChangeExt = fileName.substring(idx);
			System.out.println("FileController-allfilenamechange-EXCEL = fileNameChangeExt :"+fileNameChangeExt);
			
			list.add(fileNameChange);
			
			// 향상된 반복문 (Map에 저장된 키값 가져오기)
			for(String key : cellMap.keySet()) {
				
				boolean listCheck = list.get(i).contains(key);
				
				if(listCheck == true) {
					String checkValue = cellMap.get(key);
					System.out.println("FileController-allfilenamechange-EXCEL = get checkValue :"+checkValue);
					String replaceChangeValue = list.get(i).replace(key, checkValue);
					System.out.println("FileController-allfilenamechange-EXCEL = get replaceChangeValue :"+replaceChangeValue);
				}
					
			}
			
		}
		
	}
	
	// 엑셀 양식 받아오는 값 타입 설정 함수 (2번 다 적기에는 양이 많아서 따로 함수 작성)
	public String exceltypecheck(String path, int rowCheck, int cellCheck) {
		
		String value = "";
		
		try {
			
			FileInputStream file = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow row = sheet.getRow(rowCheck);
			XSSFCell cell = row.getCell(cellCheck);
			
			switch (cell.getCellType()) {
			case FORMULA:
				value=cell.getCellFormula();
				break;
			case NUMERIC:
				value=cell.getNumericCellValue() + "";
				break;
			case STRING:
				value=cell.getStringCellValue();
				break;
			case BLANK:
				value=cell.getBooleanCellValue() + "";
				break;
			case ERROR:
				value=cell.getErrorCellValue() + "";
				break;
			default:
				value = "";
			}
			
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
		
	}
	
}
