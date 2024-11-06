package com.korebap.app.view.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.biz.product.ProductDTO;

@Controller
public class ManagerListController {

    @Autowired
    private ClaimService claimService; // ClaimService 의존성 주입
    
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/managerClaimListPage.do", method = RequestMethod.GET)
    public String managerClaimList(ClaimDTO claimDTO, @RequestParam(value="currentPage", defaultValue="1") int claimList_page_num,
            Model model) {
        // 메서드 시작 로그 출력
        System.out.println("************************************************************com.korebap.app.view.manager.ManagerClaimListController_managerClaimList_GET 시작************************************************************");
        System.out.println("관리자 지난신고 목록 페이지로 이동"); // 관리자 지난신고 목록 페이지로 이동 로그

        
        // 신고 목록 조회 (대기 중)
        claimDTO.setClaim_condition("CLAIM_AFTER_SELECTALL");
        claimDTO.setClaim_page_num(claimList_page_num);
        List<ClaimDTO> claimList = claimService.selectAll(claimDTO);
        System.out.println("claimList : [ "+claimList+" ]");
        
        claimDTO.setClaim_condition("CLAIM_SELECTONE_PAGE_CNT"); // 조건 설정
        claimDTO = claimService.selectOne(claimDTO); // 총 페이지 수 조회
        System.out.println("claimDTO : [ "+claimDTO+" ]");
        
        int claimList_total_page = claimDTO.getClaim_total_page();
        System.out.println("총페이지 수 : [ "+claimList_total_page+" ]");
        System.out.println("현재페이지 수 : [ "+claimList_page_num+" ]");
        
        // 총 페이지 수가 1보다 작으면 1로 설정
        if(claimList_total_page < 1) {
        	claimList_total_page = 1;
        }
        
        // 모델에 조회한 데이터를 추가
        model.addAttribute("claimList", claimList);
        model.addAttribute("claimList_total_page", claimList_total_page); // 총 페이지 수
        model.addAttribute("currentPage", claimList_page_num); // 현재 페이지 번호
        // 메서드 종료 로그 출력
        System.out.println("************************************************************com.korebap.app.view.manager.ManagerClaimListController_managerClaimList_GET 종료************************************************************");
        // 뷰 이름 반환
        return "managerClaimList";
    }
    
    @RequestMapping(value = "/memberListPage.do", method = RequestMethod.GET)
    public String memberList(MemberDTO memberDTO, Model model, @RequestParam(value="currentPage", defaultValue="1") int memberList_page_num) {
        System.out.println("************************************************************com.korebap.app.view.manager.ManagerMemberListController_managerMemberList_GET 시작************************************************************");
        System.out.println("일반 사용자 목록 페이지로 이동");
        System.out.println("일반 사용자 목록 페이지로 이동 memberDTO :"+memberDTO);

        // 페이지 번호와 역할 설정
        memberDTO.setMember_page_num(memberList_page_num);
        memberDTO.setMember_role("USER");
        
        // OWNER 역할을 가진 모든 회원 목록 조회
        List<MemberDTO> memberList = memberService.selectAll(memberDTO);
        System.out.println("memberList : [ "+memberList+" ]");
        
        // 사장님 목록 페이지 개수 받아오기
        memberDTO.setMember_condition("MEMBER_SELECONE_PAGE_CNT"); // 조건 설정
        memberDTO.setMember_role("USER"); // 역할 설정
        memberDTO = memberService.selectOne(memberDTO); // 총 페이지 수 조회
        System.out.println("memberDTO : [ "+memberDTO+" ]");

        // 총 페이지 수 저장
        int memberList_total_page = memberDTO.getMember_total_page();
        System.out.println("총페이지 수 : [ "+memberList_total_page+" ]");
        System.out.println("현재페이지 수 : [ "+memberList_page_num+" ]");
        
        // 총 페이지 수가 1보다 작으면 1로 설정
        if(memberList_total_page < 1) {
            memberList_total_page = 1;
        }
        
        // 회원 수 로그 출력
        System.out.println("사장님 회원수 : [ " + memberList.size() + " ]");
        System.out.println(memberList);
        
        // 모델에 데이터 추가
        model.addAttribute("memberList", memberList); // 회원 목록
        model.addAttribute("memberList_total_page", memberList_total_page); // 총 페이지 수
        model.addAttribute("currentPage", memberList_page_num); // 현재 페이지 번호

        System.out.println("************************************************************com.korebap.app.view.manager.ManagerMemberListController_managerMemberList_GET 종료************************************************************");
        // 뷰 이름 반환
        return "memberList";
    }
    
    @RequestMapping(value = "/bossListPage.do", method = RequestMethod.GET )
    public String bossList(MemberDTO memberDTO, Model model, @RequestParam(value="currentPage", defaultValue="1") int bossList_page_num) {
        // 시작 로그 출력
        System.out.println("************************************************************com.korebap.app.view.manager.ManagerMemberListController_managerMemberList_GET 시작************************************************************");
        System.out.println("사장님 사용자 목록 페이지로 이동");
        System.out.println("사장님 사용자 목록 페이지로 이동 memberDTO :"+memberDTO);

        // 페이지 번호와 역할 설정
        memberDTO.setMember_page_num(bossList_page_num);
        memberDTO.setMember_role("OWNER");
        
        // OWNER 역할을 가진 모든 회원 목록 조회
        List<MemberDTO> memberList = memberService.selectAll(memberDTO);
        System.out.println("memberList : [ "+memberList+" ]");
        
        // 사장님 목록 페이지 개수 받아오기
        memberDTO.setMember_condition("MEMBER_SELECONE_PAGE_CNT"); // 조건 설정
        memberDTO.setMember_role("OWNER"); // 역할 설정
        memberDTO = memberService.selectOne(memberDTO); // 총 페이지 수 조회
        System.out.println("memberDTO : [ "+memberDTO+" ]");

        // 총 페이지 수 저장
        int bossList_total_page = memberDTO.getMember_total_page();
        System.out.println("총페이지 수 : [ "+bossList_total_page+" ]");
        System.out.println("현재페이지 수 : [ "+bossList_page_num+" ]");
        
        // 총 페이지 수가 1보다 작으면 1로 설정
        if(bossList_total_page < 1) {
            bossList_total_page = 1;
        }
        
        // 회원 수 로그 출력
        System.out.println("사장님 회원수 : [ " + memberList.size() + " ]");
        System.out.println(memberList);
        
        // 모델에 데이터 추가
        model.addAttribute("bossList", memberList); // 회원 목록
        model.addAttribute("bossList_total_page", bossList_total_page); // 총 페이지 수
        model.addAttribute("currentPage", bossList_page_num); // 현재 페이지 번호

        // 종료 로그 출력
        System.out.println("************************************************************com.korebap.app.view.manager.ManagerMemberListController_managerMemberList_GET 종료************************************************************");
        
        // 뷰 이름 반환
        return "bossList"; // "bossList"라는 이름의 뷰로 이동
    }

}
