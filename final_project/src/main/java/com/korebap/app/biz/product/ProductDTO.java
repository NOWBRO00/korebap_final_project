package com.korebap.app.biz.product;

import java.sql.Date;
import java.util.List;

public class ProductDTO { // 상품
	private int product_num; // 상품 번호(PK)
	private String product_name; // 상품 명
	private int product_price; // 상품 가격
	private String product_details; // 상품 설명
	private int product_cnt; // 상품 재고(자리)-ex.배에 탈 수 있는 정원
	private String product_location; // 상품 장소 (바다, 민물)
	private String product_category; // 상품 유형(낚시배, 낚시터,낚시카페,수상)
	private String product_address; // 상품의 주소
	private String product_seller_id; // 상품 판매자 정보(FK) - 최프 사용 예정

	// DTO에만 있는 멤버변수 (테이블에 없음)
	private String product_condition; // 상품 컨디션
	private String product_searchKeyword;// 상품 검색어
	private String product_search_criteria; // 상품 검색 정렬 기준
	private double product_avg_rating; // 별점 평균
	private int product_payment_cnt; // 상품 결제 수
	private Date product_reservation_date; // 예약일 (예약 선택일)
	private int product_wishlist_cnt; // 찜 수??
	private String product_file_dir; // 파일 경로
	private int product_page_num; // 페이지네이션 : 사용자가 선택한 페이지 번호
	private int product_total_page; // 페이지네이션 : 전체 상품 데이터 개수
	private List<String> url; // 크롤링 : 이미지 저장 리스트
	private int product_total_cnt; // 상품 개수

	private List<String> product_types; // 검색 타입
	private List<String> product_categories; // 검색 카테고리

	private String product_postcode; // 우편번호
	private String product_extraAddress; // 주소 참고항목
	private String product_detailAddress; // 상세주소

	private int product_location_by_cnt; // location별 상품 수 // 1105 추가
	private int product_location_by_category_cnt; // location별 카테고리의 상품 수 // 1105 추가

	
	public int getProduct_location_by_cnt() {
		return product_location_by_cnt;
	}

	public void setProduct_location_by_cnt(int product_location_by_cnt) {
		this.product_location_by_cnt = product_location_by_cnt;
	}

	public int getProduct_location_by_category_cnt() {
		return product_location_by_category_cnt;
	}

	public void setProduct_location_by_category_cnt(int product_location_by_category_cnt) {
		this.product_location_by_category_cnt = product_location_by_category_cnt;
	}

	public String getProduct_postcode() {
		return product_postcode;
	}

	public void setProduct_postcode(String product_postcode) {
		this.product_postcode = product_postcode;
	}

	public String getProduct_extraAddress() {
		return product_extraAddress;
	}

	public void setProduct_extraAddress(String product_extraAddress) {
		this.product_extraAddress = product_extraAddress;
	}

	public String getProduct_detailAddress() {
		return product_detailAddress;
	}

	public void setProduct_detailAddress(String product_detailAddress) {
		this.product_detailAddress = product_detailAddress;
	}

	public int getProduct_total_cnt() {
		return product_total_cnt;
	}

	public void setProduct_total_cnt(int product_total_cnt) {
		this.product_total_cnt = product_total_cnt;
	}

	public List<String> getProduct_types() {
		return product_types;
	}

	public void setProduct_types(List<String> product_types) {
		this.product_types = product_types;
	}

	public List<String> getProduct_categories() {
		return product_categories;
	}

	public void setProduct_categories(List<String> product_categories) {
		this.product_categories = product_categories;
	}

	public String getProduct_search_criteria() {
		return product_search_criteria;
	}

	public void setProduct_search_criteria(String product_search_criteria) {
		this.product_search_criteria = product_search_criteria;
	}

	public int getProduct_num() {
		return product_num;
	}

	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public String getProduct_details() {
		return product_details;
	}

	public void setProduct_details(String product_details) {
		this.product_details = product_details;
	}

	public int getProduct_cnt() {
		return product_cnt;
	}

	public void setProduct_cnt(int product_cnt) {
		this.product_cnt = product_cnt;
	}

	public String getProduct_location() {
		return product_location;
	}

	public void setProduct_location(String product_location) {
		this.product_location = product_location;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public String getProduct_address() {
		return product_address;
	}

	public void setProduct_address(String product_address) {
		this.product_address = product_address;
	}

	public String getProduct_condition() {
		return product_condition;
	}

	public void setProduct_condition(String product_condition) {
		this.product_condition = product_condition;
	}

	public String getProduct_searchKeyword() {
		return product_searchKeyword;
	}

	public void setProduct_searchKeyword(String product_searchKeyword) {
		this.product_searchKeyword = product_searchKeyword;
	}

	public double getProduct_avg_rating() {
		return product_avg_rating;
	}

	public void setProduct_avg_rating(double product_avg_rating) {
		this.product_avg_rating = product_avg_rating;
	}

	public int getProduct_payment_cnt() {
		return product_payment_cnt;
	}

	public void setProduct_payment_cnt(int product_payment_cnt) {
		this.product_payment_cnt = product_payment_cnt;
	}

	public Date getProduct_reservation_date() {
		return product_reservation_date;
	}

	public void setProduct_reservation_date(Date product_reservation_date) {
		this.product_reservation_date = product_reservation_date;
	}

	public int getProduct_wishlist_cnt() {
		return product_wishlist_cnt;
	}

	public void setProduct_wishlist_cnt(int product_wishlist_cnt) {
		this.product_wishlist_cnt = product_wishlist_cnt;
	}

	public String getProduct_file_dir() {
		return product_file_dir;
	}

	public void setProduct_file_dir(String product_file_dir) {
		this.product_file_dir = product_file_dir;
	}

	public int getProduct_page_num() {
		return product_page_num;
	}

	public void setProduct_page_num(int product_page_num) {
		this.product_page_num = product_page_num;
	}

	public int getProduct_total_page() {
		return product_total_page;
	}

	public void setProduct_total_page(int product_total_page) {
		this.product_total_page = product_total_page;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public String getProduct_seller_id() {
		return product_seller_id;
	}

	public void setProduct_seller_id(String product_seller_id) {
		this.product_seller_id = product_seller_id;
	}

	@Override
	public String toString() {
		return "ProductDTO [product_num=" + product_num + ", product_name=" + product_name + ", product_price="
				+ product_price + ", product_details=" + product_details + ", product_cnt=" + product_cnt
				+ ", product_location=" + product_location + ", product_category=" + product_category
				+ ", product_address=" + product_address + ", product_seller_id=" + product_seller_id
				+ ", product_condition=" + product_condition + ", product_searchKeyword=" + product_searchKeyword
				+ ", product_search_criteria=" + product_search_criteria + ", product_avg_rating=" + product_avg_rating
				+ ", product_payment_cnt=" + product_payment_cnt + ", product_reservation_date="
				+ product_reservation_date + ", product_wishlist_cnt=" + product_wishlist_cnt + ", product_file_dir="
				+ product_file_dir + ", product_page_num=" + product_page_num + ", product_total_page="
				+ product_total_page + ", url=" + url + ", product_total_cnt=" + product_total_cnt + ", product_types="
				+ product_types + ", product_categories=" + product_categories + ", product_postcode="
				+ product_postcode + ", product_extraAddress=" + product_extraAddress + ", product_detailAddress="
				+ product_detailAddress + ", product_location_by_cnt=" + product_location_by_cnt
				+ ", product_location_by_category_cnt=" + product_location_by_category_cnt + "]";
	}



}
