package com.korebap.app.view.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.goodLike.GoodLikeDTO;
import com.korebap.app.biz.goodLike.GoodLikeService;
import com.korebap.app.view.common.LoginCheck;


@RestController
public class BoardAsyncController {

	@Autowired
	private GoodLikeService goodLikeService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private LoginCheck loginCheck; // 로그인 상태 확인 유틸리티 주입

	// 게시글 좋아요
	@PostMapping(value="/boardLike.do")
	public @ResponseBody Map<String, Object> boardLike(@RequestBody GoodLikeDTO goodLikeDTO, BoardDTO boardDTO) {
		// 좋아요 비동기
		System.out.println("************************************************************[com.korebap.app.view.async boardLike (비동기) 시작]************************************************************");

		// json 타입으로 반환하기 위한 Map 객체 생성
		Map<String, Object> responseMap = new HashMap<>();

		// 현재 로그인된 사용자의 ID 확인
		String login_member_id = loginCheck.loginCheck();

		// 만약 로그인된 id가 없다면
		if (login_member_id.equals("")) {
			System.out.println("*****com.korebap.app.view.async boardLike 로그인 상태 아닌 경우*****");
			responseMap.put("message", "로그인 후 이용해 주세요."); // 안내 메시지
			responseMap.put("redirect", "login.do"); // 로그인 페이지 URL
			return responseMap;
		}

		int board_num = goodLikeDTO.getGoodLike_board_num(); // 글 번호

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.async boardLike board_num ["+board_num+"]*****");
		System.out.println("*****com.korebap.app.view.async boardLike login_member_id ["+login_member_id+"]*****");


		// 저장된 아이디와 글 번호를 넣고 service에게 보내서 반환 (좋아요 여부 확인하기 위함)
		goodLikeDTO.setGoodLike_member_id(login_member_id);
		goodLikeDTO.setGoodLike_board_num(board_num);
		GoodLikeDTO newLikeDTO = goodLikeService.selectOne(goodLikeDTO);

		System.out.println("*****com.korebap.app.view.async boardLike newLikeDTO ["+newLikeDTO+"]*****");


		boolean flag = true;

		// 좋아요 한 내역이 없다면 (객체가 null이라면)
		if(newLikeDTO == null) {
			System.out.println("*****com.korebap.app.view.async boardLike 기존에 좋아요 내역 없음-insert*****");

			// 기존 id와 board_num 넣은 객체를 보내서 좋아요 insert
			goodLikeService.insert(goodLikeDTO);
		}
		else {
			System.out.println("*****com.korebap.app.view.async boardLike 기존에 좋아요 내역 있음-delete*****");

			// 기존에 좋아요 한 데이터 객체를 보내서 delete
			goodLikeService.delete(newLikeDTO);
			flag = false;
		}

		// 좋아요 수를 비동기로 보여줘야 하기 때문에 DTO 객체에 글 번호를 담아 개수 확인
		boardDTO.setBoard_num(board_num);
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		boardDTO = boardService.selectOne(boardDTO); 

		// ajax 요청 응답
		responseMap.put("flag",flag); // true / false
		responseMap.put("likeCnt",boardDTO.getBoard_like_cnt()); // 좋아요 수

		System.out.println("************************************************************[com.korebap.app.view.async boardLike (비동기) 종료]************************************************************");

		return responseMap;


	} // 좋아요 메서드 종료



	// 게시판 무한스크롤 비동기 구현하는 컨트롤러
	@GetMapping(value="/boardListScroll.do")
	public @ResponseBody Map<String, Object> boardListScroll(BoardDTO boardDTO, @RequestParam(value="currentPage") int current_page) {

		System.out.println("************************************************************[com.korebap.app.view.async boardListScroll (비동기) 시작]************************************************************");


		// [게시글 전체 출력]
		// 검색어와 정렬 기준을 변수에 넣어준다.
		String searchKeyword = boardDTO.getBoard_searchKeyword(); // 검색어
		String search_criteria = boardDTO.getBoard_search_criteria(); // 정렬 기준

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 searchKeyword : [" + searchKeyword +"]*****");
		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 board_search_criteria : [" + search_criteria +"]*****");
		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 current_page : [" + current_page +"]*****");


		// 현재 페이지 번호를 M에게 보내 데이터를 반환받는다
		boardDTO.setBoard_page_num(current_page);
		boardDTO.setBoard_condition("BOARD_ALL"); // 전체출력 컨디션

		List<BoardDTO> boardList = boardService.selectAll(boardDTO);

		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 boardList ["+boardList+"]*****");



		// [게시판 페이지 전체 개수]
		boardDTO.setBoard_condition("BOARD_PAGE_COUNT");
		boardDTO = boardService.selectOne(boardDTO);
		// int 타입 변수에 받아온 값을 넣어준다.
		int board_total_page = boardDTO.getBoard_total_page();

		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 board_page_count ["+board_total_page+"]*****");


		// 마지막 페이지 요청 시 처리
		// 현재 페이지 > 전체 페이지 수
		if (current_page > board_total_page) {
			System.out.println("*****com.korebap.app.view.async boardListScroll 마지막 페이지 요청*****");
			// 전달할 데이터가 없으므로 null 반환
			return null;
		}

		// 결과를 Map에 담아 반환
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("boardList", boardList);
		responseMap.put("totalPage", board_total_page);

		System.out.println("*****com.korebap.app.view.async boardListScroll 로그 responseMap ["+responseMap+"]*****");

		System.out.println("************************************************************[com.korebap.app.view.async boardListScroll (비동기) 종료]************************************************************");
		return responseMap;

	}


}
