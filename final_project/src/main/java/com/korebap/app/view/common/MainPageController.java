package com.korebap.app.view.common;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;


@Controller
public class MainPageController {
	// 일반회원인지, 관리자인지, 사장님인지

	// 게시글 목록
	// 신규회원 목록
	// 상품 목록
	@Autowired
	BoardService boardService;
	@Autowired
	ProductService productService;

	// gps 또는 기본 gps(회원가입 시 입력되는 주소)

	@RequestMapping(value="/main.do")
	public String main(BoardDTO boardDTO, ProductDTO productDTO, Model model) {
		System.out.println("=====com.korebap.app.view.page main 시작");

		// DTO 객체를 보내 boardList를 반환받는다.
		boardDTO.setBoard_condition("BOARD_ALL");
		boardDTO.setBoard_page_num(1); // 기본 페이지 설정
		List<BoardDTO> boardList=boardService.selectAll(boardDTO);

		// DTO 객체를 보내 productList를 반받는다.
		//productDTO.setProduct_condition("PRODUCT_BY_ALL");
		productDTO.setProduct_page_num(1); // 기본 페이지 설정
		List<ProductDTO> productList = productService.selectAll(productDTO);

		// 데이터 로그
		System.out.println("=====com.korebap.app.view.page main boardList ["+boardList+"]");
		System.out.println("=====com.korebap.app.view.page main productList ["+productList+"]");

		
		String json = "";
		if(!productList.isEmpty()) { // 상품 정보가 없다면
			for(ProductDTO product: productList) {
				json += "{\"title\":\""+product.getProduct_name()+"\",";
				json += "\"address\":\""+product.getProduct_address()+"\"},";
			}
			json = json.substring(0,json.lastIndexOf(","));
		}
		//json += "]";
		System.out.println("json"+json);
		System.out.println("=====com.korebap.app.view.page main json ["+json+"]");


		model.addAttribute("boardList", boardList);
		model.addAttribute("productList", productList);
		model.addAttribute("json", json);

		// main.jsp 페이지로 데이터를 보낸다.
		
		
		System.out.println("=====com.korebap.app.view.page main 종료");

		return "main";
	}
}






//public class MainPageAction implements Action{
//   // 일반회원인지, 관리자인지, 사장님인지
//
//   // 게시글 목록
//   // 신규회원 목록
//   // 상품 목록
//   
//   // gps 또는 기본 gps(회원가입 시 입력되는 주소)
//   @Override
//   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//      System.out.println("MainPageAction 시작");
////      MemberDTO memberDTO=new MemberDTO();
////      MemberDAO memberDAO=new MemberDAO();
////      ArrayList<MemberDTO> member_list=memberDAO.selectAll(memberDTO);
//
//      BoardDTO boardDTO=new BoardDTO();
//      boardDTO.setBoard_condition("BOARD_ALL");
//      boardDTO.setBoard_page_num(1);
//      
//      BoardDAO boardDAO=new BoardDAO();
//      ArrayList<BoardDTO> boardList=boardDAO.selectAll(boardDTO);
//      
//      ProductDTO productDTO = new ProductDTO();
//      productDTO.setProduct_condition("PRODUCT_BY_ALL");
//      productDTO.setProduct_page_num(1);
//      
//      ProductDAO productDAO=new ProductDAO();
//      ArrayList<ProductDTO> productList = productDAO.selectAll(productDTO);
//      
//      String json = "";
//      if(!productList.isEmpty()) {
//         for(ProductDTO product: productList) {
//            json += "{\"title\":\""+product.getProduct_name()+"\",";
//            json += "\"address\":\""+product.getProduct_address()+"\"},";
//         }
//         json = json.substring(0,json.lastIndexOf(","));
//      }
//      //json += "]";
//      System.out.println("json"+json);
//      
////      request.setAttribute("member_list", member_list);
//      request.setAttribute("boardList", boardList);
//      request.setAttribute("productList", productList);
//      request.setAttribute("json", json);
//      
//      System.out.println(boardList);
//      System.out.println(productList);
//      
//      ActionForward forward=new ActionForward();
//      forward.setPath("main.jsp");
//      forward.setRedirect(false);
//      
//      System.out.println("MainPageAction 끝");
//      return forward;
//   }
//}
