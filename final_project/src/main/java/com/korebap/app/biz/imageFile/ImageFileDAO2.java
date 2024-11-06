package com.korebap.app.biz.imageFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//@Repository
public class ImageFileDAO2 { 
	// 게시판 사진 저장
	private final String INSERT_FILE_BOARD = "INSERT INTO IMAGEFILE "
			+ "(FILE_DIR, BOARD_ITEM_NUM) " 
			+ "VALUES (?, ?)";

	// 상품 사진 저장
	private final String INSERT_FILE_PRODUCT = "INSERT INTO IMAGEFILE "
			+ "(FILE_DIR, PRODUCT_ITEM_NUM) " 
			+ "VALUES (?, ?)";

	// 사진 변경
	private final String UPDATE_FILE = "UPDATE IMAGEFILE " 
			+ "SET FILE_DIR = ? " 
			+ "WHERE FILE_NUM = ?";

	// 사진 삭제
	private final String DELETE_FILE = "DELETE FROM IMAGEFILE " 
			+ "WHERE FILE_NUM = ?";

	// 게시판 사진 전체 출력
	private final String SELECTALL_FILE_BOARD = "SELECT FILE_NUM, FILE_DIR "
			+ "FROM IMAGEFILE "
			+ "WHERE BOARD_ITEM_NUM = ? "
			+ "ORDER BY FILE_NUM";

	// 상품 사진 전체 출력
	private final String SELECTALL_FILE_PRODUCT = "SELECT FILE_NUM, FILE_DIR "
			+ "FROM IMAGEFILE " 
			+ "WHERE PRODUCT_ITEM_NUM = ? "
			+ "ORDER BY FILE_NUM";

	@Autowired
	private JdbcTemplate jdbcTemplate; 

	public boolean insert(ImageFileDTO imageFileDTO) { // 이미지 등록
		int result; 
		System.out.println("====model.ImageFileDAO2.insert 시작");
		System.out.println("====model.ImageFileDAO2.insert imageFileDTO.getFile_condition() : [" + imageFileDTO.getFile_condition()+"]");
		
		String file_dir = imageFileDTO.getFile_dir(); // 파일 경로
		int board_num = imageFileDTO.getFile_board_num(); // 게시판 번호
		int product_num = imageFileDTO.getFile_product_num(); // 상품 번호
	
		// 컨디션이 'BOARD_FILE_INSERT'라면, 파일경로, 게시판 번호를 담아서 쿼리문을 수행한다.
		if (imageFileDTO.getFile_condition().equals("BOARD_FILE_INSERT")) { 
			result = jdbcTemplate.update(INSERT_FILE_BOARD, file_dir,board_num);
		} 
		// 컨디션이 'PRODUCT_FILE_INSERT'라면, 파일경로, 상품 번호를 담아서 쿼리문을 수행한다.
		else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_INSERT")) { 
			result = jdbcTemplate.update(INSERT_FILE_PRODUCT, file_dir,product_num);
		}
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.ImageFileDAO2.insert 컨디션 실패");
			return false; // false를 반환한다.
		}
		
		System.out.println("====model.ImageFileDAO.insert result : ["+result+"]");
		if (result <= 0) { // 만약 변경이 된 행 수가 0보다 작거나 같다면
			System.err.println("====model.ImageFileDAO2.insert 실패");
			return false; // 실패를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.insert 종료");
		return true; // 성공적으로 삽입 완료, true를 반환한다.
	}

	public boolean update(ImageFileDTO imageFileDTO) { // 사진 수정
		int result;
		System.out.println("====model.ImageFileDAO2.update 시작");
		
		String file_dir = imageFileDTO.getFile_dir(); // 파일 경로
		int file_num = imageFileDTO.getFile_num(); // 파일 번호
		
		// 파일경로, 파일 번호를 담아 쿼리문을 수행한다.
		result = jdbcTemplate.update(UPDATE_FILE,file_dir,file_num);
		
		System.out.println("====model.ImageFileDAO2.update result : ["+result+"]");
		if (result <= 0) {  // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ImageFileDAO2.update 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.update 종료");
		return true;// 성공적으로 변경 완료, true를 반환한다.
	}
	
	public boolean delete(ImageFileDTO imageFileDTO) { // 사진 삭제
		int result;
		System.out.println("====model.ImageFileDAO2.delete 시작");

		// 파일 번호를 담아 쿼리문을 수행한다.
		result = jdbcTemplate.update(DELETE_FILE,imageFileDTO.getFile_num());
		
		System.out.println("====model.ImageFileDAO2.delete result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ImageFileDAO2.delete 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.delete 종료");
		return true; // 성공적으로 삭제 완료, true를 반환한다.
	}

	public List<ImageFileDTO> selectAll(ImageFileDTO imageFileDTO) { // 사진 전체 출력
		List<ImageFileDTO> datas = new ArrayList<ImageFileDTO>();
		System.out.println("====model.ImageFileDAO2.selectAll 시작");
		System.out.println("====model.ImageFileDAO2.selectAll imageFileDTO.getFile_condition() : ["+imageFileDTO.getFile_condition()+"]");
		
		// 컨디션이 'BOARD_FILE_SELECTALL'라면, 게시판 번호를 담아 쿼리를 수행한다.
		if (imageFileDTO.getFile_condition().equals("BOARD_FILE_SELECTALL")) { 
			Object[] args = {imageFileDTO.getFile_board_num()}; // 게시판 번호
			datas = jdbcTemplate.query(SELECTALL_FILE_BOARD, args, new ImageFileRowMapper_all()); 
		} 
		// 컨디션이 'PRODUCT_FILE_SELECTALL'라면, 상품 번호를 담아 쿼리를 수행한다.
		else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_SELECTALL")) { 
			Object[] args = {imageFileDTO.getFile_product_num()}; // 상품 번호
			datas = jdbcTemplate.query(SELECTALL_FILE_PRODUCT, args, new ImageFileRowMapper_all());
		}
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.ImageFileDAO2.selectAll 컨디션 에러");
			return null; // null을 반환한다.
		}
		// 쿼리문을 수행한 데이터를 반환한다.
		System.out.println("====model.ImageFileDAO2.selectAll datas : ["+datas+"]");
		System.out.println("====model.ImageFileDAO2.selectAll 종료");
		return datas;
	}


	
	public ImageFileDTO selectOne(ImageFileDTO imageFileDTO) {
		// 기늠 미사용으로 null 반환한다.
		return null;
	}
}
//======================================================RowMapper=================================================================
// 파일 번호, 파일 경로를 반환하는 RowMapper
class ImageFileRowMapper_all implements RowMapper<ImageFileDTO> {
	
	@Override
	public ImageFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ImageFileDAO2.ImageFileRowMapper_all 실행");
		
		ImageFileDTO data = new ImageFileDTO();
		data.setFile_num(rs.getInt("FILE_NUM")); // 파일 번호
		data.setFile_dir(rs.getString("FILE_DIR")); // 파일 경로
		
		System.out.println("====model.ImageFileDAO2.ImageFileRowMapper_all 종료");
		return data;
	}
}