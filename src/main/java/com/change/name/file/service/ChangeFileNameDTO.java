package com.change.name.file.service;

public class ChangeFileNameDTO {
	
	// 파일 번호
	int no;
	
	// 파일 이름
	String name;
	
	// 파일 업로드 날짜
	String uploaddate;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	
}
