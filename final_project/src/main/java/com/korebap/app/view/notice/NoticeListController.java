package com.korebap.app.view.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

@Controller
public class NoticeListController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/noticeList.do", method=RequestMethod.GET)
	public String noticeList(BoardDTO boardDTO,Model model) {
		System.out.println("NoticePageController.java noticeList() 시작");
		
		System.out.println("NoticeListController.java noticeList() board_page_num : "+boardDTO.getBoard_page_num());
		
		if(boardDTO.getBoard_page_num() <= 0) {
			boardDTO.setBoard_page_num(1);
		}
		
		boardDTO.setBoard_page_num((boardDTO.getBoard_page_num() -1)*10);
		
		boardDTO.setBoard_condition("NOTICE_SELECT_ALL");
		List<BoardDTO> noticeList = boardService.selectAll(boardDTO);
		
		boardDTO.setBoard_condition("NOTICE_TOTAL_COUNT");
		int notice_total_count = boardService.selectOne(boardDTO).getBoard_total_cnt();
		
		int notice_page_cnt = notice_total_count/10;
		if(notice_total_count%10 > 0) {
			notice_page_cnt += 1;
		}
		System.out.println("페이지 총 개수 : "+notice_page_cnt);
		
		model.addAttribute("noticeList",noticeList);
		model.addAttribute("total_page", notice_page_cnt);
		
		System.out.println("NoticePageController.java noticeList() 끝");
		return "noticeList";
	}
}
