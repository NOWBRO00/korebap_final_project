package com.korebap.app.view.async;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.view.member.GoogleAuthentication;

import jakarta.servlet.http.HttpSession;

@RestController
public class CheckController {
    @Autowired
    private MemberService memberService; // MemberService 인스턴스를 주입받아 회원 관련 서비스 호출

    // 회원 ID 체크
    @PostMapping("/checkMemberId.do")
    public @ResponseBody String checkMemberId(@RequestBody MemberDTO memberDTO) {
        System.out.println("---------------비동기 처리(checkMemberId) 시작---------------");

        System.out.println("요청된 회원 ID : [ "+memberDTO.getMember_id()+" ]"); // 요청된 회원 ID 출력
        memberDTO.setMember_condition("CHECK_MEMBER_ID"); // 상태를 회원 ID 체크로 설정
        memberDTO = memberService.selectOne(memberDTO); // 해당 회원 정보를 조회
        
        System.out.println("selectOne 결과 : ["+ memberDTO+"]");
        String result = "false"; // 기본값은 false
        if (memberDTO != null) {
            result = "true"; // 회원 정보가 존재하면 true
        }
        System.out.println(result);
        System.out.println("---------------비동기 처리(checkMemberId) 종료---------------");
        return result; // 결과 반환
    }

    // 닉네임 체크
    @PostMapping("/checkNickName.do")
    public @ResponseBody String checkNickName(@RequestBody MemberDTO memberDTO) {
        System.out.println("---------------비동기 처리(checkNickName) 시작---------------");

        memberDTO.setMember_condition("CHECK_MEMBER_NICKNAME"); // 상태를 닉네임 체크로 설정
        memberDTO = memberService.selectOne(memberDTO); // 닉네임 정보 조회

        String result = "false"; // 기본값은 false
        if (memberDTO != null && memberDTO.getMember_nickname() != null) {
            result = "true"; // 닉네임이 존재하면 true
        }

        System.out.println("---------------비동기 처리(checkNickName) 종료---------------");
        return result; // 결과 반환
    }

    // 비밀번호 체크
    @PostMapping("/checkPassword.do")
    public @ResponseBody String checkPassword(@RequestBody MemberDTO memberDTO, HttpSession session) {
        System.out.println("---------------비동기 처리(checkPassword) 시작---------------");

        String memberId = (String) session.getAttribute("member_id"); // 세션에서 회원 ID 가져오기
        memberDTO.setMember_id(memberId); // 요청된 DTO에 회원 ID 설정
        memberDTO.setMember_condition("MEMBER_PASSWORD_GET"); // 상태를 비밀번호 체크로 설정
        memberDTO = memberService.selectOne(memberDTO); // 비밀번호 정보 조회
        String result = "false"; // 기본값은 false

        if (memberDTO != null && memberDTO.getMember_password().equals(memberDTO.getMember_password())) {
            result = "true"; // 비밀번호가 일치하면 true
        }

        System.out.println("---------------비동기 처리(checkPassword) 종료---------------");
        return result; // 결과 반환
    }

    // 이메일 전송
    @RequestMapping(value="/sendMail.do", method=RequestMethod.POST)
    public void sendMail(@RequestParam String num, @RequestParam String email) {
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_sendMail_POST 시작************************************************************");

        // 이메일 제목과 내용 설정
        String subject = "고래밥 회원가입 인증번호입니다."; // 제목 설정
        String content = "인증번호: " + num; // 내용 설정

        try {
            sendEmail(email, subject, content); // 이메일 전송 메서드 호출
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }

        System.out.println("************************************************************com.korebap.app.view.member.JoinController_sendMail_POST 종료************************************************************");
    }

    // 이메일 전송 메서드
    private void sendEmail(String receiver, String subject, String content) throws Exception {
        // 이메일 서버의 속성을 설정합니다.
        Properties p = System.getProperties(); // 서버 정보를 저장할 Properties 객체
        p.put("mail.smtp.starttls.enable", "true"); // STARTTLS를 사용하여 암호화된 연결을 설정합니다.
        p.put("mail.smtp.host", "smtp.gmail.com"); // SMTP 서버 호스트 (Gmail)
        p.put("mail.smtp.auth", "true"); // 인증을 사용합니다.
        p.put("mail.smtp.port", "587"); // SMTP 포트 번호 (587은 TLS 포트)

        System.out.println(p);
        // 인증 정보를 생성합니다.
        Authenticator auth = new GoogleAuthentication(); // GoogleAuthentication 클래스에서 인증 정보 생성

        // 메일 세션을 생성합니다.
        Session s = Session.getInstance(p, auth); // 서버 속성과 인증 정보를 사용하여 Session 객체 생성
        
        System.out.println(s);
        // 메일 메시지를 생성합니다.
        Message m = new MimeMessage(s); // 생성된 Session을 사용하여 MimeMessage 객체 생성
        Address receiverAddress = new InternetAddress(receiver); // 수신자 이메일 주소

        m.setHeader("content-type", "text/html;charset=utf-8"); // 메일의 내용 유형과 문자 인코딩 설정
        m.addRecipient(Message.RecipientType.TO, receiverAddress); // 수신자 추가
        m.setSubject(subject); // 메일 제목 설정
        m.setContent(content, "text/html;charset=utf-8"); // 메일 내용과 형식 설정
        m.setSentDate(new Date()); // 메일 전송 날짜 설정

        System.out.println(m);
        // 메일을 전송합니다.
        Transport.send(m); // 메일을 전송합니다.
    }
}
