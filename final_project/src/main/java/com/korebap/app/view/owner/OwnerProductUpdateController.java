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
public class OwnerProductUpdateController {


	@Autowired
	private LoginCheck loginCheck;

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageFileService imageFileService;

	// 사진 저장될 경로
	private final static String PATH = "PATH";


	// 상품 수정 (페이지 이동)
	@GetMapping(value="/ownerProductUpdate.do")
	public String ownerProductUpdate(Model model, ProductDTO productDTO, ImageFileDTO imageFileDTO) {
		// 상품 등록 : 단순 페이지 이동
		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductUpdate(GET) 시작]************************************************************");

		// 로그인 상태를 확인한다
		String member_id = loginCheck.loginCheck();

		// 이동시킬 경로를 저장할 변수
		// Info로 보내는 것들이 많아서 info로 초기화
		String viewName = "info";

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) member_id ["+member_id+"]*****");


		if(member_id.equals("")) {
			// 로그인 상태가 아니라면 login 페이지로 이동시킨다

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

		}
		// 로그인 한 상태라면 role을 확인하여 상품 등록 페이지로 이동시킨다

		// 로그인 된 role 확인한다
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) member_role [" + member_role + "]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) role이 OWNER이 아님*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");
		}
		else {
			// role이 OWNER라면

			// 화면에 데이터를 띄워주기 위해 데이터를 전달해준다.
			int product_num = productDTO.getProduct_num();

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) procudt_num ["+product_num+"]*****");

			// id를 보내서 상품 상세 내역과 이미지 상세 내역을 가져온다.
			// PRODUCT_BY_INFO : 상품 내역 selectOne
			productDTO.setProduct_seller_id(member_id);
			productDTO.setProduct_condition("PRODUCT_BY_INFO");
			productDTO = productService.selectOne(productDTO);


			if(productDTO == null) {
				System.out.println("*****com.korebap.app.view.product ownerProductUpdate(GET) 상품 상세 없음 *****");

				model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
				model.addAttribute("path", "ownerProductList.do");

				return viewName;
			}



			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) productDTO ["+productDTO+"]*****");

			// PRODUCT_FILE_SELECTALL : 사진 목록 selectAll
			imageFileDTO.setFile_product_num(product_num);
			imageFileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
			List<ImageFileDTO> fileList = imageFileService.selectAll(imageFileDTO);

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) fileList ["+fileList+"]*****");

			String product_address = productDTO.getProduct_address();



			// 만약 productDTO 객체가 있다면
			System.out.println("*****com.korebap.app.view.product ownerProductUpdate(GET) 상품 상세 있음 *****");

			if(product_address.contains("_")) {
				// DB에는 주소가 합쳐져서 보여지고 있음
				// 저장시 분리 기준이 되는 "_" 기준으로 데이터를 나눠줌
				String[] total_address = productDTO.getProduct_address().split("_");

				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) total_address ["+total_address.toString()+"]*****");

				productDTO.setProduct_postcode(total_address[0]); // 우편번호
				productDTO.setProduct_address(total_address[1]); // 기본 주소
				productDTO.setProduct_extraAddress(total_address[2]); // 추가 주소
				productDTO.setProduct_detailAddress(total_address[3]); // 상세 주소

				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) productDTO ["+productDTO+"]*****");

			}


			model.addAttribute("product", productDTO);
			model.addAttribute("fileList", fileList);



			// 상품 작성 페이지로 이동시킨다.
			viewName = "productManagementEdit";
		}

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(GET) viewName [" + viewName + "]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductUpdate(GET) 종료]************************************************************");

		return viewName;
	} // 상품 수정(GET : 페이지 이동) 메서드 종료



	// 상품 수정
	@PostMapping(value="/ownerProductUpdate.do")
	public String ownerProductUpdate(Model model, ProductDTO productDTO, ImageFileDTO imageFileDTO, List<MultipartFile> product_files,RedirectAttributes redirectAttributes,
			@RequestParam(value="postcode") String product_postcode,
			@RequestParam(value="address") String product_address,
			@RequestParam(value="extraAddress") String product_extraAddress,
			@RequestParam(value="detailAddress") String product_detailAddress) throws IllegalStateException, IOException {

		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductUpdate(POST) 시작]************************************************************");


		// 로그인 상태를 확인한다
		String member_id = loginCheck.loginCheck();

		// 이동시킬 경로를 저장할 변수
		// Info로 보내는 것들이 많아서 info로 초기화
		String viewName = "info";

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) member_id ["+member_id+"]*****");


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

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) member_role [" + member_role + "]*****");

		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) role이 OWNER이 아님*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

		}
		else { // role이 OWNER이라면
			// 상품 사진 / category / location / 상품명 / 가격 / 상품 주소 / 상품 설명

			// 데이터 로그
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 사진 ["+productDTO.getProduct_file_dir()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) category ["+productDTO.getProduct_category()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) location ["+productDTO.getProduct_location()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품명 ["+productDTO.getProduct_name()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 가격 ["+productDTO.getProduct_price()+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 주소(postcode) ["+product_postcode+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 주소(address) ["+product_address+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 주소(extraAddress) ["+product_extraAddress+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 주소(detailAddress) ["+product_detailAddress+"]*****");
			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 설명 ["+productDTO.getProduct_details()+"]*****");

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) product_num ["+productDTO.getProduct_num()+"]*****");



			// 만약 [주소 수정]이 있다면 주소 조합
			if(product_address != null) {
				// 전체 주소 조합
				String total_address = product_postcode + "_" + product_address + "_" + product_extraAddress + "_" + product_detailAddress;

				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) total_address [" + total_address + "]*****");
				productDTO.setProduct_address(total_address);

			}

			int product_num = productDTO.getProduct_num();

			// [이미지 수정] 기존 이미지가 있는지 확인하기 위해 image 데이터를 받아온다.
			imageFileDTO.setFile_product_num(product_num);
			imageFileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
			List<ImageFileDTO> change_image = imageFileService.selectAll(imageFileDTO);

			System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) change_image [" + change_image + "]*****");


			// 파일이 업로드되지 않았을 때 처리
			// 파일 리스트를 stream 타입으로 변환 (스트림 변환시 리스트 항목 연산 적용 가능)
			// MultipartFile::isEmpty는 모든 파일의 isEmpty() 메서드를 호출해, 각 파일이 비어 있는지 확인
			//		>> 실제로 업로드 되지 않거나 비어있는 경우 true 처리
			if (product_files == null || product_files.isEmpty() || 
					product_files.stream().allMatch(MultipartFile::isEmpty)) {
				// 업로드된 파일이 없을 경우의 처리
				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 업로드 된 사진 없음*****");
				// 필요에 따라 추가적인 로직을 작성
			}
			// 새 사진 저장 (업로드한 사진이 있다면)
			else if (!product_files.isEmpty() && product_files != null) {
				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 사진 insert 시작*****");

				// 기존 이미지가 있는 경우
				if(change_image != null && !change_image.isEmpty()) {
					System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 기존 이미지가 있는 경우 *****");

					for(ImageFileDTO imageDTO : change_image ) {
						System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 기존 이미지 삭제를 위한 for문 *****");

						// 기존 이미지 파일의 경로를 가져온다
						String existingFileName = imageDTO.getFile_dir(); // 파일 이름 가져오기
						File change_File = new File(PATH + existingFileName);
						// exists 메서드를 통해 파일이 실제로 존재하는지 확인
						if(change_File.exists()) {
							change_File.delete(); // 기존 파일 삭제
							boolean flag = imageFileService.delete(imageDTO); // DB에서도 삭제

							if(!flag) {
								System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 사진 삭제 실패*****");

								model.addAttribute("msg", "기존 파일 변경에 실패했습니다. 다시 시도해주세요.");
								model.addAttribute("path", "ownerProductUpdate.do");
								// 바로 이동
								return viewName;
							}
						}

					}// 이미지 삭제 for문 종료
				} // 기존 이미지가 있는 경우 로직 종료


				// 반복문을 돌려 사진을 저장시킨다
				for (MultipartFile file : product_files) {

					String originalFilename = file.getOriginalFilename(); // 원본 파일명
					UUID uuid = UUID.randomUUID(); // 파일명 중복을 막기 위해 랜덤값 생성
					String imagePath = uuid.toString() + originalFilename;
					file.transferTo(new File(PATH + imagePath));

					// DB에 새 이미지 정보 저장
					imageFileDTO.setFile_dir(imagePath);
					imageFileDTO.setFile_product_num(product_num);
					imageFileDTO.setFile_condition("PRODUCT_FILE_INSERT");

					boolean imageFileUpdateFlag = imageFileService.insert(imageFileDTO);

					System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) imageFileUpdateFlag ["+imageFileUpdateFlag+"]*****");

					// 사진 inert 실패시
					if(!imageFileUpdateFlag) {
						System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 사진 변경(insert) 실패*****");

						model.addAttribute("msg", "사진 등록에 실패했습니다. 다시 시도해주세요.");
						model.addAttribute("path", "ownerProductUpdate.do");

						// 바로 이동
						return viewName;
					}
				}


			} // 새 사진 저장 로직 종료


			// DB에 변경되는 내용을 update 시킨다.
			productDTO.setProduct_seller_id(member_id);
			boolean product_update_flag = productService.update(productDTO);

			// 수정 성공시
			if(product_update_flag) {
				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 update 성공*****");

				redirectAttributes.addAttribute("product_num", product_num);
				return "redirect:ownerProductDetail.do";
			}else {

				System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 상품 update 실패*****");

				model.addAttribute("msg", "상품 수정에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "ownerProductUpdate.do");
			}


		}

		System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) viewName ["+viewName+"]*****");


		System.out.println("************************************************************[com.korebap.app.view.owner ownerProductUpdate(POST) 종료]************************************************************");


		return viewName;
	}



}
