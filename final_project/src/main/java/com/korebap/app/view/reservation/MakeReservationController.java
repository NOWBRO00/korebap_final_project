package com.korebap.app.view.reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MakeReservationController {

    @Autowired
    private PaymentService PaymentService; // 결제 서비스 객체

    @Autowired
    private ReservationService reservationService; // 예약 서비스 객체
    
    @Autowired
    private LoginCheck loginCheck; // 로그인 확인 유틸리티

    // 예약 생성 처리 메서드, GET 요청을 처리
    @RequestMapping(value = "/makeReservation.do", method = RequestMethod.GET)
    public ModelAndView makeReservation(HttpServletRequest request,
                                         ModelAndView modelAndView,
                                         SimpleDateFormat formatter,
                                         PaymentDTO paymentDTO,
                                         ReservationDTO reservationDTO) {
        // 예약 생성 메서드 시작
        System.out.println("************************************************************com.korebap.app.view.reservation.MakeReservation_makeReservation_GET 시작************************************************************");

        // 현재 로그인된 사용자의 ID를 확인
        String login_member_id = loginCheck.loginCheck();
        
        // 사용자가 로그인하지 않은 경우
        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다."); // 사용자에게 메시지 전달
            modelAndView.addObject("path", "login.do"); // 로그인 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
            return modelAndView; // 메서드 종료
        }
        
        // 요청 파라미터에서 결제 고유 ID와 예약 날짜를 가져옴
        String merchant_uid = request.getParameter("merchant_uid"); // 결제 고유 ID
        String regDate = request.getParameter("reservation_registrarion_date"); // 예약 날짜
        System.out.println("request value(받아온 값) : [ " + regDate + " ]");
        
        // 날짜 포맷을 지정하여 설정
        formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 문자열을 java.util.Date 객체로 변환
        Date reservationDate = null;
        try {
            reservationDate = formatter.parse(regDate); // 문자열을 Date 객체로 변환
        } catch (ParseException e) {
            System.out.println("형변환 실패"); // 변환 실패 시 로그 출력
            e.printStackTrace(); // 예외 스택 트레이스 출력
        }

        // 데이터 로그 출력
        System.out.println("merchant_uid : [ " + merchant_uid + " ]");
        System.out.println("reservation_registrarion_date : [ " + regDate + " ]");

        // 결제 정보를 가져오기 위해 결제 DTO 설정
        paymentDTO.setMerchant_uid(merchant_uid); // 결제 DTO에 merchant_uid 설정
        paymentDTO.setPayment_condition("SELECT_BY_MERCHANT_UID"); // 결제 조건 설정

        // 결제 정보를 데이터베이스에서 조회
        paymentDTO = PaymentService.selectOne(paymentDTO); // 결제 서비스 호출

        // 예약 DTO에 예약 날짜와 결제 번호 설정
        reservationDTO.setReservation_registration_date(reservationDate); // 예약 날짜 설정
        reservationDTO.setReservation_payment_num(paymentDTO.getPayment_num()); // 결제 번호 설정

        // 예약 처리 요청
        boolean flag = reservationService.insert(reservationDTO); // 예약 서비스 호출

        // 예약 성공 여부 확인
        if (flag) {
            System.out.println("MakeReservationAction 로그 : 예약 성공");
            reservationDTO.setReservation_condition("RESERVATION_LAST_NUM"); // 예약 상태 설정
            reservationDTO = reservationService.selectOne(reservationDTO); // 방금 생성한 예약 정보 조회
            // 예약 상세 페이지로 리다이렉트
            modelAndView.setViewName("redirect:reservationDetail.do?reservation_num=" + reservationDTO.getReservation_num()); 
        } else {
            System.out.println("MakeReservationAction 로그 : 예약 실패");
            modelAndView.addObject("msg", "예약에 실패했습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "main.do"); // 메인 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
        }

        // 메서드 종료 로그 출력
        System.out.println("MakeReservationAction 끝");
        System.out.println("************************************************************com.korebap.app.view.reservation.MakeReservation_makeReservation_GET 종료************************************************************");
        return modelAndView; // 최종적으로 ModelAndView 반환
    }
}
