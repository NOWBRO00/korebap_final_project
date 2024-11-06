package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

@Controller
public class JoinController {
    @Autowired
    private MemberService memberService; // MemberService 의존성 주입

    // 회원가입 페이지 요청 처리 (GET)
    @RequestMapping(value = "/join.do", method = RequestMethod.GET)
    public String join() {
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_GET 시작************************************************************");
        System.out.println("회원가입 페이지로 이동"); // 회원가입 페이지로의 이동 로그
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_GET 종료************************************************************");
        return "join"; // 회원가입 페이지 반환
    }

    // 회원가입 요청 처리 (POST)
    @RequestMapping(value = "/join.do", method = RequestMethod.POST)
    public String join(@RequestParam("postcode") String member_postcode,
                       @RequestParam("address") String member_address,
                       @RequestParam("extraAddress") String member_extraAddress,
                       @RequestParam("detailAddress") String member_detailAddress,
                       MemberDTO memberDTO, Model model) {
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 시작************************************************************");

        // 주소 정보를 MemberDTO에 설정
        memberDTO.setMember_postcode(member_postcode);
        memberDTO.setMember_address(member_address);
        memberDTO.setMember_extraAddress(member_extraAddress);
        memberDTO.setMember_detailAddress(member_detailAddress);

        // 데이터 로그
        System.out.println("JoinController.java join() member_id : [ " + memberDTO.getMember_id() + " ]");
        System.out.println("JoinController.java join() member_pw : [ " + memberDTO.getMember_password() + " ]");
        System.out.println("JoinController.java join() member_nickname : [ " + memberDTO.getMember_nickname() + " ]");
        System.out.println("JoinController.java join() member_name : [ " + memberDTO.getMember_name() + " ]");
        System.out.println("JoinController.java join() member_phone : [ " + memberDTO.getMember_phone() + " ]");
        System.out.println("JoinController.java join() member_postcode : [ " + memberDTO.getMember_postcode() + " ]");
        System.out.println("JoinController.java join() member_address : [ " + memberDTO.getMember_address() + " ]");
        System.out.println("JoinController.java join() member_extraAddress : [ " + memberDTO.getMember_extraAddress() + " ]");
        System.out.println("JoinController.java join() member_detailAddress : [ " + memberDTO.getMember_detailAddress() + " ]");
        System.out.println("JoinController.java join() member_role : [ " + memberDTO.getMember_role() + " ]");

        // 전체 주소 조합
        String totalAddress = member_postcode + "_" + member_address + "_" + member_extraAddress + "_" + member_detailAddress;
        System.out.println("JoinController.java join() totalAddress : [ " + totalAddress + " ]");

        memberDTO.setMember_address(totalAddress); // 전체 주소를 MemberDTO에 설정

        // 회원가입 처리
        boolean flag = memberService.insert(memberDTO); // MemberService의 insert 메서드 호출

        if (flag) {
            System.out.println("회원가입 성공 -> 로그인 페이지로 이동"); // 회원가입 성공 시 로그
            System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 종료************************************************************");
            return "login"; // 로그인 페이지로 리다이렉트
        }

        // 회원가입 실패 시
        model.addAttribute("msg", "회원가입에 실패했습니다. 다시 시도해주세요."); // 오류 메시지 설정
        model.addAttribute("path", "join.jsp"); // 리다이렉트 경로 설정
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 종료************************************************************");
        return "info"; // 정보 페이지로 리다이렉트
    }
    // 회원가입 메서드 끝
}
