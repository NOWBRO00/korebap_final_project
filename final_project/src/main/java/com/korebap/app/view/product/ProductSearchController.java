package com.korebap.app.view.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;

@Controller
public class ProductSearchController {
    @Autowired
    private ProductService productService;

    @GetMapping(value="/searchProduct.do")
    public String searchProducts(
            ProductDTO productDTO,
            Model model,
            @RequestParam(value="currentPage", defaultValue="1") int product_page_num) { // 현재 페이지 번호

        System.out.println("=====com.korebap.app.view.async ProductSearchController 시작");
        System.out.println("=====com.korebap.app.view.async ProductSearchController productDTO : [" + productDTO + "]");

        // 기본값 설정
        if (productDTO.getProduct_searchKeyword() == null) {
            productDTO.setProduct_searchKeyword(""); // 검색어 기본값
        }
        if (productDTO.getProduct_types() == null) {
            productDTO.setProduct_types(List.of()); // 기본값: 빈 리스트
        }
        if (productDTO.getProduct_categories() == null) {
            productDTO.setProduct_categories(List.of()); // 기본값: 빈 리스트
        }
        // 여기서 product_location, product_category 등 다른 필드도 필요에 따라 기본값을 설정할 수 있습니다.

        
        // 검색 결과 조회
        productDTO.setProduct_condition("PRODUCT_SELECTALL_SEARCH");
        List<ProductDTO> productList = productService.selectAll(productDTO); // 검색된 상품 목록

        // 총 개수 및 총 페이지 수 계산
        int totalPages = productDTO.getProduct_total_page(); // 총 페이지 수
        List<String> product_types   = productDTO.getProduct_types();
        List<String> product_categories = productDTO.getProduct_categories();
        String product_searchKeyword = productDTO.getProduct_searchKeyword();
        System.out.println("=====com.korebap.app.view.async ProductSearchController productList : [" + productList + "]");
        System.out.println("Total Pages: " + totalPages);
 

        // 모델에 데이터 추가
        model.addAttribute("productList", productList);  // 검색된 상품 목록 추가
        model.addAttribute("productTypes", product_types); // 유형 목록 수 추가
        model.addAttribute("productCategories", product_categories); // 카테고리 목록 수 추가
        model.addAttribute("product_searchKeyword",product_searchKeyword); // 키워드
        model.addAttribute("product_page_count", totalPages); // 총 페이지 수 추가
        model.addAttribute("currentPage", product_page_num); // 현재 페이지 추가

        // 값 확인을 위한 로그 출력
        System.out.println("=====com.korebap.app.view.async ProductSearchController 총 페이지 수: [" + totalPages + "]");
        System.out.println("=====com.korebap.app.view.async ProductSearchController 현재 페이지: [" + product_page_num + "]");
        System.out.println("=====com.korebap.app.view.async ProductSearchController 유형 목록 수: [" + product_types + "]");
        System.out.println("=====com.korebap.app.view.async ProductSearchController 카테고리 목록 수: [" + product_categories + "]");
        System.out.println("=====com.korebap.app.view.async ProductSearchController 키워드: [" + product_searchKeyword + "]");

        System.out.println("=====com.korebap.app.view.async ProductSearchController 종료");
        // productList.jsp로 이동
        return "productList";
    }
}
