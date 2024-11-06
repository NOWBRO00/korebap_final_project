package com.korebap.app.view.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class ClaimController {

    // ClaimService를 자동으로 주입받음
    @Autowired
    private ClaimService claimService;
    
    // 로그인 체크를 위한 객체를 자동으로 주입받음
    @Autowired
    private LoginCheck loginCheck;

    // 신고 요청을 처리하는 메서드
    @RequestMapping(value = "/claim.do",method = RequestMethod.GET)
    public String claimController(Model model,
                                  ClaimDTO claimDTO) {
        // 신고 처리 시작 로그
        System.out.println("************************************************************com.korebap.app.view.claim.ClaimController_claimController 시작************************************************************");

        // 로그인 세션 체크
        String login_member_id = loginCheck.loginCheck();
        
        // 로그인하지 않은 경우 처리
        if (login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음");
            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 로그인 필요 메시지 추가
            model.addAttribute("path", "login.do"); // 로그인 페이지로 리다이렉션
            return "info"; // info.jsp로 포워딩
        }

        // 신고하는 회원의 ID
        String claim_reporter_id = login_member_id;

        // 데이터 로그
        System.out.println("board_num : [ " + claimDTO.getClaim_board_num() + " ]");
        System.out.println("reply_num : [ " + claimDTO.getClaim_reply_num() + " ]");
        System.out.println("claim_reporter_id : [ " + claim_reporter_id + " ]");
        System.out.println("claim_target_member_id : [ " + claimDTO.getClaim_target_member_id() + " ]");

        // ClaimDTO에 신고 관련 정보 설정
        claimDTO.setClaim_reporter_id(claim_reporter_id); // 신고자 ID 설정
        
        if (claimDTO.getClaim_board_num() >= 0) {
            // 게시글 신고인 경우
            claimDTO.setClaim_condition("INSERT_CLAIM_BOARD"); // 게시글 신고 조건 설정
        } else {
            // 댓글 신고인 경우
            claimDTO.setClaim_condition("INSERT_CLAIM_REPLY"); // 댓글 신고 조건 설정
        }
        
        // 신고 내용을 DB에 삽입
        boolean flag = claimService.insert(claimDTO);

        // 결과 메시지 및 리다이렉션 경로 설정
        model.addAttribute("path", "boardListPage.do"); // 게시판 목록 페이지로 리다이렉션
        if (flag) {
            // 신고 성공
            model.addAttribute("msg", "신고가 처리가 완료되었습니다. 감사합니다.");
        } else {
            // 신고 실패
            model.addAttribute("msg", "신고가 처리에 실패했습니다. 다시 시도해 주세요.");
        }

        // 신고 처리 종료 로그
        System.out.println("************************************************************com.korebap.app.view.claim.ClaimController_claimController 종료************************************************************");
        return "info"; // info.jsp로 포워딩
    }
}
