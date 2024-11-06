package com.korebap.app.view.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class OwnerReservationCancelController {

	@Autowired
	private LoginCheck loginCheck;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PaymentService paymentService;

	// 예약 취소 (삭제 xx)
	// 예약 상태를 변경한다
	@PostMapping(value="/ownerReservationCancle.do")
	public String ownerReservationCancle(ReservationDTO reservationDTO, PaymentDTO paymentDTO, Model model, RedirectAttributes redirectAttributes) {
		// 로그인 체크, role 체크


		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationCancle 시작]************************************************************");

		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {

			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle 로그인 상태 세션 없음*****");

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}

		System.out.println("*****com.korebap.app.view.owner ownerReservationCancle 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner ownerReservationCancle member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

		}
		else { // role이 OWNER 라면

			// 받아온 예약 정보를 확인해서 취소시킨다
			// 결제도 취소해야한다

			// 받아온 예약 번호 확인
			int reservation_num = reservationDTO.getReservation_num();

			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle reservation_num["+reservation_num+"]*****");

			// 해당 예약건이 존재하는지 확인
			reservationDTO.setReservation_num(reservation_num);
			reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
			reservationDTO = reservationService.selectOne(reservationDTO);
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle reservationDTO["+reservationDTO+"]*****");

			// 예약 취소 성공 여부 
			boolean flag = false; 
			
			// 예약정보가 있다면
			if(reservationDTO != null) {
				// 예약 번호를 보내서 예약 상태 변경 요청을 한다
				// 이미 DTO에 담겨 있는 상태이므로 따로 set 하지 않는다.
				flag = reservationService.update(reservationDTO);
			}
			else { // 예약 정보가 없다면
				model.addAttribute("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요.");
				model.addAttribute("path", "ownerReservationDetail.do?reservation_num="+reservation_num);
				
				// 바로 이동
				return viewName;
			}
			
			// 결제 정보 조회를 위해 결제 번호 확인
			int payment_num = reservationDTO.getReservation_payment_num();
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle payment_num["+payment_num+"]*****");
			
	        paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num()); // 결제 DTO에 결제 번호 설정
	        paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM"); // 결제 조건 설정

	        // 결제 정보 조회
	        // merchant_uid 확인하기 위함
	        paymentDTO = paymentService.selectOne(paymentDTO);
	        
	        String merchant_uid = paymentDTO.getMerchant_uid();
	        
			System.out.println("*****com.korebap.app.view.owner ownerReservationCancle merchant_uid["+merchant_uid+"]*****");

			// 예약 취소 성공 여부에 따라 처리
	        if (flag) {
				System.out.println("*****com.korebap.app.view.owner ownerReservationCancle 예약 취소 성공*****");
	            // 결제 취소 페이지로 보내준다
				redirectAttributes.addAttribute("merchant_uid", merchant_uid);
				
				viewName = "redirect:cancelPayment.do";
				
	        } else { // 예약 취소 실패
				System.out.println("*****com.korebap.app.view.owner ownerReservationCancle 예약 취소 실패*****");

				model.addAttribute("msg", "예약 취소에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "ownerReservationDetail.do?reservation_num="+reservation_num);
				
	        }

			

		}



		System.out.println("*****com.korebap.app.view.owner ownerReservationCancle viewName ["+viewName+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationCancle 종료]************************************************************");

		return viewName;
	}
}
