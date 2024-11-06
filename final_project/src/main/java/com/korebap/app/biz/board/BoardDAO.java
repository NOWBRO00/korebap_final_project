package com.korebap.app.biz.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class BoardDAO { // 게시판
	// 게시글 작성
	private final String BOARD_INSERT = "INSERT INTO BOARD (BOARD_TITLE,BOARD_CONTENT,BOARD_WRITER_ID) VALUES(?,?,?)";
	// 게시글 수정
	private final String BOARD_UPDATE = "UPDATE BOARD SET BOARD_TITLE=COALESCE(?,BOARD_TITLE), BOARD_CONTENT=COALESCE(?,BOARD_CONTENT) WHERE BOARD_NUM=?";
	// 게시글 삭제
	private final String BOARD_DELETE = "DELETE FROM BOARD WHERE BOARD_NUM=?";
	// 게시물 총 개수
	private final String BOARD_SELECTONE_COUNT = "SELECT COUNT(BOARD_NUM) FROM BOARD";
	// 게시판 전체 출력 통합
	private final String BOARD_SELECTALL="SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT,FILE_DIR\r\n"
			+ "FROM (\r\n"
			+ "    SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_DIR,\r\n"
			+ "           ROW_NUMBER() OVER (\r\n"
			+ "               ORDER BY \r\n"
			+ "               CASE WHEN ? IS NOT NULL AND ? = 'like' THEN LIKE_COUNT \r\n"
			+ "                   ELSE BOARD_NUM \r\n"
			+ "               END DESC\r\n"
			+ "           ) AS ROW_NUM\r\n"
			+ "    FROM BOARD_INFO_VIEW\r\n"
			+ "    WHERE (? IS NULL OR BOARD_TITLE LIKE CONCAT('%',?,'%') OR BOARD_CONTENT LIKE CONCAT('%',?,'%'))\r\n"
			+ ") as subqeury\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 내 글 전체보기
	private final String BOARD_SELECTALL_MY_BOARD = "SELECT MB.BOARD_NUM, MB.BOARD_TITLE, MB.BOARD_REGISTRATION_DATE, MB.MEMBER_NICKNAME,MB.LIKE_COUNT, MB.FILE_DIR\r\n"
			+ "FROM (SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_REGISTRATION_DATE, M.MEMBER_NICKNAME, (SELECT COUNT(G.LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT, I.FILE_DIR,\r\n"
			+ "ROW_NUMBER() OVER(ORDER BY BOARD_NUM DESC) AS ROW_NUM\r\n" + "FROM BOARD B\r\n"
			+ "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (SELECT FILE_DIR, BOARD_ITEM_NUM FROM (SELECT FILE_DIR, BOARD_ITEM_NUM, ROW_NUMBER() OVER(PARTITION BY BOARD_ITEM_NUM ORDER BY FILE_NUM)AS RN FROM IMAGEFILE)as inner_query WHERE RN=1)I ON B.BOARD_NUM = I.BOARD_ITEM_NUM\r\n"
			+ "WHERE B.BOARD_WRITER_ID = ?)MB\r\n" + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 글 상세페이지 출력(제목, 내용, 작성일, 작성자ID, 작성자 닉네임, 좋아요 개수)
	private final String BOARD_SELECTONE = "SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_CONTENT, B.BOARD_REGISTRATION_DATE, COALESCE(B.BOARD_WRITER_ID,'탈퇴한 회원입니다') AS MEMBER_ID, COALESCE(M.MEMBER_NICKNAME, '탈퇴한 회원입니다.') AS MEMBER_NICKNAME,(SELECT COUNT(LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT\r\n"
			+ "FROM BOARD B\r\n" + "LEFT JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n" + "WHERE B.BOARD_NUM=?";
	// 전체 페이지 수 출력 (기본)
	private final String BOARD_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(BOARD_NUM),0)/9.0)AS BOARD_TOTAL_PAGE FROM BOARD WHERE 1=1";
	// 전체 페이지 수 출력 (키워드 검색 페이지 수)
	private final String BOARD_SEARCH_PAGE = "AND BOARD_TITLE LIKE CONCAT('%',?,'%') OR BOARD_CONTENT LIKE CONCAT('%',?,'%')";
	// 전체 페이지 수 출력 (내가 쓴 글 페이지 수)
	private final String BOARD_MYBOARD_PAGE = "AND BOARD_WRITER_ID = ?";

	// 크롤링 - 게시판 pk 출력
	private final String BOARD_NUM_SELECTONE = "SELECT MAX(BOARD_NUM) AS MAX_NUM FROM BOARD";
	// 크롤링 - 게시판 전체 출력
	private final String BOARD_SELECT = "SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER_ID, BOARD_REGISTRATION_DATE FROM BOARD";

	public boolean insert(BoardDTO boardDTO) { // 게시글 등록
		System.out.println("====model.BoardDAO.insert 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(BOARD_INSERT);
			pstmt.setString(1, boardDTO.getBoard_title()); // 글 제목
			pstmt.setString(2, boardDTO.getBoard_content()); // 글 내용
			pstmt.setString(3, boardDTO.getBoard_writer_id()); // 글 작성자

			int result = pstmt.executeUpdate();
			System.out.println("	model.BoardDAO.insert result : [" + result + "]");

			if (result <= 0) {
				System.out.println("====model.BoardDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.BoardDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.BoardDAO.insert SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.BoardDAO.insert 종료");
		}
		return true;
	}

	public boolean update(BoardDTO boardDTO) { // 게시글 수정
		System.out.println("====model.BoardDAO.update 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(BOARD_UPDATE);
			pstmt.setString(1, boardDTO.getBoard_title()); // 글 제목
			pstmt.setString(2, boardDTO.getBoard_content()); // 글 내용
			pstmt.setInt(3, boardDTO.getBoard_num()); // 글 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.BoardDAO.update result : [" + result + "]");

			if (result <= 0) {
				System.out.println("====model.BoardDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.BoardDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.BoardDAO.update SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.BoardDAO.update 종료");
		}
		return true;
	}

	public boolean delete(BoardDTO boardDTO) { // 게시글 삭제
		System.out.println("====model.BoardDAO.delete 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(BOARD_DELETE);
			pstmt.setInt(1, boardDTO.getBoard_num()); // 글 번호

			int result = pstmt.executeUpdate();
			System.out.println("	model.BoardDAO.delete result : [" + result + "]");

			if (result <= 0) {
				System.out.println("====model.BoardDAO.delete 행 변경 실패");
				return false;
			}
			System.out.println("====model.BoardDAO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.BoardDAO.delete SQL문 실패");
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.BoardDAO.delete 종료");
		}
		return true;
	}

	public List<BoardDTO> selectAll(BoardDTO boardDTO) { // 게시글 전체 출력
		System.out.println("====model.BoardDAO.selectAll 시작");
		// JDBC 연결
		List<BoardDTO> datas = new ArrayList<BoardDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("====model.BoardDAO.selectAll boardDTO.getBoard_condition() : ["
					+ boardDTO.getBoard_condition() + "]");
			if (boardDTO.getBoard_condition().equals("BOARD_ALL")) { // 글 전체 출력
				pstmt = conn.prepareStatement(BOARD_SELECTALL);
				pstmt.setString(1, boardDTO.getBoard_search_criteria()); // 게시판 정렬 기준 (NULL 판단)
				pstmt.setString(2, boardDTO.getBoard_search_criteria()); // 게시판 정렬 기준 (like와 같은지 판단)
				pstmt.setString(3, boardDTO.getBoard_searchKeyword()); // 검색어 (NULL 판단)
				pstmt.setString(4, boardDTO.getBoard_searchKeyword()); // 검색어 (title에서 찾기)
				pstmt.setString(5, boardDTO.getBoard_searchKeyword()); // 검색어 (content에서 찾기)
				pstmt.setInt(6, boardDTO.getBoard_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(7, boardDTO.getBoard_page_num()); // 페이지 번호, 마지막 데이터 계산 페이지번호 *한 페이지에 나오는 데이터 수
			} 
			else if (boardDTO.getBoard_condition().equals("MYBOARD_LIST")) { // 내가 쓴 글 전체 목록 출력
				pstmt = conn.prepareStatement(BOARD_SELECTALL_MY_BOARD);
				pstmt.setString(1, boardDTO.getBoard_writer_id()); // 작성자 id
				pstmt.setInt(2, boardDTO.getBoard_page_num()); // 페이지네이션-페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(3, boardDTO.getBoard_page_num()); // 페이지네이션-페이지 번호, 마지막 데이터 계산 페이지번호 *한 페이지에 나오는 데이터 수
			} 
			else {
				System.out.println("====model.BoardDAO.selectAll 컨디션 오류");
			}

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO data = new BoardDTO();
				System.out.println("====model.BoardDAO.selectAll rs.next() 시작");
				if (boardDTO.getBoard_condition().equals("BOARD_ALL")) {
					data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
					data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
					data.setBoard_writer_id(rs.getString("MEMBER_NICKNAME")); // 회원 닉네임
					data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
					data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
					data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로
				} else if (boardDTO.getBoard_condition().equals("MYBOARD_LIST")) {
					data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
					data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
					data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
					data.setBoard_writer_id(rs.getString("MEMBER_NICKNAME")); // 회원 닉네임
					data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
					data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로
				}
				System.out.println("	model.BoardDAO.selectAll data : [" + data + "]");
				datas.add(data);
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.err.println("====model.BoardDAO.selectAll SQL문 실패");
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.BoardDAO.selectAll 종료");
		}
		return datas;
	}

	public BoardDTO selectOne(BoardDTO boardDTO) { // 한개 출력
		System.out.println("====model.BoardDAO.selectOne 시작");
		// JDBC 연결
		BoardDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	model.BoardDAO.selectOne boardDTO.getBoard_condition() : ["
					+ boardDTO.getBoard_condition() + "]");
			if (boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) { // 글 상세페이지 출력
				pstmt = conn.prepareStatement(BOARD_SELECTONE);
				pstmt.setInt(1, boardDTO.getBoard_num());
			} 
			else if (boardDTO.getBoard_condition().equals("BOARD_NUM_SELECTONE")) { // 크롤링 관련
				pstmt = conn.prepareStatement(BOARD_NUM_SELECTONE);
			} 
			else if (boardDTO.getBoard_condition().equals("BOARD_SELECT")) { // 크롤링 관련
				pstmt = conn.prepareStatement(BOARD_SELECT);
			} 
			else if (boardDTO.getBoard_condition().equals("BOARD_SELECTONE_COUNT")) { // 전체 게시판 개수
				pstmt = conn.prepareStatement(BOARD_SELECTONE_COUNT);
			}
			// 페이지네이션 : 전체 페이지 수 반환
			else if (boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
				if (boardDTO.getBoard_searchKeyword() != null && !boardDTO.getBoard_searchKeyword().isEmpty()) {
					pstmt = conn.prepareStatement(BOARD_TOTAL_PAGE + " " + BOARD_SEARCH_PAGE);
					pstmt.setString(1, boardDTO.getBoard_searchKeyword()); // 검색어
					pstmt.setString(2, boardDTO.getBoard_searchKeyword()); // 검색어
				} 
				else if (boardDTO.getBoard_writer_id() != null && !boardDTO.getBoard_writer_id().isEmpty()) {
					pstmt = conn.prepareStatement(BOARD_TOTAL_PAGE + " " + BOARD_MYBOARD_PAGE);
					pstmt.setString(1, boardDTO.getBoard_writer_id()); // 회원 ID
				} 
				else {
					pstmt = conn.prepareStatement(BOARD_TOTAL_PAGE);
				}
			} 
			else {
				System.out.println("====model.BoardDAO.selectOne 컨디션 오류");
			}

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new BoardDTO();
				System.out.println("====model.BoardDAO.selectOne 쿼리 읽어오기 시작");

				if (boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) { // 글 상세보기
					data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
					data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
					data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
					data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
					data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
					data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 작성자 (닉네임)
					data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
				} 
				else if (boardDTO.getBoard_condition().equals("BOARD_SELECTONE_COUNT")) { // 전체 게시판 개수
					data.setBoard_total_cnt(rs.getInt("BOARD_TOTAL_CNT"));
				}
				else if (boardDTO.getBoard_condition().equals("BOARD_NUM_SELECTONE")) {// 크롤링 select 위해 게시글 번호
					data.setBoard_num(rs.getInt("MAX_NUM"));
				} 
				else if (boardDTO.getBoard_condition().equals("BOARD_SELECT")) { // 크롤링 확인 전체 출력
					data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
					data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
					data.setBoard_writer_id(rs.getString("BOARD_WRITER_ID")); // 작성자 ID
					data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
					data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
				} 
				else if (boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
					data.setBoard_total_page(rs.getInt("BOARD_TOTAL_PAGE")); // 전체 페이지 수
				} 
				else {
					System.out.println("====model.BoardDAO.selectOne rs.next() 실패");
				}
				System.out.println("	model.BoardDAO.selectOne data : [" + data+"]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("====model.BoardDAO.selectOneSQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.BoardDAO.selectOne 종료");
		}
		return data;
	}
}