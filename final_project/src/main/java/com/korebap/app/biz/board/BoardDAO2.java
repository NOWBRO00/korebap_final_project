package com.korebap.app.biz.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO2 { // 게시판
   // 게시글 작성
   private final String BOARD_INSERT = "INSERT INTO BOARD "
         + "(BOARD_TITLE,BOARD_CONTENT,BOARD_WRITER_ID) "
         + "VALUES(?,?,?)";
   // 게시글 수정
   private final String BOARD_UPDATE =  "UPDATE BOARD " 
         + "SET BOARD_TITLE = COALESCE(?, BOARD_TITLE), " 
          + "    BOARD_CONTENT = COALESCE(?, BOARD_CONTENT) "
          + "WHERE BOARD_NUM = ?";
   // 게시글 삭제
   private final String BOARD_DELETE = "DELETE FROM BOARD "
         + "WHERE BOARD_NUM=?";
   // 글 상세페이지 출력
   private final String BOARD_SELECTONE = "SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_CONTENT, " 
         + "       B.BOARD_REGISTRATION_DATE, " 
         + "       COALESCE(B.BOARD_WRITER_ID, '탈퇴한 회원입니다') AS MEMBER_ID, " 
         + "       COALESCE(M.MEMBER_NICKNAME, '탈퇴한 회원입니다.') AS MEMBER_NICKNAME, "
         + "       (SELECT COUNT(LIKE_BOARD_NUM) " 
         + "        FROM GOODLIKE G " 
         + "        WHERE G.LIKE_BOARD_NUM = B.BOARD_NUM) AS LIKE_COUNT " 
         + "FROM BOARD B " 
         + "LEFT JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID " 
         + "WHERE B.BOARD_NUM = ?";
   // 전체 게시물 총 개수
   private final String BOARD_SELECTONE_COUNT = "SELECT COUNT(BOARD_NUM) AS BOARD_TOTAL_CNT FROM BOARD";
   // 일반 게시물 총 개수 쿼리문 추가
   private final String BOARD_SELECTONE_USER_COUNT = "SELECT COUNT(B.BOARD_NUM) AS BOARD_TOTAL_CNT "
         + "FROM BOARD B "
         + "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID "
         + "WHERE M.MEMBER_ROLE='USER'";
   // 공지사항 총 개수
   private final String BOARD_SELECTONE_NOTICE_COUNT = "SELECT COUNT(B.BOARD_NUM) AS BOARD_TOTAL_CNT " 
         + "FROM BOARD B " 
         + "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID " 
         + "WHERE M.MEMBER_ROLE = 'ADMIN'";
   // 크롤링 : 제일 큰 PK 찾기
   private final String BOARD_SELECTONE_NUM = "SELECT BOARD_NUM AS MAX_NUM FROM BOARD ORDER BY BOARD_NUM DESC LIMIT 1";
   // 전체 페이지 수 출력 (기본)
   private final String BOARD_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(BOARD_NUM),0)/9.0)AS BOARD_TOTAL_PAGE FROM BOARD WHERE 1=1";
   // 전체 페이지 수 출력 (키워드 검색 페이지 수)
   private final String BOARD_SEARCH_PAGE = "AND BOARD_TITLE LIKE CONCAT('%',?,'%') OR BOARD_CONTENT LIKE CONCAT('%',?,'%')";
   // 전체 페이지 수 출력 (내가 쓴 글 페이지 수)
   private final String BOARD_MYBOARD_PAGE = "AND BOARD_WRITER_ID = ?";
   // 게시판 전체 출력 통합(USER + OWNER)
   private final String BOARD_SELECTALL_USER = "SELECT BOARD_NUM, BOARD_TITLE, MEMBER_ID, MEMBER_NICKNAME, " 
         + "       DATE_FORMAT(BOARD_REGISTRATION_DATE, '%Y-%m-%d %H:%i:%s') AS BOARD_REGISTRATION_DATE, " 
          + "       LIKE_COUNT, FILE_DIR " 
          + "FROM ( " 
          + "    SELECT BOARD_NUM, BOARD_TITLE, MEMBER_ID, MEMBER_NICKNAME, " 
          + "           BOARD_CONTENT, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_DIR, " 
          + "           ROW_NUMBER() OVER (ORDER BY " 
          + "               CASE WHEN ? IS NOT NULL AND ? = 'like' THEN LIKE_COUNT " 
          + "                    ELSE BOARD_NUM END DESC) AS ROW_NUM " 
          + "    FROM BOARD_INFO_VIEW " 
          + "    WHERE (MEMBER_ROLE = 'USER' or MEMBER_ROLE = 'OWNER') "
          + "      AND (? IS NULL OR BOARD_TITLE LIKE CONCAT('%', ?, '%') " 
          + "           OR BOARD_CONTENT LIKE CONCAT('%', ?, '%')) " 
          + ") AS subquery " 
          + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
   // 공지사항 전체 보기
   private final String BOARD_SELECTALL_ADMIN = "SELECT BOARD_NUM, BOARD_TITLE, MEMBER_ID, MEMBER_NICKNAME, "
          + "    DATE_FORMAT(BOARD_REGISTRATION_DATE, '%Y-%m-%d %H:%i:%s') AS BOARD_REGISTRATION_DATE, "
          + "    LIKE_COUNT, FILE_DIR "
          + "FROM ( "
          + "      SELECT "
          + "          BOARD_NUM, BOARD_TITLE, MEMBER_ID, MEMBER_NICKNAME, BOARD_CONTENT, "
          + "          BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_DIR, "
          + "          ROW_NUMBER() OVER ( "
          + "              ORDER BY BOARD_REGISTRATION_DATE DESC ) AS ROW_NUM "
          + "        FROM BOARD_INFO_VIEW "
          + "        WHERE "
          + "            MEMBER_ROLE = 'ADMIN' "
          + "    ) AS SUBQUERY "
          + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
   // 내 글 전체보기
   private final String BOARD_SELECTALL_MY_BOARD = "SELECT MB.BOARD_NUM, MB.BOARD_TITLE, MB.MEMBER_ID, " 
         + "MB.MEMBER_NICKNAME, MB.BOARD_REGISTRATION_DATE, MB.LIKE_COUNT, MB.FILE_DIR " 
         + "FROM ( " 
         + "    SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_REGISTRATION_DATE, " 
         + "           M.MEMBER_ID, M.MEMBER_NICKNAME, " 
         + "           (SELECT COUNT(G.LIKE_BOARD_NUM) FROM GOODLIKE G " 
         + "            WHERE G.LIKE_BOARD_NUM = B.BOARD_NUM) AS LIKE_COUNT, " 
         + "           I.FILE_DIR, " 
         + "           ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS ROW_NUM " 
         + "    FROM BOARD B " 
         + "    JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID " 
         + "    LEFT JOIN ( "
         + "        SELECT FILE_DIR, BOARD_ITEM_NUM " 
         + "        FROM ( "
         + "            SELECT FILE_DIR, BOARD_ITEM_NUM, " 
         + "                   ROW_NUMBER() OVER (PARTITION BY BOARD_ITEM_NUM ORDER BY FILE_NUM) AS RN " 
         + "            FROM IMAGEFILE " 
         + "        ) AS inner_query " 
         + "        WHERE RN = 1 "
         + "    ) I ON B.BOARD_NUM = I.BOARD_ITEM_NUM " 
         + "    WHERE B.BOARD_WRITER_ID = ? " 
         + ") MB " 
         + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
   // 공지사항 전체 보기
   private final String BOARD_SELECTALL_NOTICE = "SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_WRITER_ID, " 
         + "       M.MEMBER_NICKNAME, B.BOARD_REGISTRATION_DATE " 
         + "FROM BOARD B " 
         + "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID " 
         + "WHERE M.MEMBER_ID IN ( " 
         + "    SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ROLE = 'ADMIN' " 
          + ") " 
          + "ORDER BY B.BOARD_NUM DESC LIMIT ?, 10";
   // 크롤링 : 전체 게시물 번호 출력
   private final String BOARD_SELECTALL_CRAWLING = "SELECT BOARD_NUM FROM BOARD";


   @Autowired
   private JdbcTemplate jdbcTemplate;

   public boolean insert(BoardDTO boardDTO) { // 게시글 등록
      System.out.println("====model.BoardDAO2.insert 시작");

      String board_title = boardDTO.getBoard_title(); // 제목
      String board_content = boardDTO.getBoard_content(); // 내용
      String board_writer_id = boardDTO.getBoard_writer_id(); // 작성자

      // 제목, 내용, 작성자를 담아서 쿼리문을 수행한다.
      int result = jdbcTemplate.update(BOARD_INSERT, board_title, board_content, board_writer_id);

      System.out.println("====model.BoardDAO2.insert result : ["+result+"]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.BoardDAO2.insert 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.BoardDAO2.insert 성공");
      return true; // 성공적으로 등록 완료, true를 반환한다.
   }

   public boolean update(BoardDTO boardDTO) { // 게시글 수정
      System.out.println("====model.BoardDAO2.update 시작");
      
      String board_title = boardDTO.getBoard_title(); // 제목
      String board_content = boardDTO.getBoard_content(); // 내용
      int board_num = boardDTO.getBoard_num(); // 게시글 번호

      // 제목, 내용, 게시글 번호를 담아서 쿼리문을 수행한다.
      int result = jdbcTemplate.update(BOARD_UPDATE, board_title, board_content, board_num);

      System.out.println("====model.BoardDAO2.update result : ["+result+"]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.BoardDAO2.update 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.BoardDAO2.update 종료");
      return true;// 성공적으로 수정 완료, true를 반환한다.
   }

   public boolean delete(BoardDTO boardDTO) { // 게시글 삭제
      System.out.println("====model.BoardDAO2.delete 시작");

      // 게시글 번호를 담아서 쿼리문을 수행한다.
      int result = jdbcTemplate.update(BOARD_DELETE, boardDTO.getBoard_num()); // 게시글 번호
      
      System.out.println("====model.BoardDAO2.delete result : ["+result+"]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.BoardDAO2.delete 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.BoardDAO2.delete 종료");
      return true; // 성공적으로 삭제 완료, true를 반환한다.
   }

   public List<BoardDTO> selectAll(BoardDTO boardDTO) { // 게시물 전체 출력
      List<BoardDTO> datas = new ArrayList<>();
      System.out.println("====model.BoardDAO2.selectAll 시작");
      
      String search_criteria =boardDTO.getBoard_search_criteria(); // 게시판 정렬 기준
      String searchKeyword =boardDTO.getBoard_searchKeyword(); // 검색어
      int page_num =boardDTO.getBoard_page_num(); // 페이지 번호
      String writer_id =boardDTO.getBoard_writer_id(); // 작성자 아이디
      
      // 만약 컨디션이 'BOARD_ALL'라면 - 전체 게시물 목록 보기
      if (boardDTO.getBoard_condition().equals("BOARD_ALL")) {
         Object[] args = { search_criteria,search_criteria,searchKeyword,searchKeyword,
               searchKeyword,page_num, page_num};
         datas = jdbcTemplate.query(BOARD_SELECTALL_USER, args, new BoardRowMapper_all());
      }
      // 만약 컨디션이 'NOTICE_ALL'라면 - 전체 공지사항 목록 보기
      else if (boardDTO.getBoard_condition().equals("NOTICE_ALL")) {
         Object[] args = { page_num, page_num};
         datas = jdbcTemplate.query(BOARD_SELECTALL_ADMIN, args, new BoardRowMapper_all());
      }
      // 만약 컨디션이 'MYBOARD_LIST'라면 - 내 게시물 목록 보기
      else if (boardDTO.getBoard_condition().equals("MYBOARD_LIST")) {
         Object[] args = { writer_id, page_num,page_num };
         datas = jdbcTemplate.query(BOARD_SELECTALL_MY_BOARD, args, new BoardRowMapper_all());
      } 
      // 만약 컨디션이 'BOARD_SELECTALL_NOTICE'라면 - 공지사항 전체 보기
      else if (boardDTO.getBoard_condition().equals("NOTICE_SELECT_ALL")) {
         Object[] args = {page_num};
         datas = jdbcTemplate.query(BOARD_SELECTALL_NOTICE, args, new BoardRowMapper_one_notice());
      } 
      // 만약 컨디션이 'BOARD_SELECTALL_CRAWLING'라면 - 크롤링을 위해 전체 게시물 번호 받기
      else if (boardDTO.getBoard_condition().equals("BOARD_SELECTALL_CRAWLING")) {
         datas = jdbcTemplate.query(BOARD_SELECTALL_CRAWLING, new BoardRowMapper_all_crawling());
      } 
      // 해당하는 컨디션이 없다면,
      else {
         System.err.println("====model.BoardDAO2.selectAll 컨디션 에러");
         return null; // null을 반환한다.
      }
      
      // 쿼리문을 수행한 데이터를 반환한다. 
      System.out.println("====model.BoardDAO2.selectAll datas : ["+datas+"]");
      System.out.println("====model.BoardDAO2.selectAll 종료");
      return datas;
   }

   public BoardDTO selectOne(BoardDTO boardDTO) { // 선택 게시물 보기
      BoardDTO data;
      System.out.println("====model.BoardDAO2.selectOne 시작");
      System.out.println("====model.BoardDAO2.selectOne boardDTO.getBoard_condition() : ["
            + boardDTO.getBoard_condition() + "]");
      
      int board_num = boardDTO.getBoard_num(); // 게시물 번호
      String searchKeyword =boardDTO.getBoard_searchKeyword(); // 검색어
      String writer_id =boardDTO.getBoard_writer_id(); // 작성자 아이디
      
      try {
         // 만약 컨디션이 'BOARD_SELECT_ONE'라면 - 글 상세페이지 출력
         if (boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) {
            Object[] args = { board_num }; 
            data = jdbcTemplate.queryForObject(BOARD_SELECTONE, args, new BoardRowMapper_one());
         }
         // 만약 컨디션이 'BOARD_SELECTONE_COUNT'라면 - 전체 게시물 총 개수
         else if (boardDTO.getBoard_condition().equals("BOARD_SELECTONE_COUNT")) {
            data = jdbcTemplate.queryForObject(BOARD_SELECTONE_COUNT, new BoardRowMapper_one_count());
         }
         // 만약 컨디션이 'BOARD_SELECTONE_USER_COUNT'라면 - 일반회원 게시물 총 개수
         else if (boardDTO.getBoard_condition().equals("BOARD_SELECTONE_USER_COUNT")) {
            data = jdbcTemplate.queryForObject(BOARD_SELECTONE_USER_COUNT, new BoardRowMapper_one_count());
         }
         // 만약 컨디션이 'BOARD_SELECTONE_NOTICE_COUNT'라면 - 공시사항 총 개수
         else if (boardDTO.getBoard_condition().equals("NOTICE_TOTAL_COUNT")) {
            data = jdbcTemplate.queryForObject(BOARD_SELECTONE_NOTICE_COUNT, new BoardRowMapper_one_count());
         }
         // 만약 컨디션이 'BOARD_NUM_SELECTONE'라면 - 가장 최근 게시글 번호를 받아오기
         else if (boardDTO.getBoard_condition().equals("BOARD_NUM_SELECTONE")) {
            data = jdbcTemplate.queryForObject(BOARD_SELECTONE_NUM, new BoardRowMapper_one_num());
         }
         // 만약 컨디션이 'BOARD_PAGE_COUNT'라면 - 페이지네이션: 전체 페이지 수 반환
         else if (boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
            Object[] args;
            // 만약 검색어가 있다면 - 검색 결과 총 패이지 개수
            if (boardDTO.getBoard_searchKeyword() != null && !boardDTO.getBoard_searchKeyword().isEmpty()) {
               args = new Object[] { searchKeyword, searchKeyword };
               data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE + " " + BOARD_SEARCH_PAGE, args, new BoardRowMapper_one_page_count());
            } 
            // 만약 작성자가 있다면 - 작성자 결과 총 페이지 개수
            else if (boardDTO.getBoard_writer_id() != null && !boardDTO.getBoard_writer_id().isEmpty()) {
               args = new Object[] { writer_id };
               data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE + " " + BOARD_MYBOARD_PAGE, args, new BoardRowMapper_one_page_count());
            } 
            // 둘 다 없다면 - 전체 페이지 개수
            else {
               data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE, new BoardRowMapper_one_page_count());
            }
         } 
         // 해당하는 컨디션이 없다면,
         else {
            System.err.println("====model.BoardDAO2.selectOne 컨디션 실패");
            return null; // null을 반환한다.
         }
      } catch (Exception e) { // 예외가 발생한다면,
         System.err.println("====model.BoardDAO2.selectOne Exception 발생");
         return null; // null을 반환한다.
      }

      // 쿼리문을 수행한 데이터를 반환한다. 
      System.out.println("====model.BoardDAO2.selectOne data : ["+data+"]");
      System.out.println("====model.BoardDAO2.selectOne 종료");
      return data;
   }

}
//======================================================RowMapper=================================================================
// 게시판 전체 정보를 반환하는 RowMapper
class BoardRowMapper_all implements RowMapper<BoardDTO> {
   
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_all 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_num(rs.getInt("BOARD_NUM")); // 게시판 번호
      data.setBoard_title(rs.getString("BOARD_TITLE")); // 게시판 제목
      data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자(아이디)
      data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 (닉네임)
      data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 게시판 작성일
      data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 좋아요 개수
      data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로
      
      System.out.println("====model.BoardRowMapper_all 반환");
      return data;
   }
}

// 공지사항 상세 정보를 반환하는 RowMapper
class BoardRowMapper_one_notice implements RowMapper<BoardDTO> {
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_one_notice 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
      data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
      data.setBoard_writer_id(rs.getString("BOARD_WRITER_ID")); // 작성자 ID
      data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 작성자 (닉네임)
      data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
      
      System.out.println("====model.BoardRowMapper_one_notice 반환");
      return data;
   }
}

// 크롤링을 위해 게시물 전체 번호를 반환하는 RowMapper
class BoardRowMapper_all_crawling implements RowMapper<BoardDTO> {
   
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_all_crawling 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_num(rs.getInt("BOARD_NUM")); // 게시판 번호
      
      System.out.println("====model.BoardRowMapper_all_crawling 반환");
      return data;
   }
}

// 상세 게시물 정보를 반환하는 RowMapper
class BoardRowMapper_one implements RowMapper<BoardDTO> {
   // 게시판 전체 출력
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_one 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
      data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
      data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
      data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
      data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
      data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 작성자 (닉네임)
      data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
      
      System.out.println("====model.BoardRowMapper_one 반환");
      return data;
   }
}

// 게시물 개수를 반환하기 위한 RowMapper
class BoardRowMapper_one_count implements RowMapper<BoardDTO> {
   
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_one_count 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_total_cnt(rs.getInt("BOARD_TOTAL_CNT")); // 게시물 개수
      
      System.out.println("====model.BoardRowMapper_one_count 반환");
      return data;
   }
}

// 크롤링을 위한 제일 큰 PK 찾기 RowMapper
class BoardRowMapper_one_num implements RowMapper<BoardDTO> {
   
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_one_num 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_num(rs.getInt("MAX_NUM")); // 제일 큰 게시판 번호
      
      System.out.println("====model.BoardRowMapper_one_num 반환");
      return data;
   }
}

// 페이지 개수를 반환하기 위한 RowMapper
class BoardRowMapper_one_page_count implements RowMapper<BoardDTO> {
   
   @Override
   public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.BoardRowMapper_one_page_count 실행");
      
      BoardDTO data = new BoardDTO();
      data.setBoard_total_page(rs.getInt("BOARD_TOTAL_PAGE")); // 전체 페이지 수
      
      System.out.println("====model.BoardRowMapper_one_page_count 반환");
      return data;
   }
}