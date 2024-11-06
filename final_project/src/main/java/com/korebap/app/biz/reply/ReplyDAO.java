package com.korebap.app.biz.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;


//@Repository
public class ReplyDAO {	
	// 댓글 작성
	private final String REPLY_INSERT = "INSERT INTO REPLY (REPLY_CONTENT, REPLY_WRITER_ID, REPLY_BOARD_NUM) VALUES (?, ?, ?)";
	// 댓글 수정
	private final String REPLY_UPDATE = "UPDATE REPLY SET REPLY_CONTENT= ? WHERE REPLY_NUM= ?";
	// 댓글 삭제
	private final String REPLY_DELETE = "DELETE FROM REPLY WHERE REPLY_NUM = ?";
	// 댓글 전체보기
	private final String REPLY_ALL = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, R.REPLY_WRITER_ID, M.MEMBER_NICKNAME, R.REPLY_REGISTRATION_DATE, M.MEMBER_PROFILE \r\n"
			+ "FROM REPLY R JOIN BOARD B ON R.REPLY_BOARD_NUM = B.BOARD_NUM \r\n"
			+ "LEFT JOIN MEMBER M ON R.REPLY_WRITER_ID = M.MEMBER_ID \r\n"
			+ "WHERE R.REPLY_BOARD_NUM = ? ORDER BY R.REPLY_NUM DESC";

	public List<ReplyDTO> selectAll(ReplyDTO replyDTO){ // 전체 출력
		System.out.println("====model.ReplyDAO.selectAll 시작");
		// 반환할 List 생성, JDBCUtil 연결
		List<ReplyDTO> datas=new ArrayList<ReplyDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt=conn.prepareStatement(REPLY_ALL);
			pstmt.setInt(1, replyDTO.getReply_board_num()); // 댓글을 단 글 번호

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println("====model.ReplyDAO.selectAll while문 시작");
				ReplyDTO data = new ReplyDTO();
				data.setReply_num(rs.getInt("REPLY_NUM"));
				data.setReply_content(rs.getString("REPLY_CONTENT")); // 댓글 내용
				data.setReply_writer_id(rs.getString("REPLY_WRITER_ID")); // 댓글 작성자 ID
				data.setReply_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 댓글 작성자(닉네임)
				data.setReply_registration_date(rs.getDate("REPLY_REGISTRATION_DATE")); // 댓글 작성일
				data.setReply_member_profile(rs.getString("MEMBER_PROFILE")); // 회원의 프로필 이미지
				datas.add(data);
				
				System.out.println("	model.ReplyDAO.selectAll data : " + data);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("====model.ReplyDAO.selectAll SQL문 실패");
		}  
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReplyDAO.selectAll 종료");
		}
		return datas;
	}
	
	//"INSERT INTO REPLY (REPLY_CONTENT, REPLY_WRITER_ID, REPLY_BOARD_NUM) VALUES (?, ?, ?)";
	public boolean insert(ReplyDTO replyDTO){ // 댓글 작성
		System.out.println("====model.ReplyDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt=null;
		
		try {
			pstmt = conn.prepareStatement(REPLY_INSERT);
			pstmt.setString(1, replyDTO.getReply_content()); // 댓글 내용
			pstmt.setString(2, replyDTO.getReply_writer_id()); // 댓글 작성자
			pstmt.setInt(3, replyDTO.getReply_board_num()); // 댓글을 단 글 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ReplyDAO.insert result : " + result);
			if(result <= 0) { 
				System.out.println("====model.ReplyDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.ReplyDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("====model.ReplyDAO.insert SQL 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReplyDAO.insert 종료");
		}
		return true;
	}

	//"UPDATE REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUM = ?";
	public boolean update(ReplyDTO replyDTO){ // 댓글 수정
		System.out.println("====model.ReplyDAO.update 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt=conn.prepareStatement(REPLY_UPDATE);
			pstmt.setString(1, replyDTO.getReply_content()); // 댓글 내용
			pstmt.setInt(2, replyDTO.getReply_num()); // 글 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ReplyDAO.update result : " + result);
			if(result<=0) { 
				System.out.println("====model.ReplyDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.ReplyDAO.update 행 변경 성공");
		} 
		catch (SQLException e) {
			System.err.println("====model.ReplyDAO.update SQL문 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReplyDAO.update 종료");
		}
		return true;
	}

	//"DELETE FROM REPLY WHERE REPLY_NUM = ?";
	public boolean delete(ReplyDTO replyDTO){ // 댓글 삭제
		System.out.println("====model.ReplyDAO.delete 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		
		try {
			pstmt=conn.prepareStatement(REPLY_DELETE);
			pstmt.setInt(1, replyDTO.getReply_num()); // 댓글 번호
			
			int result = pstmt.executeUpdate();
			System.out.println("====model.ReplyDAO.delete result : " + result);
			if(result <= 0) { 
				System.out.println("====model.ReplyDAO.delete 행 변경 성공");
				return false;
			}
			System.out.println("====model.ReplyDAO.delete 행 변경 실패");

		} 
		catch (SQLException e) {
			System.err.println("====model.ReplyDAO.delete SQL문 실패");
			return false;
		}
		finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ReplyDAO.delete 종료");
		}
		return true;
	}

	// 기능 미구현으로 null 반환
	public ReplyDTO selectOne(ReplyDTO replyDTO){
		ReplyDTO data=null;
		return data;
	}
}
