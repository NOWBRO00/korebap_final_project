package com.korebap.app.biz.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 MemberService의 구현체(실현체)
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDAO2 replyDAO;
	
	@Override
	public List<ReplyDTO> selectAll(ReplyDTO replyDTO) {
		return this.replyDAO.selectAll(replyDTO);
	}

	@Override
	public ReplyDTO selectOne(ReplyDTO replyDTO) {
		System.out.println("reply selectOne 기능 미구현으로 null 반환");
		return this.replyDAO.selectOne(replyDTO);
	}

	@Override
	public boolean insert(ReplyDTO replyDTO) {
		return this.replyDAO.insert(replyDTO);
	}

	@Override
	public boolean update(ReplyDTO replyDTO) {
		return this.replyDAO.update(replyDTO);
	}

	@Override
	public boolean delete(ReplyDTO replyDTO) {
		return this.replyDAO.delete(replyDTO);
	}
}
