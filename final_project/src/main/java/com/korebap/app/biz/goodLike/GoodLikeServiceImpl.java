package com.korebap.app.biz.goodLike;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("goodLikeService")
public class GoodLikeServiceImpl implements GoodLikeService {

	@Autowired
	private GoodLikeDAO2 goodLikeDAO;

	@Override
	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO) {
		System.out.println("goodlike update기능 미구현으로 null 반환");
		return this.goodLikeDAO.selectAll(goodLikeDTO);
	}

	@Override
	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO) {
		// return this.goodLikeDAO.selectOne(goodLikeDTO);
		try { // 반환되는 값이 있으면 해당값 반환한다.
			return goodLikeDAO.selectOne(goodLikeDTO);
		} catch (EmptyResultDataAccessException e) { // 반환되는 값이 없으면 안내문을 콘솔에 출력한다.
			System.out.println("반환되는 값이 없어 발생하는 오류 : 기능에 맞는 값이 없음");
			return null;
		}
	}

	@Override
	public boolean insert(GoodLikeDTO goodLikeDTO) {
		return this.goodLikeDAO.insert(goodLikeDTO);
	}

	@Override
	public boolean update(GoodLikeDTO goodLikeDTO) {
		System.out.println("goodlike update기능 미구현으로 false 반환");
		return this.goodLikeDAO.update(goodLikeDTO);
	}

	@Override
	public boolean delete(GoodLikeDTO goodLikeDTO) {
		return this.goodLikeDAO.delete(goodLikeDTO);
	}

}
