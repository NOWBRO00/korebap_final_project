package com.korebap.app.biz.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class ReservationDAO {
	// 예약 생성
	private final String RESERVATION_INSERT = "INSERT INTO RESERVATION (RESERVATION_PAYMENT_NUM, RESERVATION_REGISTRATION_DATE) VALUES (?, ?)";
	// 예약 내용 변경 (예약 상태 변경)
	private final String RESERVATION_UPDATE = "UPDATE RESERVATION SET RESERVATION_STATUS = '예약취소' WHERE RESERVATION_NUM = ?";
	// 사장님 예약건 전체보기
	private final String RESERVATION_OWNER_SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS\r\n"
			+ "FROM RESERVATION R JOIN PRODUCT P ON R.RESERVATION_PRODUCT_NUM = P.PRODUCT_NUM\r\n"
			+ "WHERE P.PRODUCT_SELLER_ID= ?";
	// 내 예약 전체보기
	private final String RESERVATION_SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, P.PAYMENT_PRICE "
			+ "FROM RESERVATION R " + "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "WHERE P.PAYMENT_MEMBER_ID = ? ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";
	// 내 예약 상세보기
	private final String RESERVATION_SELECTONE = "SELECT P.PAYMENT_NUM, R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, "
			+ "P.PAYMENT_PRICE, P.PAYMENT_REGISTRATION_DATE, P.PAYMENT_METHOD, P.PAYMENT_STATUS, "
			+ "COALESCE(P.PAYMENT_MEMBER_ID, '탈퇴한 회원입니다.') AS PAYMENT_MEMBER_ID, "
			+ "COALESCE(M.MEMBER_NAME, '탈퇴한 회원입니다.') AS MEMBER_NAME, "
			+ "COALESCE(M.MEMBER_PHONE, '탈퇴한 회원입니다.') AS MEMBER_PHONE, " + "F.FILE_DIR " + "FROM RESERVATION R "
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "LEFT JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID " + "LEFT JOIN ( "
			+ "    SELECT FILE_DIR, PRODUCT_ITEM_NUM, "
			+ "           ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS ROW_NUM "
			+ "    FROM IMAGEFILE " + ") F ON F.ROW_NUM = 1 AND PR.PRODUCT_NUM = F.PRODUCT_ITEM_NUM "
			+ "WHERE R.RESERVATION_NUM = ?";
	// 사장님 예약 상세보기
	private final String RESERVATION_OWNER_SELECTONE = "SELECT R.RESERVATION_NUM,R.RESERVATION_REGISTRATION_DATE,R.RESERVATION_STATUS,COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM,\r\n"
			+ "    COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME,P.PAYMENT_PRICE,\r\n"
			+ "    M.MEMBER_ID AS MEMBER_ID,M.MEMBER_NAME AS MEMBER_NAME,M.MEMBER_PHONE AS MEMBER_PHONE  \r\n"
			+ "FROM RESERVATION R\r\n"
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM\r\n"
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM\r\n"
			+ "JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID  -- 일반회원 정보를 가져오기 위한 조인\r\n"
			+ "WHERE PR.PRODUCT_SELLER_ID = ? \r\n"
			+ "ORDER BY R.RESERVATION_REGISTRATION_DATE DESC;";
	// 가장 마지막에 저장된 PK 번호 보여주기
	private final String SELECTONE_LAST_NUM = "SELECT RESERVATION_NUM FROM RESERVATION ORDER BY RESERVATION_NUM DESC LIMIT 1";

	
	public boolean insert(ReservationDTO reservationDTO) { // 예약 생성
		System.out.println("====model.reservationDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			Date utilDate = reservationDTO.getReservation_registration_date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // 날짜 sql타입으로 변환
			pstmt = conn.prepareStatement(RESERVATION_INSERT);
			pstmt.setInt(1, reservationDTO.getReservation_payment_num()); // 결제 번호
			pstmt.setDate(2, sqlDate); // 변환한 예약일

			int result = pstmt.executeUpdate();
			System.out.println("	model.reservationDAO.insert result : " + result);
			if (result <= 0) {
				System.out.println("====model.reservationDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.reservationDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.reservationDAO.insert SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.reservationDAO.insert 종료");
		}
		return true;
	}

	public boolean update(ReservationDTO reservationDTO) { // 예약 상태 변경(완료 > 취소)
		System.out.println("====model.reservationDAO.update 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(RESERVATION_UPDATE);
			pstmt.setInt(1, reservationDTO.getReservation_num()); // 예약 번호 - 찾을 데이터

			int result = pstmt.executeUpdate();
			System.out.println("	model.reservationDTO.update result : [" + result + "]");
			if (result <= 0) {
				System.out.println("====model.reservationDTO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.reservationDTO.update 행 변경 성공");
		} catch (SQLException e) {
			System.out.println("====model.reservationDTO.update SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.reservationDTO.update 종료");
		}
		return true;
	}

	public ArrayList<ReservationDTO> selectAll(ReservationDTO reservationDTO) { // 전체 출력
		System.out.println("====model.reservationDAO.selectAll 시작");
		// JDBC 연결, 반환 객체 생성
		ArrayList<ReservationDTO> datas = new ArrayList<ReservationDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	model.reservationDAO.selectAll reservationDTO.getReservation_condition() : ["
					+ reservationDTO.getReservation_condition() + "]");
			if (reservationDTO.getReservation_condition().equals("RESERVATION_SELECTALL")) {
				pstmt = conn.prepareStatement(RESERVATION_SELECTALL);
				pstmt.setString(1, reservationDTO.getReservation_member_id()); // 회원 ID
			} else if (reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTALL")) {
				pstmt = conn.prepareStatement(RESERVATION_OWNER_SELECTALL);
				pstmt.setString(1, reservationDTO.getReservation_seller_id()); // 사장님 ID
			} else {
				System.out.println("====model.reservationDAO.selectAll 컨디션 에러");
			}

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("====model.reservationDAO.selectAll rs.next() 시작");
				ReservationDTO data = new ReservationDTO();
				data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
				java.sql.Date sqlDate = rs.getDate("RESERVATION_REGISTRATION_DATE"); // DB에 저장된 예약일
				data.setReservation_registration_date(new Date(sqlDate.getTime())); // 예약일 (상품 이용일)
				data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
				data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
				data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
				data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
				datas.add(data);
				System.out.println("	model.reservationDAO.selectAll datas : [" + datas + "]");
			}
		} catch (SQLException e) {
			System.out.println("====model.reservationDAO.selectAll SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.reservationDAO.selectAll 종료");
		}
		return datas;
	}

	public ReservationDTO selectOne(ReservationDTO reservationDTO) { // 한개 출력
		System.out.println("====model.reservationDAO.selectOne 시작");
		// JDBC 연결
		ReservationDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("====model.reservationDAO.selectOne reservationDTO.getReservation_condition() : ["+reservationDTO.getReservation_condition()+"]");
			if(reservationDTO.getReservation_condition().equals("RESERVATION_SELECTONE")) {
				pstmt = conn.prepareStatement(RESERVATION_SELECTONE);
				pstmt.setInt(1, reservationDTO.getReservation_num()); // 예약 번호 (PK)
			}
			else if(reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTONE")) {
				pstmt = conn.prepareStatement(RESERVATION_OWNER_SELECTONE);
				pstmt.setString(1, reservationDTO.getReservation_seller_id()); // 예약 번호 (PK)
			}
			else if(reservationDTO.getReservation_condition().equals("RESERVATION_LAST_NUM")) {
				pstmt = conn.prepareStatement(SELECTONE_LAST_NUM);
			}
			else {
				System.err.println("====model.reservationDAO.selectOne 컨디션 에러");
			}
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("====model.reservationDAO.selectOne rs.next() 시작");
				data = new ReservationDTO();
				data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
				data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
				data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
				data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
				data.setReservation_product_file_dir(rs.getString("FILE_DIR")); // 예약 상품 썸네일
				data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
				data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
				data.setReservation_payment_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
				data.setReservation_payment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
				data.setReservation_payment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (카드, 페이)
				data.setReservation_payment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제 완료, 결제 취소)
				data.setReservation_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
				data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 예약자 성명
				data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 예약자 핸드폰 번호
				System.out.println("model.reservationDTO.selectOne data : [" + data + "]");
			}
		} catch (SQLException e) {
			System.err.println("====model.reservationDAO.selectOne SQL문 실패");
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("===model.reservationDAO.selectOne 종료");
		}
		return data;
	}

	// 기능 미구현으로 false 반환
	public boolean delete(ReservationDTO reservationDTO) { // 삭제
		return false;
	}
}
