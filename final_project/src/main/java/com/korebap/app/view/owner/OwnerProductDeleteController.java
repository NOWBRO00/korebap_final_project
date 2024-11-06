package com.korebap.app.view.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class OwnerProductDeleteController {

	@Autowired
	private ProductService productService;

	@Autowired
	private LoginCheck loginCheck;

	@PostMapping(value="/ownerProductDelete.do")
	public String productDelete(ProductDTO productDTO, Model model) {

		System.out.println("************************************************************[com.korebap.app.view.owner productDelete 시작]************************************************************");

		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {
			
			System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 없음*****");

			
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}
		
		System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();
		
		System.out.println("*****com.korebap.app.view.owner productDelete member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner productDelete role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

			// 바로 이동
			return viewName;

		}
		
		// 상품 번호를 받아와서 DB에 delete 요청
		int product_num = productDTO.getProduct_num();

		System.out.println("*****com.korebap.app.view.owner productDelete product_num ["+product_num+"]*****");

		boolean flag = productService.delete(productDTO);
		
		// 삭제 성공시
		if(flag) {
			System.out.println("*****com.korebap.app.view.owner productDelete 글 삭제 성공*****");

			// 상품 목록 페이지로 이동시킨다.
			viewName = "redirect:ownerProductList.do";
		}
		else{
			System.out.println("*****com.korebap.app.view.owner productDelete 글 삭제 실패*****");

			// 삭제 실패시
			model.addAttribute("msg", "상품 삭제에 실패했습니다. 다시 시도해주세요.");
			model.addAttribute("path", "ownerProductDetail.do?product_num="+product_num);
		}

		System.out.println("*****com.korebap.app.view.owner productDelete viewName ["+viewName+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner productDelete 종료]************************************************************");

		return viewName;
	}

}
