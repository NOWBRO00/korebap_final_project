package com.korebap.app.view.reply;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class DeleteReplyController {

	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private LoginCheck loginCheck;

	@GetMapping(value="/deleteReply.do")
	public String deleteReply(ReplyDTO replyDTO,@RequestParam int board_num, Model model, RedirectAttributes redirectAttributes) {
		// [ 댓글 삭제 ]

		System.out.println("************************************************************[com.korebap.app.view.reply deleteReply 시작]************************************************************");

		// 경로를 담을 변수
		String viewName;

		// 상세페이지로 이동시키기 위해 변수 선언
//		int board_num = replyDTO.getReply_board_num();

		// 로그인 상태 확인
		String login_member_id = loginCheck.loginCheck();


		// 데이터 로그
		System.out.println("*****com.korebap.app.view.reply deleteReply member_id 확인 : ["+login_member_id+"]*****");
		System.out.println("*****com.korebap.app.view.reply deleteReply board_num 확인 : ["+board_num+"]*****");
		System.out.println("*****com.korebap.app.view.reply deleteReply reply_num 확인 : ["+replyDTO.getReply_num()+"]*****");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("*****com.korebap.app.view.reply deleteReply 로그인 세션 없음*****");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {
			System.out.println("*****com.korebap.app.view.reply deleteReply 로그인 세션 있음*****");

			// service에 삭제 요청 보낸다
			boolean flag = replyService.delete(replyDTO);

			if(flag) { // 삭제 성공시
				System.out.println("*****com.korebap.app.view.reply deleteReply 댓글 삭제 성공*****");

				redirectAttributes.addAttribute("board_num", board_num);

				return "redirect:boardDetail.do";

			}
			else { // 삭제 실패시
				System.out.println("*****com.korebap.app.view.reply deleteReply 댓글 삭제 실패*****");

				model.addAttribute("msg", "댓글 삭제 실패. 다시 시도해주세요.");
				model.addAttribute("path", "boardDetail.do?board_num="+board_num);

				viewName = "info";
			}

		}
		
		System.out.println("*****com.korebap.app.view.reply deleteReply viewName ["+viewName+"]*****");
		
		
		System.out.println("************************************************************[com.korebap.app.view.reply deleteReply 종료]************************************************************");

		return viewName;

	}
}
