package com.korebap.app.biz.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentDAO2 paymentDAO;
	
	@Override
	public List<PaymentDTO> selectAll(PaymentDTO paymentDTO) {
		System.out.println("기능 미구현으로 null 반환");
		return this.paymentDAO.selectAll(paymentDTO);
	}

	@Override
	public PaymentDTO selectOne(PaymentDTO paymentDTO) {
		return this.paymentDAO.selectOne(paymentDTO);
	}

	@Override
	public boolean insert(PaymentDTO paymentDTO) {
		return this.paymentDAO.insert(paymentDTO);
	}

	@Override
	public boolean update(PaymentDTO paymentDTO) {
		return this.paymentDAO.update(paymentDTO);
	}

	@Override
	public boolean delete(PaymentDTO paymentDTO) {
		System.out.println("기능 미구현으로 false 반환");
		return this.paymentDAO.delete(paymentDTO);
	}
}
