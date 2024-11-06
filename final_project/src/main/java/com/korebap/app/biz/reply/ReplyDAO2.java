package com.korebap.app.biz.reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAO2 {
	// 댓글 추가
	private final String REPLY_INSERT = "INSERT INTO REPLY "
			+ "(REPLY_CONTENT, REPLY_WRITER_ID, REPLY_BOARD_NUM) " 
			+ "VALUES (?, ?, ?)";

	// 댓글 수정
	private final String REPLY_UPDATE = "UPDATE REPLY "
			+ "SET REPLY_CONTENT = ? " 
			+ "WHERE REPLY_NUM = ?";

	// 댓글 삭제
	private final String REPLY_DELETE = "DELETE FROM REPLY "
			+ "WHERE REPLY_NUM = ?";

	// 특정 게시글의 모든 댓글 조회
	private final String REPLY_SELECTALL = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, R.REPLY_WRITER_ID, M.MEMBER_NICKNAME, "
			+ "R.REPLY_REGISTRATION_DATE, M.MEMBER_PROFILE "
			+ "FROM REPLY R "
			+ "JOIN MEMBER M ON R.REPLY_WRITER_ID = M.MEMBER_ID "
			+ "JOIN BOARD B ON R.REPLY_BOARD_NUM = B.BOARD_NUM "
			+ "WHERE B.BOARD_NUM = ? " 
			+ "ORDER BY R.REPLY_NUM DESC";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean insert(ReplyDTO replyDTO){ // 댓글 등록
		System.out.println("====model.ReplyDAO2.insert 시작");
		
		String reply_content =replyDTO.getReply_content(); // 댓글 내용
		String writer_id = replyDTO.getReply_writer_id(); // 작성자 아이디
		int board_num = replyDTO.getReply_board_num(); // 게시물 번호
		
		// 댓글 내용, 작성자 아이디, 게시물 번호를 넣어 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REPLY_INSERT,reply_content,writer_id,board_num);
		
		System.out.println("====model.ReplyDAO2.insert result : ["+result+"]");
		if(result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ReplyDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReplyDAO2.insert 종료");
		return true; // 성공적으로 등록 완료, true를 반환한다.
	}

	public boolean update(ReplyDTO replyDTO){ // 댓글 수정
		System.out.println("====model.ReplyDAO2.update 시작");
		
		String reply_content = replyDTO.getReply_content(); // 댓글 내용
		int reply_num = replyDTO.getReply_num(); // 댓글 번호
		
		// 댓글 내용, 댓글 번호를 넣어서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REPLY_UPDATE,reply_content,reply_num);
		
		System.out.println("====model.ReplyDAO2.update result : ["+result+"]");
		if(result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ReplyDAO2.update 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReplyDAO2.update 종료");
		return true; // 성공적으로 수정 완료, true를 반환한다.
	}

	public boolean delete(ReplyDTO replyDTO){ // 댓글 삭제
		System.out.println("====model.ReplyDAO2.delete 시작");
		
		// 댓글 번호를 넣어서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(REPLY_DELETE,replyDTO.getReply_num());
		
		System.out.println("====model.ReplyDAO2.delete result : ["+result+"]");
		if(result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ReplyDAO2.delete 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ReplyDAO2.delete 종료");
		return true; // 성공적으로 삭제 완료, true를 반환한다.
	}

	public List<ReplyDTO> selectAll(ReplyDTO replyDTO){ // 댓글 전체 출력
		List<ReplyDTO> datas = new ArrayList<ReplyDTO>();
		System.out.println("====model.ReplyDAO2.selectAll 시작");
		
		// 게시물 번호를 넣어 쿼리문을 수행한다.
		Object[] args = {replyDTO.getReply_board_num()}; // 게시물 번호
		datas = jdbcTemplate.query(REPLY_SELECTALL,args,new ReplyRowMapper());
		
		System.out.println("====model.ReplyDAO2.selectAll datas : ["+datas+"]");
		System.out.println("====model.ReplyDAO2.selectAll 종료");
		return datas;
	}

	public ReplyDTO selectOne(ReplyDTO replyDTO){
		// 미사용으로 null 반환한다.
		return null;
	}
}

//======================================================RowMapper=================================================================
// 댓글 내용, 작성자 아이디, 작성자 닉네임, 작성일, 작성자 프로필 사진을 반환하는 RowMapper
class ReplyRowMapper implements RowMapper<ReplyDTO>{
	
	@Override
	public ReplyDTO mapRow(ResultSet rs, int rowNum){
		System.out.println("====model.ReplyDAO2.ReplyRowMapper 시작");
		ReplyDTO data = new ReplyDTO();
		
		try {
			data.setReply_num(rs.getInt("REPLY_NUM"));
			data.setReply_content(rs.getString("REPLY_CONTENT")); // 댓글 내용
			data.setReply_writer_id(rs.getString("REPLY_WRITER_ID")); // 댓글 작성자 아이디
			data.setReply_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 댓글 작성자(닉네임)
			data.setReply_registration_date(rs.getDate("REPLY_REGISTRATION_DATE")); // 댓글 작성일
			data.setReply_member_profile(rs.getString("MEMBER_PROFILE")); // 회원의 프로필 이미지
		} 
		catch (SQLException e) {
			System.err.println("====model.ReplyDAO2.ReplyRowMapper 실패");
			e.printStackTrace();
		}
		System.out.println("====model.ReplyDAO2.ReplyRowMapper 종료");
		return data;
	}
}
