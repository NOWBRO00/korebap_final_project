package com.korebap.app.biz.imageFile;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageFileService")
public class ImageFileServiceImpl implements ImageFileService{
	@Autowired
	private ImageFileDAO3 fileDAO;

	@Override
	public List<ImageFileDTO> selectAll(ImageFileDTO fileDTO) {
		return this.fileDAO.selectAll(fileDTO);
	}

	@Override
	public ImageFileDTO selectOne(ImageFileDTO fileDTO) {
		System.out.println("goodlike update기능 미구현으로 null 반환");
		return this.fileDAO.selectOne(fileDTO);
	}

	@Override
	public boolean insert(ImageFileDTO fileDTO) {
		return this.fileDAO.insert(fileDTO);
	}

	@Override
	public boolean update(ImageFileDTO fileDTO) {
		return this.fileDAO.update(fileDTO);
	}

	@Override
	public boolean delete(ImageFileDTO fileDTO) {
		return this.fileDAO.delete(fileDTO);
	}

	

}
