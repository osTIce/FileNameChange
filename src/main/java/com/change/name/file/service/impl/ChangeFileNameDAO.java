package com.change.name.file.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.change.name.file.service.ChangeFileNameDTO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("changeFileNameDAO")
public class ChangeFileNameDAO extends EgovAbstractMapper{
	
	// 엑셀 업로드 파일 DB에 삽입
	public int fileuploadinsert(ChangeFileNameDTO changeFileNameDTO) {
		return insert("changeFileNameDAO.fileuploadinsert", changeFileNameDTO);
	}
	
	// 엑셀 서식 업로드 파일 리스트 확인 화면
	public List<ChangeFileNameDTO> excelfilelist() {
		return selectList("changeFileNameDAO.excelfilelist");
	}
	
}
