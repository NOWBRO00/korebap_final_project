package com.korebap.app.view.product;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.biz.review.ReviewDTO;
import com.korebap.app.biz.review.ReviewService;
import com.korebap.app.biz.wishlist.WishlistDTO;


@Controller
public class ProductDetailController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ImageFileService fileService;

	@GetMapping(value="/productDetail.do")
	public String productDetail(Model model,ProductDTO productDTO, 
			ReviewDTO reviewDTO,ImageFileDTO fileDTO, WishlistDTO wishlistDTO) {

		// [ 상품 상세보기 ]
		System.out.println("************************************************************[com.korebap.app.view.product productDetail 시작]************************************************************");

		// 경로를 담을 변수 설정
		String viewName;
		
		// 상품 번호
		int product_num = productDTO.getProduct_num();
		
		// 데이터 로그
		System.out.println("*****com.korebap.app.view.product product_num ["+product_num+"]*****");

		
		// [상품 리스트] 반환
		productDTO.setProduct_condition("PRODUCT_BY_INFO");
		productDTO = productService.selectOne(productDTO);
		
		// [파일 리스트] 반환
		fileDTO.setFile_product_num(product_num);
		fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
		List<ImageFileDTO> fileList = fileService.selectAll(fileDTO);

		// [리뷰 리스트] 반환
		reviewDTO.setReview_product_num(product_num);
		System.out.println("reviewDTO 확인 : "+reviewDTO+"]");
		List<ReviewDTO> reviewList = reviewService.selectAll(reviewDTO);

		
		// 만약 productDTO 객체가 있다면
		if(productDTO != null) {
			System.out.println("*****com.korebap.app.view.product productDetail 상품 상세 있음 *****");

			model.addAttribute("product", productDTO);
			model.addAttribute("fileList", fileList);
			model.addAttribute("reviewList", reviewList);
			
			viewName = "productDetail";
		}
		else {
			System.out.println("*****com.korebap.app.view.product productDetail 상품 상세 없음 *****");
			
			model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
			model.addAttribute("path", "productList.do");
			
			viewName = "info";
			
		}
		System.out.println("*****com.korebap.app.view.product productDetail viewName ["+ viewName +"]*****");
		
		System.out.println("************************************************************[com.korebap.app.view.product productDetail 종료]************************************************************");

		return viewName;
	}

}