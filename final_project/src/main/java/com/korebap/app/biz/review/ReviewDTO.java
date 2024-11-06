package com.korebap.app.biz.review;

import java.sql.Date;

public class ReviewDTO {
	private int review_num; // 리뷰 번호 - PK
	private String review_content; // 리뷰 내용
	private int review_product_num; // 리뷰 상품 번호 - FK
	private String review_writer_id; // 작성자 ID - FK
	private Date review_registration_date; // 작성일
	private int review_star; // 별점
	
	// DTO에만 있는 멤버변수 (테이블에 없음)
	private String review_writer_nickname; // 작성자 닉네임
	private String review_member_profile; // 회원 프로필



	public String getReview_member_profile() {
		return review_member_profile;
	}

	public void setReview_member_profile(String review_member_profile) {
		this.review_member_profile = review_member_profile;
	}

	public int getReview_num() {
		return review_num;
	}

	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public int getReview_product_num() {
		return review_product_num;
	}

	public void setReview_product_num(int review_product_num) {
		this.review_product_num = review_product_num;
	}

	public String getReview_writer_id() {
		return review_writer_id;
	}

	public void setReview_writer_id(String review_writer_id) {
		this.review_writer_id = review_writer_id;
	}

	public Date getReview_registration_date() {
		return review_registration_date;
	}

	public void setReview_registration_date(Date review_registration_date) {
		this.review_registration_date = review_registration_date;
	}

	public int getReview_star() {
		return review_star;
	}

	public void setReview_star(int review_star) {
		this.review_star = review_star;
	}

	public String getReview_writer_nickname() {
		return review_writer_nickname;
	}

	public void setReview_writer_nickname(String review_writer_nickname) {
		this.review_writer_nickname = review_writer_nickname;
	}

	@Override
	public String toString() {
		return "ReviewDTO [review_num=" + review_num + ", review_content=" + review_content + ", review_product_num="
				+ review_product_num + ", review_writer_id=" + review_writer_id + ", review_registration_date="
				+ review_registration_date + ", review_star=" + review_star + ", review_writer_nickname="
				+ review_writer_nickname + ", review_member_profile=" + review_member_profile + "]";
	}


	
}
