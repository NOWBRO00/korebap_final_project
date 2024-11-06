package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteMemberController {

    @Autowired
    private MemberService memberService; // MemberService 의존성 주입

    // 회원 탈퇴 요청 처리
    @RequestMapping(value = "/deleteMember.do", method = RequestMethod.POST)
    public String deleteMember(HttpSession session, MemberDTO memberDTO) {
        System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 시작************************************************************");

        // 로그인 세션에서 회원 ID 가져오기
        String login_member_id = (String) session.getAttribute("member_id");

        // 로그인 세션 확인
        if (login_member_id == null || login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음"); // 로그인되지 않은 경우
            return "main"; // 메인 페이지로 리다이렉트
        }

        // 데이터 로그
        System.out.println("member_id : " + login_member_id); // 로그인된 회원 ID 출력

        memberDTO.setMember_id(login_member_id); // 회원 DTO에 로그인된 회원 ID 설정

        // 회원 탈퇴 처리
        boolean flag = memberService.delete(memberDTO); // MemberService의 delete 메서드 호출

        if (flag) {
            System.out.println("DeleteMemberController 로그 : 회원탈퇴 성공"); // 탈퇴 성공 시 로그
            // 세션에서 회원 ID 및 역할 제거
            session.removeAttribute("member_id");
            session.removeAttribute("role");
            System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 종료************************************************************");
            return "main"; // 메인 페이지로 리다이렉트
        } else {
            System.out.println("DeleteMemberController 로그 : 회원탈퇴 실패"); // 탈퇴 실패 시 로그
            System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 종료************************************************************");
            return "main"; // 메인 페이지로 리다이렉트
        }
    }
}
