package com.korebap.app.view.wishlist;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korebap.app.biz.wishlist.WishlistDTO;
import com.korebap.app.biz.wishlist.WishlistService;
import com.korebap.app.view.common.LoginCheck;



@Controller
public class WishListPageController{
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 주입


	@GetMapping(value="/wishListPage.do")
	public String wishListPage(WishlistDTO wishlistDTO,Model model) {
		// 위시리스트 목록 페이지
		System.out.println("************************************************************[com.korebap.app.view.page  wishListPage 시작]************************************************************");

		
		// 로그인 체크
		String login_member_id = loginCheck.loginCheck();
		
		
		// 데이터 로그
		System.out.println("*****com.korebap.app.view.page wishListPage 로그인 아이디 : ["+login_member_id+"]*****");
		
		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.page wishListPage 로그인 세션 없음*****");
			// 로그인 안내 후 login 페이지로 이동시킨다
			
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");
			
			return "info";
		}
		else { // 만약 로그인 상태라면
			System.out.println("*****com.korebap.app.view.page wishListPage 로그인 세션 있음*****");

			// 세션에서 받아온 id 정보를 M에게 전달하기 위해 DTO 객체에 담아준다.
			wishlistDTO.setWishlist_member_id(login_member_id);
			
			// [위시리스트 전체 목록]
			// M에게 데이터를 보내주고, 결과를 List로 반환받는다.
			List<WishlistDTO> wishlist = wishlistService.selectAll(wishlistDTO);
			
			System.out.println("*****com.korebap.app.view.page wishListPage wishlist ["+wishlist+"]*****");

			
			// [위시리스트 개수]
			// M에게 데이터를 보내주고, 결과를 DTO로 반환받는다.
			wishlistDTO = wishlistService.selectOne(wishlistDTO);
			
			//int 타입 변수에 받아온 값을 넣어준다.
			int wishlist_count = wishlistDTO.getWishlist_cnt();
			
			System.out.println("*****com.korebap.app.view.page wishListPage wishlist_count ["+wishlist_count+"]*****");

			
			// V에게 전달해주기 위해 model 객체에 데이터를 저장한다.
			model.addAttribute("wishlist", wishlist); // 위시리스트 목록
			model.addAttribute("wishlist_count", wishlist_count); // 위시리스트 개수
			
		}
		
		
		System.out.println("************************************************************[com.korebap.app.view.page  wishListPage 종료]************************************************************");

		return "wishList";
	}

}
