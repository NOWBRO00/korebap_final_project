package com.korebap.app.biz.payment;

import java.net.http.HttpResponse;

public class PaymentInfo {
	private String token; // 포트원 토큰
	private String imp_uid;	// 결제 번호
	private String merchant_uid; // 고래밥 결제 번호
	private int amount; // 가격
	private int product_num; // 상품 번호
	private HttpResponse<String> response; // 포트원 응답
	
	
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public HttpResponse<String> getResponse() {
		return response;
	}
	public void setResponse(HttpResponse<String> response) {
		this.response = response;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImp_uid() {
		return imp_uid;
	}
	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	
}
