package com.korebap.app.biz.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

// 인터페이스인 MemberService의 구현체(실현체)
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO2 memberDAO;

	@Override
	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		return this.memberDAO.selectAll(memberDTO);
	}

	@Override
	public MemberDTO selectOne(MemberDTO memberDTO) {
		//	      return this.memberDAO.selectOne(memberDTO);
		try { // 반환되는 값이 있으면 해당값 반환한다.
			return memberDAO.selectOne(memberDTO);
		} catch (EmptyResultDataAccessException e) { // 반환되는 값이 없으면 안내문을 콘솔에 출력한다.
			System.out.println("반환되는 값이 없어 발생하는 오류 : 기능에 맞는 값이 없음");
			return null; 
		}
	}

	@Override
	public boolean insert(MemberDTO memberDTO) {
		return this.memberDAO.insert(memberDTO);
	}

	@Override
	public boolean update(MemberDTO memberDTO) {
		return this.memberDAO.update(memberDTO);
	}

	@Override
	public boolean delete(MemberDTO memberDTO) {
		return this.memberDAO.delete(memberDTO);
	}
}
