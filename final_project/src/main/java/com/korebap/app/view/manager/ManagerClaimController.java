package com.korebap.app.view.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;
import com.korebap.app.view.common.LoginCheck;

@Controller
public class ManagerClaimController {

	@Autowired
	ClaimService claimService; // ClaimService 의존주입

	@Autowired
	private LoginCheck loginCheck; // 로그인 & 권한 확인 의존성 주입

	// 클레임을 승인하는 요청을 처리하는 메서드
	@RequestMapping(value="/accessClaim.do", method=RequestMethod.GET)
	public String accessClaim(ClaimDTO claimDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_accessClaim_GET 시작************************************************************");

		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {

			System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 없음*****");


			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}

		System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner productDelete member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("ADMIN")) {

			System.out.println("*****com.korebap.app.view.owner productDelete role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

			// 바로 이동
			return viewName;

		}

		// 요청으로부터 받은 클레임 번호를 DTO에 설정
		System.out.println("신고 번호 : [ "+claimDTO.getClaim_num()+" ]");

		// 클레임 상태를 "COMPLETED"로 설정
		claimDTO.setClaim_status("COMPLETED");
		System.out.println("신고 처리 : [ "+claimDTO.getClaim_status()+" ]");

		// 클레임 정보를 업데이트하고 결과를 플래그로 저장
		boolean flag = claimService.update(claimDTO);
		System.out.println("반환값 : "+claimDTO);

		if(flag) { // 업데이트가 성공적일 경우
			System.out.println(claimDTO.getClaim_status());
			return "redirect:managerMain.do"; // 결과 반환
		}
		model.addAttribute("msg", "신고처리에 실패하였습니다");
		model.addAttribute("path", "managerMain.do");
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_accessClaim_GET 종료************************************************************");
		return "info"; // 결과 반환
	}

	// 클레임을 반려하는 요청을 처리하는 메서드
	@RequestMapping(value="/returnClaim.do", method=RequestMethod.GET)
	public String returnClaim(ClaimDTO claimDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_returnClaim_GET 시작************************************************************");
		
		// 로그인, role 체크
		String member_id = loginCheck.loginCheck();

		// 경로를 저장할 변수 설정
		String viewName="info";

		//만약 로그인 상태가 아니라면
		if(member_id.equals("")) {

			System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 없음*****");


			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 바로 이동
			return viewName;
		}

		System.out.println("*****com.korebap.app.view.owner productDelete 로그인 상태 세션 있음*****");


		// role 확인
		String member_role = loginCheck.loginRoleCheck();

		System.out.println("*****com.korebap.app.view.owner productDelete member_role["+member_role+"]*****");


		// role이 OWNER가 아니라면 메인페이지로 이동시킨다.
		if(!member_role.equals("ADMIN")) {

			System.out.println("*****com.korebap.app.view.owner productDelete role이 OWNER이 아닌 경우*****");

			model.addAttribute("msg", "권한이 없는 아이디입니다.");
			model.addAttribute("path", "main.do");

			// 바로 이동
			return viewName;

		}
		// 요청으로부터 받은 클레임 번호를 DTO에 설정
		System.out.println("신고 번호 : [ "+claimDTO.getClaim_num()+" ]");

		// 클레임 상태를 "COMPLETED"로 설정
		claimDTO.setClaim_status("REJECTED");
		System.out.println("신고 처리 : [ "+claimDTO.getClaim_status()+" ]");

		// 클레임 정보를 업데이트하고 결과를 플래그로 저장
		boolean flag = claimService.update(claimDTO);
		System.out.println("반환값 : "+claimDTO);

		if(flag) { // 업데이트가 성공적일 경우
			System.out.println(claimDTO.getClaim_status());
			return "redirect:managerMain.do"; // 결과 반환
		}
		model.addAttribute("msg", "신고처리에 실패하였습니다");
		model.addAttribute("path", "managerMain.do");

		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_returnClaim_GET 종료************************************************************");
		return "info"; // 결과 반환
	}
}
