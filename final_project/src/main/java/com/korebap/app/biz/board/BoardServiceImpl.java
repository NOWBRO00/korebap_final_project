package com.korebap.app.biz.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO2 boardDAO;
	
	@Override
	public BoardDTO selectOne(BoardDTO boardDTO) {
		return this.boardDAO.selectOne(boardDTO);
	}

	@Override
	public boolean insert(BoardDTO boardDTO) {
		return this.boardDAO.insert(boardDTO);
	}

	@Override
	public boolean update(BoardDTO boardDTO) {
		return this.boardDAO.update(boardDTO);
	}

	@Override
	public boolean delete(BoardDTO boardDTO) {
		return this.boardDAO.delete(boardDTO);
	}

	@Override
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		return this.boardDAO.selectAll(boardDTO);

	}

}
