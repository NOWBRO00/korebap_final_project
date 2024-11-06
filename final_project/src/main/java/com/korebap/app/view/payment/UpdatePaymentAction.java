package com.korebap.app.view.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class UpdatePaymentAction {

    @Autowired
    private PaymentService paymentService; // 결제 서비스 자동 주입
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 상태 체크 유틸리티

    // 결제 업데이트 요청 처리
    @RequestMapping(value = "/updatePayment.do", method = RequestMethod.GET)
    public ModelAndView updatePayment(ModelAndView modelAndView,
                                       PaymentDTO paymentDTO) {
        System.out.println("************************************************************com.korebap.app.view.payment.UpdatePaymentAction_updatePayment_GET 시작************************************************************");

        // 현재 로그인한 사용자 ID 확인
        String login_member_id = loginCheck.loginCheck();

        // 로그인 세션이 없을 경우
        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다."); // 사용자에게 메시지 추가
            modelAndView.addObject("path", "loginPage.do"); // 로그인 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
            System.out.println("************************************************************com.korebap.app.view.payment.UpdatePaymentAction_updatePayment_GET 종료************************************************************");
            return modelAndView; // 모델 반환
        }

        // 결제 번호 가져오기
        int payment_num = paymentDTO.getPayment_num();
        
        // 결제 번호 로그 출력
        System.out.println("payment_num : " + payment_num);

        // 결제 DTO에 결제 번호 및 상태 설정
        paymentDTO.setPayment_num(payment_num); // 결제 번호 설정
        paymentDTO.setPayment_status("결제취소"); // 결제 상태를 "결제취소"로 설정

        // 결제 상태 업데이트 수행
        boolean flag = paymentService.update(paymentDTO); // 업데이트 성공 여부 반환

        // 업데이트 결과에 따른 처리
        if (flag) {
            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 성공");
            modelAndView.addObject("msg", "결제가 취소가 완료 되었습니다."); // 성공 메시지 추가
            modelAndView.addObject("path", "myReservationListPage.do"); // 예약 목록 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
        } else {
            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 실패");
            modelAndView.addObject("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "main.do"); // 메인 페이지로 리다이렉트
            modelAndView.setViewName("info"); // info.jsp로 설정
        }

        System.out.println("************************************************************com.korebap.app.view.payment.UpdatePaymentAction_updatePayment_GET 종료************************************************************");
        return modelAndView; // 최종 모델 반환
    }
}
