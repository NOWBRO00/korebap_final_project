package com.korebap.app.view.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;


@RestController
public class ProductAsyncController {

   @Autowired
   ProductService productService;

   @GetMapping(value="/productList.do")
   public @ResponseBody Map<String, Object> productPage(ProductDTO productDTO,
         @RequestParam(value="currentPage", required = false, defaultValue = "1") int current_page) {

      System.out.println("************************************************************[com.korebap.app.view.async productPage (비동기) 시작]************************************************************");

      // [ 상품 페이지네이션]
      // 데이터로그
      System.out.println("*****com.korebap.app.view.async productPage 비동기 currentPage [" + current_page + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 product_searchKeyword [" + productDTO.getProduct_searchKeyword() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 product_location [" + productDTO.getProduct_location() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 product_category [" + productDTO.getProduct_category() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 product_search_criteria [" + productDTO.getProduct_search_criteria() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 Product_page_num [" + productDTO.getProduct_page_num() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 Product_categories ["+productDTO.getProduct_categories()+"]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 Product_total_page [" + productDTO.getProduct_total_page() + "]*****");
      System.out.println("*****com.korebap.app.view.async productPage 비동기 Product_types ["+productDTO.getProduct_types()+"]*****");

      List<ProductDTO> productList=null;
      int product_total_page=0;
      Map<String, Object> responseMap = new HashMap<>();

      // 위치나 카테고리 중 하나라도 값이 존재하지 않을 경우
      if ((productDTO.getProduct_location() == null || productDTO.getProduct_location().isEmpty()) &&
            (productDTO.getProduct_category() == null || productDTO.getProduct_category().isEmpty())) {

         // 정제
         if (productDTO.getProduct_types() != null && !productDTO.getProduct_types().isEmpty()) {
            //타입이 있다면
            String Product_type = productDTO.getProduct_types().get(0);
            // 리스트를 받아와서 첫번째 값을 넣어줌
            Product_type = Product_type.replace("[", "").replace("]", "").replace(" ", "");
            String[] ProductTypes = Product_type.split(",");
            List<String> Product_types = new ArrayList<>();
            for (String ProductType : ProductTypes) {
               Product_types.add(ProductType);
            }
            productDTO.setProduct_types(Product_types);
         } else {
            System.out.println("productDTO.getProduct_types == null");
            // 빈 리스트로 설정
            productDTO.setProduct_types(new ArrayList<>());
         }

         if (productDTO.getProduct_categories() != null && !productDTO.getProduct_categories().isEmpty()) {
            // 카테고리가 있다면
            String Product_categorie = productDTO.getProduct_categories().get(0);
            Product_categorie = Product_categorie.replace("[", "").replace("]", "").replace(" ", "");
            String[] ProductCategorie = Product_categorie.split(",");
            List<String> productCategories = new ArrayList<>();
            for (String productCategorie : ProductCategorie) {
               productCategories.add(productCategorie);
            }
            productDTO.setProduct_categories(productCategories);
         } else {
            System.out.println("productDTO.getProduct_categories == null");
            // 빈 리스트로 설정
            productDTO.setProduct_categories(new ArrayList<>());
         }



         productDTO.setProduct_condition("PRODUCT_SELECTALL_SEARCH");
         productDTO.setProduct_page_num(current_page);
         productList = productService.selectAll(productDTO);  
      }

      else  {

         // 카테고리와 위치에 대한 기본값 설정
         if (productDTO.getProduct_category() == null || productDTO.getProduct_category().isEmpty()) {
            productDTO.setProduct_category(null); // 필터 적용하지 않음
         }

         if (productDTO.getProduct_location() == null || productDTO.getProduct_location().isEmpty()) {
            productDTO.setProduct_location(null); // 필터 적용하지 않음
         }


         //M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다. 
         productDTO.setProduct_page_num(current_page);
         productList = productService.selectAll(productDTO);

         System.out.println("*****com.korebap.app.view.async productPage 비동기 productList [" + productList + "]*****");


         // [게시판 페이지 전체 개수]
         productDTO.setProduct_condition("PRODUCT_PAGE_COUNT");
         productDTO = productService.selectOne(productDTO);

      }

      // int 타입 변수에 받아온 값을 넣어준다.
      product_total_page = productDTO.getProduct_total_page();
      // 현재 페이지 > 전체 페이지
      if (current_page > product_total_page) {
         System.out.println("*****com.korebap.app.view.async productPage 비동기 : 마지막 페이지 요청, 더 이상 데이터 없음");
         return null;
      }


      System.out.println("*****com.korebap.app.view.async productPage 비동기 product_total_page [" + product_total_page + "]*****");

      // 결과를 Map에 담아 반환
      //Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("productList", productList);
      responseMap.put("product_page_count", product_total_page);
      responseMap.put("currentPage", current_page);


      System.out.println("*****com.korebap.app.view.async productPage 비동기 responseMap" +responseMap+ "]");

      System.out.println("************************************************************[com.korebap.app.view.async productPage (비동기) 종료]************************************************************");

      return responseMap; 
   }

}
