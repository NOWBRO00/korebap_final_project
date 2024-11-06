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
public class MypageController {
    @Autowired
    private MemberService memberService; // MemberService 의존성 주입
    
    // 마이페이지 요청 처리 (GET)
    @RequestMapping("/mypage.do")
    public String mypage(HttpSession session, MemberDTO memberDTO, Model model) {
        System.out.println("************************************************************com.korebap.app.view.member.MypageController_mypage_GET 시작************************************************************");

        // 현재 로그인된 사용자 ID 가져오기
        String loginMember = (String) session.getAttribute("member_id");

        // 로그인 세션 확인
        if (session == null || loginMember == null) {
            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 메시지 설정
            model.addAttribute("path", "login.jsp"); // 로그인 페이지로 리다이렉트
            return "info";
        }

        // 회원 정보 조회를 위한 설정
        memberDTO.setMember_id(loginMember);
        memberDTO.setMember_condition("MYPAGE");

        // 회원 정보 조회
        memberDTO = memberService.selectOne(memberDTO);

        // 회원 정보가 조회된 경우
        if (memberDTO != null) {
            // 주소 정보 분리
            String[] totalAddress = memberDTO.getMember_address().split("_");

            memberDTO.setMember_postcode(totalAddress[0]); // 우편번호
            memberDTO.setMember_address(totalAddress[1]); // 기본 주소
            memberDTO.setMember_extraAddress(totalAddress[2]); // 추가 주소
            memberDTO.setMember_detailAddress(totalAddress[3]); // 상세 주소
            
            model.addAttribute("member", memberDTO); // 모델에 회원 정보 추가
        } else {
            // 회원 정보 조회 실패
            model.addAttribute("msg", "서비스중 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("path", "main.do"); // 메인 페이지로 리다이렉트
            System.out.println("************************************************************com.korebap.app.view.member.MypageController_mypage_GET 종료************************************************************");
            return "info";
        }

        System.out.println("************************************************************com.korebap.app.view.member.MypageController_mypage_GET 종료************************************************************");
        return "mypage"; // 마이페이지로 이동
    }
    // 마이페이지 요청 처리 종료

    // 이름 및 닉네임 수정 요청 처리 (POST)
    @RequestMapping(value = "/updateMypage.do", method = RequestMethod.POST)
    public String updateMypage(HttpSession session, MemberDTO memberDTO, Model model) {
        System.out.println("************************************************************com.korebap.app.view.member.MypageController_updateMypage_POST 시작************************************************************");
        String loginMember = (String) session.getAttribute("member_id");

        // 로그인 세션 확인
        if (session == null || loginMember == null) {
            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 메시지 설정
            model.addAttribute("path", "login.do"); // 로그인 페이지로 리다이렉트
            return "info";
        }

        // 수정할 회원 ID 및 조건 설정
        memberDTO.setMember_id(loginMember);
        memberDTO.setMember_condition("MEMBER_NAME");
        
        // 데이터 로그
        System.out.println("MypageController.java updateMypage() member_id : [ " + memberDTO.getMember_id() + " ]");
        System.out.println("MypageController.java updateMypage() member_name : [ " + memberDTO.getMember_name() + " ]");
        System.out.println("MypageController.java updateMypage() member_nickname : [ " + memberDTO.getMember_nickname() + " ]");
    
        // 회원 정보 수정 요청
        boolean flag = memberService.update(memberDTO);
        
        model.addAttribute("path", "mypage.do"); // 수정 후 리다이렉트 경로 설정
        if (!flag) {
            System.out.println("MypageController.java updateMypage() 실패"); // 실패 로그
            model.addAttribute("msg", "회원정보 수정을 실패했습니다. 다시 시도해주세요."); // 오류 메시지 설정
        } else {
            System.out.println("MypageController.java updateMypage() 성공"); // 성공 로그
            model.addAttribute("msg", "회원정보 수정을 완료했습니다."); // 성공 메시지 설정
        }
        
        System.out.println("************************************************************com.korebap.app.view.member.MypageController_updateMypage_POST 종료************************************************************");
        return "info"; // 정보 페이지로 리다이렉트
    } 
    // 이름 및 닉네임 수정 요청 종료
    
    // 비밀번호 수정 요청 처리 (POST)
    @RequestMapping(value = "/updatePassword.do", method = RequestMethod.POST)
    public String updatePassword(HttpSession session, MemberDTO memberDTO, Model model) {
        System.out.println("com.koreait.app.view.member.MypageController_updatePassword_POST 시작");
        String loginMember = (String) session.getAttribute("member_id");

        // 로그인 세션 확인
        if (session == null || loginMember == null) {
            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 메시지 설정
            model.addAttribute("path", "login.jsp"); // 로그인 페이지로 리다이렉트
            return "info";
        }
        
        // 수정할 회원 ID 및 조건 설정
        memberDTO.setMember_id(loginMember);
        memberDTO.setMember_condition("MEMBER_PASSWORD");
        
        // 데이터 로그
        System.out.println("MypageController.java updatePassword() member_id : [ " + memberDTO.getMember_id() + " ]");
        System.out.println("MypageController.java updatePassword() member_password : [ " + memberDTO.getMember_password() + " ]");
        
        // 비밀번호 수정 요청
        boolean flag = memberService.update(memberDTO);
        
        model.addAttribute("path", "mypage.do"); // 수정 후 리다이렉트 경로 설정
        if (!flag) {
            System.out.println("MypageController.java updatePassword() 실패"); // 실패 로그
            model.addAttribute("msg", "회원정보 수정을 실패했습니다. 다시 시도해주세요."); // 오류 메시지 설정
        } else {
            System.out.println("MypageController.java updatePassword() 성공"); // 성공 로그
            model.addAttribute("msg", "회원정보 수정을 완료했습니다."); // 성공 메시지 설정
        }
        
        System.out.println("com.koreait.app.view.member.MypageController_updatePassword_POST 종료");
        return "info"; // 정보 페이지로 리다이렉트
    }
    // 비밀번호 수정 요청 종료
}
