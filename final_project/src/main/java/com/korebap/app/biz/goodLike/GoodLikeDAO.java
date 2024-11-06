package com.korebap.app.biz.goodLike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class GoodLikeDAO {
	// 좋아요 등록
	private final String GOODLIKE_INSERT = "INSERT INTO GOODLIKE(LIKE_BOARD_NUM,LIKE_MEMBER_ID) VALUES(?,?)";
	// 좋아요 삭제
	private final String GOODLIKE_DELETE = "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";
	// 좋아요 개수 
	private final String GOODLIKE_SELECTONE = "SELECT LIKE_BOARD_NUM, LIKE_MEMBER_ID FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";

	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO) { // 좋아요 개수 반환
		System.out.println("====model.GoodLikeDAO.selectOne 시작");
		// JDBCUtil 연결
		GoodLikeDTO data = null;
		Connection conn = JDBCUtil.connect(); 
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(GOODLIKE_SELECTONE);
			pstmt.setInt(1, goodLikeDTO.getGoodLike_board_num());
			pstmt.setString(2, goodLikeDTO.getGoodLike_member_id());

			ResultSet rs = pstmt.executeQuery();
			System.out.println("	model.GoodLikeDAO.selectOne result : " + rs);
			if (rs.next()) {
				System.err.println("====model.GoodLikeDAO.selectOne if문 시작");
				data = new GoodLikeDTO();
				data.setGoodLike_board_num(rs.getInt("LIKE_BOARD_NUM"));
				data.setGoodLike_member_id(rs.getString("LIKE_MEMBER_ID"));
			}
		} catch (SQLException e) {
			System.err.println("====model.GoodLikeDAO.selectOne SQL 실패");
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); 
			System.out.println("====model.GoodLikeDAO.selectOne 종료");
		}
		return data;
	}
	
	// "INSERT INTO GOODLIKE(LIKE_BOARD_NUM,LIKE_MEMBER_ID) VALUES(?,?)"
	public boolean insert(GoodLikeDTO goodLikeDTO) { // 좋아요 등록
		// JDBCUtil 연결
		System.out.println("====model.GoodLikeDAO.insert 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(GOODLIKE_INSERT);
			pstmt.setInt(1, goodLikeDTO.getGoodLike_board_num()); // 게시글
			pstmt.setString(2, goodLikeDTO.getGoodLike_member_id()); // 회원

			int result = pstmt.executeUpdate();
			System.out.println("	model.goodLikeDAO.insert result : " + result);
			if (result <= 0) {
				System.err.println("====model.GoodLikeDAO.insert 행 변경 실패");
				return false;
			}
			System.err.println("====model.GoodLikeDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("====model.GoodLikeDAO.insert SQL 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 종료
			System.out.println("====model.GoodLikeDAO.insert 종료");
		}
		return true;

	}

	// "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?"
	public boolean delete(GoodLikeDTO goodLikeDTO) { // 좋아요 삭제
		System.out.println("====model.GoodLikeDAO.delete 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(GOODLIKE_DELETE);
			pstmt.setInt(1, goodLikeDTO.getGoodLike_board_num());
			pstmt.setString(2, goodLikeDTO.getGoodLike_member_id());

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				System.err.println("====model.GoodLikeDAO.delete 행 변경 실패");
				return false;
			}
			System.err.println("====model.GoodLikeDAO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.GoodLikeDAO.delete SQL 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 종료
			System.err.println("====model.GoodLikeDAO.delete 종료");
		}
		return true;
	}

	// 기능 미구현으로 false 반환
	public boolean update(GoodLikeDTO goodLikeDTO) { // 업데이트
		return false;
	}

	// 기능 미구현으로 null 반환
	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO) { // 전체 출력
		List<GoodLikeDTO> datas = new ArrayList<GoodLikeDTO>();
		datas = null;
		return datas;
	}
}
