package com.korebap.app.biz.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDAO2 reviewDAO;

	@Override
	public boolean insert(ReviewDTO reviewDTO) {
		return this.reviewDAO.insert(reviewDTO);
	}

	@Override
	public boolean update(ReviewDTO reviewDTO) {
		return this.reviewDAO.update(reviewDTO);
	}

	@Override
	public boolean delete(ReviewDTO reviewDTO) {
		return this.reviewDAO.delete(reviewDTO);
	}

	@Override
	public List<ReviewDTO> selectAll(ReviewDTO reviewDTO) {
		return this.reviewDAO.selectAll(reviewDTO);

	}
	@Override
	public ReviewDTO selectOne(ReviewDTO reviewDTO) {
		System.out.println("review selectOne 기능 미구현으로 null 반환");
		return this.reviewDAO.selectOne(reviewDTO);
	}
}
