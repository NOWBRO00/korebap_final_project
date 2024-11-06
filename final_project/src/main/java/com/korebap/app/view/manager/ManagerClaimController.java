package com.korebap.app.view.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.claim.ClaimService;

@Controller
public class ManagerClaimController {

	@Autowired
	ClaimService claimService; // ClaimService 의존주입

	// 클레임을 승인하는 요청을 처리하는 메서드
	@RequestMapping(value="/accessClaim.do", method=RequestMethod.GET)
	public String accessClaim(ClaimDTO claimDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_accessClaim_GET 시작************************************************************");
		
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
