package com.korebap.app.view.wishlist;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.wishlist.WishlistDTO;
import com.korebap.app.biz.wishlist.WishlistService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class AddWishListController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private LoginCheck loginCheck;

	@PostMapping(value="/addWishList.do")
	public String addWishList(WishlistDTO wishlistDTO,Model model,RedirectAttributes redirectAttributes) {
		// [ 위시리스트 추가 ]
		
		System.out.println("************************************************************[com.korebap.app.view.wishlist addWishList 시작]************************************************************");


		// 로그인체크
		String login_member_id = loginCheck.loginCheck();

		// 경로를 저장하는 변수
		String viewName = "info";
		
		// 상품 상세페이지로 보낼 때 필요한 상품 번호
		int wishlist_product_num = wishlistDTO.getWishlist_product_num();


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.wishlist addWishList member_id 확인 : ["+login_member_id+"]*****");
		System.out.println("*****com.korebap.app.view.wishlist addWishList wishlist_product_num 확인 : ["+wishlist_product_num+"]*****");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.wishlist addWishList 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

		}
		else { // 만약 로그인 상태라면
			System.out.println("*****com.korebap.app.view.wishlist addWishList 로그인 상태 시작*****");


			// 로그인한 회원의 ID를 DTO 객체에 넣어준다.
			wishlistDTO.setWishlist_member_id(login_member_id);

			// 중복 확인을 위해 M에게 데이터를 보내, 해당 회원의 위시리스트를 반환받는다.
			List<WishlistDTO> wishlist = wishlistService.selectAll(wishlistDTO);

			System.out.println("*****com.korebap.app.view.wishlist addWishList 데이터 확인 wishlist : ["+wishlist+"]*****");


			// 위시리스트 중 해당 상품 번호가 있는지 확인
			for (WishlistDTO item : wishlist) {
				if (item.getWishlist_product_num() == wishlist_product_num) {
					// 원하는 상품 번호를 찾았을 때 처리
					System.out.println("*****com.korebap.app.view.wishlist addWishList 동일한 상품 위시리스트에 존재 : ["+item.getWishlist_product_num()+"]*****");

					// 실패 안내 (메세지 view에 전달)
					redirectAttributes.addFlashAttribute("msg", "이미 추가된 상품입니다.");
					// 어디로 보내는지 경로를 작성한다
					return"redirect:productDetail.do?product_num=" + wishlist_product_num;
				}
				System.out.println("*****com.korebap.app.view.wishlist addWishList 중복 상품번호 찾기 종료*****");
			}


			// M에게 받은 위시리스트 중 없다면 insert 진행
			// M에게 DTO 객체를 보내 insert 진행하고, 결과를 boolean 타입으로 반환받는다.
			boolean flag = wishlistService.insert(wishlistDTO);

			System.out.println("*****com.korebap.app.view.wishlist addWishList insert 결과 ["+flag+"]*****");

			if(flag) {// 만약 true 반환받았다면 
				System.out.println("*****com.korebap.app.view.wishlist addWishList insert 성공*****");
				// 상품 상세페이지로 이동

				// addFlashAttribute : 주로 리다이렉트 후에 한 번만 사용할 데이터를 전달할 때 사용 (ex.성공 메시지, 에러 메시지 등)
				// addAttribute :  뷰에 직접 전달하고자 하는 데이터를 설정
				redirectAttributes.addFlashAttribute("msg", "상품이 찜 목록에 추가되었습니다.");
				redirectAttributes.addAttribute("product_num", wishlist_product_num);

				viewName = "redirect:productDetail.do";

			}
			else { // 만약 false 반환받았다면
				System.out.println("*****com.korebap.app.view.wishlist addWishList insert 실패*****");
				// 실패 안내
				model.addAttribute("msg", "위시리스트 추가에 실패했습니다. 다시 시도해주세요.");
				// 상품 상세페이지로 이동
				model.addAttribute("path", "productDetail.do?product_num="+wishlist_product_num);

				// 어디로 보내는지 경로를 작성한다
				// 보낼 데이터가 있다면 포워드, 없다면 리다이렉트 방식으로 전달한다.
				viewName = "info";
			}
		}
		System.out.println("*****com.korebap.app.view.wishlist addWishList viewName ["+viewName+"]*****");
		System.out.println("************************************************************[com.korebap.app.view.wishlist addWishList 종료]************************************************************");

		return viewName;

	}

}

