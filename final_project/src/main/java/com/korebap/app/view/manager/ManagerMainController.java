package com.korebap.app.view.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

@Controller
public class ManagerMainController {

	@Autowired
	private MemberService memberService; // MemberService 의존성 주입

	@Autowired
	private ProductService productService; // ProductService 의존성 주입

	@Autowired
	private BoardService boardService; // BoardService 의존성 주입
	
	@Autowired
	private ReplyService replyService; // ReplyService 의존성 주입

	@Autowired
	private ClaimService claimService; // ClaimService 의존성 주입

	@RequestMapping(value = "/managerMain.do", method = RequestMethod.GET)
	public String managerMain(MemberDTO memberDTO, ProductDTO productDTO, BoardDTO boardDTO, ClaimDTO claimDTO,
			@RequestParam(value="currentPage", defaultValue="1") int claimList_page_num, Model model) {
		// 메서드 시작 로그 출력
		System.out.println("************************************************************com.korebap.app.view.manager.ManagerMainController_managerMain_GET 시작************************************************************");
		System.out.println("관리자 메인페이지로 이동"); // 관리자 메인 페이지로 이동 로그

		// 전체 회원 수 조회
		memberDTO.setMember_condition("MEMBER_SELECTONE_COUNT");
		MemberDTO allMemberData = memberService.selectOne(memberDTO);
		int all_memberCnt = allMemberData.getMember_total_cnt();

		// 일반 사용자 수 조회
		memberDTO.setMember_condition("MEMBER_USER_SELECTONE_COUNT");
		MemberDTO userMemberData = memberService.selectOne(memberDTO);
		int user_memberCnt = userMemberData.getMember_total_cnt();

		// 사장님 수 조회
		memberDTO.setMember_condition("MEMBER_OWNER_SELECTONE_COUNT");
		MemberDTO ownerMemberData = memberService.selectOne(memberDTO);
		int owner_memberCnt = ownerMemberData.getMember_total_cnt();

		// 게시판 수 조회
		boardDTO.setBoard_condition("BOARD_SELECTONE_COUNT");
		BoardDTO boardData = boardService.selectOne(boardDTO);
		int all_boardCnt = boardData.getBoard_total_cnt();

		// 신고 목록 조회
		claimDTO.setClaim_condition("CLAIM_PENDING_SELECTALL");
		claimDTO.setClaim_page_num(claimList_page_num);
		List<ClaimDTO> claimList = claimService.selectAll(claimDTO);
		System.out.println("claimList : [ "+claimList+" ]");

		// 신고 목록 전체 페이지 조회
		claimDTO.setClaim_condition("CLAIM_SELECTONE_PAGE_CNT"); // 조건 설정
		claimDTO = claimService.selectOne(claimDTO); // 총 페이지 수 조회
		System.out.println("claimDTO : [ "+claimDTO+" ]");
		int claimList_total_page = claimDTO.getClaim_total_page();

//		// 전체 상품 수 조회
//		productDTO.setProduct_condition("PRODUCT_TOTAL_CNT");
//		List<ProductDTO> productCnt = productService.selectAll(productDTO);
		
		productDTO.setProduct_condition("PRODUCT_TOTAL_COUNT_VV");
		productDTO = productService.selectOne(productDTO);
		int product_total_cnt = productDTO.getProduct_total_cnt();
		
		productDTO.setProduct_condition("PRODUCT_BY_LOCATION");
		List<ProductDTO> product_location_by_cnt = productService.selectAll(productDTO);
		
		productDTO.setProduct_condition("PRODUCT_LOCATION_BY_CATEGORY");
		List<ProductDTO> product_location_by_category = productService.selectAll(productDTO);

		// 조회한 수량 로그 출력
		System.out.println("전체 유저		명수 : [ " + all_memberCnt + " ]");
		System.out.println("일반 사용자		명수 : [ " + user_memberCnt + " ]");
		System.out.println("사장님 사용자	명수 : [ " + owner_memberCnt + " ]");
		System.out.println("게시판	    개수 : [ " + all_boardCnt + " ]");
		System.out.println("미처리 신고		개수 : [ " + claimList.size() + " ]"); // 리스트의 크기로 신고 수 출력
		System.out.println("전체  상품	    개수 : [ " + product_total_cnt + " ]"); // 리스트의 크기로 상품 수 출력
		System.out.println("총페이지 수 : [ "+claimList_total_page+" ]");
		System.out.println("현재페이지 수 : [ "+claimList_page_num+" ]");
		System.out.println("바다 상품 수 : [ "+product_location_by_cnt.get(0).getProduct_location_by_cnt()+" ]");
		System.out.println("민물 상품 수 : [ "+product_location_by_cnt.get(1).getProduct_location_by_cnt()+" ]");
		System.out.println("바다 - 낚시배 상품 수 : [ "+product_location_by_category.get(0).getProduct_location_by_category_cnt()+" ]");
		System.out.println("바다 - 낚시터 상품 수 : [ "+product_location_by_category.get(1).getProduct_location_by_category_cnt()+" ]");
		System.out.println("민물 - 낚시카페 상품 수 : [ "+product_location_by_category.get(2).getProduct_location_by_category_cnt()+" ]");
		System.out.println("민물 - 수상 상품 수 : [ "+product_location_by_category.get(3).getProduct_location_by_category_cnt()+" ]");
		

		// 총 페이지 수가 1보다 작으면 1로 설정
		if(claimList_total_page < 1) {
			claimList_total_page = 1;
		}

		// 모델에 조회한 데이터를 추가
		model.addAttribute("all_memberCnt", all_memberCnt);
		model.addAttribute("user_memberCnt", user_memberCnt);
		model.addAttribute("owner_memberCnt", owner_memberCnt);
		model.addAttribute("all_boardCnt", all_boardCnt);
		//model.addAttribute("productCnt", productCnt);
		model.addAttribute("claimList", claimList);
		model.addAttribute("claimList_total_page", claimList_total_page); // 총 페이지 수
		model.addAttribute("currentPage", claimList_page_num); // 현재 페이지 번호
		
		model.addAttribute("product_total_cnt", product_total_cnt);
		model.addAttribute("ocean_cnt", product_location_by_cnt.get(0).getProduct_location_by_cnt());
		model.addAttribute("freshwater", product_location_by_cnt.get(1).getProduct_location_by_cnt());
		model.addAttribute("ocean_boat", product_location_by_category.get(0).getProduct_location_by_category_cnt());
		model.addAttribute("ocean_spot", product_location_by_category.get(1).getProduct_location_by_category_cnt());
		model.addAttribute("fresh_cafe", product_location_by_category.get(2).getProduct_location_by_category_cnt());
		model.addAttribute("fresh_onWater", product_location_by_category.get(3).getProduct_location_by_category_cnt());

		// 메서드 종료 로그 출력
		System.out.println("************************************************************com.korebap.app.view.manager.ManagerMainController_managerMain_GET 종료************************************************************");
		// 뷰 이름 반환
		return "managerMain";
	}
	
	@RequestMapping(value="selectReply.do", method=RequestMethod.GET)
	public String selectReply(ReplyDTO replyDTO, BoardDTO boardDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.manager.ManagerMainController_selectReply_GET 시작************************************************************");
		
		//int replyNum = replyDTO.getReply_num();
		System.out.println("댓글 번호 : [ "+replyDTO.getReply_num()+" ]");
		replyDTO = replyService.selectOne(replyDTO);
		int baordNum = replyDTO.getReply_board_num();
		System.out.println("게시글 번호 : [ "+baordNum+" ]");
		
		//boardDTO.setBoard_num(2);
		boardDTO.setBoard_num(baordNum);
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		boardDTO = boardService.selectOne(boardDTO);
		
		replyDTO.setReply_board_num(2);
		List<ReplyDTO> replyList = replyService.selectAll(replyDTO);
		System.out.println(replyList);
		model.addAttribute("board",boardDTO);
		model.addAttribute("replyList",replyList);
		
		
		System.out.println("************************************************************com.korebap.app.view.manager.ManagerMainController_selectReply_GET 종료************************************************************");
		return "boardDetails";
	}
	
}
