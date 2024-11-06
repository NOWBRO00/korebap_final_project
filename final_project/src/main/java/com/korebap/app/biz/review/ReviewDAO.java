package com.korebap.app.biz.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;


//@Repository
public class ReviewDAO {
	// 리뷰 작성 
	private final String REVIEW_INSERT = "INSERT INTO REVIEW (REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR) VALUES (?, ?, ?, ?)";
	// 리뷰 수정
	private final String REVIEW_UPDATE = "UPDATE REVIEW SET REVIEW_CONTENT = ?, REVIEW_STAR = ? WHERE REVIEW_NUM = ?";
	// 리뷰 삭제
	private final String REVIEW_DELETE = "DELETE FROM REVIEW WHERE REVIEW_NUM = ?";
	// 리뷰 전체 출력 (최신순)
	private final String REVIEW_SELECTALL_NEW_REVIEW = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, \r\n"
			+ "COALESCE(R.REVIEW_WRITER_ID,'탈퇴한 회원입니다.'), COALESCE(M.MEMBER_NICKNAME,'탈퇴한 회원입니다.'), M.MEMBER_PROFILE FROM REVIEW R\r\n"
			+ "LEFT JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? ORDER BY R.REVIEW_NUM DESC";
	// 리뷰 전체 출력 (별점 높은 순)
	private final String REVIEW_SELECTALL_HIGH_STAR = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, \r\n"
			+ "COALESCE(R.REVIEW_WRITER_ID,'탈퇴한 회원입니다.'), COALESCE(M.MEMBER_NICKNAME,'탈퇴한 회원입니다.'), M.MEMBER_PROFILE FROM REVIEW R\r\n"
			+ "LEFT JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = 1 ORDER BY R.REVIEW_STAR DESC";
	// 리뷰 전체 출력 (별점 낮은 순)
	private final String REVIEW_SELECTALL_LOW_STAR = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR,\r\n"
			+ "COALESCE(R.REVIEW_WRITER_ID,'탈퇴한 회원입니다.'), COALESCE(M.MEMBER_NICKNAME,'탈퇴한 회원입니다.'), M.MEMBER_PROFILE FROM REVIEW R\r\n"
			+ "LEFT JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = 1 ORDER BY R.REVIEW_STAR";

	public List<ReviewDTO> selectAll(ReviewDTO reviewDTO){ // 리뷰 전체 출력
//		System.out.println("====model.ReviewDAO.selectAll 시작");
//		// JDBCUtil 연결
		List<ReviewDTO> datas=new ArrayList<ReviewDTO>();
//		Connection conn = JDBCUtil.connect();
//		PreparedStatement pstmt = null;
//		
//		try {
//			if(reviewDTO.getReview_condition().equals("REVIEW_ALL")) { // 리뷰 전체 출력 (최신순)
//				pstmt=conn.prepareStatement(REVIEW_SELECTALL_NEW_REVIEW);
//				System.out.println("====model.ReviewDAO.selectAll REVIEW_ALL 시작");
//			}
//			else if(reviewDTO.getReview_condition().equals("REVIEW_HIGH")) { // 리뷰 전체 출력 (별점 높은 순)
//				pstmt=conn.prepareStatement(REVIEW_SELECTALL_HIGH_STAR);
//				System.out.println("====model.ReviewDAO.selectAll REVIEW_HIGH 시작");
//			}
//			else if(reviewDTO.getReview_condition().equals("REVIEW_LOW")) { // 리뷰 전체 출력 (별점 낮은 순)
//				pstmt=conn.prepareStatement(REVIEW_SELECTALL_LOW_STAR);
//				System.out.println("====model.ReviewDAO.selectAll REVIEW_LOW 시작");
//			}
//			else {
//				System.out.println("====model.ReviewDAO.selectAll 컨디션 실패");
//			}
//			pstmt.setInt(1, reviewDTO.getReview_product_num()); // 상품 글 번호
//			
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				System.out.println("====model.ReviewDAO.selectAll while문 시작");
//				ReviewDTO data = new ReviewDTO();
//				data.setReview_num(rs.getInt("REVIEW_NUM")); // 리뷰 번호 (PK)
//				data.setReview_content(rs.getString("REVIEW_CONTENT")); // 리뷰 내용
//				data.setReview_registration_date(rs.getDate("REVIEW_REGISTRATION_DATE")); // 리뷰 작성일
//				data.setReview_star(rs.getInt("REVIEW_STAR")); // 별점 (1~5)
//				data.setReview_writer_id(rs.getString("REVIEW_WRITER_ID")); // 작성자 아이디
//				data.setReview_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 닉네임
//				data.setReview_member_profile(rs.getString("MEMBER_PROFILE")); // 작성자 프로필
//				datas.add(data);	
//				System.out.println("	model.ReviewDAO.selectAll datas : " + datas);
//			}
//		} catch(SQLException e) {
//			System.err.println("====model.ReviewDAO.selectAll SQL문 실패");
//		}
//		finally {
//			JDBCUtil.disconnect(pstmt, conn);
//			System.out.println("====model.ReviewDAO.selectAll 종료");
//		}
		return datas;
	}

	// "INSERT INTO REVIEW (REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR) VALUES (?, ?, ?, ?)";
	public boolean insert(ReviewDTO reviewDTO){	// 리뷰 작성
		System.out.println("====model.ReviewDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		
		try {
			pstmt=conn.prepareStatement(REVIEW_INSERT);
			pstmt.setString(1, reviewDTO.getReview_content()); // 리뷰 내용
			pstmt.setInt(2, reviewDTO.getReview_product_num()); // 리뷰를 쓴 상품 번호
			pstmt.setString(3, reviewDTO.getReview_writer_id()); // 작성자 ID
			pstmt.setInt(4, reviewDTO.getReview_star()); // 별점 (1~5)

			int result = pstmt.executeUpdate();
			System.out.println("	model.ReviewDAO.insert result : " + result);
			if(result<=0) {
				System.out.println("====model.ReviewDAO.insert 행 변경 실패 ");
				return false;
			}
			System.out.println("====model.ReviewDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ReviewDAO.insert SQL문 실패");
			return false;
		}
		finally {
	        JDBCUtil.disconnect(pstmt, conn);
	        System.out.println("====model.ReviewDAO.insert 종료");
	    }
		return true;
	}

	// "UPDATE REVIEW SET REVIEW_CONTENT = ?, REVIEW_STAR = ? WHERE REVIEW_NUM = ?";
	public boolean update(ReviewDTO reviewDTO){	// 리뷰 수정
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		
		try {
			System.out.println("====model.ReviewDAO.update 시작");
			pstmt=conn.prepareStatement(REVIEW_UPDATE);
			pstmt.setString(1, reviewDTO.getReview_content()); // 리뷰 내용
			pstmt.setInt(2, reviewDTO.getReview_star()); // 별점 (1~5)
			pstmt.setInt(3, reviewDTO.getReview_num()); // 리뷰 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ReviewDAO.update result : " + result);
			if(result<=0) {
				System.out.println("====model.ReviewDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.ReviewDAO.update 행 변경 성공");
		} 
		catch (SQLException e) {
			System.err.println("====model.ReviewDAO.update SQL문 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReviewDAO.update 종료");
		}
		return true;
	}

	// "DELETE FROM REVIEW WHERE REVIEW_NUM = ?";
	public boolean delete(ReviewDTO reviewDTO){	// 리뷰 삭제
		System.out.println("====model.ReviewDAO.delete 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		
		try {
			pstmt=conn.prepareStatement(REVIEW_DELETE);
			pstmt.setInt(1, reviewDTO.getReview_num()); // 리뷰 번호 (선택용)

			int result = pstmt.executeUpdate();
			System.out.println("	model.ReviewDAO.delete result : " + result);
			if(result<=0) {
				System.out.println("====model.ReviewDAO.delete 행 변경 실패");
				return false;
			}
			System.out.println("====model.ReviewDAO.delete 행 변경 성공");
		} 
		catch (SQLException e) {
			System.err.println("====model.ReviewDAO.delete SQL문 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReviewDAO.delete 종료");
		}
		return true;
	}

	// 기능 미구현으로 null 반환
	public ReviewDTO selectOne(ReviewDTO reviewDTO){ 
		ReviewDTO data=null;
		return data;
	}
}
