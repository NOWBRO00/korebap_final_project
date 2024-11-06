package com.korebap.app.view.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class PaymentInfoPageAction {

    @Autowired
    private ProductService productService; // 상품 서비스 자동 주입

    @Autowired
    private ImageFileService imageFileService; // 이미지 파일 서비스 자동 주입

    @Autowired
    private MemberService memberService; // 회원 서비스 자동 주입
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 자동 주입

    // 결제 정보 페이지 요청 처리
    @RequestMapping(value="/paymentInfo.do", method=RequestMethod.POST)
    public String paymentInfoPage(Model model,
                                   @RequestParam("reservation_date") String reservationDate, // 예약 날짜 파라미터
                                   MemberDTO memberDTO, // 회원 DTO
                                   ProductDTO productDTO, // 상품 DTO
                                   ImageFileDTO fileDTO) { // 이미지 파일 DTO
        System.out.println("************************************************************com.korebap.app.view.page.paymentInfoPage_paymentInfoPage_POST 시작************************************************************");
        
        // 로그인 상태 확인
        String login_member_id = loginCheck.loginCheck();
        
        // 로그인 세션이 없는 경우 처리
        if (login_member_id.isEmpty()) {
            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 메시지 추가
            model.addAttribute("path", "login.do"); // 로그인 페이지로 리다이렉트
            return "info"; // info.jsp
        }
        int productNum = productDTO.getProduct_num();
        // 상품 정보 조회 준비
        productDTO.setProduct_num(productNum); // 상품 번호 설정
        productDTO.setProduct_condition("PRODUCT_BY_INFO"); // 조건 설정
        productDTO = productService.selectOne(productDTO); // 상품 정보 조회

        // 상품이 존재하지 않을 경우 처리
        if (productDTO == null) {
            model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해주세요."); // 메시지 추가
            model.addAttribute("path", "main.do"); // 메인 페이지로 리다이렉트
            return "info"; // info.jsp
        }

        // 이미지 파일 정보 조회
        fileDTO.setFile_product_num(productNum); // 상품 번호 설정
        fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL"); // 조건 설정
        List<ImageFileDTO> fileList = imageFileService.selectAll(fileDTO); // 이미지 파일 리스트 조회

        // 회원 정보 조회
        memberDTO.setMember_id(login_member_id); // 로그인한 회원 ID 설정
        memberDTO.setMember_condition("MYPAGE"); // 조건 설정
        memberDTO = memberService.selectOne(memberDTO); // 회원 정보 조회
        
        System.out.println(memberDTO); // 회원 정보 로그 출력

        // 회원 정보가 존재하지 않을 경우 처리
        if (memberDTO == null) {
            model.addAttribute("msg", "사용자 정보를 찾을 수 없습니다. 다시 시도해주세요."); // 메시지 추가
            model.addAttribute("path", "main.do"); // 메인 페이지로 리다이렉트
            System.out.println("model : [ "+model+" ]"); // model 값 확인 로그
            System.out.println("************************************************************com.korebap.app.view.page.paymentInfoPage_paymentInfoPage_POST 종료************************************************************");
            return "info"; // info.jsp
        }

        // 모델에 데이터 추가
        model.addAttribute("reservation_date", reservationDate); // 예약 날짜 추가
        model.addAttribute("product", productDTO); // 상품 정보 추가
        model.addAttribute("fileList", fileList); // 이미지 파일 리스트 추가
        model.addAttribute("member", memberDTO); // 회원 정보 추가
        
        System.out.println("model : [ "+model+" ]"); // model 값 확인 로그
        System.out.println("리저베이션으로 이동중...");
        System.out.println("************************************************************com.korebap.app.view.page.paymentInfoPage_paymentInfoPage_POST 종료************************************************************");
        return "reservation"; // reservation.jsp
    }
}
