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
public class DeleteReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private LoginCheck loginCheck;

	@PostMapping(value="/deleteReview.do")
	public String deleteReview(ReviewDTO reviewDTO, Model model, RedirectAttributes redirectAttributes) {
		// [ 리뷰 삭제 ]
		System.out.println("************************************************************[com.korebap.app.view.review deleteReview 시작]************************************************************");


		// 로그인 체크
		String login_member_id = loginCheck.loginCheck();
		// 페이지 경로 변수 설정
		String viewName;
		// 상세페이지로 이동시키기 위해 변수 선언
		int review_product_num = reviewDTO.getReview_product_num();


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.review deleteReview member_mid 확인 : ["+login_member_id+"]*****");
		System.out.println("*****com.korebap.app.view.review deleteReview review_num 확인 : ["+reviewDTO.getReview_num()+"]*****");
		System.out.println("*****com.korebap.app.view.review deleteReview review_product_num 확인 : ["+review_product_num+"]*****");




		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.review deleteReview 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {
			System.out.println("*****com.korebap.app.view.review deleteReview 로그인 세션 있음*****");

			// M에게 보내 삭제 요청
			boolean flag = reviewService.delete(reviewDTO);

			if(flag) {
				System.out.println("*****com.korebap.app.view.review deleteReview 리뷰 삭제 성공 반환*****");

				// 상품 번호를 쿼리 매개변수로 추가
				redirectAttributes.addAttribute("product_num", review_product_num);		
				
				// 경로 설정
				viewName = "redirect:productDetail.do";

			}
			else {
				System.out.println("*****com.korebap.app.view.review deleteReview 리뷰 삭제 실패*****");

				// 로그인 안내 후 login 페이지로 이동시킨다
				model.addAttribute("msg", "리뷰 삭제에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "productDetail.do?product_num="+review_product_num);

				// 경로 설정
				viewName = "info";
			}

		}
		
		System.out.println("*****com.korebap.app.view.reply deleteReview viewName ["+viewName+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.review deleteReview 종료]************************************************************");
		
		return viewName;

	}

}
