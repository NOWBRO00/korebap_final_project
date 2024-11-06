package com.korebap.app.view.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.common.LoginCheck;

@Controller // 이 클래스가 Spring의 Controller 역할을 함을 나타냄
public class ReservationDetailController {

    @Autowired
    private ReservationService reservationService; // 예약 관련 서비스 객체 주입

    @Autowired
    private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 주입

    // 예약 상세 정보를 조회하는 메서드, GET 요청을 처리
    @RequestMapping(value = "/reservationDetail.do", method = RequestMethod.GET)
    public ModelAndView reservationDetail(ModelAndView modelAndView,
                                          ReservationDTO reservationDTO) {
        // 예약 상세 보기 메서드 시작
        System.out.println("************************************************************com.korebap.app.view.reservation.ReservationDetail_reservationDetail_GET 시작************************************************************");

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

        // 예약 조건을 설정하여 특정 예약 조회를 요청
        reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
        reservationDTO = reservationService.selectOne(reservationDTO); // 예약 서비스 호출로 예약 정보 조회

        // 예약 정보가 정상적으로 조회된 경우
        if (reservationDTO != null) {
            System.out.println("ReservationDetailAction 로그 : 예약 조회 성공");
            modelAndView.addObject("reservation", reservationDTO); // 조회된 예약 정보를 모델에 추가
            modelAndView.setViewName("reservationDetails"); // reservationDetails.jsp 뷰 설정
        } else { // 예약 정보가 조회되지 않은 경우
            System.out.println("ReservationDetailAction 로그 : 예약 조회 실패");
            modelAndView.addObject("msg", "예약 내역을 찾지 못했습니다. 다시 시도해주세요."); // 실패 메시지 추가
            modelAndView.addObject("path", "reservationList.do"); // 예약 목록 페이지로 리다이렉트 설정
            modelAndView.setViewName("info"); // info.jsp 뷰 설정
        }

        // 메서드 종료 로그 출력
        System.out.println("************************************************************com.korebap.app.view.reservation.ReservationDetail_reservationDetail_GET 종료************************************************************");
        return modelAndView; // 최종적으로 ModelAndView 반환
    }
}
