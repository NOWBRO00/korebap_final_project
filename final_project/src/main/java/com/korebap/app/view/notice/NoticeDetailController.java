package com.korebap.app.view.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

@Controller
public class NoticeDetailController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/noticeDetail.do", method=RequestMethod.GET)
	public String noticeDetail(BoardDTO boardDTO, Model model) {
		System.out.println("NoticeDetailController.java noticeDetail() 시작");
		
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		System.out.println("NoticeDetailController.java noticeDetail() board_num : "+ boardDTO.getBoard_num());
		System.out.println("NoticeDetailController.java noticeDetail() GET board_condition : "+boardDTO.getBoard_condition());
		
		boardDTO = boardService.selectOne(boardDTO);
		
		if(boardDTO == null) {
			model.addAttribute("msg", "게시글을 찾을 수 없습니다. 다시 시도해 주세요.");
			model.addAttribute("path", "noticeList.do");
			return "info";
		}
		
		model.addAttribute("notice", boardDTO);
		
		System.out.println("NoticeDetailController.java noticeDetail() 끝");
		return "noticeDetail";
	}
}
