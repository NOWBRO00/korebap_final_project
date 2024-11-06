package com.korebap.app.biz.goodLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GoodLikeDAO2 {
	// 좋아요 등록
	private final String GOODLIKE_INSERT = "INSERT INTO GOODLIKE "
			+ "(LIKE_BOARD_NUM, LIKE_MEMBER_ID) " 
			+ "VALUES (?, ?)";

	// 좋아요 삭제
	private final String GOODLIKE_DELETE = "DELETE FROM GOODLIKE "
			+ "WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";

	// 좋아요 개수 출력
	private final String GOODLIKE_SELECTONE = "SELECT LIKE_NUM, LIKE_BOARD_NUM, LIKE_MEMBER_ID " 
			+ "FROM GOODLIKE " 
			+ "WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";

	@Autowired 
	private JdbcTemplate jdbcTemplate;

	public boolean insert(GoodLikeDTO goodLikeDTO) { // 좋아요 등록
		System.out.println("====model.GoodLikeDAO2.insert 시작");
		
		int board_num = goodLikeDTO.getGoodLike_board_num();
		String member_id = goodLikeDTO.getGoodLike_member_id();
		
		// 값을 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(GOODLIKE_INSERT,board_num,member_id); 
		
		System.out.println("====model.GoodLikeDAO2.insert result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.GoodLikeDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.GoodLikeDAO2.insert 성공");
		return true; // 성공적으로 삽입 완료, true를 반환한다.
	}
	
	public boolean delete(GoodLikeDTO goodLikeDTO) { // 좋아요 삭제
		System.out.println("====model.GoodLikeDAO2.delete 시작");
		
		int board_num = goodLikeDTO.getGoodLike_board_num();
		String member_id = goodLikeDTO.getGoodLike_member_id();
		
		// 값을 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(GOODLIKE_DELETE, board_num,member_id);
		
		System.out.println("====model.GoodLikeDAO2.delete result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.GoodLikeDAO2.delete 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.GoodLikeDAO2.delete 성공");
		return true; // 성공적으로 삽입 완료, true를 반환한다.
	}

	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO) { // 좋아요 총 개수 출력
	      System.out.println("====model.GoodLikeDAO2.selectOne 시작");

	      int board_num = goodLikeDTO.getGoodLike_board_num();
	      String member_id = goodLikeDTO.getGoodLike_member_id();
	      
	      Object[] args = { board_num,member_id };
	      // 값을 담아서 쿼리문을 수행한다.
	      GoodLikeDTO data = jdbcTemplate.queryForObject(GOODLIKE_SELECTONE, args, new GoodLikeRowMapper());
	      System.out.println("====model.GoodLikeDAO2.selectOne data : ["+data+"]");
	      System.out.println("====model.GoodLikeDAO2.selectOne 종료");
	      return data; // 데이터가 있으면 data를 반환한다.
	   
	   }


	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO) {
		// 기능 미사용으로 null을 반환한다.
		return null;
	}

	
	public boolean update(GoodLikeDTO goodLikeDTO) { 
		// 기능 미사용으로 false를 반환한다.
		return false;
	}

}
//======================================================RowMapper=================================================================
// 좋아요, 게시물 번호, 좋아요 누른 멤버의 아이디를 반환하는 RowMapper
class GoodLikeRowMapper implements RowMapper<GoodLikeDTO> {

	@Override
	public GoodLikeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.GoodLikeDAO2.GoodLikeRowMapper 실행");

		GoodLikeDTO data = new GoodLikeDTO();
		data.setGoodLike_num(rs.getInt("LIKE_NUM"));
		data.setGoodLike_board_num(rs.getInt("LIKE_BOARD_NUM"));
		data.setGoodLike_member_id(rs.getString("LIKE_MEMBER_ID"));
		
		System.out.println("====model.GoodLikeDAO2.GoodLikeRowMapper 종료");
		return data;
	}

}
