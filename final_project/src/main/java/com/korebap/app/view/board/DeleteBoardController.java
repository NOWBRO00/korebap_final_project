package com.korebap.app.view.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class DeleteBoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 주입

	@GetMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardDTO boardDTO, Model model,RedirectAttributes redirectAttributes) {
		// [ 게시글 삭제 ]

		System.out.println("************************************************************[com.korebap.app.view.board deleteBoard 시작]************************************************************");

		// 경로를 담을 변수
		String viewName = "info";

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = boardDTO.getBoard_num();

		// 현재 로그인된 사용자의 ID 확인
		String member_id = loginCheck.loginCheck();

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.board deleteBoard member_id 확인 : ["+member_id+"]*****");
		System.out.println("*****com.korebap.app.view.board deleteBoard board_num 확인 : ["+board_num+"]*****");


		if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.board deleteBoard 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

		}
		else { // 로그인 상태일때
			System.out.println("*****com.korebap.app.view.board deleteBoard 로그인 세션 있음*****");

			// service를 통하여 게시글 삭제 요청을 한다
			boolean flag = boardService.delete(boardDTO);

			if(flag) { // 삭제 성공시
				System.out.println("*****com.korebap.app.view.board deleteBoard 게시글 삭제 성공*****");

				// 글 목록 페이지로 이동
				viewName ="redirect:boardListPage.do";
			}
			else { // 삭제 실패시
				System.out.println("*****com.korebap.app.view.board deleteBoard 게시글 삭제 실패*****");

				model.addAttribute("msg", "게시글 삭제에 실패했습니다. 다시 시도해 주세요.");
				model.addAttribute("path", "boardDetail.do?boardNum="+board_num);
			}

		}

		System.out.println("*****com.korebap.app.view.board deleteBoard viewName ["+viewName+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.board deleteBoard 종료]************************************************************");
		return viewName;
	}

}