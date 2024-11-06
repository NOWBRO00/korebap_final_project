package com.korebap.app.view.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeDeleteController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/deleteNotice.do", method=RequestMethod.GET)
	public String deleteNotice(BoardDTO boardDTO, Model model, HttpSession session) {
		System.out.println("NoticeDeleteController.java deleteNotice() 시작");
		
		String member_id = (String)session.getAttribute("member_id");
		String member_role = (String)session.getAttribute("member_role");
		
		if(member_id == null || !member_role.equals("ADMIN")) {
			model.addAttribute("msg", "관리자 계정으로 로그인 후 서비스 이용이 가능합니다.");
			model.addAttribute("path", "login.do");
			return "info";
		}
		
		int board_num = boardDTO.getBoard_num();
		System.out.println("NoticeDeleteController.java deleteNotice() board_num : "+boardDTO.getBoard_num());
		
		boolean flag = boardService.delete(boardDTO);
		
		if(!flag) {
			model.addAttribute("msg", "게시글 삭제에 실패했습니다. 다시 시도해주세요.");
			model.addAttribute("path", "noticeDetail.do?board_num="+board_num);
			return "info";
		}
		
		model.addAttribute("msg", "게시글 삭제에 성공했습니다.");
		model.addAttribute("path", "noticeList.do");
		System.out.println("NoticeDeleteController.java deleteNotice() 끝");
		return "info";
	}
}
