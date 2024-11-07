package com.korebap.app.view.owner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class OwnerProductInsertController {

	@Autowired
	private LoginCheck loginCheck;

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageFileService imageFileService;

	// 사진 저장될 경로
	private final static String PATH = "PATH";


	// 상품 등록
	@GetMapping(value="/ownerProductInsert.do")
	public String ownerProductInsert(Model model) {
		// 상품 등록 : 단순 페이지 이동
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductInsert(GET) 시작]************************************************************");

		// 로그인 상태를 확인한다
		String member_id = loginCheck.loginCheck();

		// 이동시킬 경로를 저장할 변수
		// Info로 보내는 것들이 많아서 info로 초기화
		String viewName = "info";

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(GET) member_id ["+member_id+"]*****");


		if(member_id.equals("")) {
			// 로그인 상태가 아니라면 login 페이지로 이동시킨다

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");
			
			return viewName;

		}
		// 로그인 한 상태라면 role을 확인하여 상품 등록 페이지로 이동시킨다

		// 로그인 된 role 확인한다
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(GET) member_role [" + member_role + "]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(GET) role이 OWNER이 아님*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");
		}
		else {
			// role이 OWNER라면
			// 상품 작성 페이지로 이동시킨다.
			viewName = "redirect:productManagementInsert.jsp";
		}

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(GET) viewName [" + viewName + "]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductInsert(GET) 종료]************************************************************");

		return viewName;

	} // 상품 등록(GET) 메서드 종료



	
	@PostMapping(value="/ownerProductInsert.do")
	public String ownerProductInsert(Model model, ProductDTO productDTO, ImageFileDTO imageFileDTO,
			RedirectAttributes redirectAttributes,
			@RequestParam("files") List<MultipartFile> files,
			@RequestParam(value="postcode") String product_postcode,
			@RequestParam(value="address") String product_address,
			@RequestParam(value="extraAddress") String product_extraAddress,
			@RequestParam(value="detailAddress") String product_detailAddress) throws IOException {

		// 상품 등록
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductInsert(POST) 시작]************************************************************");

		// 로그인 상태를 확인한다
		String member_id = loginCheck.loginCheck();

		// 이동시킬 경로를 저장할 변수
		// Info로 보내는 것들이 많아서 info로 초기화
		String viewName = "info";

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) member_id ["+member_id+"]*****");


		if(member_id.equals("")) {
			// 로그인 상태가 아니라면 login 페이지로 이동시킨다

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");
			// 바로 이동
			return viewName;

		}
		
		// 로그인 한 상태라면 role을 확인하여 상품 등록 페이지로 이동시킨다
		// 로그인 된 role 확인한다
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) member_role [" + member_role + "]*****");

		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) role이 OWNER이 아닌경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

		}
		else {
			// role이 OWNER라면
			// 상품 정보를 insert

			// 상품 사진 / category / location / 상품명 / 가격 / 상품 주소 / 상품 설명

			// 데이터 로그
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 사진 ["+productDTO.getProduct_file_dir()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) category ["+productDTO.getProduct_category()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) location ["+productDTO.getProduct_location()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품명 ["+productDTO.getProduct_name()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 가격 ["+productDTO.getProduct_price()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) files ["+files+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 주소(postcode) ["+product_postcode+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 주소(address) ["+product_address+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 주소(extraAddress) ["+product_extraAddress+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 주소(detailAddress) ["+product_detailAddress+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 설명 ["+productDTO.getProduct_details()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 재고 ["+productDTO.getProduct_cnt()+"]*****");

			// 전체 주소 조합
			String total_address = product_postcode + "_" + product_address + "_" + product_extraAddress + "_" + product_detailAddress;

			System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) total_address [" + total_address + "]*****");

			// DB에 저장하기 위해 로그인 한 id / 조합한 주소 / condition을 DTO에 저장
			productDTO.setProduct_seller_id(member_id);
			productDTO.setProduct_address(total_address);
			productDTO.setProduct_condition("PRODUCT_INSERT");

			// [상품] DB에 insert요청
			boolean flag = productService.insert(productDTO);

			// [상품 번호 받아오기]
			if(flag) { // 상품 등록이 성공했다면
				System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) 상품 insert 성공*****");

				// 사진을 저장하기 위해 가장 최근 저장된 상품 pk를 받아온다
				productDTO.setProduct_condition("PRODUCT_NUM_SELECT");
				productDTO = productService.selectOne(productDTO);
				
				int product_num = productDTO.getProduct_num();
				
				System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) product_num ["+product_num+"]*****");

				
				// [이미지파일 등록]
				// DTO에 들어있는 MultipartFile 객체를 가지고 온다. (업로드 된 정보를 가지고 있음)
				//files = imageFileDTO.getFiles();
				
				// 파일이 비어있지 않은 경우 (사진이 있는 경우)
				if(!files.isEmpty() && files != null) {
					//반복문을 돌려 사진등록 로직 시작
					for(MultipartFile file : files) {
						// 원본 파일명을 받아온다
						String originalFilename = file.getOriginalFilename();
						
						// 파일이 겹치는 경우를 방지하기 위해 UUID 사용 (랜덤값)
						UUID uuid = UUID.randomUUID();
						
						// UUID + 원본 파일명 결합하여 파일명 만들어준다.
						String imagePath = uuid.toString() + originalFilename;
						
						System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) imagePath ["+imagePath+"]*****");

						
						// transferTo 메서드를 사용하여 업로드한 사진을 경로에 저장한다
						// File 객체를 생성하여 (경로 + 파일명)으로 지정
						file.transferTo(new File(PATH + imagePath));
						
						// dir을 DB에 저장
						imageFileDTO.setFile_dir(imagePath); // 상품 사진 dir
						imageFileDTO.setFile_product_num(product_num); // 상품 번호
						imageFileDTO.setFile_condition("PRODUCT_FILE_INSERT");
						
						boolean imageFile_flag = imageFileService.insert(imageFileDTO);
						
						
						
						// 만약 사진 업로드 실패시
						if(!imageFile_flag) {
							model.addAttribute("msg", "사진 업로드에 실패했습니다. 다시 시도해주세요.");
							model.addAttribute("path", "ownerProductInsert.do");
							// info로 바로 이동
							return viewName;
						}
						
						
					}// for문 종료
					
					
				} // 사진이 있는 경우 종료
				
				// 상품 등록 모두 성공한 경우
				redirectAttributes.addAttribute("product_num", product_num);
				viewName = "redirect:ownerProductDetail.do";

			}


		}

		System.out.println("*****com.korebap.app.view.owner ownerProductInsert(POST) viewName [" + viewName + "]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductInsert(POST) 시작]************************************************************");

		return viewName;

	} // 상품 insert(POST) 메서드 종료


	
	// 상품 사진만 추가
	@PostMapping(value="/ownerProductImageAdd.do")
	public String ownerProductImageAdd(Model model, ProductDTO productDTO, ImageFileDTO imageFileDTO,
			RedirectAttributes redirectAttributes,
			@RequestParam("files") List<MultipartFile> files) throws IOException {

		
		// @RequestParam("product_num") int product_num,
		// 상품 등록
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductImageAdd 시작]************************************************************");

		// 로그인 상태를 확인한다
		String member_id = loginCheck.loginCheck();

		// 이동시킬 경로를 저장할 변수
		// Info로 보내는 것들이 많아서 info로 초기화
		String viewName = "info";

		System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd member_id ["+member_id+"]*****");


		if(member_id.equals("")) {
			// 로그인 상태가 아니라면 login 페이지로 이동시킨다

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");
			// 바로 이동
			return viewName;

		}
		
		// 로그인 한 상태라면 role을 확인하여 상품 등록 페이지로 이동시킨다
		// 로그인 된 role 확인한다
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd member_role [" + member_role + "]*****");

		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd role이 OWNER이 아닌경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

		}
		else {
			// role이 OWNER라면
			System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd role이 OWNER라면*****");

			// 상품의 사진을 추가한다! (insert)

			// 데이터 로그
			System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd 상품 사진 ["+productDTO.getProduct_file_dir()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd files ["+files+"]*****");

			// [상품 번호 받아오기]
//			if(flag) { // 상품 등록이 성공했다면
				// 상품 번호 받아오기
				int product_num = productDTO.getProduct_num();
				
				System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd product_num ["+product_num+"]*****");

				
				// [이미지파일 등록]
				// DTO에 들어있는 MultipartFile 객체를 가지고 온다. (업로드 된 정보를 가지고 있음)
				//files = imageFileDTO.getFiles();
				
				// 파일이 비어있지 않은 경우 (사진이 있는 경우)
				if(!files.isEmpty() && files != null) {
					//반복문을 돌려 사진등록 로직 시작
					for(MultipartFile file : files) {
						// 원본 파일명을 받아온다
						String originalFilename = file.getOriginalFilename();
						
						// 파일이 겹치는 경우를 방지하기 위해 UUID 사용 (랜덤값)
						UUID uuid = UUID.randomUUID();
						
						// UUID + 원본 파일명 결합하여 파일명 만들어준다.
						String imagePath = uuid.toString() + originalFilename;
						
						System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd imagePath ["+imagePath+"]*****");

						
						// transferTo 메서드를 사용하여 업로드한 사진을 경로에 저장한다
						// File 객체를 생성하여 (경로 + 파일명)으로 지정
						file.transferTo(new File(PATH + imagePath));
						
						// dir을 DB에 저장
						imageFileDTO.setFile_dir(imagePath); // 상품 사진 dir
						imageFileDTO.setFile_product_num(product_num); // 상품 번호
						imageFileDTO.setFile_condition("PRODUCT_FILE_INSERT");
						
						boolean imageFile_flag = imageFileService.insert(imageFileDTO);
						
						
						// 만약 사진 업로드 실패시
						if(!imageFile_flag) {
							model.addAttribute("msg", "사진 업로드에 실패했습니다. 다시 시도해주세요.");
							model.addAttribute("path", "ownerProductInsert.do");
							// info로 바로 이동
							return viewName;
						}
						
					}// for문 종료
					
					
				} // 사진이 있는 경우 종료
				
				// 상품 등록 모두 성공한 경우
				redirectAttributes.addAttribute("product_num", product_num);
				viewName = "redirect:ownerProductDetail.do";

//			}


		}

		System.out.println("*****com.korebap.app.view.owner ownerProductImageAdd viewName [" + viewName + "]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductImageAdd 종료]************************************************************");

		return viewName;

	} // 상품 이미지 insert(POST) 메서드 종료
	
	
	
	
	
}
