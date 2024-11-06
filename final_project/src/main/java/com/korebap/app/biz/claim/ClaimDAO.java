package com.korebap.app.biz.claim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;
import com.korebap.app.biz.imageFile.ImageFileDTO;

//@Repository
public class ClaimDAO {
	// 게시글 신고
	private final String CLAIM_INSERT_BOARD = "INSERT INTO CLAIM (CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) VALUES (?, NULL, ?, ?);";
	// 댓글 신고
	private final String CLAIM_INSERT_REPLY = "INSERT INTO CLAIM (CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) VALUES (NULL,?,?,?);";
	// 신고 처리 완료
	private final String CLAIM_UPDATE = "UPDATE CLAIM SET CLAIM_STATUS='Resolved' WHERE CLAIM_NUM=?";
	// 신고여부 확인
	private final String CLAIM_SELECTONE = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_NUM = ?";
	// 보류 신고 개수
	private final String CLAIM_PENDING_COUNT_SELECTONE = "SELECT COUNT(*) AS RESOLVED_CLAIM_COUNT FROM CLAIM WHERE CLAIM_STATUS = 'Pending'";
	// 처리 완료 신고 개수
	private final String CLAIM_RESOLVED_COUNT_SELECTONE = "SELECT COUNT(*) AS RESOLVED_CLAIM_COUNT FROM CLAIM WHERE CLAIM_STATUS = 'Resolved'";
	// 신고 전체 보기
	private final String CLAIM_ALL_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM";
	// 보류 신고 전체 보기
	private final String CLAIM_PENDING_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_STATUS = 'Pending'";
	// 처리 완료 신고 전체 보기
	private final String CLAIM_RESOLVED_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_STATUS = 'Resolved'";

	public List<ClaimDTO> selectAll(ClaimDTO claimDTO) {
		List<ClaimDTO> datas = new ArrayList<ClaimDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			if (claimDTO.getClaim_condition().equals("CLAIM_ALL_SELECTALL")) { // 신고 전체 보기
				System.out.println("====model.ClaimDAO.selectAll CLAIM_ALL_SELECTALL 시작");
				pstmt = conn.prepareStatement(CLAIM_ALL_SELECTALL);
			} else if (claimDTO.getClaim_condition().equals("CLAIM_PENDING_SELECTALL")) { // 보류 신고 전체 보기
				System.out.println("====model.ClaimDAO.selectAll CLAIM_PENDING_SELECTALL 시작");
				pstmt = conn.prepareStatement(CLAIM_PENDING_SELECTALL);
			} else if (claimDTO.getClaim_condition().equals("CLAIM_RESOLVED_SELECTALL")) { // 처리 완료 신고 전체 보기
				System.out.println("====model.ClaimDAO.selectAll CLAIM_RESOLVED_SELECTALL 시작");
				pstmt = conn.prepareStatement(CLAIM_RESOLVED_SELECTALL);
			} else {
				System.err.println("====model.ClaimDAO.selectAll 컨디션 에러");
			}

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("====model.ClaimDAO.selectAll while문 시작");
				ClaimDTO data = new ClaimDTO();
				data.setClaim_num(rs.getInt("CLAIM_NUM")); // 신고 번호
				data.setClaim_board_num(rs.getInt("CLAIM_BOARD_NUM")); // 게시판 번호
				data.setClaim_reply_num(rs.getInt("CLAIM_REPLY_NUM")); // 댓글 번호
				data.setClaim_status(rs.getString("CLAIM_STATUS")); // 신고 상태
				data.setClaim_target_member_id(rs.getString("CLAIM_TARGET_MEMBER_ID")); // 신고 당한 사람
				data.setClaim_reporter_id(rs.getString("CLAIM_REPORTER_ID")); // 신고자
				datas.add(data);

				System.out.println("	model.ClaimDAO.selectAll datas : " + datas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("====model.ClaimDAO.selectAll SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ClaimDAO.selectAll 종료");
		}

		return datas;
	}

	public boolean insert(ClaimDTO claimDTO) { // 신고 등록
		System.out.println("====model.ClaimDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_BOARD")) {
				System.out.println("====model.ClaimDAO.insert INSERT_CLAIM_BOARD 시작");
				pstmt = conn.prepareStatement(CLAIM_INSERT_BOARD); // INSERT 실행
				pstmt.setInt(1, claimDTO.getClaim_board_num()); // 게시글 번호 - FK
				pstmt.setString(2, claimDTO.getClaim_target_member_id()); // 신고 당한 유저
				pstmt.setString(3, claimDTO.getClaim_reporter_id()); // 신고한 유저
			}
			// 댓글 신고
			else if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_REPLY")) {
				System.out.println("====model.ClaimDAO.insert INSERT_CLAIM_REPLY 시작");
				pstmt = conn.prepareStatement(CLAIM_INSERT_REPLY); // INSERT 실행
				pstmt.setInt(1, claimDTO.getClaim_reply_num()); // 댓글 번호 - FK
				pstmt.setString(2, claimDTO.getClaim_target_member_id()); // 신고 당한 유저
				pstmt.setString(3, claimDTO.getClaim_reporter_id()); // 신고한 유저
			}

			int result = pstmt.executeUpdate();
			System.out.println("	model.ClaimDAO.insert result : " + result);
			if (result <= 0) {
				System.err.println("====model.ClaimDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.ClaimDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ClaimDAO.insert SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 연결 해제/종료
			System.out.println("====model.ClaimDAO.insert 종료");
		}
		return true;
	}

	public ClaimDTO selectOne(ClaimDTO claimDTO) { // 신고 한 개 보기
		System.out.println("====model.ClaimDAO.selectOne 시작");
		// JDBC연결
		ClaimDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			if(claimDTO.getClaim_condition().equals("CLAIM_SELECTONE")) {
				System.out.println("====model.ClaimDAO.selectOne CLAIM_SELECTONE 시작");
				pstmt = conn.prepareStatement(CLAIM_SELECTONE);
				pstmt.setInt(1, claimDTO.getClaim_num());
			}
			else if (claimDTO.getClaim_condition().equals("CLAIM_PENDING_COUNT_SELECTONE")) { // 보류 신고 개수 보기
				System.out.println("====model.ClaimDAO.selectOne CLAIM_PENDING_COUNT_SELECTONE 시작");
				pstmt = conn.prepareStatement(CLAIM_PENDING_COUNT_SELECTONE);
			}
			else if (claimDTO.getClaim_condition().equals("CLAIM_RESOLVED_COUNT_SELECTONE")) { // 처리 완료 신고 개수 보기
				System.out.println("====model.ClaimDAO.selectOne CLAIM_RESOLVED_COUNT_SELECTONE 시작");
				pstmt = conn.prepareStatement(CLAIM_RESOLVED_COUNT_SELECTONE);
			}
			else {
				System.err.println("====model.ClaimDAO.selectOne 컨디션 에러");
			}

			ResultSet rs = pstmt.executeQuery();
			System.out.println("	model.ClaimDAO.selectOne rs : " + rs);
			if (rs.next()) {
				data = new ClaimDTO();
				data.setClaim_num(rs.getInt("CLAIM_NUM"));
				System.out.println("	model.ClaimDAO.selectOne data : " + data);
			}
		} catch (SQLException e) {
			System.err.println("====model.ClaimDAO.selectOne SQL문 실패");
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ClaimDAO.selectOne 종료");
		}
		return data;
	}

	// "UPDATE CLAIM SET CLAIM_STATUS='Resolved' WHERE CLAIM_NUM=?";
	public boolean update(ClaimDTO claimDTO) { // 보류 > 해결 로 수정
		System.out.println("====model.ClaimDAO.update 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(CLAIM_UPDATE);
			pstmt.setInt(1, claimDTO.getClaim_num());// 신고 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.ClaimDAO.update result : " + result);
			if (result <= 0) { // 만약 변경된 행 수가 없다면
				System.out.println("====model.ClaimDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.ClaimDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ClaimDAO.update SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ClaimDAO.update 종료");
		}
		return true;
	}

	// 기능 미구현으로 false 반환
	public boolean delete(ClaimDTO claimDTO) {
		return false;
	}

}
