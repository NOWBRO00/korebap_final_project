package com.korebap.app.biz.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class PaymentDAO {
	// 결제 생성
	private final String INSERT = "INSERT INTO PAYMENT (PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	// 결제 변경 (완료 > 취소)
	private final String UPDATE = "UPDATE PAYMENT SET PAYMENT_STATUS = '결제취소' WHERE PAYMENT_NUM = ? AND PAYMENT_STATUS = '결제완료'";
	// 결제 내역 반환
	private final String SELECTONE_BY_MERCHANT_UID = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD "
			+ "FROM PAYMENT WHERE MERCHANT_UID = ?";
	// 결제 번호로 결제 내역 반환
	private final String SELECTONE_BY_PAYMENT_NUM = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD "
			+ "FROM PAYMENT WHERE PAYMENT_NUM = ?";

	public boolean insert(PaymentDTO paymentDTO) { // 결제 생성
		System.out.println("====model.PaymentDAO.insert 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, paymentDTO.getPayment_member_id()); // 결제자 ID (PK)
			pstmt.setInt(2, paymentDTO.getPayment_product_num()); // 결제 상품 번호
			pstmt.setString(3, paymentDTO.getPayment_order_num()); // 주문 번호 (포트원 생성 번호)
			pstmt.setString(4, paymentDTO.getMerchant_uid()); // UUID + 시간 조합 번호 (controller 사용)
			pstmt.setInt(5, paymentDTO.getPayment_price()); // 결제 금액
			pstmt.setString(6, paymentDTO.getPayment_status()); // 결제 상태 : 결제완료, 결제취소
			pstmt.setString(7, paymentDTO.getPayment_method()); // 결제 방법 : 카드, 페이

			int result = pstmt.executeUpdate();
			System.out.println("	model.PaymentDAO.insert result : " + result);
			if (result <= 0) {// 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("====model.PaymentDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.PaymentDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.PaymentDAO.insert SQL문 실패");
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.PaymentDAO.update 종료");
		}
		return true;
	}

	public boolean update(PaymentDTO paymentDTO) { // 결제 변경( 완료 > 취소)
		System.out.println("====model.PaymentDAO.update 시작");
		// JDBCUtil 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setInt(1, paymentDTO.getPayment_num()); // 결제 PK

			int result = pstmt.executeUpdate();
			System.out.println("	model.PaymentDAO.update result : " + result);
			if (result <= 0) {
				System.out.println("====model.PaymentDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("====model.PaymentDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.PaymentDAO.update SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.PaymentDAO.update 종료");
		}
		return true;
	}

	public PaymentDTO selectOne(PaymentDTO paymentDTO) {// 한명 출력
		System.out.println("====model.PaymentDAO.selectOne 시작");
		// JDBCUtil 연결
		PaymentDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	model.PaymentDAO.selectOne condition : " + paymentDTO.getPayment_condition());
			if (paymentDTO.getPayment_condition().equals("SELECT_BY_MERCHANT_UID")) {
				pstmt = conn.prepareStatement(SELECTONE_BY_MERCHANT_UID);
				pstmt.setString(1, paymentDTO.getMerchant_uid());
			} else if (paymentDTO.getPayment_condition().equals("SELECT_BY_PAYMENT_NUM")) {
				pstmt = conn.prepareStatement(SELECTONE_BY_PAYMENT_NUM);
				pstmt.setInt(1, paymentDTO.getPayment_num());
			}

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("====model.PaymentDAO.selectOne rs.next() 시작");
				data = new PaymentDTO();
				data.setPayment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
				data.setPayment_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
				data.setPayment_product_num(rs.getInt("PAYMENT_PRODUCT_NUM")); // 결제한 상품 번호
				data.setPayment_order_num(rs.getString("PAYMENT_ORDER_NUM")); // 포트원에서 생성된 결제 번호
				data.setMerchant_uid(rs.getString("MERCHANT_UID")); // controller에서 UUID+시간 조합으로 사용하는 번호
				data.setPayment_registration_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
				data.setPayment_price(rs.getInt("PAYMENT_PRICE")); // 결제 금액
				data.setPayment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제완료, 결제 취소)
				data.setPayment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (card, pay)
				System.out.println("	model.PaymentDAO.selectOne data : " + data);
			}

		} catch (SQLException e) {
			System.err.println("====model.PaymentDAO.selectOne SQL문 실패");
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.PaymentDAO.selectOne 종료");
		}
		return data;
	}

	// 기능 미구현으로 false 반환
	public boolean delete(PaymentDTO paymentDTO) {
		return false;
	}

	// 기능 미구현으로 null 반환
	public ArrayList<PaymentDTO> selectAll(PaymentDTO paymentDTO) {
		ArrayList<PaymentDTO> datas = new ArrayList<PaymentDTO>();
		return datas;
	}

}
