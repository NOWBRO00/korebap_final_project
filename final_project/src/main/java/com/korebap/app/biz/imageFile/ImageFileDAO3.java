package com.korebap.app.biz.imageFile;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.korebap.app.biz.board.BoardDTO;

@Repository
public class ImageFileDAO3 {
	@Autowired
	private SqlSessionTemplate mybatis;

	public boolean insert(ImageFileDTO imageFileDTO) {
		int result = 0;
		System.out.println("====model.ImageFileDAO3.insert 시작");
		System.out.println("====model.ImageFileDAO3.insert imageFileDTO.getFile_condition() : [" + imageFileDTO.getFile_condition()+"]");
		
		if (imageFileDTO.getFile_condition() != null && imageFileDTO.getFile_condition().equals("BOARD_FILE_INSERT")) {
			result = mybatis.insert("imageFileDAO.insert_BOARD", imageFileDTO);
		} else if (imageFileDTO.getFile_condition() != null
				&& imageFileDTO.getFile_condition().equals("PRODUCT_FILE_INSERT")) {
			result = mybatis.insert("imageFileDAO.insert_PRODUCT", imageFileDTO);
		} else {
			System.err.println("====model.ImageFileDAO3.insert 컨디션 실패");
			return false;
		}

		if (result <= 0) {
			System.err.println("====model.ImageFileDAO3.insert 실패");
			return false;
		}
		System.out.println("====model.ImageFileDAO3.insert 종료");
		return true;
	}

	public boolean update(ImageFileDTO imageFileDTO) {
		int result;
		System.out.println("====model.ImageFileDAO3.update 시작");
		
		result = mybatis.update("update", imageFileDTO);
		if (result <= 0) {
			System.err.println("====model.ImageFileDAO3.update 실패");
			return false;
		}
		System.out.println("====model.ImageFileDAO3.update 종료");
		return true;
	}

	public boolean delete(ImageFileDTO imageFileDTO) {
		int result;
		System.out.println("====model.ImageFileDAO3.delete 시작");
		
		result = mybatis.delete("delete", imageFileDTO);
		if (result <= 0) {
			System.err.println("====model.ImageFileDAO3.delete 실패");
			return false;
		}
		System.out.println("====model.ImageFileDAO3.delete 종료");
		return true;
	}

	public List<ImageFileDTO> selectAll(ImageFileDTO imageFileDTO) {
		List<ImageFileDTO> datas = new ArrayList<ImageFileDTO>();
		System.out.println("====model.ImageFileDAO3.selectAll 시작");
		System.out.println("====model.ImageFileDAO3.selectAll imageFileDTO.getFile_condition() : ["+imageFileDTO.getFile_condition()+"]");
		
		if (imageFileDTO.getFile_condition() != null && imageFileDTO.getFile_condition().equals("BOARD_FILE_SELECTALL")) { 
			datas = mybatis.selectList("selectAll_BOARD", imageFileDTO);
		} 
		else if (imageFileDTO.getFile_condition() != null && imageFileDTO.getFile_condition().equals("PRODUCT_FILE_SELECTALL")) { 
			datas = mybatis.selectList("selectAll_PRODUCT", imageFileDTO);
		}
		else {
			System.err.println("====model.ImageFileDAO3.selectAll 컨디션 에러");
			return null; 
		}

		System.out.println("====model.ImageFileDAO3.selectAll datas : ["+datas+"]");
		System.out.println("====model.ImageFileDAO3.selectAll 종료");
		return datas;
	}
	
	public ImageFileDTO selectOne(ImageFileDTO imageFileDTO) {
		// 기늠 미사용으로 null 반환한다.
		return null;
	}
	
	public boolean delete(BoardDTO boardDTO) {
		return false;
	}

}
