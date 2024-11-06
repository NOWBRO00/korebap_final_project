package com.korebap.app.view.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginCheck {

	@Autowired
	private HttpSession session;

	public String loginCheck() {
		System.out.println("//////////com.korebap.app.view.common.LoginCheck_loginCheck 시작//////////");
		String loginMember = (String) session.getAttribute("member_id");

		if(session == null || loginMember == null) {
			System.out.println("유저 상태 : [ 로그아웃 상태 ]"); // 로그인 세션이 없을 때 로그
			System.out.println("//////////com.korebap.app.view.common.LoginCheck_loginCheck 종료//////////");
			return ""; // 로그인 페이지로 리다이렉트
		}
		System.out.println("유저 상태 : [ 로그인 상태]"); // 로그인 세션이 있을 때 로그
		System.out.println("//////////com.korebap.app.view.common.LoginCheck_loginCheck 종료//////////");
		return loginMember;

	}
	
	public String loginRoleCheck() {
		System.out.println("//////////com.korebap.app.view.common.LoginCheck loginRoleCheck 시작//////////");
		
		String login_role = (String) session.getAttribute("member_role");
		
		if(session == null || login_role == null) {
			System.out.println("유저 상태 : [ 로그아웃 상태 ]"); // 로그인 세션이 없을 때 로그
			System.out.println("//////////com.korebap.app.view.common.LoginCheck loginRoleCheck 종료//////////");
			return ""; // 로그인 페이지로 리다이렉트
		}
		
		System.out.println("유저 상태 : [ 로그인 상태]"); // 로그인 세션이 있을 때 로그
		System.out.println("//////////com.korebap.app.view.common.LoginCheck loginRoleCheck 종료//////////");
		
		return login_role;
	}
	
	
}
