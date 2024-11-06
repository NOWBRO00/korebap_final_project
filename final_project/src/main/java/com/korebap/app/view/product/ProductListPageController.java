package com.korebap.app.view.product;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;

@Controller
public class ProductListPageController {

	@Autowired
	private ProductService productService;

	@GetMapping(value="/productListPage.do")
	public String productListPage(ProductDTO productDTO, Model model, @RequestParam(value="currentPage", defaultValue="1") int product_page_num) {
		// 인자로 받는 cruuentPage는 defaultValue를 설정하여 값이 없는 경우 1로 설정한다.
		// *초기 페이지이므로 null로 들어올 수 있음

		// [ 상품 목록 페이지 ]
		System.out.println("************************************************************[com.korebap.app.view.page productListPage 시작]************************************************************");


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.page productListPage productDTO ["+productDTO+"]*****");
		System.out.println("*****com.korebap.app.view.page productListPage product_page_num ["+product_page_num+"]*****");

		// V에서 받아온 파라미터
		String product_searchKeyword = productDTO.getProduct_searchKeyword(); // 검색어
		String product_location = productDTO.getProduct_location(); // 상품 장소 (바다,민물)
		String product_category = productDTO.getProduct_category(); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
		String product_search_criteria = productDTO.getProduct_search_criteria(); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준

		// 데이터
		System.out.println("*****com.korebap.app.view.page productListPage product_searchKeyword ["+product_searchKeyword+"]*****");
		System.out.println("*****com.korebap.app.view.page productListPage product_location ["+product_location+"]*****");
		System.out.println("*****com.korebap.app.view.page productListPage product_category ["+product_category+"]*****");
		System.out.println("*****com.korebap.app.view.page productListPage product_search_criteria ["+product_search_criteria+"]*****");


		// 사용자가 선택한 페이지번호 처리
		productDTO.setProduct_page_num(product_page_num);
		// M에게 데이터를 보내주고 결과를 받음
		List<ProductDTO> productList = productService.selectAll(productDTO);

		System.out.println("*****com.korebap.app.view.page productListPage productList ["+productList+"]*****");



		// [전체 페이지 개수 받아오기]
		productDTO.setProduct_condition("PRODUCT_PAGE_COUNT");
		productDTO = productService.selectOne(productDTO);

		// int 타입 변수에 받아온 값을 넣어준다.
		int product_total_page = productDTO.getProduct_total_page();
		
		System.out.println("*****com.korebap.app.view.page productListPage product_total_page ["+product_total_page+"]*****");

		// 전체 페이지 수가 1보다 작다면
		if (product_total_page < 1) {
			product_total_page = 1; // 최소 페이지 수를 1로 설정
		}



		// 모델에 데이터 추가
		model.addAttribute("productList", productList); // 상품 목록
		model.addAttribute("product_location", product_location); // 위치 필터
		model.addAttribute("product_category", product_category); // 카테고리 필터
		model.addAttribute("searchOption", product_search_criteria); // 검색 기준
		model.addAttribute("product_page_count", product_total_page); // 페이지 수
		model.addAttribute("currentPage", product_page_num); // 현재 페이지

		System.out.println("************************************************************[com.korebap.app.view.page productListPage 종료]************************************************************");

		return "productList";
	}

}