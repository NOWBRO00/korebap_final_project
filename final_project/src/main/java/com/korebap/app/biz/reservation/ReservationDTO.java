package com.korebap.app.biz.reservation;

import java.util.Date;

public class ReservationDTO {
	private int reservation_num; // 예약번호 PK
	private int reservation_payment_num;// 결제 번호 FK
	private Date reservation_registration_date; // 예약 날짜
	private String reservation_status; // 예약 상태 (예약완료, 예약취소)

	// DTO에만 있는 멤버변수 (테이블에 없음)
	private int reservation_product_num; // 예약한 상품 번호
	private String reservation_product_name; // 예약한 상품명
	private String reservation_product_file_dir;
	private int reservation_price; // 예약한 상품 결제 금액
	private Date reservation_payment_date; // 예약한 상품 결제일
	private String reservation_member_id; // 예약자 ID
	private String reservation_seller_id; // 예약자 ID
	private String reservation_member_name; // 예약자 성명
	private String reservation_member_phone; // 예약자 전화번호
	private String reservation_payment_method; // 결제 방법 (카드, 페이)
	private String reservation_payment_status; // 결제 상태 (결제 완료, 결제 취소)
	private String reservation_condition; // 예약 컨디션

	public String getReservation_seller_id() {
		return reservation_seller_id;
	}

	public void setReservation_seller_id(String reservation_seller_id) {
		this.reservation_seller_id = reservation_seller_id;
	}

	public String getReservation_product_file_dir() {
		return reservation_product_file_dir;
	}

	public void setReservation_product_file_dir(String reservation_product_file_dir) {
		this.reservation_product_file_dir = reservation_product_file_dir;
	}

	public String getReservation_condition() {
		return reservation_condition;
	}

	public void setReservation_condition(String reservation_condition) {
		this.reservation_condition = reservation_condition;
	}

	public String getReservation_payment_method() {
		return reservation_payment_method;
	}

	public void setReservation_payment_method(String reservation_payment_method) {
		this.reservation_payment_method = reservation_payment_method;
	}

	public String getReservation_payment_status() {
		return reservation_payment_status;
	}

	public void setReservation_payment_status(String reservation_payment_status) {
		this.reservation_payment_status = reservation_payment_status;
	}

	public String getReservation_product_name() {
		return reservation_product_name;
	}

	public void setReservation_product_name(String reservation_product_name) {
		this.reservation_product_name = reservation_product_name;
	}

	public int getReservation_price() {
		return reservation_price;
	}

	public void setReservation_price(int reservation_price) {
		this.reservation_price = reservation_price;
	}

	public Date getReservation_payment_date() {
		return reservation_payment_date;
	}

	public void setReservation_payment_date(Date reservation_payment_date) {
		this.reservation_payment_date = reservation_payment_date;
	}

	public String getReservation_member_name() {
		return reservation_member_name;
	}

	public void setReservation_member_name(String reservation_member_name) {
		this.reservation_member_name = reservation_member_name;
	}

	public String getReservation_member_phone() {
		return reservation_member_phone;
	}

	public void setReservation_member_phone(String reservation_member_phone) {
		this.reservation_member_phone = reservation_member_phone;
	}

	public int getReservation_num() {
		return reservation_num;
	}

	public void setReservation_num(int reservation_num) {
		this.reservation_num = reservation_num;
	}

	public String getReservation_member_id() {
		return reservation_member_id;
	}

	public void setReservation_member_id(String reservation_member_id) {
		this.reservation_member_id = reservation_member_id;
	}

	public int getReservation_payment_num() {
		return reservation_payment_num;
	}

	public void setReservation_payment_num(int reservation_payment_num) {
		this.reservation_payment_num = reservation_payment_num;
	}

	public Date getReservation_registration_date() {
		return reservation_registration_date;
	}

	public void setReservation_registration_date(Date reservation_registration_date) {
		this.reservation_registration_date = reservation_registration_date;
	}

	public String getReservation_status() {
		return reservation_status;
	}

	public void setReservation_status(String reservation_status) {
		this.reservation_status = reservation_status;
	}

	public int getReservation_product_num() {
		return reservation_product_num;
	}

	public void setReservation_product_num(int reservation_product_num) {
		this.reservation_product_num = reservation_product_num;
	}

	@Override
	public String toString() {
		return "ReservationDTO [reservation_num=" + reservation_num + ", reservation_payment_num="
				+ reservation_payment_num + ", reservation_registration_date=" + reservation_registration_date
				+ ", reservation_status=" + reservation_status + ", reservation_product_num=" + reservation_product_num
				+ ", reservation_product_name=" + reservation_product_name + ", reservation_product_file_dir="
				+ reservation_product_file_dir + ", reservation_price=" + reservation_price
				+ ", reservation_payment_date=" + reservation_payment_date + ", reservation_member_id="
				+ reservation_member_id + ", reservation_seller_id=" + reservation_seller_id
				+ ", reservation_member_name=" + reservation_member_name + ", reservation_member_phone="
				+ reservation_member_phone + ", reservation_payment_method=" + reservation_payment_method
				+ ", reservation_payment_status=" + reservation_payment_status + ", reservation_condition="
				+ reservation_condition + "]";
	}

}
