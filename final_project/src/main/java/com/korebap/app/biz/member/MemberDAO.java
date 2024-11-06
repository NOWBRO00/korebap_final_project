package com.korebap.app.biz.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class MemberDAO {
	// 회원가입
	private final String MEMBER_INSERT = "INSERT INTO MEMBER (MEMBER_ID, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_ROLE) \r\n"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	// 회원 탈퇴
	private final String MEMBER_DELETE = "DELETE FROM MEMBER WHERE MEMBER_ID=?";
	// 회원 정보 수정
	private final String MEMBER_UPDATE_PROFILE = "UPDATE MEMBER SET MEMBER_PROFILE = ? WHERE MEMBER_ID = ?"; // 프로필 사진
	// 비밀번호 변경																									// 변경
	private final String MEMBER_UPDATE_PASSWORD = "UPDATE MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_ID = ?"; // 비밀번호
	// 이름+ 닉네임 변경																								// 변경
	private final String MEMBER_UPDATE_NAME_NICKNAME = "UPDATE MEMBER SET MEMBER_NAME = COALESCE(?, MEMBER_NAME), MEMBER_NICKNAME = COALESCE(?, MEMBER_NICKNAME) WHERE MEMBER_ID = ?"; // 이름
	// 회원 비활성화																																									// +
	private final String MEMBER_UPDATE_ROLE = "UPDATE MEMBER SET MEMBER_ROLE = 'BANNED' WHERE MEMBER_ID = ?"; // 계정 차단
	// 회원 전체보기
	private final String MEMBER_SELECTALL = "SELECT MEMBER_ID,MEMBER_NAME,MEMBER_NICKNAME,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_ROLE FROM MEMBERORDER BY MEMBER_REGISTRATION_DATE DESC";
	// 아이디로 회원 찾기
	private final String MEMBER_SEARCH_SELECTALL = "SELECT MEMBER_ID,MEMBER_NAME,MEMBER_NICKNAME,MEMBER_PHONE,MEMBER_ADDRESS,MEMBER_PROFILE FROM MEMBER WHERE MEMBER_ID LIKE CONCAT('%',?,'%');";
	// 회원 전체 수 출력
	private final String MEMBER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) FROM MEMBER";
	// 사장님 전체 수 출력
	private final String MEMBER_OWNER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) FROM MEMBER WHERE MEMBER_ROLE='OWNER'";
	// 아이디 중복검사 - 비동기 중복 검사
	private final String SELECTONE_MEMBER_ID = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID = ?"; // 아이디(이메일) 중복검사
	// 닉네임 중복검사 - 비동기 중복 검사
	private final String SELECTONE_MEMBER_NICKNAME = "SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_NICKNAME = ?"; // 닉네임
	// 로그인
	private final String SELECTONE_LOGIN = "SELECT MEMBER_ID, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE \r\n"
			+ "FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PASSWORD = ?"; // 로그인 확인
	// 마이페이지
	private final String SELECTONE_MYPAGE = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_PROFILE FROM MEMBER WHERE MEMBER_ID = ?"; // 마이페이지
	// 비밀번호 확인 -비동기
	private final String SELECTONE_MEMBER_PASSWORD = "SELECT MEMBER_ID, MEMBER_PASSWORD FROM MEMBER WHERE MEMBER_ID = ?"; // 회원의
	// 회원 ID 랜덤으로 출력을 위한 member_id 전체 select >> 크롤링
	private final String RANDOM_MEMBER_ID = "SELECT MEMBER_ID FROM MEMBER ORDER BY RAND() LIMIT 1";
	// 사장님 ID 랜덤으로 출력을 위한 member_id 전체 select >> 크롤링
	private final String RANDOM_MEMBER_OWNER_ID="SELECT * FROM MEMBER WHERE MEMBER_ROLE = 'OWNER' ORDER BY RAND() LIMIT 1";
	
	public boolean insert(MemberDTO memberDTO) { // 회원가입
		System.out.println("====member.MemberDAO.insert 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(MEMBER_INSERT);// INSERT 실행
			pstmt.setString(1, memberDTO.getMember_id()); // 아이디
			pstmt.setString(2, memberDTO.getMember_password()); // 비밀번호
			pstmt.setString(3, memberDTO.getMember_nickname()); // 닉네임
			pstmt.setString(4, memberDTO.getMember_name()); // 이름
			pstmt.setString(5, memberDTO.getMember_phone()); // 전화번호
			pstmt.setString(6, memberDTO.getMember_address()); // 주소
			pstmt.setString(7, memberDTO.getMember_role()); // 유저 구분

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				System.err.println("====member.MemberDAO.insert 행 변경 실패");
				return false;
			}
			System.err.println("====member.MemberDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====member.MemberDAO.insert SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 종료
			System.out.println("====member.MemberDAO.insert 종료");
		}
		return true;
	}

	public boolean update(MemberDTO memberDTO) { // 업데이트
		System.out.println("====member.MemberDAO.update 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("====member.MemberDAO.update 시작");
			System.out.println(
					"====member.MemberDAO.update member_condition : [" + memberDTO.getMember_condition() + "]");

			if (memberDTO.getMember_condition().equals("MEMBER_PROFILE")) { // 프로필(사진) 변경
				pstmt = conn.prepareStatement(MEMBER_UPDATE_PROFILE);
				pstmt.setString(1, memberDTO.getMember_profile()); // 새 프로필 사진
				pstmt.setString(2, memberDTO.getMember_id()); // 회원 아이디
			} else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD")) { // 비밀번호 변경
				pstmt = conn.prepareStatement(MEMBER_UPDATE_PASSWORD);
				pstmt.setString(1, memberDTO.getMember_password()); // 새 비밀번호
				pstmt.setString(2, memberDTO.getMember_id()); // 회원 아이디
			} else if (memberDTO.getMember_condition().equals("MEMBER_NAME")) { // 이름+닉네임 변경
				pstmt = conn.prepareStatement(MEMBER_UPDATE_NAME_NICKNAME);
				pstmt.setString(1, memberDTO.getMember_name()); // 회원 이름
				pstmt.setString(2, memberDTO.getMember_nickname()); // 회원 닉네임
				pstmt.setString(3, memberDTO.getMember_id()); // 회원 ID
			} else if (memberDTO.getMember_condition().equals("MEMBER_ROLE")) { // 계정 차단
				pstmt = conn.prepareStatement(MEMBER_UPDATE_ROLE);
				pstmt.setString(1, memberDTO.getMember_id()); // 회원 ID
			} else {
				System.err.println("====member.MemberDAO.update 컨디션 에러");
				return false;
			}

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				System.err.println("====member.MemberDAO.update 행 변경 실패");
				return false;
			}
			System.err.println("====member.MemberDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====member.MemberDAO.update SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 종료
			System.out.println("====member.MemberDAO.update 종료");
		}
		return true;
	}

	public boolean delete(MemberDTO memberDTO) { // 회원 탈퇴
		System.out.println("====member.MemberDAO.delete 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(MEMBER_DELETE);
			pstmt.setString(1, memberDTO.getMember_id());

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				System.err.println("====member.MemberDAO.delete 행 변경 실패");
				return false;
			}
			System.err.println("====member.MemberDAO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.out.println("====member.MemberDAO.delete SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====member.MemberDAO.delete 종료");
		}
		return true;
	}

	public List<MemberDTO> selectAll(MemberDTO memberDTO) { // 회원 전체 출력
		System.out.println("====member.MemberDAO.selectAll 시작");
		// JDBC 연결, 반환 객체 생성
		List<MemberDTO> datas = new ArrayList<MemberDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("====member.MemberDAO.selectAll memberDTO.getMember_condition() : ["
					+ memberDTO.getMember_condition() + "]");
			if (memberDTO.getMember_condition().equals("MEMBER_SELECTALL")) {
				pstmt = conn.prepareStatement(MEMBER_SELECTALL);
			} 
			else if (memberDTO.getMember_condition().equals("MEMBER_SEARCH_SELECTALL")) {
				pstmt = conn.prepareStatement(MEMBER_SEARCH_SELECTALL);
				pstmt.setString(1, memberDTO.getMember_searchkeyword());
			}
			else {
		    	System.err.println("====member.MemberDAO2.selectAll 컨디션 에러");
		    }
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO data = new MemberDTO();
				data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
				data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
				data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
				data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
				data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입 날짜
				data.setMember_role(rs.getString("MEMBER_ROLE"));
				System.out.println("====member.MemberDAO.selectAll data : [" + data + "]");
				datas.add(data);
			}

		} catch (SQLException e) {
			System.err.println("====member.MemberDAO.selectAll SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 연결 종료
			System.out.println("====member.MemberDAO.selectAll 종료");
		}
		return datas;
	}

	public MemberDTO selectOne(MemberDTO memberDTO) {// 한명 출력
		System.out.println("====member.MemberDAO.selectOne 시작");
		// JDBC 연결
		MemberDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	model.MemberDAO.selectOne condition : [" + memberDTO.getMember_condition() + "]");
			if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) { // 아이디(이메일) 중복검사
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_ID);
				pstmt.setString(1, memberDTO.getMember_id());
			} else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) { // 닉네임 중복검사
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_NICKNAME);
				pstmt.setString(1, memberDTO.getMember_nickname());
			} else if (memberDTO.getMember_condition().equals("LOGIN")) { // 로그인
				pstmt = conn.prepareStatement(SELECTONE_LOGIN);
				pstmt.setString(1, memberDTO.getMember_id());
				pstmt.setString(2, memberDTO.getMember_password());
			} else if (memberDTO.getMember_condition().equals("MYPAGE")) { // 마이페이지
				pstmt = conn.prepareStatement(SELECTONE_MYPAGE);
				pstmt.setString(1, memberDTO.getMember_id());
			} else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) { // 회원의 비밀번호 반환
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_PASSWORD);
				pstmt.setString(1, memberDTO.getMember_id());
			} else if (memberDTO.getMember_condition().equals("MEMBER_SELECTONE_COUNT")) { // 회원 전체 수 반환
				pstmt = conn.prepareStatement(MEMBER_SELECTONE_COUNT);
			} else if (memberDTO.getMember_condition().equals("MEMBER_OWNER_SELECTONE_COUNT")) { // 사장님 전체 수 반환
				pstmt = conn.prepareStatement(MEMBER_OWNER_SELECTONE_COUNT);
			} else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) { // 크롤링 관련
				pstmt = conn.prepareStatement(RANDOM_MEMBER_ID);
			} else {
				System.err.println("====model.MemberDAO.selectOne 컨디션 에러");
				return null;
			}

			System.out.println("model.MemberDAO.selectOne : id " + memberDTO.getMember_id());
			System.out.println("model.MemberDAO.selectOne : pw " + memberDTO.getMember_password());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("====model.MemberDAO.selectOne rs.next()실행");
				data = new MemberDTO();

				if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) { // 아이디(이메일) 중복검사
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
				} 
				else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) { // 닉네임 중복검사
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
				} 
				else if (memberDTO.getMember_condition().equals("LOGIN")) { // 로그인
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
				} 
				else if (memberDTO.getMember_condition().equals("MYPAGE")) { // 마이페이지
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_phone(rs.getString("MEMBER_PHONE")); // 핸드폰 번호
					data.setMember_address(rs.getString("MEMBER_ADDRESS")); // 주소
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
				} 
				else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) { // 회원의 비밀번호 반환
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 회원의 비밀번호
				} 
				else if (memberDTO.getMember_condition().equals("MEMBER_SELECTONE_COUNT")) { // 회원 전체 수 반환
					data.setMember_total_cnt(rs.getInt("MEMBER_TOTAL_CNT"));
				} 
				else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) { // 크롤링 때문에!
					data.setMember_id(rs.getString("MEMBER_ID"));
				}
				System.out.println("	member.MemberDAO.selectOne data : [" + data + "]");
			} else {
				System.out.println("====model.MemberDAO.selectOne rs.next() false");
			}
		} catch (SQLException e) {
			System.err.println("====model.MemberDAO.selectOne SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn); // 연결 종료
			System.out.println("====member.MemberDAO.selectOne 종료");
		}
		return data;
	}
}
