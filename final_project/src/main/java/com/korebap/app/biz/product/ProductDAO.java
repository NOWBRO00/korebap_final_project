package com.korebap.app.biz.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;

//@Repository
public class ProductDAO {
	// 전체출력 통합 >> 정렬기준 + 검색어
	private final String PRODUCT_SELECTALL = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR"
			+ " FROM ("
			+ "    SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT,FILE_DIR,"
			+ "           ROW_NUMBER() OVER (" + "           		ORDER BY" + "           		CASE"
			+ "           		 	WHEN ?  = 'newest' THEN PRODUCT_NUM"
			+ "                    WHEN ? = 'rating' THEN COALESCE(RATING, -1)"
			+ "                    WHEN ? = 'wish' THEN COALESCE(WISHLIST_COUNT, -1)"
			+ "                    WHEN ? = 'payment' THEN COALESCE(PAYMENT_COUNT, -1)"
			+ "                    ELSE PRODUCT_NUM" + "                   END DESC) AS ROW_NUM"
			+ "    FROM PRODUCT_INFO_VIEW" + "    WHERE PRODUCT_NAME LIKE CONCAT('%',COALESCE(?, ''), '%')"
			+ "    AND (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION)) AND (PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY)) "
			+ ") AS subquery " + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";

	// 상품 상세보기
	private final String SELECTONE = "SELECT PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, "
			+ "(SELECT COALESCE(ROUND(AVG(R.REVIEW_STAR), 1), 0) FROM REVIEW R WHERE R.REVIEW_PRODUCT_NUM = PRODUCT_NUM) AS RATING, "
			+ "(SELECT COUNT(PA.PAYMENT_PRODUCT_NUM) FROM PAYMENT PA WHERE PA.PAYMENT_PRODUCT_NUM = PRODUCT_NUM) AS PAYMENT_COUNT, "
			+ "(SELECT COUNT(W.WISHLIST_PRODUCT_NUM) FROM WISHLIST W WHERE W.WISHLIST_PRODUCT_NUM = PRODUCT_NUM) AS WISHLIST_COUNT "
			+ "FROM PRODUCT WHERE PRODUCT_NUM = ?";

	// 사용자가 선택한 일자의 재고 보기
	private final String SELECTONE_CURRENT_STOCK = "SELECT P.PRODUCT_NUM, (P.PRODUCT_CNT - COALESCE(RS.RESERVATION_COUNT, 0)) AS CURRENT_STOCK "
			+ "FROM PRODUCT P "
			+ "LEFT JOIN (SELECT PA.PAYMENT_PRODUCT_NUM AS PRODUCT_NUM, COUNT(R.RESERVATION_REGISTRATION_DATE) AS RESERVATION_COUNT "
			+ "FROM RESERVATION R " + "JOIN PAYMENT PA ON R.RESERVATION_PAYMENT_NUM = PA.PAYMENT_NUM "
			+ "WHERE R.RESERVATION_REGISTRATION_DATE = ? "
			+ "GROUP BY PA.PAYMENT_PRODUCT_NUM) RS ON P.PRODUCT_NUM = RS.PRODUCT_NUM " + "WHERE P.PRODUCT_NUM = ?";

	// 전체 데이터 개수를 반환 (전체 페이지 수 - 기본)
	private final String PRODUCT_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(PRODUCT_NUM), 0) / 9.0) AS PRODUCT_TOTAL_PAGE FROM PRODUCT";

	// 전체 데이터 개수를 반환 (검색어 사용 페이지수)
	private final String PRODUCT_SEARCH_PAGE = "WHERE PRODUCT_NAME LIKE CONCAT('%', ?, '%')";

	// 전체 데이터 개수를 반환 (필터링 검색 페이지 수)
	private final String PRODUCT_FILTERING_PAGE = "WHERE (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION) AND PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY))";

	// 샘플데이터(크롤링) insert
	// 바다
	// 낚시배
	private final String PRODUCT_CRAWLING_SEA_BOAR_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY) "
			+ "VALUES (?, ?, ?, ?, ?, '바다', '낚시배')";

	// 낚시터
	private final String PRODUCT_CRAWLING_SEA_FISHING_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY) "
			+ "VALUES (?, ?, '바다 낚시터입니다~!', 99, ?, '바다', '낚시터')";

	// 민물
	// 낚시터
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY) "
			+ "VALUES (?, ?, '민물 낚시터입니다~!', ?, ?, '민물', '수상')";

	// 낚시카페
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY) "
			+ "VALUES (?, ?, '민물 낚시카페입니다~!', 50, ?, '민물', '낚시카페')";

	// 이미지 파일 저장을 위한 select
	// 상품 pk 출력
	private final String PRODUCT_NUM_SELECT = "SELECT MAX(PRODUCT_NUM) AS MAX_NUM FROM PRODUCT";

	public boolean insert(ProductDTO productDTO) {
		System.out.println("====model.ProductDAO.insert 시작");
		// JDBC 연결
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		System.out.println("인서트 컨디션 : "+productDTO.getProduct_condition());
		System.out.println("productDTO : "+productDTO);
		
		try {
			if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_BOAR_INSERT")) {
				pstmt = conn.prepareStatement(PRODUCT_CRAWLING_SEA_BOAR_INSERT);
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_details());// 설명
				pstmt.setInt(4, productDTO.getProduct_cnt());// 재고
				pstmt.setString(5, productDTO.getProduct_address());// 주소
			} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_FISHING_INSERT")) {
				pstmt = conn.prepareStatement(PRODUCT_CRAWLING_SEA_FISHING_INSERT);
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_address());// 주소
			} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT")) {
				pstmt = conn.prepareStatement(PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT);
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setInt(3, productDTO.getProduct_cnt());// 수량
				pstmt.setString(4, productDTO.getProduct_address());// 주소
			} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT")) {
				pstmt = conn.prepareStatement(PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT);
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_address());// 주소
			} else {
				System.out.println("====model.ProductDAO.insert 컨디션 에러");
			}

			int result = pstmt.executeUpdate();
			System.out.println("	model.ProductDAO.insert  result : " + result);
			if (result <= 0) {
				System.out.println("====model.ProductDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("====model.ProductDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("====model.ProductDAO.insert SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ProductDAO.insert 종료");
		}
		return true;
	}

	public ArrayList<ProductDTO> selectAll(ProductDTO productDTO) { // 전체 출력
		System.out.println("====model.ProductDAO.selectAll 시작");
		// JDBC 연결
		ArrayList<ProductDTO> datas = new ArrayList<ProductDTO>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(PRODUCT_SELECTALL);
			pstmt.setString(1, productDTO.getProduct_search_criteria()); // 검색 정렬 기준
			pstmt.setString(2, productDTO.getProduct_search_criteria()); // 검색 정렬 기준
			pstmt.setString(3, productDTO.getProduct_search_criteria()); // 검색 정렬 기준
			pstmt.setString(4, productDTO.getProduct_search_criteria()); // 검색 정렬 기준
			pstmt.setString(5, productDTO.getProduct_searchKeyword()); // 검색어
			pstmt.setString(6, productDTO.getProduct_location()); // 상품 장소 (바다/민물)
			pstmt.setString(7, productDTO.getProduct_category()); // 상품 유형 (낚시배/낚시터/낚시카페/수상)
			// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
			pstmt.setInt(8, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
			pstmt.setInt(9, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산 페이지번호 *한 페이지에 나오는 데이터 수

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("====model.ProductDAO.selectAll rs.next()실행");
				ProductDTO data = new ProductDTO();
				data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
				data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
				data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
				data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
				data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
				data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터,낚시카페, 수상)
				data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
				data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
				data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수
				data.setProduct_file_dir(rs.getString("FILE_DIR")); // 파일 경로
				datas.add(data);
				System.out.println("	model.ProductDAO.selectAll datas : [" + datas + "]");
			}
		} catch (SQLException e) {
			System.err.println("====model.ProductDAO.selectAll SQL문 실패");
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ProductDAO.selectAll 종료");
		}
		return datas;
	}


	public ProductDTO selectOne(ProductDTO productDTO) {// 한개 출력
		System.out.println("====model.ProductDAO.selectOne 시작");
		// JDBC 연결
		ProductDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			System.out.println("	model.ProductDAO.selectOne productDTO.getProduct_condition() : ["
					+ productDTO.getProduct_condition() + "]");
			if (productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) { // 상품 상세보기
				pstmt = conn.prepareStatement(SELECTONE);
				pstmt.setInt(1, productDTO.getProduct_num());
			} else if (productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) { // 사용자가 선택한 일자의 재고 보기
				pstmt = conn.prepareStatement(SELECTONE_CURRENT_STOCK);
				pstmt.setDate(1, productDTO.getProduct_reservation_date());
				pstmt.setInt(2, productDTO.getProduct_num());
			} else if (productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) { // 크롤링 select
				pstmt = conn.prepareStatement(PRODUCT_NUM_SELECT);
				System.out.println("로그로그 첫번째");
			}  else if (productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) { // 페이지네이션에 사용하기 위해 전체 페이지 수 반환
				if (productDTO.getProduct_searchKeyword() != null && !productDTO.getProduct_searchKeyword().isEmpty()) {
					pstmt = conn.prepareStatement(PRODUCT_TOTAL_PAGE + " " + PRODUCT_SEARCH_PAGE);
					pstmt.setString(1, productDTO.getProduct_searchKeyword()); // 검색어
				} else if (productDTO.getProduct_location() != null && !productDTO.getProduct_location().isEmpty()
						|| productDTO.getProduct_category() != null && !productDTO.getProduct_category().isEmpty()) {
					pstmt = conn.prepareStatement(PRODUCT_TOTAL_PAGE + " " + PRODUCT_FILTERING_PAGE);
					pstmt.setString(1, productDTO.getProduct_location()); // 상품 장소 (바다/민물)
					pstmt.setString(2, productDTO.getProduct_category()); // 상품 유형 (낚시배/낚시터/낚시카페/수상)
				} else {
					pstmt = conn.prepareStatement(PRODUCT_TOTAL_PAGE);
				}
			} else {
				System.err.println("====model.ProductDAO.selectOne 컨디션 실패");
			}

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new ProductDTO();
				System.out.println("====model.ProductDAO.selectOne rs.next() 시작");
				if (productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) { // 상품 상세보기
					data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
					data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
					data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
					data.setProduct_details(rs.getString("PRODUCT_DETAILS")); // 상품 설명
					data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
					data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
					data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터)
					data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
					data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
					data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수
				} else if (productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) { // 사용자가 선택한 일자의 재고 보기
					data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
					data.setProduct_cnt(rs.getInt("CURRENT_STOCK")); // 상품의 재고
				} else if (productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) { // 상품번호 보여주기 (크롤링)
					data.setProduct_num(rs.getInt("MAX_NUM")); // 상품 번호
					System.out.println("로그로그 무건이 로그");
				}  else if (productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) { // 상품 테이블의 전체 개수 출력
					data.setProduct_total_page(rs.getInt("PRODUCT_TOTAL_PAGE")); // 상품 개수
				} else {
					System.err.println("====model.ProductDAO.selectOne 컨디션 실패");
				}
				System.out.println("	model.ProductDAO.selectOne data : [" + data+"]");
			}
		} catch (SQLException e) {
			System.err.println("====model.ProductDAO.selectOne SQL문 실패");
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
			System.out.println("====model.ProductDAO.selectOne 종료");
		}
		return data;
	}

	// 기능 미구현으로 private 처리
	private boolean update(ProductDTO productDTO) { // 입력

		return false;
	}

	// 기능 미구현으로 private 처리
	private boolean delete(ProductDTO productDTO) { // 입력

		return false;
	}

}
