package com.korebap.app.biz.review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO2 {
	// 리뷰 작성
	private final String REVIEW_INSERT = "INSERT INTO REVIEW "
			+ "(REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR) " 
			+ "VALUES (?, ?, ?, ?)";

	// 리뷰 수정
	private final String REVIEW_UPDATE = "UPDATE REVIEW " 
			+ "SET REVIEW_CONTENT = ?, REVIEW_STAR = ? "
			+ "WHERE REVIEW_NUM = ?";

	// 리뷰 삭제
	private final String REVIEW_DELETE =  "DELETE FROM REVIEW " 
			+ "WHERE REVIEW_NUM = ?";

	// 리뷰 전체 출력 (최신순)
	private final String REVIEW_SELECTALL_NEW_REVIEW = "SELECT R.REVIEW_NUM, R.REVIEW_PRODUCT_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, "
			+ "COALESCE(R.REVIEW_WRITER_ID, '탈퇴한 회원입니다.') AS REVIEW_WRITER_ID, "
			+ "COALESCE(M.MEMBER_NICKNAME, '탈퇴한 회원입니다.') AS MEMBER_NICKNAME, " 
			+ "M.MEMBER_PROFILE "
			+ "FROM REVIEW R "
			+ "LEFT JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID "
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? " 
			+ "ORDER BY R.REVIEW_NUM DESC";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(ReviewDTO reviewDTO) { // 리뷰 등록
		System.out.println("====model.ReviewDAO2.insert 시작");
		
		String review_content = reviewDTO.getReview_content(); // 리뷰 내용
		int product_num = reviewDTO.getReview_product_num(); // 상품 번호
		String writer_id = reviewDTO.getReview_writer_id(); // 작성자 아이디
		int review_star = reviewDTO.getReview_star(); // 별점
		
		// 리뷰 내용, 상품 번호, 작성자 아이디, 별점를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REVIEW_INSERT,review_content,product_num,writer_id,review_star);
		
		System.out.println("====model.ReviewDAO2.insert result : ["+result+"]");
		
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ReviewDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReviewDAO2.insert 종료");
		return true; // 성공적으로 등록 완료, true를 반환한다.
	}

	public boolean update(ReviewDTO reviewDTO) { // 리뷰 수정
		System.out.println("====model.ReviewDAO2.update 시작");
		
		String review_content = reviewDTO.getReview_content(); // 리뷰 내용
		int review_star = reviewDTO.getReview_star(); // 별점
		int review_num = reviewDTO.getReview_num(); // 리뷰 번호
		
		// 리뷰 내용, 별점, 리뷰 번호를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REVIEW_UPDATE,review_content,review_star,review_num);
		System.out.println("====model.ReviewDAO2.update result : ["+result+"]");
		
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ReviewDAO2.update 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReviewDAO2.update 종료");
		return true; // 성공적으로 수정 완료, true를 반환한다.
	}

	public boolean delete(ReviewDTO reviewDTO) { // 리뷰 삭제
		System.out.println("====model.ReviewDAO2.delete 시작");
		
		// 리뷰 번호를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REVIEW_DELETE, reviewDTO.getReview_num());
		
		System.out.println("====model.ReviewDAO2.delete result : ["+result+"]");
		
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.out.println("====model.ReviewDAO2.delete 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReviewDAO2.delete 성공");
		return true; // 성공적으로 삭제 완료, true를 반환한다.
	}
	
	public List<ReviewDTO> selectAll(ReviewDTO reviewDTO) { // 리뷰 전체 출력
		List<ReviewDTO> datas = new ArrayList<ReviewDTO>();
		System.out.println("====model.ReviewDAO2.selectAll 시작");
		
		// 상품 번호를 담아서 쿼리문을 수행한다.
		Object[] args ={ reviewDTO.getReview_product_num() }; // 상품 번호
 		datas = jdbcTemplate.query(REVIEW_SELECTALL_NEW_REVIEW, args, new ReviewRowMapper());
 		
 		System.out.println("====model.ReviewDAO2.selectAll datas : ["+datas+"]");
		System.out.println("====model.ReviewDAO2.selectAll 종료");
		return datas;
	}

	public ReviewDTO selectOne(ReviewDTO reviewDTO) {
		// 기능 미사용으로 null 반환한다.
		return null;
	}
}
//======================================================RowMapper=================================================================
// 리뷰 번호, 내용, 작성일, 별점, 작성자 아이디, 닉네임, 프로필 사진을 반환하는 RowMapper
class ReviewRowMapper implements RowMapper<ReviewDTO> {
	
	@Override
	public ReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ReviewDAO2.ReviewRowMapper 실행");
		
		ReviewDTO data = new ReviewDTO();
		data.setReview_num(rs.getInt("REVIEW_NUM")); // 리뷰 번호
		data.setReview_product_num(rs.getInt("REVIEW_PRODUCT_NUM")); // 리뷰 상품 번호
		data.setReview_content(rs.getString("REVIEW_CONTENT")); // 리뷰 내용
		data.setReview_registration_date(rs.getDate("REVIEW_REGISTRATION_DATE")); // 리뷰 작성일
		data.setReview_star(rs.getInt("REVIEW_STAR")); // 리뷰 별점
		data.setReview_writer_id(rs.getString("REVIEW_WRITER_ID")); // 작성자 아이디
		data.setReview_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 닉네임
		data.setReview_member_profile(rs.getString("MEMBER_PROFILE")); // 작성자 프로필
		
		System.out.println("====model.ReviewDAO2.ReviewRowMapper 종료");
		return data;
	}
}
