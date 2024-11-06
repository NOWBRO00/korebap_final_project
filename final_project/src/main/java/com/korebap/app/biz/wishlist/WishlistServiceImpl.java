package com.korebap.app.biz.wishlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wishlistService")
public class WishlistServiceImpl implements WishlistService{
	
	@Autowired
	WishlistDAO2 wishlistDAO;

	@Override
	public List<WishlistDTO> selectAll(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.selectAll(wishlistDTO);
	}

	@Override
	public WishlistDTO selectOne(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.selectOne(wishlistDTO);
	}

	@Override
	public boolean insert(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.insert(wishlistDTO);
	}

	@Override
	public boolean update(WishlistDTO wishlistDTO) {
		System.out.println("wishlist update 기능 미구현으로 false 반환");
		return this.wishlistDAO.update(wishlistDTO);
	}

	@Override
	public boolean delete(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.delete(wishlistDTO);
	}

}
