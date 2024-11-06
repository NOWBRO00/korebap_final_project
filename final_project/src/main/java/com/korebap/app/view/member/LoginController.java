package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    // MemberService 의존성 주입
    @Autowired
    private MemberService memberService; 

    // 로그인 페이지 요청 처리 (GET)
    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String login() {
        // 로그인 페이지로의 이동 로그
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_login_GET 시작************************************************************");
        System.out.println("로그인 페이지 이동중...");
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_login_GET 종료************************************************************");
        return "login"; // 로그인 페이지 반환
    }

    // 로그인 요청 처리 (POST)
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(HttpSession session, MemberDTO memberDTO, Model model) {
        // 로그인 요청 처리 시작 로그
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_login_POST 시작************************************************************");

        // 현재 로그인된 사용자 ID 가져오기
        String login_member = (String) session.getAttribute("member_id");

        // 이미 로그인된 경우 처리
        if (session != null && login_member != null) {
            model.addAttribute("msg", "이미 로그인된 사용자 입니다."); // 메시지 설정
            model.addAttribute("path", "main.do"); // 리다이렉트 경로 설정
        }

        // 로그인 데이터 로그
        System.out.println("LoginController.java login() member_id : [ " + memberDTO.getMember_id() + " ]");
        System.out.println("LoginController.java login() member_password : [ " + memberDTO.getMember_password() + " ]");

        // 로그인 조건 설정
        memberDTO.setMember_condition("LOGIN");
        
        // 회원 정보 조회
        memberDTO = memberService.selectOne(memberDTO); 

        // 로그인 성공 여부 확인
        if (memberDTO != null) {
            // 로그인 성공 로그
            System.out.println("LoginController.java login() 로그인 성공");
            session.setAttribute("member_id", memberDTO.getMember_id()); // 세션에 회원 ID 설정
            session.setAttribute("member_role", memberDTO.getMember_role()); // 세션에 회원 역할 설정
            System.out.println(session.getAttribute("member_id"));
            System.out.println(session.getAttribute("member_role"));
            
            // 차단된 계정 처리
            if(memberDTO.getMember_role().equals("BANNED")){
                // 차단된 유저 로그
                System.out.println("LoginController.java login() BANNED 유저");
                model.addAttribute("msg", "차단된 계정입니다. 관리자에게 문의하세요."); // 오류 메시지 설정
                model.addAttribute("path", "main.jsp"); // 로그인 페이지로 리다이렉트
                session.removeAttribute("member_id");
                session.removeAttribute("member_role");
                return "info"; // 정보 페이지로 리다이렉트
            }
            // 로그인 성공 후 메인 페이지로 이동
            System.out.println("************************************************************com.korebap.app.view.member.LoginController_login_POST 종료************************************************************");
            return "redirect:main.do"; // 메인 페이지로 리다이렉트
        } else {
            // 로그인 실패 로그
            System.out.println("LoginController.java login() 로그인 실패");
            model.addAttribute("msg", "아이디 또는 비밀번호를 확인해 주세요."); // 오류 메시지 설정
            model.addAttribute("path", "login.jsp"); // 로그인 페이지로 리다이렉트
        }

        // 로그인 처리 종료 로그
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_login_POST 종료************************************************************");
        return "info"; // 정보 페이지로 리다이렉트
    }

    // 로그아웃 요청 처리
    @RequestMapping("/logout.do")
    public String logout(HttpSession session, Model model) {
        // 로그아웃 처리 시작 로그
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_logout_GET 시작************************************************************");
        System.out.println("로그아웃 처리 중...");

        // 세션이 없는 경우 처리
        if (session == null) {
            System.out.println("이미 로그인된 사용자 입니다."); // 로그인되지 않은 경우 로그
            model.addAttribute("msg", "이미 로그아웃된 사용자 입니다."); // 메시지 설정
            model.addAttribute("path", "login.do"); // 리다이렉트 경로 설정
        }

        // 세션에서 회원 ID 및 역할 제거
        session.removeAttribute("member_id");
        session.removeAttribute("member_role");

        model.addAttribute("msg", "로그아웃 완료"); // 로그아웃 메시지 설정
        model.addAttribute("path", "login.do"); // 로그인 페이지로 리다이렉트
        System.out.println("************************************************************com.korebap.app.view.member.LoginController_logout_GET 종료************************************************************");
        return "info"; // 정보 페이지로 리다이렉트
    }
}
