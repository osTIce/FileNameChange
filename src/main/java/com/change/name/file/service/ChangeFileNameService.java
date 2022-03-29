package com.change.name.file.service;

import java.util.List;

public interface ChangeFileNameService {
	
	// 엑셀 업로드 파일 DB에 삽입
	public int fileuploadinsert(ChangeFileNameDTO changeFileNameDTO);
	
	// 엑셀 서식 업로드 파일 리스트 확인 화면
	public List<ChangeFileNameDTO> excelfilelist();
	
}
