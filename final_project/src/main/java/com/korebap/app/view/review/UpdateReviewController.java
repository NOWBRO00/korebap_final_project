package com.korebap.app.view.review;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.review.ReviewDTO;
import com.korebap.app.biz.review.ReviewService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class UpdateReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private LoginCheck loginCheck;
	
	@PostMapping(value="/updateReview.do")
	public String updateReview(ReviewDTO reviewDTO, Model model,RedirectAttributes redirectAttributes) {

		// [ 리뷰 수정 ]

		System.out.println("************************************************************[com.korebap.app.view.review updateReview 시작]************************************************************");

		
		// 로그인 체크
		String login_member_id = loginCheck.loginCheck();

		// 경로를 저장하는 변수
		String viewName;
		// 상품 상세페이지로 보낼 때 필요한 상품 번호
		int review_product_num = reviewDTO.getReview_product_num();

		// DTO 데이터 로그
		System.out.println("***** com.korebap.app.view.review updateReview member_id 확인 : [" + login_member_id + "] *****");
		System.out.println("***** com.korebap.app.view.review updateReview review_product_num 확인 : [" + review_product_num + "] *****");
		System.out.println("***** com.korebap.app.view.review updateReview review_num 확인 : [" + reviewDTO.getReview_num() + "] *****");
		System.out.println("***** com.korebap.app.view.review updateReview review_star 확인 : [" + reviewDTO.getReview_star() + "] *****");
		System.out.println("***** com.korebap.app.view.review updateReview review_content 확인 : [" + reviewDTO.getReview_content() + "] *****");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.review updateReview 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {

			System.out.println("*****com.korebap.app.view.review updateReview 로그인 세션 있음*****");


			// DTO 객체를 보내 update 진행
			boolean flag = reviewService.update(reviewDTO); 

			if(flag) {
				System.out.println("*****com.korebap.app.view.review updateReview 리뷰 수정 성공*****");

				// 상품 상세 페이지로 보내줌
				// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
				// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
				redirectAttributes.addAttribute("product_num", review_product_num);

				viewName = "redirect:productDetail.do";

			}
			else {
				System.out.println("*****com.korebap.app.view.review updateReview 리뷰 수정 실패*****");

				// 상품 상세 페이지로 보내줌
				model.addAttribute("msg", "리뷰 수정에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "productDetail.do?product_num="+review_product_num);

				viewName = "info";
			}

		}
		System.out.println("*****com.korebap.app.view.wishlist updateReview viewName ["+viewName+"]*****");
		
		System.out.println("************************************************************[com.korebap.app.view.review updateReview 종료]************************************************************");
		return viewName;

	}

}