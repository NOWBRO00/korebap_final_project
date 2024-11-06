package com.korebap.app.biz.wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class WishlistDAO {
	// 위시리스트 추가
	private final String WISHLIST_INSERT = "INSERT INTO WISHLIST (WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM) VALUES (?,?)";
	// 위시리스트 삭제
	private final String WISHLIST_DELETE = "DELETE FROM WISHLIST WHERE WISHLIST_NUM = ?"; 
	// 나의 위시리스트 개수 보기
	private final String WISHLIST_SELECTONE = "SELECT COUNT(WISHLIST_NUM) AS WISHLIST_COUNT FROM WISHLIST WHERE WISHLIST_MEMBER_ID=?";
	// 나의 위시리스트 전체 보기
	private final String WISHLIST_SELECTALL = "SELECT W.WISHLIST_NUM,P.PRODUCT_NUM,P.PRODUCT_NAME,P.PRODUCT_PRICE,P.PRODUCT_LOCATION,P.PRODUCT_CATEGORY,I.FILE_DIR\r\n"
			+ "FROM WISHLIST W JOIN PRODUCT P ON W.WISHLIST_PRODUCT_NUM = P.PRODUCT_NUM\r\n"
			+ "LEFT JOIN (SELECT FILE_DIR,PRODUCT_ITEM_NUM,ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS IMAGE_NUMBER\r\n"
			+ "FROM IMAGEFILE) I ON P.PRODUCT_NUM = I.PRODUCT_ITEM_NUM AND I.IMAGE_NUMBER = 1 WHERE W.WISHLIST_MEMBER_ID = ?";

	public List<WishlistDTO> selectAll(WishlistDTO wishlistDTO) { // 위시리스트 전체 출력
		System.out.println("====model.WishlistDAO.selectAll 시작");
		// 반환할 List 생성, JDBCUtil 연결
		List<WishlistDTO> datas = new ArrayList<WishlistDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try { 
			System.out.println("	member_id: " + wishlistDTO.getWishlist_member_id());
			pstmt = conn.prepareStatement(WISHLIST_SELECTALL);
			pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID

			ResultSet rs = pstmt.executeQuery(); // 쿼리를 실행하고 결과를 ResultSet에 저장
			while (rs.next()) { // 행을 반복시켜 DTO에 저장
				System.out.println("model.WishlistDAO.selectAll while문 시작");
				
				WishlistDTO data = new WishlistDTO(); // 데이터를 담을 새로운 DTO 생성
				data.setWishlist_num(rs.getInt("WISHLIST_NUM")); // 위시리스트 번호 (PK)
				data.setWishlist_product_num(rs.getInt("PRODUCT_NUM")); // 상품번호
				data.setWishlist_product_name(rs.getString("PRODUCT_NAME")); // 상품명
				data.setWishlist_product_price(rs.getInt("PRODUCT_PRICE")); // 상품 금액
				data.setWishlist_product_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소
				data.setWishlist_product_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형(낚시배,낚시터,낚시카페,수상)
				data.setWishlist_file_dir(rs.getString("FILE_DIR")); // 파일 경로;
				datas.add(data);
				
				System.out.println("	model.WishlistDAO.selectAll datas : " + datas);
			}
		} 
		catch (SQLException e) {
			System.err.println("====model.WishlistDAO.selectAll SQL 실패");
		}
		finally { // 자원 해제
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("	model.WishlistDAO.selectAll 종료");
		}
		return datas;
	}

	// "SELECT COUNT(WISHLIST_NUM) AS WISHLIST_COUNT FROM WISHLIST WHERE WISHLIST_MEMBER_ID=?";
	public WishlistDTO selectOne(WishlistDTO wishlistDTO) { // 내 위시리스트 개수 출력
		System.out.println("====model.WishlistDAO.selectOne 시작");
		// JDBCUtil 연결
		WishlistDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	member_id: " + wishlistDTO.getWishlist_member_id());
			pstmt = conn.prepareStatement(WISHLIST_SELECTONE);
			pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			
			ResultSet rs = pstmt.executeQuery(); // 쿼리를 실행하고 결과를 ResultSet에 저장
			if (rs.next()) { // 행을 읽어 DTO에 저장
				data = new WishlistDTO();
				data.setWishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 위시리스트 개수
				System.out.println("	model.WishlistDAO.selectOne data : " + data);
			}
		} 
		catch (SQLException e) {
			System.err.println("====model.WishlistDAO.selectOne SQL 실패");
			return null;
		}
		finally {
			JDBCUtil.disconnect(pstmt,conn); // 종료
			System.out.println("	model.WishlistDAO.selectOne 종료");
		}
		return data;
	}	
	
	// "INSERT INTO WISHLIST (WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM) VALUES (?,?)";
	public boolean insert(WishlistDTO wishlistDTO) { // 위시리스트 추가
		System.out.println("====model.WishlistDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(WISHLIST_INSERT);
			pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			pstmt.setInt(2, wishlistDTO.getWishlist_product_num()); // 상품 번호
			int result = pstmt.executeUpdate();
			System.out.println("	model.WishlistDAO.insert result : " + result);

			if (result <= 0) {
				System.err.println("====model.WishlistDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.WishlistDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.WishlistDAO.insert SQL 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.WishlistDAO.insert 종료");
		}
		return true;
	}

	// "DELETE FROM WISHLIST WHERE WISHLIST_NUM = ?"
	public boolean delete(WishlistDTO wishlistDTO) { // 위시리스트 삭제
		System.out.println("====model.WishlistDAO.delete 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(WISHLIST_DELETE);
			pstmt.setInt(1, wishlistDTO.getWishlist_num()); // 위시리스트 번호 (PK)
			int result = pstmt.executeUpdate();
			System.out.println("	model.WishlistDAO.delete result : " + result);

			if (result <= 0) {
				System.out.println("====model.WishlistDAO.delete 행 변경 실패");
				return false;
			}
			System.out.println("====model.WishlistDAO.delete 행 변경 성공");
		} 
		catch (SQLException e) {
			System.err.println("====model.WishlistDAO.delete SQL 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.WishlistDAO.delete 종료");
		}
		return true;
	}
	
	// 기능 미구현으로 false 반환
	public boolean update(WishlistDTO wishlistDTO){ 
		return false;
	}
}
