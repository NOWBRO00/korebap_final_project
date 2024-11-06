package com.korebap.app.view.owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class OwnerReservationPageController {

	@Autowired
	private LoginCheck loginCheck;
	
	@Autowired
	private ReservationService reservationService;
	

	// 내(사장님) 상품의 예약 목록 전체 보기
	@GetMapping(value="/ownerReservationList.do")
	public String ownerReservationList(ReservationDTO reservationDTO, Model model) {
		
		// 로그인, role 체크
		// 로그인 상태 + role이 OWNER라면
		// 로그인 한 id를 DTO에 저장해서 DB에 list 요청, 반환받는다
		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationList 시작]************************************************************");

		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationList 로그인 상태 세션 없음*****");

			
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}
		
		System.out.println("*****com.korebap.app.view.owner ownerReservationList 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();
		
		System.out.println("*****com.korebap.app.view.owner ownerReservationList member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerReservationList role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");
			
		}
		else { //role이 OWNER인 경우
			

			// 로그인 한 id를 DB에 보내 List 반환받는다.
			reservationDTO.setReservation_seller_id(member_id);
			reservationDTO.setReservation_condition("RESERVATION_OWNER_SELECTALL");
			List<ReservationDTO> datas = reservationService.selectAll(reservationDTO);
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationList datas ["+datas+"]*****");

			
			// view에 전달하기 위해 model 객체에 데이터 저장
			model.addAttribute("reservationList", datas);
			
			// 사장님 예약 목록 페이지로 이동
			viewName = "reservationManagement";
			
		}
		
		
		System.out.println("*****com.korebap.app.view.owner ownerReservationList viewName ["+viewName+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationList 종료]************************************************************");

		return viewName;
	} // 내(사장님) 상품의 예약 목록 전체 보기 메서드 종료
	
	
	
	// 내(사장님) 상품의 예약 상세페이지 보기
	@GetMapping(value="/ownerReservationDetail.do")
	public String ownerReservationDetail(ReservationDTO reservationDTO, Model model) {
	
		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationDetail 시작]************************************************************");

		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationDetail 로그인 상태 세션 없음*****");

			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}
		
		System.out.println("*****com.korebap.app.view.owner ownerReservationDetail 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();
		
		System.out.println("*****com.korebap.app.view.owner ownerReservationDetail member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("OWNER")) {

			System.out.println("*****com.korebap.app.view.owner ownerReservationDetail role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");
			
		}
		else { // OWNER라면
			// 예약의 상세페이지 요청
			
			
			// view 에게 받아온 num 확인
			int reservation_num = reservationDTO.getReservation_num();
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationDetail reservation_num["+reservation_num+"]*****");

			// DB에 id와 reservation_num을 보내 데이터를 반환받는다.
			reservationDTO.setReservation_seller_id(member_id);
			reservationDTO.setReservation_condition("RESERVATION_OWNER_SELECTONE");
			reservationDTO = reservationService.selectOne(reservationDTO);
			
			
			System.out.println("*****com.korebap.app.view.owner ownerReservationDetail reservationDTO["+reservationDTO+"]*****");

			
			// 상품 정보가 있다면
			if(reservationDTO != null) {
				System.out.println("*****com.korebap.app.view.owner ownerReservationDetail reservationDTO 있음*****");

				// model 객체에 데이터를 담아 전달
				model.addAttribute("reservation", reservationDTO);
				
				// 예약 상세페이지
				viewName = "reservationManagementDetail";
				
			}
			else {
				System.out.println("*****com.korebap.app.view.owner ownerReservationDetail reservationDTO 없음*****");

				// 반환받은 상품 정보가 없다면
				model.addAttribute("msg", "예약 내역을 찾을 수 없습니다. 다시 시도해 주세요.");
				model.addAttribute("path", "ownerReservationList.do");

			}
			
			
		}
		
		System.out.println("*****com.korebap.app.view.product ownerReservationDetail viewName ["+ viewName +"]*****");

		
		System.out.println("************************************************************[com.korebap.app.view.owner ownerReservationDetail 종료]************************************************************");

		return viewName;
	}
	
	
}
