package com.korebap.app.view.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.common.LoginCheck;

@Controller // 이 클래스가 Spring의 Controller 역할을 함을 나타냄
public class CancelReservationController {

    @Autowired
    private ReservationService reservationService; // 예약 관련 서비스 객체 주입

    @Autowired
    private PaymentService paymentService; // 결제 관련 서비스 객체 주입
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 주입

    // 예약 취소 요청을 처리하는 메서드, GET 요청을 처리
    @RequestMapping(value = "/cancelReservation.do", method = RequestMethod.GET)
    public ModelAndView cancelReservation(ModelAndView modelAndView,
                                          ReservationDTO reservationDTO,
                                          PaymentDTO paymentDTO) {
        // 예약 취소 메서드 시작
        System.out.println("************************************************************com.korebap.app.view.reservation.CancelReservationController_cancelReservation 시작************************************************************");

        // 현재 로그인된 사용자의 ID 확인
        String login_member_id = loginCheck.loginCheck();

        // 사용자가 로그인하지 않은 경우
        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다."); // 사용자에게 메시지 추가
            modelAndView.addObject("path", "login.do"); // 로그인 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
            return modelAndView; // 메서드 종료
        }

        // 예약 번호를 DTO에서 가져옴
        int reservation_num = reservationDTO.getReservation_num();
        // 데이터 로그 출력
        System.out.println("reservation_num : [ " + reservation_num + " ]");

        // 예약 조건을 설정하여 특정 예약 조회를 요청
        reservationDTO.setReservation_num(reservation_num);
        reservationDTO.setReservation_condition("RESERVATION_SELECTONE");

        // 예약 서비스 호출로 예약 정보 조회
        reservationDTO = reservationService.selectOne(reservationDTO);
        System.out.println(reservationDTO); // 조회된 예약 정보 출력
        boolean flag = false; // 예약 취소 성공 여부 플래그 초기화

        // 예약 정보가 정상적으로 조회된 경우
        if (reservationDTO != null) {
            reservationDTO.setReservation_status("예약취소"); // 예약 상태를 '예약취소'로 변경
            flag = reservationService.update(reservationDTO); // 예약 정보 업데이트 시도
        } else { // 예약 정보가 조회되지 않은 경우
            System.out.println("예약 내역 없음");
            modelAndView.addObject("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "myReservationListPage.do"); // 예약 목록 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
            return modelAndView; // 메서드 종료
        }

        // 결제 정보 조회를 위한 결제 번호 로그 출력
        System.out.println("결제 번호 : [ " + reservationDTO.getReservation_payment_num() + " ]");
        paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num()); // 결제 DTO에 결제 번호 설정
        paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM"); // 결제 조건 설정

        // 결제 서비스 호출로 결제 정보 조회
        paymentDTO = paymentService.selectOne(paymentDTO);
        System.out.println(paymentDTO); // 조회된 결제 정보 출력

        // 예약 취소 성공 여부에 따라 처리
        if (flag) {
            System.out.println("CancelReservationAction 로그 : 예약 취소 성공");
            // 결제 취소 페이지로 리다이렉트
            modelAndView.setViewName("redirect:cancelPayment.do?merchant_uid=" + paymentDTO.getMerchant_uid());
        } else { // 예약 취소 실패
            System.out.println("CancelReservationAction 로그 : 예약 취소 실패");
            modelAndView.addObject("msg", "예약 취소에 실패했습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "main.do"); // 메인 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
        }

        // 메서드 종료 로그 출력
        System.out.println("CancelReservationAction 끝");
        System.out.println("************************************************************com.korebap.app.view.reservation.CancelReservationController_cancelReservation 종료************************************************************");
        return modelAndView; // 최종적으로 ModelAndView 반환
    }
}
