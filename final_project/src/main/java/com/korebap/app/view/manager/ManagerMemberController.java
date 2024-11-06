package com.korebap.app.view.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.claim.ClaimDTO;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

@Controller
public class ManagerMemberController {

	@Autowired
	MemberService memberService;

	@RequestMapping(value="/MemberBan.do", method=RequestMethod.GET)
	public String MemverBan(MemberDTO memberDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_MemverBan_GET 시작************************************************************");
		//////////////////////////////////////////관리자 로그인 넣기
		System.out.println("여기 관리자 로그인 확인해야됌!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// 요청으로부터 받은 클레임 번호를 DTO에 설정
		System.out.println("멤버 아이디 : [ "+memberDTO.getMember_id()+" ]");
		memberDTO.setMember_condition("MYPAGE");
		memberDTO = memberService.selectOne(memberDTO);
		String memberRole = memberDTO.getMember_role();
		// 클레임 상태를 "COMPLETED"로 설정
		memberDTO.setMember_role("BANNED");
		System.out.println("멤버 권한 : [ "+memberDTO.getMember_role()+" ]");

		// 클레임 정보를 업데이트하고 결과를 플래그로 저장
		memberDTO.setMember_condition("MEMBER_ROLE");
		boolean flag = memberService.update(memberDTO);
		System.out.println("반환값 : "+memberDTO);

		if(flag) { // 업데이트가 성공적일 경우
			System.out.println(memberDTO.getMember_role());
			if(memberRole.equals("USER")) {
				return "redirect:memberListPage.do"; // 결과 반환
			}
			else {
				return "redirect:bossListPage.do"; // 결과 반환
			}
		}
		model.addAttribute("msg", "정지처리를 실패했습니다");
		model.addAttribute("path", "managerMain.do");
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_MemverBan_GET 종료************************************************************");
		return "info"; // 결과 반환
	}

	// 클레임을 반려하는 요청을 처리하는 메서드
	@RequestMapping(value="/deleteMember.do", method=RequestMethod.GET)
	public String deleteMember(MemberDTO memberDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_deleteMember_GET 시작************************************************************");
		//////////////////////////////////////////관리자 로그인 넣기
		System.out.println("여기 관리자 로그인 확인해야됌!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("멤버 아이디 : [ "+memberDTO.getMember_id()+" ]");
		memberDTO.setMember_condition("MYPAGE");
		memberDTO = memberService.selectOne(memberDTO);
		String memberRole = memberDTO.getMember_role();

		boolean flag = memberService.delete(memberDTO);
		
		memberDTO.setMember_condition("MYPAGE");
		memberDTO = memberService.selectOne(memberDTO);
		
		if(flag) { // 업데이트가 성공적일 경우
			System.out.println(memberDTO.getMember_role());
			if(memberRole.equals("USER")) {
				return "redirect:memberListPage.do"; // 결과 반환
			}
			else {
				return "redirect:bossListPage.do"; // 결과 반환
			}
		}
		model.addAttribute("msg", "멤버삭제를 실패했습니다");
		model.addAttribute("path", "managerMain.do");

		System.out.println("************************************************************com.korebap.app.view.member.ClaimController_deleteMember_GET 종료************************************************************");
		return "info"; // 결과 반환
	}
}
