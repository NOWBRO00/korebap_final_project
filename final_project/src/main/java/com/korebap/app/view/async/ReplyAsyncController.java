package com.korebap.app.view.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

@RestController
public class ReplyAsyncController {

    @Autowired
    private ReplyService replyService; // ReplyService를 의존성 주입

    @PostMapping(value = "/updateReply.do")
    public @ResponseBody boolean updateReply(@RequestBody ReplyDTO replyDTO) {
		System.out.println("************************************************************[com.korebap.app.view.async updateReply (비동기) 시작]************************************************************");

        // 데이터 로그
        System.out.println("*****com.korebap.app.view.async updateReply reply_num : [" + replyDTO.getReply_num() +"]*****");
        System.out.println("*****com.korebap.app.view.async updateReply reply_content : [" + replyDTO.getReply_content() +"]*****");

        // 데이터베이스 업데이트
        boolean flag = replyService.update(replyDTO);

        // 응답 반환
        if (flag) { // 성공
            System.out.println("*****com.korebap.app.view.async updateReply 변경 성공*****");
        }
        else { // 실패
            System.out.println("*****com.korebap.app.view.async updateReply 변경 실패*****");
        }
        
		System.out.println("************************************************************[com.korebap.app.view.async updateReply (비동기) 종료]************************************************************");

        return flag;
    }
}
