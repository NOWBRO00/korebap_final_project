package com.korebap.app.biz.imageFile;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileDTO { //상품 파일
	private int file_num; // 파일 번호(PK)
	private String file_dir; // 파일 경로(URL)
	private int file_board_num; // 글 번호(FK)
	private int file_product_num; // 글 번호(FK)
	
	// DTO에만 있는 멤버변수
	private String file_condition; 
	private List<MultipartFile> files;
	
	

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public int getFile_num() {
		return file_num;
	}

	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}

	public String getFile_dir() {
		return file_dir;
	}

	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}

	public int getFile_board_num() {
		return file_board_num;
	}

	public void setFile_board_num(int file_board_num) {
		this.file_board_num = file_board_num;
	}

	public int getFile_product_num() {
		return file_product_num;
	}

	public void setFile_product_num(int file_product_num) {
		this.file_product_num = file_product_num;
	}

	public String getFile_condition() {
		return file_condition;
	}

	public void setFile_condition(String file_condition) {
		this.file_condition = file_condition;
	}

	@Override
	public String toString() {
		return "ImageFileDTO [file_num=" + file_num + ", file_dir=" + file_dir + ", file_board_num=" + file_board_num
				+ ", file_product_num=" + file_product_num + ", file_condition=" + file_condition + ", files=" + files
				+ "]";
	}

	
	
	
}