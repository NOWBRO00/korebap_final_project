package com.korebap.app.biz.payment;

import java.sql.Date;


public class PaymentDTO { // 결제
	private int payment_num; //결제 번호(PK)
	private String payment_member_id; // 결제자 MID(FK)
	private int payment_product_num; //상품 번호(FK)
	private String payment_order_num; // 주문 번호 (포트원 생성 번호)
	private String merchant_uid; // C에서 사용하는 번호 (UUID + 시간 조합)
	private Date payment_registration_date; //결제 날짜
	private int payment_price; // 결제 금액
	private String payment_status; // 결제 상태 (결제 완료, 결제 취소)
	private String payment_method; // 결제 방법 (카드, 페이)
	
	private String payment_condition;
	
	public String getPayment_condition() {
		return payment_condition;
	}
	public void setPayment_condition(String payment_condition) {
		this.payment_condition = payment_condition;
	}
	public int getPayment_num() {
		return payment_num;
	}
	public void setPayment_num(int payment_num) {
		this.payment_num = payment_num;
	}
	public String getPayment_member_id() {
		return payment_member_id;
	}
	public void setPayment_member_id(String payment_member_id) {
		this.payment_member_id = payment_member_id;
	}
	public int getPayment_product_num() {
		return payment_product_num;
	}
	public void setPayment_product_num(int payment_product_num) {
		this.payment_product_num = payment_product_num;
	}
	public String getPayment_order_num() {
		return payment_order_num;
	}
	public void setPayment_order_num(String payment_order_num) {
		this.payment_order_num = payment_order_num;
	}
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public Date getPayment_registration_date() {
		return payment_registration_date;
	}
	public void setPayment_registration_date(Date payment_registration_date) {
		this.payment_registration_date = payment_registration_date;
	}
	public int getPayment_price() {
		return payment_price;
	}
	public void setPayment_price(int payment_price) {
		this.payment_price = payment_price;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	@Override
	public String toString() {
		return "PaymentDTO [payment_num=" + payment_num + ", payment_member_id=" + payment_member_id
				+ ", payment_product_num=" + payment_product_num + ", payment_order_num=" + payment_order_num
				+ ", merchant_uid=" + merchant_uid + ", payment_registration_date=" + payment_registration_date
				+ ", payment_price=" + payment_price + ", payment_status=" + payment_status + ", payment_method="
				+ payment_method + ", payment_condition=" + payment_condition + ", getPayment_condition()="
				+ getPayment_condition() + ", getPayment_num()=" + getPayment_num() + ", getPayment_member_id()="
				+ getPayment_member_id() + ", getPayment_product_num()=" + getPayment_product_num()
				+ ", getPayment_order_num()=" + getPayment_order_num() + ", getMerchant_uid()=" + getMerchant_uid()
				+ ", getPayment_registration_date()=" + getPayment_registration_date() + ", getPayment_price()="
				+ getPayment_price() + ", getPayment_status()=" + getPayment_status() + ", getPayment_method()="
				+ getPayment_method() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
