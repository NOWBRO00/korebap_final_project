package com.korebap.app.biz.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 MemberService의 구현체(실현체)
@Service("productService")
public class ProductServiceImpl implements ProductService {
   @Autowired
   private ProductDAO2 productDAO;
   
   @Override
   public List<ProductDTO> selectAll(ProductDTO productDTO) {
      return this.productDAO.selectAll(productDTO);
   }

   @Override
   public ProductDTO selectOne(ProductDTO productDTO) {
      return this.productDAO.selectOne(productDTO);
   }

   @Override
   public boolean insert(ProductDTO productDTO) {
      return this.productDAO.insert(productDTO);
   }

   @Override
   public boolean update(ProductDTO productDTO) {
      return this.productDAO.update(productDTO);
   }

   @Override
   public boolean delete(ProductDTO productDTO) {
      return this.productDAO.delete(productDTO);
   }
}
