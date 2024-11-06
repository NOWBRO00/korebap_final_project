package com.korebap.app.biz.member;

import java.util.List;

public interface MemberService {
	List<MemberDTO> selectAll(MemberDTO memberDTO);
	MemberDTO selectOne(MemberDTO memberDTO);
	boolean insert(MemberDTO memberDTO);
	boolean update(MemberDTO memberDTO);
	boolean delete(MemberDTO memberDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!