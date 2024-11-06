package com.korebap.app.view.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.common.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyReservationListPageController {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private LoginCheck loginCheck;

    @RequestMapping(value="/myReservationListPage.do",method = RequestMethod.GET)
    public String myReservationList(HttpServletRequest request, Model model, ReservationDTO reservationDTO) {
    	System.out.println("************************************************************com.korebap.app.view.page.MyreservationList_myReservationList_GET 시작************************************************************");
   
        String login_member_id = loginCheck.loginCheck();
        System.out.println(login_member_id);
        if (login_member_id == null || login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음");
            model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
            model.addAttribute("path", "login.do");
            return "info"; // info.jsp로 이동 (Thymeleaf를 사용하는 경우 info.html)
        }
        reservationDTO.setReservation_condition("RESERVATION_SELECTALL");
        reservationDTO.setReservation_member_id(login_member_id);

        List<ReservationDTO> myReservationList = reservationService.selectAll(reservationDTO);
        model.addAttribute("myReservationList", myReservationList);
        System.out.println("내 예약 목록 이동중...");
        System.out.println("************************************************************com.korebap.app.view.page.MyreservationList_myReservationList_GET 종료************************************************************");
        return "myReservationList";
    }
}