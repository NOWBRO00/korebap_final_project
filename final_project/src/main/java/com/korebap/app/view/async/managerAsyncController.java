package com.korebap.app.view.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

@RestController
public class managerAsyncController {

	// MemberService를 자동 주입
	@Autowired
	MemberService memberService;
	
	@Autowired
	ClaimService claimService;


	@RequestMapping(value="/claimList.do", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> claimPageNation(ClaimDTO claimDTO,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentpage) {
		// [ 상품 페이지네이션]
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_claimPageNation_GET 시작************************************************************");
		System.out.println("---------------비동기 처리(bossPageNation) 시작---------------");

		// 현재 페이지 정보 로그 출력
		System.out.println("DAO 이전 현재 페이지 : ["+currentpage+"]");

		// MemberDTO에 현재 페이지와 역할 설정
		claimDTO.setClaim_page_num(currentpage);
		claimDTO.setClaim_condition("CLAIM_PENDING_SELECTALL");
		// 모든 회원 목록을 조회
		List<ClaimDTO> claimList = claimService.selectAll(claimDTO);
		
		// DAO 호출 후의 현재 페이지 및 역할 로그 출력
		System.out.println("DAO 이후 현재 페이지 : ["+currentpage+"]");
		System.out.println("비동기 로그 memberList ["+claimList+"]");

		// 총 페이지 수를 조회
		claimDTO.setClaim_condition("CLAIM_SELECTONE_PENDING_PAGE_CNT");
		claimDTO = claimService.selectOne(claimDTO);
		int claim_total_page = claimDTO.getClaim_total_page();

		// 총 페이지 수 로그 출력
		System.out.println(" 로그 member_total_page ["+claim_total_page+"]");

		// 요청한 페이지가 총 페이지 수를 초과하는 경우 처리
		if (currentpage > claim_total_page) {
			System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
			return null; // 데이터가 없으므로 null 반환
		}

		// 응답 데이터 맵 생성 및 값 추가
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("claimList", claimList); // 회원 목록
		responseMap.put("claim_total_page", claim_total_page); // 총 페이지 수
		responseMap.put("currentPage", currentpage); // 현재 페이지

		// 응답 로그 출력
		System.out.println("로그!!!!!!!! claimList : ["+claimList+"]");
		System.out.println("로그!!!!!!!! claim_page_count : ["+claim_total_page+"]");
		System.out.println("로그!!!!!!!! currentpage : ["+currentpage+"]");

		// 종료 로그 출력
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_claimPageNation_GET 종료************************************************************");

		// 응답 데이터 반환
		return responseMap; 
	}
	
	@RequestMapping(value="/managerClaimList.do", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> managerClaimPageNation(ClaimDTO claimDTO,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentpage) {
		// [ 상품 페이지네이션]
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_claimPageNation_GET 시작************************************************************");
		System.out.println("---------------비동기 처리(bossPageNation) 시작---------------");

		// 현재 페이지 정보 로그 출력
		System.out.println("DAO 이전 현재 페이지 : ["+currentpage+"]");

		// MemberDTO에 현재 페이지와 역할 설정
		claimDTO.setClaim_page_num(currentpage);
		claimDTO.setClaim_condition("CLAIM_AFTER_SELECTALL");
		// 모든 회원 목록을 조회
		List<ClaimDTO> claimList = claimService.selectAll(claimDTO);
		
		// DAO 호출 후의 현재 페이지 및 역할 로그 출력
		System.out.println("DAO 이후 현재 페이지 : ["+currentpage+"]");
		System.out.println("비동기 로그 memberList ["+claimList+"]");

		// 총 페이지 수를 조회
		claimDTO.setClaim_condition("CLAIM_SELECTONE_AFTER_PAGE_CNT");
		claimDTO = claimService.selectOne(claimDTO);
		int claim_total_page = claimDTO.getClaim_total_page();

		// 총 페이지 수 로그 출력
		System.out.println(" 로그 member_total_page ["+claim_total_page+"]");

		// 요청한 페이지가 총 페이지 수를 초과하는 경우 처리
		if (currentpage > claim_total_page) {
			System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
			return null; // 데이터가 없으므로 null 반환
		}

		// 응답 데이터 맵 생성 및 값 추가
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("claimList", claimList); // 회원 목록
		responseMap.put("claim_total_page", claim_total_page); // 총 페이지 수
		responseMap.put("currentPage", currentpage); // 현재 페이지

		// 응답 로그 출력
		System.out.println("로그!!!!!!!! claimList : ["+claimList+"]");
		System.out.println("로그!!!!!!!! claim_page_count : ["+claim_total_page+"]");
		System.out.println("로그!!!!!!!! currentpage : ["+currentpage+"]");

		// 종료 로그 출력
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_claimPageNation_GET 종료************************************************************");

		// 응답 데이터 반환
		return responseMap; 
	}


	// 클라이언트의 GET 요청을 처리하는 메서드
	@RequestMapping(value="/bossList.do", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> bossPageNation(MemberDTO memberDTO,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentpage) {
		// [ 상품 페이지네이션]
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_bossPageNation_GET 시작************************************************************");
		System.out.println("---------------비동기 처리(bossPageNation) 시작---------------");

		// 현재 페이지 정보 로그 출력
		System.out.println("DAO 이전 현재 페이지 : ["+currentpage+"]");
		String searchKeyword = memberDTO.getMember_searchkeyword();
		// MemberDTO에 현재 페이지와 역할 설정
		memberDTO.setMember_page_num(currentpage);
		memberDTO.setMember_role("OWNER");

		// 모든 회원 목록을 조회
		List<MemberDTO> bossList = memberService.selectAll(memberDTO);

		// DAO 호출 후의 현재 페이지 및 역할 로그 출력
		System.out.println("DAO 이후 현재 페이지 : ["+currentpage+"]");
		System.out.println("DAO 이후 현재 role : ["+memberDTO.getMember_role()+"]");
		System.out.println("DAO 이후 현재 role : ["+memberDTO.getMember_searchkeyword()+"]");
		System.out.println("비동기 로그 memberList ["+bossList+"]");

		// 총 페이지 수를 조회
		memberDTO.setMember_condition("MEMBER_SELECONE_PAGE_CNT");
		memberDTO = memberService.selectOne(memberDTO);
		int boss_total_page = memberDTO.getMember_total_page();

		// 총 페이지 수 로그 출력
		System.out.println("memberList 로그 member_total_page ["+boss_total_page+"]");

		// 요청한 페이지가 총 페이지 수를 초과하는 경우 처리
		if (currentpage > boss_total_page) {
			System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
			return null; // 데이터가 없으므로 null 반환
		}

		// 응답 데이터 맵 생성 및 값 추가
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("bossList", bossList); // 회원 목록
		responseMap.put("boss_page_count", boss_total_page); // 총 페이지 수
		responseMap.put("currentPage", currentpage); // 현재 페이지
		responseMap.put("searchTerm",searchKeyword );

		// 응답 로그 출력
		System.out.println("로그!!!!!!!! productList : ["+bossList+"]");
		System.out.println("로그!!!!!!!! searchTerm : ["+searchKeyword+"]");
		System.out.println("로그!!!!!!!! boss_total_page : ["+boss_total_page+"]");
		System.out.println("로그!!!!!!!! currentpage : ["+currentpage+"]");

		// 종료 로그 출력
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_bossPageNation_GET 종료************************************************************");

		// 응답 데이터 반환
		return responseMap; 
	}

	@RequestMapping(value="/memberList.do", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> userPageNation(MemberDTO memberDTO,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentpage) {
		// [ 상품 페이지네이션]
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_userPageNation_GET 시작************************************************************");
		System.out.println("---------------비동기 처리(bossPageNation) 시작---------------");


		// 현재 페이지 정보 로그 출력
		System.out.println("DAO 이전 현재 페이지 : ["+currentpage+"]");
		System.out.println("DAO 이전 현재 검색어 : ["+memberDTO.getMember_searchkeyword()+"]");
		String searchKeyword = memberDTO.getMember_searchkeyword();
		// MemberDTO에 현재 페이지와 역할 설정
		memberDTO.setMember_page_num(currentpage);
		memberDTO.setMember_role("USER");

		// 모든 회원 목록을 조회
		List<MemberDTO> memberList = memberService.selectAll(memberDTO);

		// DAO 호출 후의 현재 페이지 및 역할 로그 출력
		System.out.println("DAO 이후 현재 페이지 : ["+currentpage+"]");
		System.out.println("DAO 이후 현재 role : ["+memberDTO.getMember_role()+"]");
		System.out.println("DAO 이후 현재 role : ["+memberDTO.getMember_searchkeyword()+"]");
		System.out.println("비동기 로그 memberList ["+memberList+"]");

		// 페이지 카운트를 위한 조건 설정
		memberDTO.setMember_condition("MEMBER_SELECONE_PAGE_CNT");

		// 총 페이지 수를 조회
		memberDTO = memberService.selectOne(memberDTO);
		int member_total_page = memberDTO.getMember_total_page();

		// 총 페이지 수 로그 출력
		System.out.println("memberList 로그 member_total_page ["+member_total_page+"]");

		// 요청한 페이지가 총 페이지 수를 초과하는 경우 처리
		if (currentpage > member_total_page) {
			System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
			return null; // 데이터가 없으므로 null 반환
		}

		// 응답 데이터 맵 생성 및 값 추가
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("memberList", memberList); // 회원 목록
		responseMap.put("member_page_count", member_total_page); // 총 페이지 수
		responseMap.put("currentPage", currentpage); // 현재 페이지
		responseMap.put("searchTerm",searchKeyword );

		// 응답 로그 출력
		System.out.println("로그!!!!!!!! productList : ["+memberList+"]");
		System.out.println("로그!!!!!!!! member_page_count : ["+member_total_page+"]");
		System.out.println("로그!!!!!!!! currentpage : ["+currentpage+"]");
		System.out.println("로그!!!!!!!! searchTerm : ["+searchKeyword+"]");

		// 종료 로그 출력
		System.out.println("************************************************************com.korebap.app.view.async.managerAsyncController_userPageNation_GET 종료************************************************************");

		// 응답 데이터 반환
		return responseMap; 
	}
}