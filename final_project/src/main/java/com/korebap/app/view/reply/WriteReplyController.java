package com.korebap.app.view.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class WriteReplyController {

	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private LoginCheck loginCheck;

	@PostMapping(value="/writeReply.do")
	public String writeReply(ReplyDTO replyDTO, Model model, RedirectAttributes redirectAttributes,
			@RequestParam int board_num) {
		// [댓글 작성]

		System.out.println("************************************************************[com.korebap.app.view.reply writeReply 시작]************************************************************");

		
		// 로그인 체크
		String login_member_id = loginCheck.loginCheck();

		// 경로를 저장하는 변수
		String viewName;

		// 데이터 로그
		System.out.println("*****com.korebap.app.view.review writeReply member_id 확인 : ["+login_member_id+"]*****");
		System.out.println("*****com.korebap.app.view.review writeReply DTO 확인 : ["+replyDTO+"]*****");
		System.out.println("*****com.korebap.app.view.review writeReply board_num 확인 : ["+board_num+"]*****");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.review writeReply 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {
			System.out.println("*****com.korebap.app.view.review writeReply 로그인 세션 있음*****");

			// Service에게 DTO를 보내 댓글 insert 진행
			replyDTO.setReply_writer_id(login_member_id);
			replyDTO.setReply_board_num(board_num);
			boolean flag = replyService.insert(replyDTO);

			// 반환받은 값이 true라면
			if(flag) {
				// 댓글 작성 성공
				// 글 상세 페이지로 보내준다
				System.out.println("*****com.korebap.app.view.review writeReply 댓글 작성 성공*****");
				// 상품 상세 페이지로 보내줌
				// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
				// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
				redirectAttributes.addAttribute("board_num", board_num);
				viewName = "redirect:boardDetail.do";
			}
			else {
				// 댓글 작성 실패
				// 실패 안내와 함께 글 상세 페이지로 보내준다.
				System.out.println("*****com.korebap.app.view.review writeReply 댓글 작성 실패*****");

				model.addAttribute("msg", "댓글 작성에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "boardDetail.do?board_num="+board_num);
				viewName = "info";
			}

		}
		
		
		System.out.println("*****com.korebap.app.view.review writeReply viewName ["+viewName+"]*****");
		System.out.println("************************************************************[com.korebap.app.view.reply writeReply 종료]************************************************************");
		
		return viewName;

	}

}
