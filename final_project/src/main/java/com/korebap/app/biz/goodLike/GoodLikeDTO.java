package com.korebap.app.biz.goodLike;


public class GoodLikeDTO {
	private int goodLike_num; // 좋아요 번호 - PK
	private int goodLike_board_num; // 좋아요 받은 글 - FK
	private String goodLike_member_id; // 좋아요 누른 사람 - FK
	
	public int getGoodLike_num() {
		return goodLike_num;
	}
	public void setGoodLike_num(int goodLike_num) {
		this.goodLike_num = goodLike_num;
	}
	public int getGoodLike_board_num() {
		return goodLike_board_num;
	}
	public void setGoodLike_board_num(int goodLike_board_num) {
		this.goodLike_board_num = goodLike_board_num;
	}
	public String getGoodLike_member_id() {
		return goodLike_member_id;
	}
	public void setGoodLike_member_id(String goodLike_member_id) {
		this.goodLike_member_id = goodLike_member_id;
	}
	@Override
	public String toString() {
		return "GoodLikeDTO [goodLike_num=" + goodLike_num + ", goodLike_board_num=" + goodLike_board_num
				+ ", goodLike_member_id=" + goodLike_member_id + "]";
	}
	
}
