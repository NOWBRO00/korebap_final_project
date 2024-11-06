package com.korebap.app.biz.wishlist;

import java.util.List;

public interface WishlistService {
	List<WishlistDTO> selectAll(WishlistDTO wishlistDTO);
	WishlistDTO selectOne(WishlistDTO wishlistDTO);
	boolean insert(WishlistDTO wishlistDTO);
	boolean update(WishlistDTO wishlistDTO);
	boolean delete(WishlistDTO wishlistDTO);
}
