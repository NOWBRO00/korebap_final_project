package com.korebap.app.biz.board;

import java.util.List;

public interface BoardService {
	List<BoardDTO> selectAll(BoardDTO boardDTO);
	BoardDTO selectOne(BoardDTO boardDTO);
	boolean insert(BoardDTO boardDTO);
	boolean update(BoardDTO boardDTO);
	boolean delete(BoardDTO boardDTO);
}
