package com.korebap.app.view.member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.view.common.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UpdateProfileController {

    // 프로필 이미지 저장 경로
    private final static String PATH = "D:\\moomoo\\workspace02\\teem_project\\src\\main\\webapp\\img\\profile";

    @Autowired
    private MemberService memberService; // 회원 서비스 의존성 주입
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 확인 유틸리티 의존성 주입

    // 프로필 업데이트 요청 처리 (POST)
    @RequestMapping(value = "/updateProfile.do", method = RequestMethod.POST)
    public String updateProfile(HttpServletRequest request, MultipartFile member_profile) {
        System.out.println("UpdateProfileController 시작"); // 메소드 시작 로그

        // 로그인 세션 확인
        String login_member_id = loginCheck.loginCheck();
        if (login_member_id == null || login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음"); // 로그인 세션이 없을 때 로그
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        System.out.println("member_id : " + login_member_id); // 로그인한 회원 ID 로그

        // MemberDTO 객체 생성 및 설정
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(login_member_id); // 회원 ID 설정
        memberDTO.setMember_condition("MEMBER_PROFILE"); // 회원 상태 설정

        // 프로필 이미지가 업로드 되었는지 확인
        if (member_profile != null && !member_profile.isEmpty()) {
            System.out.println("UpdateProfile 로그 : 이미지 업로드 시작"); // 이미지 업로드 시작 로그

            // 파일명 생성: 사용자 ID + 파일 확장자
            String imagePath = login_member_id + (Paths.get(member_profile.getOriginalFilename()).getFileName().toString()
                    .substring(member_profile.getOriginalFilename().lastIndexOf("."))); 

            // 이미지 업로드 로직
            File uploadFile = new File(PATH, imagePath); // 업로드할 파일 경로 생성
            try (InputStream input = member_profile.getInputStream(); // 입력 스트림 생성
                 FileOutputStream output = new FileOutputStream(uploadFile)) { // 출력 스트림 생성
                byte[] buffer = new byte[1024]; // 버퍼 생성
                int length;
                // 입력 스트림에서 읽어서 출력 스트림에 쓰기
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length); // 버퍼의 내용을 출력 스트림에 씀
                }
                memberDTO.setMember_profile(imagePath); // 변경된 파일명을 MemberDTO에 설정
                System.out.println("UpdateProfile 로그 : 이미지 업로드 끝"); // 이미지 업로드 종료 로그
            } catch (Exception e) {
                e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                return "error"; // 에러 페이지로 리다이렉트
            }
        }

        // 프로필 업데이트 수행
        boolean flag = memberService.update(memberDTO);

        // 업데이트 성공 여부에 따라 리다이렉트
        if (flag) {
            System.out.println("UpdateProfileController 로그 : 성공"); // 성공 로그
            return "redirect:mypage.do"; // 성공 메시지와 함께 메인 페이지로 리다이렉트
        } else {
            System.out.println("UpdateProfileController 로그 : 실패"); // 실패 로그
            return "redirect:main.do"; // 실패 메시지와 함께 메인 페이지로 리다이렉트
        }
    }
}
