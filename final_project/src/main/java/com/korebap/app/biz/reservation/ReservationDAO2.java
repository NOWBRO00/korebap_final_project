package com.korebap.app.biz.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAO2 {
	// 예약 생성
	private final String RESERVATION_INSERT = "INSERT INTO RESERVATION "
			+ "(RESERVATION_PAYMENT_NUM, RESERVATION_REGISTRATION_DATE) " 
			+ "VALUES (?, ?)";

	// 예약 내용 변경 (예약 상태 변경)
	private final String RESERVATION_UPDATE = "UPDATE RESERVATION " 
			+ "SET RESERVATION_STATUS = '예약취소' " 
			+ "WHERE RESERVATION_NUM = ?";

	// 사장님 ID로 사장님 전체 예약건 보기
	private final String RESERVATION_OWNER_SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, "
			+ "COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, "
			+ "P.PAYMENT_PRICE, M.MEMBER_ID , " 
			+ "M.MEMBER_NAME , M.MEMBER_PHONE  "
			+ "FROM RESERVATION R "
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID "
			+ "WHERE PR.PRODUCT_SELLER_ID = ? "
			+ "ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";

	// 내 예약 전체보기
	private final String RESERVATION_SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, "
			+ "COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, "
			+ "P.PAYMENT_PRICE "
			+ "FROM RESERVATION R "
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "WHERE P.PAYMENT_MEMBER_ID = ? "
			+ "ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";

	// 내 예약 상세보기
	private final String RESERVATION_SELECTONE = "SELECT P.PAYMENT_NUM, R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, " 
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, " 
			+ "COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, " 
			+ "P.PAYMENT_PRICE, P.PAYMENT_REGISTRATION_DATE, P.PAYMENT_METHOD, P.PAYMENT_STATUS, "
			+ "COALESCE(P.PAYMENT_MEMBER_ID, '탈퇴한 회원입니다.') AS PAYMENT_MEMBER_ID, " 
			+ "COALESCE(M.MEMBER_NAME, '탈퇴한 회원입니다.') AS MEMBER_NAME, " 
			+ "COALESCE(M.MEMBER_PHONE, '탈퇴한 회원입니다.') AS MEMBER_PHONE, "
			+ "F.FILE_DIR " 
			+ "FROM RESERVATION R "
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "LEFT JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID " 
			+ "LEFT JOIN ( " 
			+ "SELECT FILE_DIR, PRODUCT_ITEM_NUM, "
			+ "ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS ROW_NUM "
			+ "FROM IMAGEFILE " 
			+ ") F ON F.ROW_NUM = 1 AND PR.PRODUCT_NUM = F.PRODUCT_ITEM_NUM "
			+ "WHERE R.RESERVATION_NUM = ?";

	// 사장님 ID, 예약번호로 상세예약건 보기
	private final String RESERVATION_OWNER_SELECTONE = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, \r\n"
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, \r\n"
			+ "COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, \r\n"
			+ "P.PAYMENT_PRICE, P.PAYMENT_REGISTRATION_DATE, P.PAYMENT_METHOD, P.PAYMENT_STATUS, \r\n"
			+ "M.MEMBER_ID, M.MEMBER_NAME, \r\n"
			+ "M.MEMBER_PHONE,\r\n"
			+ "I.FILE_DIR \r\n"
			+ "FROM RESERVATION R \r\n"
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM \r\n"
			+ "JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM \r\n"
			+ "JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID \r\n"
			+ "LEFT JOIN (SELECT FILE_DIR,PRODUCT_ITEM_NUM, ROW_NUMBER() OVER(PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS F\r\n"
			+ "FROM IMAGEFILE) I ON P.PAYMENT_PRODUCT_NUM = I.PRODUCT_ITEM_NUM AND I.F=1\r\n"
			+ "WHERE PR.PRODUCT_SELLER_ID = ? AND R.RESERVATION_NUM = ?";

	// 가장 마지막에 저장된 PK 번호 보여주기
	private final String SELECTONE_LAST_NUM = "SELECT RESERVATION_NUM "
			+ "FROM RESERVATION "
			+ "ORDER BY RESERVATION_NUM DESC " 
			+ "LIMIT 1";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(ReservationDTO reservationDTO) { // 예약 등록
		System.out.println("====model.reservationDAO2.insert 시작");
		
		int payment_num = reservationDTO.getReservation_payment_num(); // 예약 번호
		java.sql.Date sqlDate = new java.sql.Date(reservationDTO.getReservation_registration_date().getTime()); // 날짜 변환
		Date sql_date = sqlDate;
		
		// 예약 번호, 날짜를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(RESERVATION_INSERT,payment_num, sql_date);

		System.out.println("====model.reservationDAO2.insert result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.reservationDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.reservationDAO2.insert 성공");
		return true; // 성공적으로 등록 완료, true를 반환한다.
	}

	public boolean update(ReservationDTO reservationDTO) { // 예약 상태 변경
		System.out.println("====model.reservationDAO2.update 시작");
		
		// 예약 번호를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(RESERVATION_UPDATE, reservationDTO.getReservation_num());
		
		System.out.println("====model.reservationDAO2.update result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.reservationDAO2.update 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.reservationDAO2.update 종료");
		return true; // 성공적으로 등록 완료, true를 반환한다.
	}

	public List<ReservationDTO> selectAll(ReservationDTO reservationDTO) { // 예약 전체 출력
		List<ReservationDTO> datas = new ArrayList<ReservationDTO>();
		System.out.println("====model.reservationDAO2.selectAll 시작");
		System.out.println("====model.reservationDAO2.selectAll reservationDTO.getReservation_condition() : ["
				+ reservationDTO.getReservation_condition() + "]");
		
		// 컨디션이 'RESERVATION_SELECTALL'이라면, 회원 아이디를 담아서 쿼리문을 수행한다.
		if (reservationDTO.getReservation_condition().equals("RESERVATION_SELECTALL")) {
			Object[] args = { reservationDTO.getReservation_member_id() };// 회원 ID
			datas = jdbcTemplate.query(RESERVATION_SELECTALL, args, new ReservationRowMapper_all());
		} 
		// 컨디션이 'RESERVATION_OWNER_SELECTALL'이라면, 사장님 아이디를 담아서 쿼리문을 수행한다.
		else if (reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTALL")) {
			Object[] args = { reservationDTO.getReservation_seller_id() }; // 사장님 ID
			datas = jdbcTemplate.query(RESERVATION_OWNER_SELECTALL, args,new ReservationRowMapper_owner_all());
		}
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.reservationDAO2.selectAll 컨디션 에러");
			return null; // null을 반환한다.
		}

		// 쿼리문을 수행한 데이터를 반환한다. 
		System.out.println("====model.reservationDAO2.selectAll datas : ["+datas+"]");
		System.out.println("====model.reservationDAO2.selectAll 종료");
		return datas;
	}

	public ReservationDTO selectOne(ReservationDTO reservationDTO) { // 예약 상세보기
		ReservationDTO data;
		System.out.println("====model.reservationDAO2.selectOne 시작");
		System.out.println("====model.reservationDAO2.selectOne reservationDTO.getReservation_condition() : ["
				+ reservationDTO.getReservation_condition() + "]");
		
		int reservation_num = reservationDTO.getReservation_num();
		String reservation_seller_id =reservationDTO.getReservation_seller_id();
		
		// 컨디션이 'RESERVATION_SELECTONE'라면, 예약 번호를 넣어 쿼리문을 수행한다.
		if (reservationDTO.getReservation_condition().equals("RESERVATION_SELECTONE")) { 
			Object[] args = { reservation_num }; // 예약 번호 (PK)
			data = jdbcTemplate.queryForObject(RESERVATION_SELECTONE, args, new ReservationRowMapper_one());
		}
		// 컨디션이 'RESERVATION_OWNER_SELECTONE'라면, 판매자 아이디, 예약번호를 넣어 쿼리문을 수행한다.
		else if(reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTONE")) {
			Object[] args = { reservation_seller_id,reservation_num }; 
			data = jdbcTemplate.queryForObject(RESERVATION_OWNER_SELECTONE, args, new ReservationRowMapper_one_owner());
		}
		// 컨디션이 'RESERVATION_LAST_NUM'라면, 아래의 쿼리문을 수행한다.
		else if (reservationDTO.getReservation_condition().equals("RESERVATION_LAST_NUM")) {
			data = jdbcTemplate.queryForObject(SELECTONE_LAST_NUM, new ReservationRowMapper_one_last_num());
		} 
		// 해당하는 컨디션이 없다면,
		else {
			System.err.println("====model.reservationDAO2.selectOne 컨디션 에러");
			return null; // null을 반환한다.
		}
		
		// 쿼리문을 수행한 데이터를 반환한다. 
		System.out.println("====model.reservationDAO2.selectOne data : ["+data+"]");
		System.out.println("====model.reservationDAO2.selectOne 종료");
		return data;
	}

	public boolean delete(ReservationDTO reservationDTO) { 
		// 기능 미사용으로 false 반환한다.
		return false;
	}
}
//======================================================RowMapper=================================================================
// 예약 정보를 반환하는 RowMapper
class ReservationRowMapper_all implements RowMapper<ReservationDTO> {
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ReservationRowMapper_all 시작");

		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		java.sql.Date sqlDate = rs.getDate("RESERVATION_REGISTRATION_DATE"); // DB에 저장된 예약일
		data.setReservation_registration_date(new Date(sqlDate.getTime())); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액

		System.out.println("model.ReservationRowMapper_all 종료");
		return data;
	}
}
// 예약 정보를 반환하는 RowMapper
class ReservationRowMapper_owner_all implements RowMapper<ReservationDTO> {
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ReservationRowMapper_all 시작");
		
		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		java.sql.Date sqlDate = rs.getDate("RESERVATION_REGISTRATION_DATE"); // DB에 저장된 예약일
		data.setReservation_registration_date(new Date(sqlDate.getTime())); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
		data.setReservation_member_id(rs.getString("MEMBER_ID")); // 회원 아이디
		data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 회원 이름
		data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 회원 전화번호
		
		System.out.println("model.ReservationRowMapper_all 종료");
		return data;
	}
}
 
// 예약 상세 정보를 반환하는 RowMapper
class ReservationRowMapper_one implements RowMapper<ReservationDTO> {
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one 시작");

		ReservationDTO data = new ReservationDTO();
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

		System.out.println("model.ReservationRowMapper_one 종료");
		return data;
	}
}

// 사장님 예약 정보를 반환하는 RowMapper
class ReservationRowMapper_one_owner implements RowMapper<ReservationDTO> { 
	
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one_owner 시작");
	    
		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품 이름
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
		data.setReservation_member_id(rs.getString("MEMBER_ID")); // 결제자 ID
		data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 예약자 성명
		data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 예약자 핸드폰 번호
		data.setReservation_product_file_dir(rs.getString("FILE_DIR"));	// 상품 이미지
		System.out.println("model.ReservationRowMapper_one_owner 종료");
		return data;
	}
}

// 예약 마지막 pk를 반환하는 RowMapper
class ReservationRowMapper_one_last_num implements RowMapper<ReservationDTO> { 
	
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one_last_num 시작");

		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호

		System.out.println("model.ReservationRowMapper_one_last_num 종료");
		return data;
	}
}