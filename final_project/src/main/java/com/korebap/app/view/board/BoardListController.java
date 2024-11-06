package com.korebap.app.view.board;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;


@Controller
public class BoardListController {

	@Autowired
	private BoardService boardService;

	// 글 목록보기
	@GetMapping(value="/boardListPage.do")
	public String boardListPage(BoardDTO boardDTO, Model model,@RequestParam(value="currentPage", defaultValue="1") int board_page_num) {
		// 인자로 받는 cruuentPage는 defaultValue를 설정하여 값이 없는 경우 1로 설정한다.
		// *초기 페이지이므로 null로 들어올 수 있음
		// [ 게시판 목록 페이지]
		
		System.out.println("************************************************************[com.korebap.app.view.page boardListPage 시작]************************************************************");

		
		String searchCriteria = boardDTO.getBoard_search_criteria(); // 검색어
		String searchKeyword = boardDTO.getBoard_searchKeyword(); // 키워드

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.page boardListPage productDTO [" + boardDTO + "]*****");
		System.out.println("*****com.korebap.app.view.page boardListPage product_page_num [" + board_page_num + "]*****");
		System.out.println("*****com.korebap.app.view.page boardListPage searchCriteria [" + searchCriteria + "]*****");
		System.out.println("*****com.korebap.app.view.page boardListPage searchKeyword [" + searchKeyword + "]*****");


		
		// 사용자가 선택한 페이지번호 처리 (초기 1)
		boardDTO.setBoard_page_num(board_page_num);
		boardDTO.setBoard_condition("BOARD_ALL");

		//M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다. (게시판 전체 글)
		List<BoardDTO> boardList = boardService.selectAll(boardDTO);

		
		// [게시판 페이지 전체 개수]
		boardDTO.setBoard_condition("BOARD_PAGE_COUNT");
		boardDTO = boardService.selectOne(boardDTO);
		// int 타입 변수에 받아온 값을 넣어준다.
		int board_total_page = boardDTO.getBoard_total_page();


		// 전체 페이지 수가 1 미만이라면
		if (board_total_page < 1) {
			board_total_page = 1; // 최소 페이지 수를 1로 설정
		}

		// 데이터로그
		System.out.println("*****com.korebap.app.view.page boardListPage boardList ["+boardList+"]*****");
		System.out.println("*****com.korebap.app.view.page boardListPage board_total_page ["+board_total_page+"]*****");

		// view에게 전달하기 위해 model 객체에 저장
		model.addAttribute("boardList", boardList); // 게시판 list
		model.addAttribute("totalPage", board_total_page); // 전체 페이지 개수
		model.addAttribute("currentPage", board_page_num); // 현재 페이지 번호
		model.addAttribute("search_criteria",searchCriteria); // 검색 정렬 기준 (정렬 기준 유지시키기 위함)
		
		System.out.println("************************************************************[com.korebap.app.view.page boardListPage 종료]************************************************************");

		return "boardList";

	}

}