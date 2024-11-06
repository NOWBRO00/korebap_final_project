package com.korebap.app.biz.claim;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("claimService")
public class ClaimServiceImpl implements ClaimService{
	@Autowired
	private ClaimDAO2 claimDAO;

	@Override
	public List<ClaimDTO> selectAll(ClaimDTO claimDTO) {
		return this.claimDAO.selectAll(claimDTO);
	}

	@Override
	public ClaimDTO selectOne(ClaimDTO claimDTO) {
		return this.claimDAO.selectOne(claimDTO);
	}

	@Override
	public boolean insert(ClaimDTO claimDTO) {
		return this.claimDAO.insert(claimDTO);
	}

	@Override
	public boolean update(ClaimDTO claimDTO) {
		return this.claimDAO.update(claimDTO);
	}

	@Override
	public boolean delete(ClaimDTO claimDTO) {
		System.out.println("기능 미구현으로 false 반환");
		return this.claimDAO.delete(claimDTO);
	}
}
