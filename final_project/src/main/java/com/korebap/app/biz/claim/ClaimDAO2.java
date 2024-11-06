package com.korebap.app.biz.claim;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClaimDAO2 {
	// 게시글 신고 등록
	private final String CLAIM_INSERT_BOARD = "INSERT INTO CLAIM "
			+ "(CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) "
			+ "VALUES (?, NULL, ?, ?)";

	// 댓글 신고 등록
	private final String CLAIM_INSERT_REPLY = "INSERT INTO CLAIM "
			+ "(CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) "
			+ "VALUES (NULL, ?, ?, ?)";

	// 신고 상태 변경
	private final String CLAIM_UPDATE = "UPDATE CLAIM " + "SET CLAIM_STATUS = ? " + "WHERE CLAIM_NUM = ?";

	// 신고 전체 보기 (페이지네이션)
	private final String CLAIM_ALL_SELECTALL = "SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, "
			+ "CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID " + "FROM ( "
			+ "    SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, "
			+ "           CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID, "
			+ "           ROW_NUMBER() OVER (ORDER BY CLAIM_NUM) AS ROW_NUM " + "    FROM CLAIM " + ") AS SUBQUERY "
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 15 + 1 AND COALESCE(?, 1) * 15";

	// 미처리 신고 전체 보기
	private final String CLAIM_PENDING_SELECTALL = "SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, "
			+ "CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID " + "FROM ( "
			+ "    SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, "
			+ "           CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID, "
			+ "           ROW_NUMBER() OVER (ORDER BY CLAIM_NUM) AS ROW_NUM " + "    FROM CLAIM "
			+ "    WHERE CLAIM_STATUS = 'PENDING' " + ") AS SUBQUERY "
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 15 + 1 AND COALESCE(?, 1) * 15";

	// 페이지 총 개수
	private final String CLAIM_SELECTONE_PAGE_CNT = "SELECT CEIL(COALESCE(COUNT(CLAIM_NUM), 0) / 15.0) AS CLAIM_TOTAL_PAGE "
			+ "FROM CLAIM";

	// 미처리 신고 페이지 총 개수
	private final String CLAIM_SELECTONE_PENDING_PAGE_CNT = "SELECT CEIL(COALESCE(COUNT(CLAIM_NUM), 0) / 15.0) AS CLAIM_TOTAL_PAGE "
			+ "FROM CLAIM " + "WHERE 1 = 1 " + "AND CLAIM_STATUS = 'PENDING'";

	// 처리된 신고 전체 보기
	private final String CLAIM_AFTER_SELECTALL = "SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT CLAIM_NUM, CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_STATUS, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID,\r\n"
			+ "           ROW_NUMBER() OVER (ORDER BY CLAIM_NUM) AS ROW_NUM\r\n" + "    FROM CLAIM\r\n"
			+ "    WHERE CLAIM_STATUS NOT IN ('PENDING')\r\n" + ") AS subquery\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 15 + 1 AND COALESCE(?, 1) * 15";
	// 처리된 신고 페이지 수
	private final String CLAIM_SELECTONE_AFTER_PAGE_CNT = "SELECT CEIL(COALESCE(COUNT(CLAIM_NUM), 0) / 15.0) AS CLAIM_TOTAL_PAGE FROM CLAIM WHERE 1 = 1 AND CLAIM_STATUS NOT IN ('PENDING')";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(ClaimDTO claimDTO) { // 신고 등록
		int result;
		System.out.println("====model.ClaimDAO2.insert 시작");
		System.out.println(
				"====model.ClaimDAO2.insert claimDTO.getClaim_condition() : [" + claimDTO.getClaim_condition() + "]");

		int board_num = claimDTO.getClaim_board_num(); // 게시글 번호 - FK
		int reply_num = claimDTO.getClaim_reply_num(); // 댓글 번호 - FK
		String target_member_id = claimDTO.getClaim_target_member_id(); // 신고 당한 유저
		String reporter_id = claimDTO.getClaim_reporter_id(); // 신고한 유저

		// 만약 컨디션이 'INSERT_CLAIM_BOARD'라면, - 게시물 신고 등록
		if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_BOARD")) {
			result = jdbcTemplate.update(CLAIM_INSERT_BOARD, board_num, target_member_id, reporter_id); // 데이터를 담아서 쿼리문을
																										// 수행한다.
		}
		// 만약 컨디션이 'INSERT_CLAIM_REPLY'라면, - 댓글 신고 등록
		else if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_REPLY")) {
			result = jdbcTemplate.update(CLAIM_INSERT_REPLY, reply_num, target_member_id, reporter_id); // 데이터를 담아서 쿼리문을
																										// 수행한다.
		}
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.ClaimDAO2.insert 컨디션 실패");
			return false; // false를 반환한다.
		}

		System.out.println("====model.ClaimDAO2.insert result : [" + result + "]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.ClaimDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ClaimDAO2.insert 종료");
		return true; // 성공적으로 삽입 완료, true를 반환한다.
	}

	public boolean update(ClaimDTO claimDTO) { // 상태 수정
		System.out.println("====model.ClaimDAO2.update 시작");

		String claim_status = claimDTO.getClaim_status();
		int cliam_num = claimDTO.getClaim_num();

		// 신고 상태와 신고 번호를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(CLAIM_UPDATE, claim_status, cliam_num); //
		System.out.println("====model.ClaimDAO2.update result : [" + result + "]");

		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면,
			System.err.println("====model.ClaimDAO2.update 행 변경 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.ClaimDAO2.update 종료");
		return true; // 성공적으로 삽입 완료, true를 반환한다.
	}

	public ClaimDTO selectOne(ClaimDTO claimDTO) { // 신고 조회
		ClaimDTO data = null;
		System.out.println("====model.ClaimDAO2.selectOne 시작");
		System.out.println("====model.ClaimDAO2.selectOne claimDTO.getClaim_condition() : ["
				+ claimDTO.getClaim_condition() + "]");

		// 만약 컨디션이 'CLAIM_SELECTONE_PAGE_CNT'라면, - 모든 신고 페이지 개수
		if (claimDTO.getClaim_condition().equals("CLAIM_SELECTONE_PAGE_CNT")) {
			data = jdbcTemplate.queryForObject(CLAIM_SELECTONE_PAGE_CNT, new ClaimRowMapper_one_total_page());
		}
		// 만약 컨디션이 'CLAIM_SELECTONE_PENDING_PAGE_CNT'라면, - 미처리 신고 페이지 개수
		else if (claimDTO.getClaim_condition().equals("CLAIM_SELECTONE_PENDING_PAGE_CNT")) {
			data = jdbcTemplate.queryForObject(CLAIM_SELECTONE_PENDING_PAGE_CNT, new ClaimRowMapper_one_total_page());
		}
		// 만약 컨디션이 'CLAIM_SELECTONE_AFTER_PAGE_CNT'라면 - 처리된 신고 페이지 개수
		else if (claimDTO.getClaim_condition().equals("CLAIM_SELECTONE_AFTER_PAGE_CNT")) { 
			data = jdbcTemplate.queryForObject(CLAIM_SELECTONE_AFTER_PAGE_CNT, new ClaimRowMapper_one_total_page()); 
		} 
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.ClaimDAO2.selectOne 컨디션 에러");
			return null; // null을 반환한다.
		}

		// 쿼리문을 수행한 데이터를 반환한다.
		System.out.println("====model.ClaimDAO2.selectOne data: [" + data + "]");
		System.out.println("====model.ClaimDAO2.selectOne 종료");
		return data;
	}

	public List<ClaimDTO> selectAll(ClaimDTO claimDTO) { // 신고 전체 조회
		List<ClaimDTO> datas = new ArrayList<>();
		System.out.println("====model.ClaimDAO2.selectAll 시작");
		System.out.println("====model.ClaimDAO2.selectAll claimDTO.getClaim_condition() : ["
				+ claimDTO.getClaim_condition() + "]");

		// 만약 컨디션이 'CLAIM_ALL_SELECTALL'라면, - 신고 전체 보기(페이지네이션)
		if (claimDTO.getClaim_condition().equals("CLAIM_ALL_SELECTALL")) {
			Object[] args = { claimDTO.getClaim_page_num(), claimDTO.getClaim_page_num() };
			datas = jdbcTemplate.query(CLAIM_ALL_SELECTALL, args, new ClaimRowMapper_all());
		}
		// 만약 컨디션이 'CLAIM_PENDING_SELECTALL'라면, - 보류 신고 전체 보기(페이지네이션)
		else if (claimDTO.getClaim_condition().equals("CLAIM_PENDING_SELECTALL")) {
			Object[] args = { claimDTO.getClaim_page_num(), claimDTO.getClaim_page_num() };
			datas = jdbcTemplate.query(CLAIM_PENDING_SELECTALL, args, new ClaimRowMapper_all());
		}
		// 만약 컨디션이 'CLAIM_ALL_SELECTALL'라면, - 처리된 신고 전체 보기(페이지네이션)
		else if (claimDTO.getClaim_condition().equals("CLAIM_AFTER_SELECTALL")) {
			Object[] args = { claimDTO.getClaim_page_num(), claimDTO.getClaim_page_num() };
			datas = (List<ClaimDTO>) jdbcTemplate.query(CLAIM_AFTER_SELECTALL, args, new ClaimRowMapper_all());
		}
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.ClaimDAO2.selectAll 컨디션 에러");
			return null; // null을 반환한다.
		}

		// 쿼리문을 수행한 데이터를 반환한다.
		System.out.println("====model.ClaimDAO2.selectAll datas: [" + datas + "]");
		System.out.println("====model.ClaimDAO2.selectAll 종료");
		return datas;
	}

	public boolean delete(ClaimDTO claimDTO) {
		// 기능 미사용으로 false 반환한다.
		return false;
	}
}

//======================================================RowMapper=================================================================
// 총 페이지 수를 반환하는 RowMapper
class ClaimRowMapper_one_total_page implements RowMapper<ClaimDTO> { // 페이지 수 반환하는
	@Override
	public ClaimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ClaimDAO2.ClaimRowMapper_one_total_page 실행");

		ClaimDTO data = new ClaimDTO();
		data.setClaim_total_page(rs.getInt("CLAIM_TOTAL_PAGE")); // 총 페이지 수

		System.out.println("====model.ClaimDAO2.ClaimRowMapper_one_total_page 종료");
		return data;
	}
}

// 신고 목록를 반환하는 RowMapper
class ClaimRowMapper_all implements RowMapper<ClaimDTO> {
	@Override
	public ClaimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ClaimDAO2.ClaimRowMapper_all 실행");

		ClaimDTO data = new ClaimDTO();
		data.setClaim_num(rs.getInt("CLAIM_NUM")); // 신고 번호
		data.setClaim_board_num(rs.getInt("CLAIM_BOARD_NUM")); // 게시판 번호
		data.setClaim_reply_num(rs.getInt("CLAIM_REPLY_NUM")); // 댓글 번호
		data.setClaim_status(rs.getString("CLAIM_STATUS")); // 신고 상태
		data.setClaim_target_member_id(rs.getString("CLAIM_TARGET_MEMBER_ID")); // 신고 당한 사람
		data.setClaim_reporter_id(rs.getString("CLAIM_REPORTER_ID")); // 신고자

		System.out.println("====model.ClaimDAO2.ClaimRowMapper_all 종료");
		return data;
	}
}
