package com.korebap.app.view.async;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.wishlist.WishlistDTO;
import com.korebap.app.biz.wishlist.WishlistService;

// wishList 관련 비동기 class
@RestController
public class DeleteWishListController {

	@Autowired
	private WishlistService wishlistService;

	@PostMapping(value="/deleteWishList.do")
	public @ResponseBody String deleteWishList(WishlistDTO wishlistDTO) {
		// [ 위시리스트 삭제 ]

		System.out.println("************************************************************[com.korebap.app.view.async deleteWishList (비동기) 시작]************************************************************");


		// V에게 삭제할 위시리스트 번호를 받아온다.
		int wishlist_num = wishlistDTO.getWishlist_num();

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.async WishList deleteWishList wishlist_num["+wishlist_num+"]*****");


		// DTO 객체에 위시리스트 번호를 담아준다.
		wishlistDTO.setWishlist_num(wishlist_num);
		// M에게 DTO객체를 전달해주고, boolean 타입으로 반환받는다.
		boolean flag = wishlistService.delete(wishlistDTO);

		// 응답을 반환하기 위한 변수 설정
		// default 성공으로 보고 반환
		String result = "true";

		if(!flag) { // 만약 false 반환받는다면 
			// 실패 응답 반환
			System.out.println("*****com.korebap.app.view.async WishList deleteWishList 비동기 실패 반환*****");
			result = "false";
		}


		System.out.println("*****com.korebap.app.view.async WishList deleteWishList result ["+result+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.async deleteWishList (비동기) 종료]************************************************************");
		return result;

	}

}
