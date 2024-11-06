package com.korebap.app.view.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class CancelPaymentAction {

    @Autowired
    private PaymentService paymentService; // 결제 서비스 자동 주입
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 자동 주입

    // 결제 취소 요청 처리
    @RequestMapping(value = "/cancelPayment.do", method = RequestMethod.GET)
    public ModelAndView cancelPayment(ModelAndView modelAndView,
                                       PaymentDTO paymentDTO,
                                       PaymentInfo paymentInfo) {
        System.out.println("************************************************************com.korebap.app.view.payment.CancelPaymentAction_cancelPayment_GET 시작************************************************************");

        // 로그인 상태 확인
        String loginMemberId = loginCheck.loginCheck();

        // 로그인 세션이 없는 경우 처리
        if (loginMemberId.isEmpty()) {
            System.out.println("cancelPaymentAction 로그 : 로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다."); // 사용자에게 메시지 추가
            modelAndView.addObject("path", "loginPage.do"); // 로그인 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
            System.out.println("************************************************************com.korebap.app.view.payment.CancelPaymentAction_cancelPayment_GET 종료************************************************************");
            return modelAndView; // 모델 반환
        }

        // 요청으로부터 상점 고유번호 가져오기
        String merchant_uid = paymentInfo.getMerchant_uid();

        // 데이터 로그 출력
        System.out.println("merchant_uid : " + merchant_uid);

        // 결제 DTO에 상점 고유번호 설정
        paymentDTO.setMerchant_uid(merchant_uid);
        paymentDTO.setPayment_condition("SELECT_BY_MERCHANT_UID"); // 결제 조건 설정
        paymentDTO = paymentService.selectOne(paymentDTO); // 결제 정보 조회

        // 결제 내역이 존재하는 경우 처리
        if (paymentDTO != null) {
            System.out.println("cancelPaymentAction 로그 : 결제 내역 있음");
            paymentInfo = PaymentUtil.portOne_code(); // 포트원 API를 통해 토큰 발행
            paymentInfo.setMerchant_uid(merchant_uid); // 상점 고유번호 설정
            paymentInfo.setAmount(paymentDTO.getPayment_price()); // 결제 금액 설정
        } else {
            // 결제 내역이 없는 경우 처리
            System.out.println("cancelPaymentAction 로그 : 결제 내역 없음");
            modelAndView.addObject("msg", "결제 내역을 찾을 수 없습니다."); // 오류 메시지 추가
            modelAndView.addObject("path", "main.do"); // 메인 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
            System.out.println("************************************************************com.korebap.app.view.payment.CancelPaymentAction_cancelPayment_GET 종료************************************************************");
            return modelAndView; // 모델 반환
        }

        // 결제 취소 요청
        boolean flag = PaymentUtil.cancelPayment(paymentInfo); // 결제 취소 요청 수행

        // 결제 취소 결과 처리
        if (flag) {
            System.out.println("cancelPaymentAction 로그 : 결제 취소 성공");
            modelAndView.setViewName("redirect:updatePayment.do?payment_num=" + paymentDTO.getPayment_num()); // 결제 취소 성공 시 리다이렉트
        } else {
            System.out.println("cancelPaymentAction 로그 : 결제 취소 실패");
            modelAndView.addObject("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "main.do"); // 메인 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
        }

        System.out.println("cancelPaymentAction 끝");
        System.out.println("************************************************************com.korebap.app.view.payment.CancelPaymentAction_cancelPayment_GET 종료************************************************************");
        return modelAndView; // 결과 반환
    }
}
