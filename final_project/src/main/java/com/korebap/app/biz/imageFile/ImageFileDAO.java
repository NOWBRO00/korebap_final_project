package com.korebap.app.biz.imageFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class ImageFileDAO { 
	// 게시판 사진 저장
	private final String BOARD_FILE_INSERT = "INSERT INTO IMAGEFILE (FILE_DIR, BOARD_ITEM_NUM) VALUES (?, ?);";
	// 상품 사진 저장
	private final String PRODUCT_FILE_INSERT = "INSERT INTO IMAGEFILE (FILE_DIR, PRODUCT_ITEM_NUM) VALUES (?, ?);";
	// 사진 변경
	private final String FILE_UPDATE = "UPDATE IMAGEFILE SET FILE_DIR=? WHERE FILE_NUM=?";
	// 사진 삭제
	private final String FILE_DELETE = "DELETE FROM IMAGEFILE WHERE FILE_NUM=?";
	// 게시판 사진 전체 출력
	private final String BOARD_FILE_SELECTALL = "SELECT FILE_NUM,FILE_DIR FROM IMAGEFILE WHERE BOARD_ITEM_NUM=? ORDER BY FILE_NUM";
	// 상품 사진 전체 출력
	private final String PRODUCT_FILE_SELECTALL = "SELECT FILE_NUM,FILE_DIR FROM IMAGEFILE WHERE PRODUCT_ITEM_NUM=? ORDER BY FILE_NUM";

	public List<ImageFileDTO> selectAll(ImageFileDTO imageFileDTO) { // 사진 전체 출력
		System.out.println("====model.ImageFileDAO.selectAll 시작");
		// JDBCUtil 연결
		List<ImageFileDTO> datas = new ArrayList<ImageFileDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			if (imageFileDTO.getFile_condition().equals("BOARD_FILE_SELECTALL")) {
				System.out.println("====model.ImageFileDAO.selectAll BOARD_FILE_SELECTALL 시작");
				pstmt = conn.prepareStatement(BOARD_FILE_SELECTALL);
				pstmt.setInt(1, imageFileDTO.getFile_board_num()); // 게시판 글 번호
			} else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_SELECTALL")) {
				System.out.println("====model.ImageFileDAO.selectAll PRODUCT_FILE_SELECTALL 시작");
				pstmt = conn.prepareStatement(PRODUCT_FILE_SELECTALL);
				pstmt.setInt(1, imageFileDTO.getFile_product_num()); // 상품 글 번호
			}
			else {
				System.err.println("====model.ImageFileDAO.selectAll 컨디션 에러");
			}

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("====model.ImageFileDAO.selectAll while문 시작");
				ImageFileDTO data = new ImageFileDTO();
				data.setFile_num(rs.getInt("FILE_NUM")); // 파일 번호
				data.setFile_dir(rs.getString("FILE_DIR")); // 파일 경로

				datas.add(data);
				System.out.println("	model.ImageFileDAO.selectAll datas : " + datas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("====model.ImageFileDAO.selectAll SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ImageFileDAO.selectAll 종료");
		}
		return datas;
	}
	
	// "INSERT INTO IMAGEFILE (FILE_DIR, BOARD_ITEM_NUM) VALUES (?, ?);"
	// "INSERT INTO IMAGEFILE (FILE_DIR, PRODUCT_ITEM_NUM) VALUES (?, ?);"
	public boolean insert(ImageFileDTO imageFileDTO) { // 생성
		System.out.println("====model.ImageFileDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			if (imageFileDTO.getFile_condition().equals("BOARD_FILE_INSERT")) {
				System.out.println("====model.ImageFileDAO.insert BOARD_FILE_INSERT 시작");
				pstmt = conn.prepareStatement(BOARD_FILE_INSERT);
				pstmt.setString(1, imageFileDTO.getFile_dir()); // 파일 경로
				pstmt.setInt(2, imageFileDTO.getFile_board_num()); // 게시판 글 번호
			} 
			else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_INSERT")) {
				System.out.println("====model.ImageFileDAO.insert PRODUCT_FILE_INSERT 시작");
				pstmt = conn.prepareStatement(PRODUCT_FILE_INSERT);
				System.out.println("imageFileDTO.getFile_dir() :"+imageFileDTO.getFile_dir());
				System.out.println("imageFileDTO.getFile_product_num() :"+imageFileDTO.getFile_product_num());
				pstmt.setString(1, imageFileDTO.getFile_dir()); // 파일 경로
				pstmt.setInt(2, imageFileDTO.getFile_product_num()); // 상품 글 번호
			}
			else {
				System.err.println("====model.ImageFileDAO.insert 컨디션 실패");
			}
			
			int result = pstmt.executeUpdate();
			System.out.println("	model.ImageFileDAO.insert result : " + result);
			if (result <= 0) { // 만약 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("====model.ImageFileDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.ImageFileDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ImageFileDAO.insert SQL문 실패");
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ImageFileDAO.insert 종료");
		}
		return true;
	}

	// "UPDATE IMAGEFILE SET FILE_DIR=? WHERE FILE_NUM=?"
	public boolean update(ImageFileDTO imageFileDTO) { // 업데이트
		System.out.println("====model.ImageFileDAO.update 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(FILE_UPDATE);
			pstmt.setString(1, imageFileDTO.getFile_dir());// 파일 경로
			pstmt.setInt(2, imageFileDTO.getFile_num()); // 파일 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ImageFileDAO.update result : " + result);
			if (result <= 0) { // 만약 변경된 행 수가 없다면
				System.out.println("====model.ImageFileDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.ImageFileDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ImageFileDAO.update SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ImageFileDAO.update 종료");
		}
		return true;
	}

	// "DELETE FROM IMAGEFILE WHERE FILE_NUM=?"
	public boolean delete(ImageFileDTO imageFileDTO) { // 삭제
		System.out.println("====model.ImageFileDAO.delete 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(FILE_DELETE);
			pstmt.setInt(1, imageFileDTO.getFile_num());// 파일번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ImageFileDAO.delete result : " + result);
			if (result <= 0) { // 만약 변경된 행 수가 없다면
				System.out.println("====model.ImageFileDAO.delete 행 변경 실패");
				return false;
			}
			System.out.println("====model.ImageFileDAO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ImageFileDAO.deleteSQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ImageFileDAO.delete 종료");
		}
		return true;
	}

	// 기늠 미구현으로 null 반환
	public ImageFileDTO selectOne(ImageFileDTO imageFileDTO) {
		ImageFileDTO data = null;
		return data;
	}
}