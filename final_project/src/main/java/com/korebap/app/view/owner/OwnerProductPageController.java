package com.korebap.app.view.owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class OwnerProductPageController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageFileService imageFileService;

	@Autowired
	private LoginCheck loginCheck;


	// 상품 목록
	@GetMapping(value="/ownerProductList.do")
	public String ownerProductList(Model model,ProductDTO productDTO, @RequestParam(value="currentPage", defaultValue="1") int product_page_num) {

		// 인자로 받는 cruuentPage는 defaultValue를 설정하여 값이 없는 경우 1로 설정한다.
		// *초기 페이지이므로 null로 들어올 수 있음

		// [ 상품 목록 페이지 ]
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductList 시작]************************************************************");

		// 로그인 체크
		String member_id = loginCheck.loginCheck();

		// 어디로 이동시킬지 경로를 저장하는 변수 (주로 info로 이동이라 info로 초기화)
		String viewName = "info";


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.owner ownerProductList productDTO ["+productDTO+"]*****");
		System.out.println("*****com.korebap.app.view.owner ownerProductList product_page_num ["+product_page_num+"]*****");
		System.out.println("*****com.korebap.app.view.owner ownerProductList member_id ["+member_id+"]*****");


		// 로그인 상태가 아니라면 로그인 페이지로 이동시킨다
		if(member_id.equals("")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductList 로그인 세션 없음" + "]*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			return viewName;

		}
		else { // 로그인 상태라면 

			System.out.println("*****com.korebap.app.view.owner ownerProductList 로그인 세션 있음" + "]*****");

			// 회원의 role 확인한다
			String member_role = loginCheck.loginRoleCheck();

			System.out.println("*****com.korebap.app.view.owner ownerProductList member_role [" + member_role + "]*****");


			// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
			if(!member_role.equals("OWNER")) {

				System.out.println("*****com.korebap.app.view.owner ownerProductList role이 OWNER이 아님 "+"*****");

				model.addAttribute("msg", "권한이 없는 아이디입니다.");
				model.addAttribute("path", "main.do");
				// 바로 이동시킴
				return viewName;
			}

			System.out.println("*****com.korebap.app.view.owner ownerProductList role이 OWNER *****");


			// seller id에 로그인 된 아이디와, 사용자가 선택한 페이지 번호를 DTO에 저장해서 DB에 자료 요청
			productDTO.setProduct_seller_id(member_id);
			productDTO.setProduct_page_num(product_page_num);
			productDTO.setProduct_condition("PRODUCT_SELECTALL_OWNER");
			System.out.println("컨트롤러  PRODUCTDTO :["+productDTO+"]");
			// M에게 데이터를 보내주고 list를 반환받음
			List<ProductDTO> ownerProductList = productService.selectAll(productDTO);


			System.out.println("*****com.korebap.app.view.owner ownerProductList ownerProductList ["+ownerProductList+"]*****");


			// [전체 페이지 개수 받아오기]
			productDTO.setProduct_condition("PRODUCT_SELECTONE_OWNER_TOTAL_PAGE");
			productDTO = productService.selectOne(productDTO);

			// int 타입 변수에 받아온 값을 넣어준다.
			int product_total_page = productDTO.getProduct_total_page();

			System.out.println("*****com.korebap.app.view.owner ownerProductList product_total_page ["+product_total_page+"]*****");

			// 전체 페이지 수가 1보다 작다면
			if (product_total_page < 1) {
				product_total_page = 1; // 최소 페이지 수를 1로 설정
			}


			// 모델 객체에 데이터 추가
			model.addAttribute("productList", ownerProductList); // 상품 목록
			model.addAttribute("product_page_count", product_total_page); // 페이지 수
			model.addAttribute("currentPage", product_page_num); // 현재 페이지

			// 사장님 상품 리스트 페이지로 이동
			viewName = "productManagement";

		}
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductList 종료]************************************************************");

		return viewName;
	} // 내 상품 목록 보기 메서드 종료


	// 상품 상세 페이지
	@GetMapping(value="/ownerProductDetail.do")
	public String ownerProductDetail(Model model,ProductDTO productDTO, ImageFileDTO imageFileDTO) {

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductDetail 시작]************************************************************");

		// PRODUCT_BY_INFO (selectOne)
		// 상품 번호를 DB에 보내서 상품 상세 내역을 받아온다 (주소 포함)
		// 상품 번호를 imageFile에 보내서 해당 상품의 사진 파일을 받아온다.

		// 상품 경로를 저장할 변수
		String viewName = "info";

		// 로그인 상태 체크
		String member_id = loginCheck.loginCheck();

		// 로그인 상태가 아니라면 로그인 페이지로 이동시킨다
		if(member_id.equals("")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductDetail 로그인 세션 없음" + "]*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

		}
		else { // 로그인 상태라면
			System.out.println("*****com.korebap.app.view.owner ownerProductDetail 로그인 세션 있음" + "]*****");

			// 회원의 role 확인한다
			String member_role = loginCheck.loginRoleCheck();

			System.out.println("*****com.korebap.app.view.owner ownerProductDetail member_role [" + member_role + "]*****");


			// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
			if(!member_role.equals("OWNER")) {

				System.out.println("*****com.korebap.app.view.owner ownerProductDetail role이 OWNER이 아님 "+"*****");

				model.addAttribute("msg", "권한이 없는 아이디입니다.");
				model.addAttribute("path", "main.do");
				// 바로 이동시킴
				return viewName;
			}

			// 상품 번호를 보내서 상품 상세 내역과 이미지 상세 내역을 가져온다.

			int product_num = productDTO.getProduct_num();

			System.out.println("*****com.korebap.app.view.owner ownerProductDetail procudt_num ["+product_num+"]*****");

			// PRODUCT_BY_INFO : 상품 내역 selectOne
			productDTO.setProduct_seller_id(member_id);
			productDTO.setProduct_condition("PRODUCT_BY_INFO");
			productDTO = productService.selectOne(productDTO);

			System.out.println("*****com.korebap.app.view.owner ownerProductDetail productDTO ["+productDTO+"]*****");

			// PRODUCT_FILE_SELECTALL : 사진 목록 selectAll
			imageFileDTO.setFile_product_num(product_num);
			imageFileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
			List<ImageFileDTO> fileList = imageFileService.selectAll(imageFileDTO);

			System.out.println("*****com.korebap.app.view.owner ownerProductDetail fileList ["+fileList+"]*****");

			String product_address = productDTO.getProduct_address();


			// 만약 productDTO 객체가 있다면
			if(productDTO != null) {
				System.out.println("*****com.korebap.app.view.product ownerProductDetail 상품 상세 있음 *****");

				if(product_address.contains("_")) {
					// DB에는 주소가 합쳐져서 보여지고 있음
					// 저장시 분리 기준이 되는 "_" 기준으로 데이터를 나눠줌
					String[] total_address = productDTO.getProduct_address().split("_");

					System.out.println("*****com.korebap.app.view.owner ownerProductDetail total_address ["+total_address.toString()+"]*****");

					productDTO.setProduct_postcode(total_address[0]); // 우편번호
					productDTO.setProduct_address(total_address[1]); // 기본 주소
					productDTO.setProduct_extraAddress(total_address[2]); // 추가 주소
					productDTO.setProduct_detailAddress(total_address[3]); // 상세 주소

					System.out.println("*****com.korebap.app.view.owner ownerProductDetail productDTO ["+productDTO+"]*****");

				}


				model.addAttribute("product", productDTO);
				model.addAttribute("fileList", fileList);

				// 상품 상세페이지로 이동
				viewName = "productManagementDetail";

			}
			else if(productDTO == null) {
				System.out.println("*****com.korebap.app.view.product ownerProductDetail 상품 상세 없음 *****");

				model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
				model.addAttribute("path", "ownerProductList.do");

			}

		}

		System.out.println("*****com.korebap.app.view.product ownerProductDetail viewName ["+ viewName +"]*****");
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductDetail 종료]************************************************************");


		return viewName;
	} // 내 상품 상세보기 메서드 종료


}
