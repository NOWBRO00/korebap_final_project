package com.korebap.app.biz.goodLike;

import java.util.List;

public interface GoodLikeService {
	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO);
	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO);
	public boolean insert(GoodLikeDTO goodLikeDTO);
	public boolean update(GoodLikeDTO goodLikeDTO);
	public boolean delete(GoodLikeDTO goodLikeDTO);
}
