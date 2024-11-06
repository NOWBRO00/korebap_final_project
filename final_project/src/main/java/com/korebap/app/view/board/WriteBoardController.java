package com.korebap.app.view.board;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardInsertTransaction;
import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.view.common.LoginCheck;

import jakarta.servlet.http.HttpSession;

@Controller
public class WriteBoardController {

	@Autowired
	private BoardInsertTransaction boardInsertTransaction; // 트랜잭션

	@Autowired
	private LoginCheck loginCheck;

	// 글 작성 페이지 이동
	@GetMapping(value="/writeBoard.do")
	public String writeBoardPage(Model model) {
		// [ 게시글 작성 페이지 이동]
		System.out.println("************************************************************[com.korebap.app.view.board writeBoardPage 시작]************************************************************");


		// 경로 저장해줄 변수
		String viewName;

		// 로그인 체크
		String member_id = loginCheck.loginCheck();

		System.out.println("*****com.korebap.app.view.page writeBoardPage member_id ["+member_id+"]*****");


		if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.page writeBoardPage 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else { //  로그인 상태라면
			System.out.println("*****com.korebap.app.view.page writeBoardPage 로그인 세션 있음*****");

			viewName = "redirect:writeBoard.jsp";


		}

		System.out.println("*****com.korebap.app.view.page writeBoardPage viewName ["+viewName+"]*****");
		System.out.println("************************************************************[com.korebap.app.view.board writeBoardPage 종료]************************************************************");


		return viewName;
	}



	@PostMapping(value="/writeBoard.do")
	public String writeBoard(HttpSession session, BoardDTO boardDTO, ImageFileDTO imageFileDTO, Model model,
			RedirectAttributes redirectAttributes, @RequestPart List<MultipartFile> files) throws IllegalStateException, IOException  {

		// [ 게시글 작성 ]
		System.out.println("************************************************************[com.korebap.app.view.board writeBoard 시작]************************************************************");

		// 경로를 담을 변수
		// info로 보내는 경우가 많아서 info로 초기화
		String viewName = "info";

		// 로그인 체크
		String member_id = loginCheck.loginCheck();


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.board writeBoard member_id 확인 : ["+member_id+"]*****");
		System.out.println("*****com.korebap.app.view.board writeBoard boardDTO 확인 : ["+boardDTO+"]*****");


		if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.board writeBoard 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

		}
		else {
			System.out.println("*****com.korebap.app.view.board writeBoard 로그인 세션 있음*****");
			
			// 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게 전달 필요)
			boardDTO.setBoard_writer_id(member_id);

			try { // 트랜잭션 RuntimeException 발생 후 페이지 이동 시키기 위해 try-catch로 묶는다

				// 트랜잭션 요청
				boolean flag = boardInsertTransaction.insertBoardAndImg(boardDTO,imageFileDTO, files);

				// 상세페이지로 이동시키기 위해 변수 선언
				System.out.println("*****com.korebap.app.view.board writeBoard getBoard_num ["+boardDTO.getBoard_num()+"]*****");

				// 글/이미지 입력 성공시
				if(flag) {
					// 게시글 상세 페이지로 이동
					// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
					// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
					redirectAttributes.addAttribute("board_num", boardDTO.getBoard_num());

					viewName = "redirect:boardDetail.do";
				}
				else { // 글 작성 실패
					System.out.println("*****com.korebap.app.view.board writeBoard 게시글 작성 실패*****");
					model.addAttribute("msg", "글 작성에 실패했습니다. 다시 시도해주세요.");
					model.addAttribute("path", "writeBoard.do");
				}
			} catch (RuntimeException e) {
				// 트랜잭션 중 예외 발생
				System.out.println("*****com.korebap.app.view.board writeBoard 트랜잭션 예외 발생: [" + e.getMessage()+"]");
				model.addAttribute("msg", "오류가 발생했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "writeBoard.do");
			}

		}// else 끝 (로그인상태)

		
		System.out.println("*****com.korebap.app.view.board writeBoard viewName ["+viewName+"]*****");

		
		System.out.println("************************************************************[com.korebap.app.view.board writeBoard 종료]************************************************************");


		return viewName;
	}
}