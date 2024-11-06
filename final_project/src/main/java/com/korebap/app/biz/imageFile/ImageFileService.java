package com.korebap.app.biz.imageFile;

import java.util.List;

public interface ImageFileService {
	List<ImageFileDTO> selectAll(ImageFileDTO fileDTO);
	ImageFileDTO selectOne(ImageFileDTO fileDTO);
	boolean insert(ImageFileDTO fileDTO);
	boolean update(ImageFileDTO fileDTO);
	boolean delete(ImageFileDTO fileDTO);
}
