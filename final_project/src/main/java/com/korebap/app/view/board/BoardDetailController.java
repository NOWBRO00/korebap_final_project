package com.korebap.app.view.board;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.goodLike.GoodLikeDTO;
import com.korebap.app.biz.goodLike.GoodLikeService;
import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardDetailController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ImageFileService fileService;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private GoodLikeService goodLikeService;
	
	@GetMapping(value="/boardDetail.do")
	public String boardDetail(HttpSession session,BoardDTO boardDTO,ImageFileDTO imagefileDTO, ReplyDTO replyDTO,
			GoodLikeDTO goodLikeDTO, Model model) {
		
		System.out.println("************************************************************[com.korebap.app.view.board boardDetail 시작]************************************************************");
		
		// 아이디 가지고 온다 (좋아요 여부 확인 필요)
		String member_id = (String)session.getAttribute("member_id");
		int board_num = boardDTO.getBoard_num();
		// 데이터 로그
		System.out.println("*****com.korebap.app.view.board boardDetail member_id 확인 : ["+member_id+"]*****");
		System.out.println("*****com.korebap.app.view.board boardDetail board_num 확인 : ["+board_num+"]*****");
		
		
		
		// 글 번호로 [게시글] 찾기
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		boardDTO = boardService.selectOne(boardDTO);
		

		// 게시글 번호를 DTO에 넣어서 [전체 파일]을 불러온다.
		imagefileDTO.setFile_board_num(board_num);
		imagefileDTO.setFile_condition("BOARD_FILE_SELECTALL");
		List<ImageFileDTO> fileList = fileService.selectAll(imagefileDTO);
		

		// 게시글 번호를 DTO에 넣어서 [전체 댓글]을 불러온다.
		replyDTO.setReply_board_num(board_num);
		List<ReplyDTO> replyList=replyService.selectAll(replyDTO);
		
		
		// [좋아요 여부]를 확인하여 여부에 따라 색상을 표시하도록 view에게 전달 필요함
		goodLikeDTO.setGoodLike_board_num(board_num);
		goodLikeDTO.setGoodLike_member_id(member_id);
		goodLikeDTO = goodLikeService.selectOne(goodLikeDTO);
		
		// view에게 좋아요 여부를 true/false로 반환한다
		String flag = "true"; // 좋아요 한 상태
		if(goodLikeDTO == null) {
			// 좋아요 하지 않았다면 false로 변경
			System.out.println("*****com.korebap.app.view.board boardDetail goodLikeDTO 좋아요 하지 않은 상태*****");

			flag = "false";
		}
		
		
		// 로그
		System.out.println("*****com.korebap.app.view.board boardDetail boardDTO 확인 : ["+boardDTO+"]*****");
		System.out.println("*****com.korebap.app.view.board boardDetail fileList 확인 : ["+fileList+"]*****");
		System.out.println("*****com.korebap.app.view.board boardDetail replyList 확인 : ["+replyList+"]*****");
		System.out.println("*****com.korebap.app.view.board boardDetail flag(좋아요 여부) 확인 : ["+flag+"]*****");

		
		model.addAttribute("board", boardDTO);
		model.addAttribute("fileList", fileList);
		model.addAttribute("replyList", replyList);
		model.addAttribute("like_member", flag);
		
		System.out.println("************************************************************[com.korebap.app.view.board boardDetail 종료]************************************************************");

		// 글 상세페이지로 이동시킴
		return "boardDetails";
	}

}