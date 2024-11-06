package com.korebap.app.biz.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO2 {
   // 회원가입
   private final String MEMBER_INSERT = "INSERT INTO MEMBER "
         + "(MEMBER_ID, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_ROLE) "
         + "VALUES (?, ?, ?, ?, ?, ?, ?)";
   // 회원 탈퇴
   private final String MEMBER_DELETE = "DELETE FROM MEMBER "
         + "WHERE MEMBER_ID=?";
   // 회원 정보 수정
   private final String MEMBER_UPDATE_PROFILE = "UPDATE MEMBER "
         + "SET MEMBER_PROFILE = ? "
         + "WHERE MEMBER_ID = ?";
   // 비밀번호 변경
   private final String MEMBER_UPDATE_PASSWORD = "UPDATE MEMBER "
         + "SET MEMBER_PASSWORD = ? "
         + "WHERE MEMBER_ID = ?";
   // 이름+ 닉네임 변경
   private final String MEMBER_UPDATE_NAME_NICKNAME = "UPDATE MEMBER " 
         + "SET MEMBER_NAME = COALESCE(NULLIF(?, ''), MEMBER_NAME), " 
         + "    MEMBER_NICKNAME = COALESCE(NULLIF(?, ''), MEMBER_NICKNAME) " 
         + "WHERE MEMBER_ID = ?";
   // 회원 비활성화 (계정 차단)
   private final String MEMBER_UPDATE_ROLE = "UPDATE MEMBER "
         + "SET MEMBER_ROLE = 'BANNED' "
         + "WHERE MEMBER_ID = ?";
   // 회원 전체 수 출력
   private final String MEMBER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) AS MEMBER_TOTAL_CNT FROM MEMBER";
   // 일반회원 전체 수 출력
   private final String MEMBER_SELECTONE_USER_COUNT = "SELECT COUNT(MEMBER_ID) AS MEMBER_TOTAL_CNT FROM MEMBER "
         + "WHERE MEMBER_ROLE='USER'";
   // 사장님 전체 수 출력
   private final String MEMBER_SELECTONE_OWNER_COUNT = "SELECT COUNT(MEMBER_ID) AS MEMBER_TOTAL_CNT FROM MEMBER "
         + "WHERE MEMBER_ROLE='OWNER'";
   // 아이디 중복검사 - 비동기 중복 검사
   private final String MEMBER_SELECTONE_ID = "SELECT MEMBER_ID FROM MEMBER "
         + "WHERE MEMBER_ID = ?";
   // 닉네임 중복검사 - 비동기 중복 검사
   private final String MEMBER_SELECTONE_NICKNAME = "SELECT MEMBER_NICKNAME FROM MEMBER "
         + "WHERE MEMBER_NICKNAME = ?";
   // 비밀번호 확인 - 비동기 중복 검사
   private final String MEMBER_SELECTONE_PASSWORD = "SELECT MEMBER_ID, MEMBER_PASSWORD FROM MEMBER "
         + "WHERE MEMBER_ID = ?";
   // 로그인
   private final String MEMBER_SELECTONE_LOGIN = "SELECT MEMBER_ID, MEMBER_ROLE, MEMBER_PASSWORD, MEMBER_NICKNAME, " 
         + "       MEMBER_NAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE " 
         + "FROM MEMBER "
         + "WHERE MEMBER_ID = ?  AND MEMBER_PASSWORD = ?";
   // 마이페이지
   private final String MEMBER_SELECTONE_MYPAGE = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_PROFILE, MEMBER_ROLE "
         + "FROM MEMBER "
         + "WHERE MEMBER_ID = ?"; 
   // 크롤링 : 회원 ID 랜덤으로 출력을 위한 member_id 전체 select
   private final String MEMBER_RANDOM_ID = "SELECT MEMBER_ID FROM MEMBER ORDER BY RAND() LIMIT 1";
   // 크롤링 : 사장님 ID 랜덤으로 출력을 위한 member_id 전체 select
   private final String MEMBER_RANDOM_OWNER_ID = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ROLE = 'OWNER' ORDER BY RAND() LIMIT 1";
   // 페이지네이션 : 페이지 개수
   private final String MEMBER_SELECONE_PAGE_CNT = "SELECT CEIL(COALESCE(COUNT(MEMBER_ID), 0) / 15.0) AS MEMBER_TOTAL_PAGE "
         + "FROM MEMBER "
         + "WHERE 1 = 1 AND MEMBER_ROLE = ? AND (? IS NULL OR MEMBER_ID LIKE CONCAT('%', ?, '%'))";
   // 페이지네이션 : 일반회원, 사장님 구분하여 전체보기 + 검색어로 찾기
   private final String MEMBER_SELECTALL = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_REGISTRATION_DATE, MEMBER_PHONE " 
         + "FROM ( " 
         + "    SELECT MEMBER_ID, MEMBER_NAME, MEMBER_REGISTRATION_DATE, MEMBER_PHONE, " 
         + "           ROW_NUMBER() OVER (ORDER BY MEMBER_REGISTRATION_DATE) AS ROW_NUM " 
         + "    FROM MEMBER " 
         + "    WHERE MEMBER_ROLE = ? " 
         + "      AND (? IS NULL OR MEMBER_ID LIKE CONCAT('%', ?, '%')) " 
         + ") AS SUBQUERY " 
         + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 15 + 1 AND COALESCE(?, 1) * 15";

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public boolean insert(MemberDTO memberDTO) { // 회원가입
      System.out.println("====member.MemberDAO2.insert 시작");

      String member_id = memberDTO.getMember_id(); // 아이디
      String member_password = memberDTO.getMember_password(); // 비밀번호
      String member_nickname = memberDTO.getMember_nickname(); // 닉네임
      String member_name = memberDTO.getMember_name(); // 이름
      String member_phone = memberDTO.getMember_phone(); // 전화번호
      String member_address = memberDTO.getMember_address(); // 주소
      String member_role = memberDTO.getMember_role(); // 역할

      int result = jdbcTemplate.update(MEMBER_INSERT, member_id, member_password, member_nickname, member_name,
            member_phone, member_address, member_role); // 값을 담아서 쿼리문을 수행한다.

      System.out.println("====member.MemberDAO2.insert result : ["+result+"]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====member.MemberDAO2.insert 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====member.MemberDAO2.insert 종료");
      return true; // 성공적으로 삽입 완료, true를 반환한다.
   }

   public boolean update(MemberDTO memberDTO) { // 회원 정보 수정
      int result;
      System.out.println("====member.MemberDAO2.update 시작");
      System.out.println("====member.MemberDAO2.update memberDTO.getMember_condition() : ["
            + memberDTO.getMember_condition() + "]");

      String member_id = memberDTO.getMember_id();
      String member_password = memberDTO.getMember_password();
      String member_profile = memberDTO.getMember_profile();
      String member_nickname = memberDTO.getMember_nickname();
      String member_name = memberDTO.getMember_name();

      // 프로필(사진) 변경 : 회원 프로필, 아이디를 담아서 쿼리문을 수행한다.
      if (memberDTO.getMember_condition().equals("MEMBER_PROFILE")) {
         result = jdbcTemplate.update(MEMBER_UPDATE_PROFILE, member_profile, member_id);
      }
      // 비밀번호 변경 : 비밀번호, 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD")) {
         result = jdbcTemplate.update(MEMBER_UPDATE_PASSWORD, member_password, member_id);
      }
      // 이름+닉네임 변경 : 이름, 닉네임, 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_NAME")) {
         result = jdbcTemplate.update(MEMBER_UPDATE_NAME_NICKNAME, member_name, member_nickname, member_id);
      }
      // 계정 차단 : 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_ROLE")) {
         result = jdbcTemplate.update(MEMBER_UPDATE_ROLE, member_id);
      }
      // 해당하는 컨디션이 없다면,
      else {
         System.err.println("====member.MemberDAO2.update 컨디션 에러");
         return false; // false를 반환한다.
      }

      System.out.println("====member.MemberDAO2.update result : ["+result+"]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====member.MemberDAO2.update 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====member.MemberDAO2.update 종료");
      return true; // 성공적으로 삽입 완료, true를 반환한다.
   }

   public boolean delete(MemberDTO memberDTO) { // 회원 탈퇴
      System.out.println("====member.MemberDAO2.delete 시작");

      // 아이디를 담아서 쿼리문을 수행한다.
      int result = jdbcTemplate.update(MEMBER_DELETE, memberDTO.getMember_id());
      System.out.println("====member.MemberDAO2.delete result : ["+result+"]");

      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====member.MemberDAO2.delete 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====member.MemberDAO2.delete 종료");
      return true; // 성공적으로 삽입 완료, true를 반환한다.
   }

   public List<MemberDTO> selectAll(MemberDTO memberDTO) { // 회원 전체 출력
      List<MemberDTO> datas = new ArrayList<>(); // 배열에 담아 전달하기 위해 list를 생성한다.
      System.out.println("====member.MemberDAO2.selectAll 시작");

      String member_role = memberDTO.getMember_role();
      String member_searchkeyword = memberDTO.getMember_searchkeyword();
      int member_page_num = memberDTO.getMember_page_num();

      // 역할, 검색어, 페이지를 담아서 쿼리문을 수행하고 반환받은 데이터를 리스트에 담는다.
      Object[] args = { member_role, member_searchkeyword, member_searchkeyword, member_page_num, member_page_num };
      datas = jdbcTemplate.query(MEMBER_SELECTALL, args, new MemberRowMapper_all());

      System.out.println("====member.MemberDAO2.selectAll datas : ["+datas+"]");
      System.out.println("====member.MemberDAO2.selectAll 종료");
      return datas; // 데이터가 담긴 리스트를 반환한다.
   }

   public MemberDTO selectOne(MemberDTO memberDTO) {
      MemberDTO data = null;
      System.out.println("====member.MemberDAO2.selectOne 시작");
      System.out.println("====member.MemberDAO2.selectOne memberDTO.getMember_condition() : ["
            + memberDTO.getMember_condition() + "]");

      String member_id = memberDTO.getMember_id();
      String member_password = memberDTO.getMember_password();
      String member_nickname = memberDTO.getMember_nickname();
      String member_role = memberDTO.getMember_role();
      String member_searchkeyword = memberDTO.getMember_searchkeyword();

      // 회원 전체 수 반환을 위해 쿼리문을 수행한다.
      if (memberDTO.getMember_condition().equals("MEMBER_SELECTONE_COUNT")) {
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_COUNT, new MemberRowMapper_one_count());
      }
      // 일반 회원 수 반환을 위해 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_USER_SELECTONE_COUNT")) {
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_USER_COUNT, new MemberRowMapper_one_count());
      }
      // 사장님 수 반환을 위해 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_OWNER_SELECTONE_COUNT")) {
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_OWNER_COUNT, new MemberRowMapper_one_count());
      }
      // 이메일 중복검사 : 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) {
         Object[] args = { member_id };
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_ID, args, new MemberRowMapper_one_id());
      }
      // 닉네임 중복검사 : 닉네임을 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) {
         Object[] args = { member_nickname };
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_NICKNAME, args, new MemberRowMapper_one_nickname());
      }
      // 비밀번호 확인 : 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) {
         Object[] args = { member_id };
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_PASSWORD, args, new MemberRowMapper_one_password());
      }
      // 로그인 : 아이디, 비밀번호를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("LOGIN")) {
         Object[] args = { member_id, member_password };
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_LOGIN, args, new MemberRowMapper_one_login());
      }
      // 마이페이지 : 아이디를 담아서 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("MYPAGE")) {
         Object[] args = { member_id };
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_MYPAGE, args, new MemberRowMapper_one_mypage());
      }
      // 크롤링을 위한 랜덤 아이디(전체 멤버) 출력을 위해 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) {
         data = jdbcTemplate.queryForObject(MEMBER_RANDOM_ID, new MemberRowMapper_one_id());
      }
      // 크롤링을 위한 랜덤 아이디(사장님) 출력을 위해 쿼리문을 수행한다.
      else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_OWNER_ID")) {
         data = jdbcTemplate.queryForObject(MEMBER_RANDOM_OWNER_ID, new MemberRowMapper_one_id());
      }
      // 페이지수 반환 : 역할, 검색어를 담아서 수행한다.
      else if (memberDTO.getMember_condition().equals("MEMBER_SELECONE_PAGE_CNT")) {
         Object[] args = { member_role, member_searchkeyword, member_searchkeyword };
         data = jdbcTemplate.queryForObject(MEMBER_SELECONE_PAGE_CNT, args, new MemberRowMapper_one_page_cnt());
      }
      // 해당하는 컨디션이 없다면,
      else {
         System.err.println("====member.MemberDAO2.selectOne 컨디션 에러");
         return null; // null을 반환한다.
      }

      // 쿼리문을 수행한 데이터를 반환한다.
      System.out.println("====member.MemberDAO2.selectOne data: ["+data+"]");
      System.out.println("====member.MemberDAO2.selectOne 종료");
      return data;
   }
}
//======================================================RowMapper=================================================================
//멤버 인원수 반환하는 RowMapper
class MemberRowMapper_one_count implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_count 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_total_cnt(rs.getInt("MEMBER_TOTAL_CNT"));

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_count 반환");
      return data;
   }
}

// 아이디를 반환하는 RowMapper
class MemberRowMapper_one_id implements RowMapper<MemberDTO> {
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_id 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_id 반환");
      return data;
   }
}

// 닉네임을 반환하는 RowMapper
class MemberRowMapper_one_nickname implements RowMapper<MemberDTO> {
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_nickname 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_nickname 반환");
      return data;
   }
}

//비밀번호를 반환하는 RowMapper
class MemberRowMapper_one_password implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_password 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 회원의 비밀번호

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_password 반환");
      return data;
   }
}

// 아이디, 역할을 반환하는 RowMapper
class MemberRowMapper_one_login implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_login 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_role(rs.getString("MEMBER_ROLE")); // 롤

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_login 반환");
      return data;
   }
}

// 아이디, 이름, 닉네임, 핸드폰 번호, 주소, 프로필 사진을 반환하는 RowMapper
class MemberRowMapper_one_mypage implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_mypage 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
      data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
      data.setMember_phone(rs.getString("MEMBER_PHONE")); // 핸드폰 번호
      data.setMember_address(rs.getString("MEMBER_ADDRESS")); // 주소
      data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
      data.setMember_role(rs.getString("MEMBER_ROLE")); // 유저 역할

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_mypage 반환");
      return data;
   }
}

//페이지 개수 반환하는 RowMapper
class MemberRowMapper_one_page_cnt implements RowMapper<MemberDTO> {
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_page_cnt 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_total_page(rs.getInt("MEMBER_TOTAL_PAGE")); // 페이지 총 수

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_page_cnt 반환");
      return data;
   }
}

//회원 목록을 반환하는 RowMapper
class MemberRowMapper_all implements RowMapper<MemberDTO> {
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_all 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
      data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입 날짜
      data.setMember_phone(rs.getString("MEMBER_PHONE")); // 전화번호
      
      System.out.println("====member.MemberDAO2.MemberRowMapper_all 반환");

      return data;
   }
}
