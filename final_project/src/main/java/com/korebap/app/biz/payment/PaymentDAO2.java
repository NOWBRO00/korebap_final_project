package com.korebap.app.biz.payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO2 {
	// 결제 생성
	private final String PAYMENT_INSERT = "INSERT INTO PAYMENT "
			+ "(PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, "
			+ "MERCHANT_UID, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	// 결제 변경 (완료 > 취소)
	private final String PAYMENT_UPDATE = "UPDATE PAYMENT " 
			+ "SET PAYMENT_STATUS = '결제취소' " 
			+ "WHERE PAYMENT_NUM = ? AND PAYMENT_STATUS = '결제완료'";

	// 결제 내역 반환 (머천트 UID로)
	private final String PAYMENT_SELECTONE_BY_MERCHANT_UID = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, " 
			+ "MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD " 
			+ "FROM PAYMENT " 
			+ "WHERE MERCHANT_UID = ?";

	// 결제 번호로 결제 내역 반환
	private final String PAYMENT_SELECTONE_BY_PAYMENT_NUM = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, "
			+ "MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD "
			+ "FROM PAYMENT " 
			+ "WHERE PAYMENT_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(PaymentDTO paymentDTO) { // 결제 등록
		System.out.println("====model.PaymentDAO2.insert 시작");
		
		String payment_member_id = paymentDTO.getPayment_member_id(); // 사용자 아이디
		int payment_product_num = paymentDTO.getPayment_product_num(); // 결제 상품 번호
		String payment_order_num = paymentDTO.getPayment_order_num(); // 주문 번호 (포트원 생성 번호)
		String merchant_uid = paymentDTO.getMerchant_uid(); // UUID + 시간 조합 번호 (controller 사용)
		int payment_price = paymentDTO.getPayment_price(); // 결제 금액
		String payment_status = paymentDTO.getPayment_status(); // 결제 상태
		String payment_method = paymentDTO.getPayment_method(); // 결제 방법 

		// 위의 값을 넣어 쿼리문을 수행한다.
		int result = jdbcTemplate.update(PAYMENT_INSERT, payment_member_id, payment_product_num, payment_order_num,
				merchant_uid, payment_price, payment_status, payment_method);
		
		System.out.println("====model.PaymentDAO2.insert result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.PaymentDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.PaymentDAO2.insert 종료");
		return true; // 성공적으로 등록 완료, true를 반환한다.
	}

	public boolean update(PaymentDTO paymentDTO) { // 결제 상태 변경
		System.out.println("====model.PaymentDAO2.update 시작");
		
		// 결제 번호를 담아서 쿼리문을 수행한다.
		int result = jdbcTemplate.update(PAYMENT_UPDATE, paymentDTO.getPayment_num()); // 결제 번호

		System.out.println("====model.PaymentDAO2.update result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.PaymentDAO2.update 실패");
			return false; // false를 반환한다.
		}
		System.out.println("====model.PaymentDAO2.update 종료");
		return true; // 성공적으로 수정 완료, true를 반환한다.
	}

	public PaymentDTO selectOne(PaymentDTO paymentDTO) { // 결제 선택 보기
		System.out.println("====model.PaymentDAO2.selectOne 시작");
		PaymentDTO data;

		System.out.println("====model.PaymentDAO2.selectOne paymentDTO.getPayment_condition() : ["
				+ paymentDTO.getPayment_condition() + "]");
		
		// 컨디션이 'SELECT_BY_MERCHANT_UID'라면, UID를 담아서 쿼리문을 수행한다.
		if (paymentDTO.getPayment_condition().equals("SELECT_BY_MERCHANT_UID")) {
			Object[] args = { paymentDTO.getMerchant_uid() }; // 포트원에서 생성된 UID
			data = jdbcTemplate.queryForObject(PAYMENT_SELECTONE_BY_MERCHANT_UID, args, new PaymentRowMapper_one());
		} 
		// 컨디션이 'SELECT_BY_PAYMENT_NUM'라면, 결제 번호를 담아서 쿼리문을 수행한다.
		else if (paymentDTO.getPayment_condition().equals("SELECT_BY_PAYMENT_NUM")) {
			Object[] args = { paymentDTO.getPayment_num() }; // 결제 번호
			data = jdbcTemplate.queryForObject(PAYMENT_SELECTONE_BY_PAYMENT_NUM, args, new PaymentRowMapper_one());
		} 
		// 해당하는 컨디션이 없다면, data
		else {
			System.err.println("====model.PaymentDAO2.selectOne 컨디션 에러");
			return null;
		}
		
		// 쿼리문을 수행한 데이터를 반환한다.
		System.out.println("====model.PaymentDAO2.selectOne data : ["+data+"]");
		System.out.println("====model.PaymentDAO2.selectOne 종료");
		return data;
	}

	public boolean delete(PaymentDTO paymentDTO) { 
		// 미사용으로 false를 반환한다.
		return false;
	}

	public List<PaymentDTO> selectAll(PaymentDTO paymentDTO) { 
		// 미사용을 null 반환한다.
		return null;
	}

}
//======================================================RowMapper=================================================================
// 결제 정보를 반환하는 RowMapper
class PaymentRowMapper_one implements RowMapper<PaymentDTO> {
	@Override
	public PaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.PaymentRowMapper_one 실행");

		PaymentDTO data = new PaymentDTO();
		data.setPayment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
		data.setPayment_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 사용자 아이디
		data.setPayment_product_num(rs.getInt("PAYMENT_PRODUCT_NUM")); // 결제한 상품 번호
		data.setPayment_order_num(rs.getString("PAYMENT_ORDER_NUM")); // 포트원에서 생성된 결제 번호
		data.setMerchant_uid(rs.getString("MERCHANT_UID")); // controller에서 UUID+시간 조합으로 사용하는 번호
		data.setPayment_registration_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
		data.setPayment_price(rs.getInt("PAYMENT_PRICE")); // 결제 금액
		data.setPayment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 
		data.setPayment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 
		
		System.out.println("====model.PaymentRowMapper_one 종료");
		return data;
	}
}
