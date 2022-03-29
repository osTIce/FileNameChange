package com.change.name.file.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.change.name.file.service.ChangeFileNameDTO;
import com.change.name.file.service.ChangeFileNameService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("changeFileNameService")
public class ChangeFileNameServiceImpl extends EgovAbstractServiceImpl implements ChangeFileNameService{

	@Resource(name="changeFileNameDAO")
	private ChangeFileNameDAO changeFileNameDAO;

	// 엑셀 업로드 파일 DB에 삽입
	public int fileuploadinsert(ChangeFileNameDTO changeFileNameDTO) {
		return changeFileNameDAO.fileuploadinsert(changeFileNameDTO);
	}
	
	// 엑셀 서식 업로드 파일 리스트 확인 화면
	public List<ChangeFileNameDTO> excelfilelist() {
		return changeFileNameDAO.excelfilelist();
	}
	
}
