package com.korebap.app.biz.wishlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDAO2 {
	// 위시리스트 추가
	private final String WISHLIST_INSERT = "INSERT INTO WISHLIST "
			+ "(WISHLIST_MEMBER_ID, WISHLIST_PRODUCT_NUM) "
			+ "VALUES (?, ?)";

	// 위시리스트 삭제
	private final String WISHLIST_DELETE = "DELETE FROM WISHLIST " 
			+ "WHERE WISHLIST_NUM = ?";

	// 나의 위시리스트 개수 보기
	private final String WISHLIST_SELECTONE = "SELECT COUNT(WISHLIST_NUM) AS WISHLIST_COUNT "
			+ "FROM WISHLIST "
			+ "WHERE WISHLIST_MEMBER_ID = ?";

	// 나의 위시리스트 전체 보기
	private final String WISHLIST_SELECTALL = "SELECT W.WISHLIST_NUM, P.PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE, " 
			+ "P.PRODUCT_LOCATION, P.PRODUCT_CATEGORY, I.FILE_DIR " 
			+ "FROM WISHLIST W " 
			+ "JOIN PRODUCT P ON W.WISHLIST_PRODUCT_NUM = P.PRODUCT_NUM "
			+ "LEFT JOIN ( " 
			+ 	"SELECT FILE_DIR, PRODUCT_ITEM_NUM, " 
			+ 	"ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS IMAGE_NUMBER " 
			+ 	"FROM IMAGEFILE "
			+ ") I ON P.PRODUCT_NUM = I.PRODUCT_ITEM_NUM AND I.IMAGE_NUMBER = 2 "
			+ "WHERE W.WISHLIST_MEMBER_ID = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(WishlistDTO wishlistDTO) { // 위시리스트 등록
		System.out.println("====model.WishlistDAO2.insert 시작");
		
		String member_id = wishlistDTO.getWishlist_member_id(); // 아이디
		int product_num = wishlistDTO.getWishlist_product_num(); // 상품 번호
		
		// 아이디, 상품 번호를 담아 쿼리문을 수행한다.
		int result = jdbcTemplate.update(WISHLIST_INSERT, member_id,product_num);
		
		System.out.println("====model.WishlistDAO2.insert result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.WishlistDAO2.insert 실패");
			return false; // false를 반환한다.
		}
		System.err.println("====model.WishlistDAO2.insert 종료");
		return true; // 성공적으로 등록 완료, true를 반환한다.

	}
	
	public boolean delete(WishlistDTO wishlistDTO) { // 위시리스트 삭제
		System.err.println("====model.WishlistDAO2.delete 시작");
		
		// 위시리스트 번호를 담아 쿼리문을 수행한다.
		int result = jdbcTemplate.update(WISHLIST_DELETE, wishlistDTO.getWishlist_num());
		
		System.out.println("====model.WishlistDAO2.delete result : ["+result+"]");
		if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
			System.err.println("====model.WishlistDAO2.delete 실패");
			return false; // false를 반환한다.// false 반환
		}
		System.err.println("====model.WishlistDAO2.delete 종료");
		return true; // 성공적으로 삭제 완료, true를 반환한다.
	}
	
	public List<WishlistDTO> selectAll(WishlistDTO wishlistDTO) { // 위시리스트 전체 보기
		List<WishlistDTO> datas = new ArrayList<WishlistDTO>();
		System.out.println("====model.WishlistDAO2.selectAll 시작");
		
		// 아이디를 담아서 쿼리문을 수행한다.
		Object[] args = { wishlistDTO.getWishlist_member_id() }; // 아이디
		datas =jdbcTemplate.query(WISHLIST_SELECTALL, args, new WishlistRowMapper());
		
		System.out.println("====model.WishlistDAO2.selectAll datas : ["+datas+"]");
		System.out.println("====model.WishlistDAO2.selectAll 종료");
		return datas;
	}

	
	public WishlistDTO selectOne(WishlistDTO wishlistDTO) { // 위시리스트 개수 보기
		System.out.println("====model.WishlistDAO2.selectOne 시작");
		
		// 아이디를 담아서 쿼리문을 수행한다.
		Object[] args = { wishlistDTO.getWishlist_member_id() };
		WishlistDTO data = jdbcTemplate.queryForObject(WISHLIST_SELECTONE, args, new Wishlist_selectOne_RowMapper());
		
		System.out.println("====model.WishlistDAO2.selectOne data : ["+data+"] ");
		System.out.println("====model.WishlistDAO2.selectOne 종료");
		return data;
	}


	public boolean update(WishlistDTO wishlistDTO) {
		// 기능 미사용으로 false 반환한다.
		return false;
	}
}
//======================================================RowMapper=================================================================
// 위시리스트 번호, 상품 번호, 상품명, 금액, 장소, 유형, 이미지 경로를 반환하는 RowMapper
class WishlistRowMapper implements RowMapper<WishlistDTO> {

	@Override
	public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.WishlistDAO2.WishlistRowMapper 실행");
		
		WishlistDTO data = new WishlistDTO(); 
		data.setWishlist_num(rs.getInt("WISHLIST_NUM")); // 위시리스트 번호 (PK)
		data.setWishlist_product_num(rs.getInt("PRODUCT_NUM")); // 상품번호
		data.setWishlist_product_name(rs.getString("PRODUCT_NAME")); // 상품명
		data.setWishlist_product_price(rs.getInt("PRODUCT_PRICE")); // 상품 금액
		data.setWishlist_product_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소
		data.setWishlist_product_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형(낚시배,낚시터,낚시카페,수상)
		data.setWishlist_file_dir(rs.getString("FILE_DIR")); // 파일 경로

		System.out.println("====model.WishlistDAO2.WishlistRowMapper 종료");
		return data;
	}

}

// 위시리스트 개수를 반환하는 RowMapper
class Wishlist_selectOne_RowMapper implements RowMapper<WishlistDTO> {

	@Override
	public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.WishlistDAO2.Wishlist_selectOne_RowMapper 실행");
		
		WishlistDTO data = new WishlistDTO();
		data.setWishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 위시리스트 개수
		
		System.out.println("====model.WishlistDAO2.Wishlist_selectOne_RowMapper 종료");
		return data;
	}

}
