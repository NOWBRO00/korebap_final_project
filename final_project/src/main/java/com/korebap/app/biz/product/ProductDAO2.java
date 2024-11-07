package com.korebap.app.biz.product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO2 {
   // 상품 등록
   private final String PRODUCT_INSERT = "INSERT INTO PRODUCT "
         + "(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
         + "VALUES (?, ?, ?, ?, ?, ?, ?)";
   // 크롤링(샘플데이터) : 바다 - 낚시배
   private final String PRODUCT_INSERT_CRAWLING_SEA_BOAT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, "
         + "PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
         + "VALUES (?, ?, ?, ?, ?, '바다', '낚시배', ?)";
   // 크롤링(샘플데이터) : 바다 - 낚시터
   private final String PRODUCT_INSERT_CRAWLING_SEA_FISHING = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, "
         + "PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
         + "VALUES (?, ?, '바다 낚시터입니다~!', 99, ?, '바다', '낚시터', ?)";
   // 크롤링(샘플데이터) : 민물 - 낚시터
   private final String PRODUCT_INSERT_CRAWLING_FRESH_WATER_FISHING = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, "
         + "PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
         + "VALUES (?, ?, '민물 낚시터입니다~!', ?, ?, '민물', '수상', ?)";
   // 크롤링(샘플데이터) : 민물 - 낚시카페
   private final String PRODUCT_INSERT_CRAWLING_FRESH_WATER_FISHING_CAFE = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, "
         + "PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
         + "VALUES (?, ?, '민물 낚시카페입니다~!', 50, ?, '민물', '낚시카페', ?)";
   // 사장님 상품 수정
   private final String PRODUCT_UPDATE = "UPDATE PRODUCT P " + "JOIN MEMBER M ON M.MEMBER_ID = P.PRODUCT_SELLER_ID "
         + "SET P.PRODUCT_NAME = ?, P.PRODUCT_PRICE = ?, P.PRODUCT_DETAILS = ?, "
         + "P.PRODUCT_ADDRESS = ?, P.PRODUCT_LOCATION = ?, P.PRODUCT_CATEGORY = ? "
         + "WHERE P.PRODUCT_NUM = ? AND P.PRODUCT_SELLER_ID = ?";
   // 상품 삭제
   private final String PRODUCT_DELETE = "DELETE FROM PRODUCT WHERE PRODUCT_NUM = ?";

   // 상품 상세보기
   private final String PRODUCT_SELECTONE = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, "
         + "PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, "
         + "(SELECT COALESCE(ROUND(AVG(R.REVIEW_STAR), 1), 0) FROM REVIEW R WHERE R.REVIEW_PRODUCT_NUM = PRODUCT_NUM) AS RATING, "
         + "(SELECT COUNT(PA.PAYMENT_PRODUCT_NUM) FROM PAYMENT PA WHERE PA.PAYMENT_PRODUCT_NUM = PRODUCT_NUM) AS PAYMENT_COUNT, "
         + "(SELECT COUNT(W.WISHLIST_PRODUCT_NUM) FROM WISHLIST W WHERE W.WISHLIST_PRODUCT_NUM = PRODUCT_NUM) AS WISHLIST_COUNT "
         + "FROM PRODUCT WHERE PRODUCT_NUM = ?";
   // 사용자가 선택한 일자의 재고 보기
   private final String PRODUCT_SELECTONE_CURRENT_STOCK = "SELECT P.PRODUCT_NUM, (P.PRODUCT_CNT - COALESCE(RS.RESERVATION_COUNT, 0)) AS CURRENT_STOCK "
         + "FROM PRODUCT P " + "LEFT JOIN ( "
         + "    SELECT PA.PAYMENT_PRODUCT_NUM AS PRODUCT_NUM, COUNT(R.RESERVATION_REGISTRATION_DATE) AS RESERVATION_COUNT "
         + "    FROM RESERVATION R " + "    JOIN PAYMENT PA ON R.RESERVATION_PAYMENT_NUM = PA.PAYMENT_NUM "
         + "    WHERE R.RESERVATION_REGISTRATION_DATE = ? " + "    GROUP BY PA.PAYMENT_PRODUCT_NUM "
         + ") RS ON P.PRODUCT_NUM = RS.PRODUCT_NUM " + "WHERE P.PRODUCT_NUM = ?";
   // 이미지 파일 저장을 위한 최근 등록된 pk 찾기
   private final String PRODUCT_SELECTONE_NUM = "SELECT PRODUCT_NUM AS MAX_NUM " + "FROM PRODUCT "
         + "ORDER BY PRODUCT_NUM DESC " + "LIMIT 1";
   // 사장님 페이지 수를 반환 
   private final String PRODUCT_SELECTONE_OWNER_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(P.PRODUCT_NUM), 0) / 9.0) AS PRODUCT_TOTAL_PAGE "
         + "FROM PRODUCT_INFO_VIEW P "
         + "WHERE PRODUCT_SELLER_ID = ? ";
   // 전체 데이터 개수를 반환 (전체 페이지 수 - 기본)
   private final String PRODUCT_SELECTONE_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(PRODUCT_NUM), 0) / 9.0) AS PRODUCT_TOTAL_PAGE "
         + "FROM PRODUCT";
   // 전체 데이터 개수를 반환 (검색어 사용 페이지수)
   private final String PRODUCT_SELECTONE_SEARCH_PAGE = "WHERE PRODUCT_NAME LIKE CONCAT('%', ?, '%')";

   // 전체 데이터 개수를 반환 (필터링 검색 페이지 수)
   private final String PRODUCT_SELECTONE_FILTERING_PAGE = "WHERE (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION) "
         + "AND PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY))";
   // 전체출력 통합 >> 정렬기준 + 검색어
   private final String PRODUCT_SELECTALL = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, "
         + "PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR " + "FROM ( "
         + "    SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, "
         + "           PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR, "
         + "           ROW_NUMBER() OVER ( " + "               ORDER BY CASE "
         + "                   WHEN ? = 'newest' THEN PRODUCT_NUM "
         + "                   WHEN ? = 'rating' THEN COALESCE(RATING, -1) "
         + "                   WHEN ? = 'wish' THEN COALESCE(WISHLIST_COUNT, -1) "
         + "                   WHEN ? = 'payment' THEN COALESCE(PAYMENT_COUNT, -1) "
         + "                   ELSE PRODUCT_NUM " + "               END DESC) AS ROW_NUM "
         + "    FROM PRODUCT_INFO_VIEW " + "    WHERE PRODUCT_NAME LIKE CONCAT('%', COALESCE(?, ''), '%') "
         + "      AND (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION)) "
         + "      AND (PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY)) " + ") AS subquery "
         + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
   // 사장님 본인 상품 전체 보기
   private final String PRODUCT_SELECTALL_OWNER = "SELECT"
         + "   PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION,"
         + "   PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR "
         + "FROM"
         + "   ( SELECT "
         + "      P.PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE, P.PRODUCT_ADDRESS,"
         + "      P.PRODUCT_LOCATION, P.PRODUCT_CATEGORY, P.RATING, P.PAYMENT_COUNT,"
         + "      P.WISHLIST_COUNT, P.FILE_DIR, P.PRODUCT_SELLER_ID,"
         + "      ROW_NUMBER() OVER ("
         + "   ORDER BY P.PRODUCT_NUM DESC) AS ROW_NUM"
         + "   FROM PRODUCT_INFO_VIEW P"
         + "      WHERE P.PRODUCT_SELLER_ID=?"
         + "    ) AS SUBQUERY "
         + "WHERE"
         + "   ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
   // 상품 번호 출력
   private final String PRODUCT_SELECTALL_CRAWLING = "SELECT PRODUCT_NUM FROM PRODUCT";
   // 상품 전체 개수
   private final String PRODUCT_SELECTALL_TOTAL_CNT = "SELECT PRODUCT_CATEGORY, COUNT(PRODUCT_NUM) AS PRODUCT_TOTAL_CNT "
         + "FROM PRODUCT " + "GROUP BY PRODUCT_CATEGORY";
   // 11.05 취합
   // 상품 총 개수
   private final String PRODUCT_TOTAL_COUNT = "SELECT COUNT(PRODUCT_NUM) AS PRODUCT_TOTAL_COUNT FROM PRODUCT";
   // 로케이션 별 상품 개수
   private final String PRODUCT_BY_LOCATION = "SELECT PRODUCT_LOCATION, COUNT(PRODUCT_NUM) AS PRODUCT_LOCATION_BY_CNT \r\n"
         + "FROM PRODUCT GROUP BY PRODUCT_LOCATION";
   // 로케이션의 카테고리별 개수
   private final String PRODUCT_LOCATION_BY_CATEGORY = "SELECT PRODUCT_LOCATION, PRODUCT_CATEGORY, COUNT(PRODUCT_NUM) AS PRODUCT_LOCATION_BY_CATEGORY\r\n"
         + "FROM PRODUCT\r\n"
         + "GROUP BY PRODUCT_LOCATION, PRODUCT_CATEGORY";
   
   // 필터링 쿼리 정의 (인스턴스 변수)
   // 필터 검색
   private final String PRODUCT_SELECTALL_FILTER_SEARCH = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR "
         + "FROM ( " + "SELECT P.PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE, P.PRODUCT_DETAILS, "
         + "P.PRODUCT_CNT, P.PRODUCT_ADDRESS, P.PRODUCT_LOCATION, P.PRODUCT_CATEGORY, "
         + "IFNULL(I.FILE_DIR, '') AS FILE_DIR, "  // 이미지 파일 경로
         + "COALESCE(ROUND(AVG(R.REVIEW_STAR), 1), 0) AS RATING, " // 리뷰 별점 평균
         + "COALESCE(SUM(W.WISHLIST_COUNT), 0) AS WISHLIST_COUNT, "  // 위시리스트 수
         + "COALESCE(COUNT(PT.PAYMENT_NUM), 0) AS PAYMENT_COUNT, "  // 결제 수
         + "ROW_NUMBER() OVER (ORDER BY P.PRODUCT_NUM) AS ROW_NUM " // ROW_NUMBER
         + "FROM PRODUCT P "  // 상품 테이블
         + "LEFT JOIN (SELECT REVIEW_PRODUCT_NUM, AVG(REVIEW_STAR) AS REVIEW_STAR FROM REVIEW GROUP BY REVIEW_PRODUCT_NUM) R "// 리뷰 평균
         + "ON P.PRODUCT_NUM = R.REVIEW_PRODUCT_NUM "  // 상품 번호로 조인
         + "LEFT JOIN (SELECT WISHLIST_PRODUCT_NUM, COUNT(*) AS WISHLIST_COUNT FROM WISHLIST GROUP BY WISHLIST_PRODUCT_NUM) W "// 위시리스트 집계
         + "ON P.PRODUCT_NUM = W.WISHLIST_PRODUCT_NUM "  // 상품 번호로 조인
         + "LEFT JOIN PAYMENT PT ON P.PRODUCT_NUM = PT.PAYMENT_PRODUCT_NUM " // 결제 정보 조인
         + "LEFT JOIN ( " + "   SELECT I1.PRODUCT_ITEM_NUM, I1.FILE_DIR FROM imagefile I1 "
         + "   WHERE I1.FILE_NUM = ( " + "       SELECT I2.FILE_NUM FROM imagefile I2 "
         + "       WHERE I2.PRODUCT_ITEM_NUM = I1.PRODUCT_ITEM_NUM " + "       ORDER BY I2.FILE_NUM "  // 이미지 번호로 정렬
         + "       LIMIT 1 OFFSET 1" // 두 번째 이미지 선택
         + "   ) " + ") I ON P.PRODUCT_NUM = I.PRODUCT_ITEM_NUM "  // 두 번째 이미지 조인
         + "WHERE 1=1 "; // 기본 조건 (모든 결과 포함)
   // 동적쿼리
   // 위치 필터링 쿼리
   private final String TYPE_FILTER = "AND P.PRODUCT_LOCATION IN (%s) ";
   // 카테고리 필터링 쿼리
   private final String CATEGORY_FILTER = "AND P.PRODUCT_CATEGORY IN (%s) ";
   // 제품 이름 검색 쿼리
   private final String NAME_SEARCH_FILTER = "AND P.PRODUCT_NAME LIKE ? ";
   // GROUP BY 절에 필요한 필드
   private final String GROUP_BY = "GROUP BY P.PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE, P.PRODUCT_DETAILS, "
         + "P.PRODUCT_CNT, P.PRODUCT_ADDRESS, P.PRODUCT_LOCATION, P.PRODUCT_CATEGORY, " + "I.FILE_DIR ";
   // 페이지네이션을 위한 쿼리
   // 페이지네이션 : 페이지 개수
   private final String FILTER_PAGE = ") AS SUBQUERY WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public boolean insert(ProductDTO productDTO) { // 상품 등록
      int result;
      System.out.println("====model.ProductDAO2.insert 시작");
      System.out.println("====model.ProductDAO2.insert productDTO.getProduct_condition() : ["
            + productDTO.getProduct_condition() + "]");

      String product_name = productDTO.getProduct_name(); // 상품명
      int product_price = productDTO.getProduct_price(); // 가격
      String product_details = productDTO.getProduct_details(); // 설명
      int product_cnt = productDTO.getProduct_cnt(); // 상품 개수
      String product_address = productDTO.getProduct_address(); // 상품 주소
      String product_location = productDTO.getProduct_location(); // 상품 위치
      String product_category = productDTO.getProduct_category(); // 상품 카테고리
      String product_seller_id = productDTO.getProduct_seller_id(); // 판매자

      // 컨디션이 'PRODUCT_INSERT'라면 - 상품등록
      if (productDTO.getProduct_condition().equals("PRODUCT_INSERT")) {
         result = jdbcTemplate.update(PRODUCT_INSERT, product_name, product_price, product_details,
               product_address, product_location, product_category, product_seller_id);
      }
      // 컨디션이 'PRODUCT_CRAWLING_SEA_BOAR_INSERT' 라면 - 크롤링(샘플데이터) : 바다 - 낚시배
      else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_BOAR_INSERT")) {
         result = jdbcTemplate.update(PRODUCT_INSERT_CRAWLING_SEA_BOAT, product_name, product_price, product_details,
               product_cnt, product_address, product_seller_id);
      }
      // 컨디션이 'PRODUCT_CRAWLING_SEA_FISHING_INSERT' 라면 - 크롤링(샘플데이터) : 바다 - 낚시터
      else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_FISHING_INSERT")) {
         result = jdbcTemplate.update(PRODUCT_INSERT_CRAWLING_SEA_FISHING, product_name, product_price,
               product_address, product_seller_id);
      }
      // 컨디션이 'PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT' 라면 - 크롤링(샘플데이터) : 민물 - 수상
      else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT")) {
         result = jdbcTemplate.update(PRODUCT_INSERT_CRAWLING_FRESH_WATER_FISHING, product_name, product_price,
               product_cnt, product_address, product_seller_id);
      }
      // 컨디션이 'PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT' 라면 - 크롤링(샘플데이터) : 민물
      // - 낚시카페
      else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT")) {
         result = jdbcTemplate.update(PRODUCT_INSERT_CRAWLING_FRESH_WATER_FISHING_CAFE, product_name, product_price,
               product_address, product_seller_id);
      }
      // 해당하는 컨디션이 없다면,
      else {
         System.err.println("====model.ProductDAO2.insert 컨디션 에러");
         return false; // false를 반환한다.
      }

      System.out.println("====model.ProductDAO2.insert result : [" + result + "]");
      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.ProductDAO2.insert 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.ProductDAO2.insert 종료");
      return true; // 성공적으로 삽입 완료, true를 반환한다.
   }

   public boolean update(ProductDTO productDTO) { // 사장님 상품 변경
      int result;
      System.out.println("====model.ProductDAO2.update 시작");

      String product_name = productDTO.getProduct_name(); // 상품명
      int product_price = productDTO.getProduct_price(); // 상품 가격
      String product_details = productDTO.getProduct_details(); // 상품 설명
      String product_address = productDTO.getProduct_address(); // 상품 주소
      String product_location = productDTO.getProduct_location(); // 상품 위치
      String product_category = productDTO.getProduct_category(); // 상품 카테고리
      int product_num = productDTO.getProduct_num(); // 상품 번호
      String product_seller_id = productDTO.getProduct_seller_id(); // 판매자

      // 파라미터 값을 담아서 쿼리문을 수행한다.
      result = jdbcTemplate.update(PRODUCT_UPDATE, product_name, product_price, product_details, product_address,
            product_location, product_category, product_num, product_seller_id);
      System.out.println("====model.ProductDAO2.update result : [" + result + "]");

      if (result <= 0) { // 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.ProductDAO2.update 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.ProductDAO2.update 종료");
      return true; // 성공적으로 변경 완료, true를 반환한다.
   }

   public boolean delete(ProductDTO productDTO) { // 상품 삭제
      System.out.println("====model.ProductDAO2.delete 시작");

      int result = jdbcTemplate.update(PRODUCT_DELETE, productDTO.getProduct_num());
      System.out.println("====model.ProductDAO2.delete result : [" + result + "]");

      if (result <= 0) {// 반환된 값이 0보다 작거나 같다면
         System.err.println("====model.ProductDAO2.delete 실패");
         return false; // false를 반환한다.
      }
      System.out.println("====model.ProductDAO2.delete 종료");
      return true; // 성공적으로 삭제 완료, true를 반환한다.
   }

   public ProductDTO selectOne(ProductDTO productDTO) { // 한개 출력
      ProductDTO data;
      System.out.println("====model.ProductDAO2.selectOne 시작");
      System.out.println("====model.ProductDAO2.selectOne productDTO.getProduct_condition() : ["
            + productDTO.getProduct_condition() + "]");

      int product_num = productDTO.getProduct_num(); // 상품 번호
      Date reservation_date = productDTO.getProduct_reservation_date(); // 예약일
      String searchKeyword = productDTO.getProduct_searchKeyword(); // 검색어
      String product_location = productDTO.getProduct_location(); // 상품 위치
      String product_category = productDTO.getProduct_category(); // 상품 카테고리
      String seller_id = productDTO.getProduct_seller_id(); // 상품 판매자(사장님) ID


      System.out.println("MODEL SELECTONE 로그!!! SELLER_ID : ["+seller_id+"]");


      try {
         // 컨디션이 'PRODUCT_BY_INFO'라면 - 상품 상세보기
         if (productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) {
            Object[] args = { product_num };
            data = jdbcTemplate.queryForObject(PRODUCT_SELECTONE, args, new ProductRowMapper_one_by_info());
         }
         // 컨디션이 'PRODUCT_BY_CURRENT_STOCK'라면 - 사용자가 선택한 일자의 재고 보기
         else if (productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) {
            Object[] args = { reservation_date, product_num };
            data = jdbcTemplate.queryForObject(PRODUCT_SELECTONE_CURRENT_STOCK, args,
                  new ProductRowMapper_one_current_stock());
         }
         // 컨디션이 'PRODUCT_NUM_SELECT'라면 - 크롤링 상품번호 조회
         else if (productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) {
            data = jdbcTemplate.queryForObject(PRODUCT_SELECTONE_NUM, new ProductRowMapper_one_num());
         }
         // 컨디션이 'PRODUCT_SELECTONE_OWNER_TOTAL_PAGE'라면 - 사장님 본인 상품 목록을 확인하기 위한 전체 페이지 수 반환
         else if (productDTO.getProduct_condition().equals("PRODUCT_SELECTONE_OWNER_TOTAL_PAGE")) {
            Object[] args = { seller_id };
            data = jdbcTemplate.queryForObject(PRODUCT_SELECTONE_OWNER_TOTAL_PAGE, args, new ProductRowMapper_one_page_count());
         }
         //11.05 취합
         else if(productDTO.getProduct_condition().equals("PRODUCT_TOTAL_COUNT_VV")) {
            data = jdbcTemplate.queryForObject(PRODUCT_TOTAL_COUNT, new ProductRowMapper_total_cnt());
         }
         // 컨디션이 'PRODUCT_PAGE_COUNT'라면 - 페이지네이션에 사용하기 위해 전체 페이지 수 반환
         else if (productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) {
            // 만약 키워드가 있다면 - 전체 상품 총 페이지 개수
            if (productDTO.getProduct_searchKeyword() != null && !productDTO.getProduct_searchKeyword().isEmpty()) {
               Object[] args = { searchKeyword };
               data = jdbcTemplate.queryForObject(
                     PRODUCT_SELECTONE_TOTAL_PAGE + " " + PRODUCT_SELECTONE_SEARCH_PAGE, args,
                     new ProductRowMapper_one_page_count());
            }
            // 만약 유형과 카테고리가 있다면 - 유형과 카테고리별 상품 총 페이지 개수
            else if ((productDTO.getProduct_location() != null && !productDTO.getProduct_location().isEmpty())
                  || (productDTO.getProduct_category() != null && !productDTO.getProduct_category().isEmpty())) {
               Object[] args = { product_location, product_category };
               data = jdbcTemplate.queryForObject(
                     PRODUCT_SELECTONE_TOTAL_PAGE + " " + PRODUCT_SELECTONE_FILTERING_PAGE, args,
                     new ProductRowMapper_one_page_count());
            }
            // 키워드, 유형, 카테고리가 없다면 - 전체 상품 총 페이지 개수
            else {
               data = jdbcTemplate.queryForObject(PRODUCT_SELECTONE_TOTAL_PAGE,
                     new ProductRowMapper_one_page_count());
            }
         }
         // 해당하는 컨디션이 없다면,
         else {
            System.err.println("====model.ProductDAO2.selectOne 컨디션 에러");
            return null; // null을 반환한다.
         }
      } catch (Exception e) { // 예외가 발생한다면,
         System.err.println("====model.ProductDAO2.selectOne 예외 발생");
         return null; // null을 반환한다.
      }

      // 쿼리문을 수행한 데이터를 반환한다.
      System.out.println("====model.ProductDAO2.selectOne data : " + data);
      System.out.println("====model.ProductDAO2.selectOne 종료");
      return data;
   }

   public List<ProductDTO> selectAll(ProductDTO productDTO) { // 상품 전체 보기
      List<ProductDTO> datas = new ArrayList<>();
      System.out.println("model.ProductDAO2.selectAll 시작");
      System.out.println(
            "====model.selectAll productDAO2.getProduct_condition() : [" + productDTO.getProduct_condition() + "]");

      String search_criteria = productDTO.getProduct_search_criteria(); // 정렬
      String searchKeyword = productDTO.getProduct_searchKeyword(); // 검색어
      String product_location = productDTO.getProduct_location(); // 상품 위치
      String product_category = productDTO.getProduct_category(); // 상품 카테고리
      int page_num = productDTO.getProduct_page_num(); // 페이지 번호
      String product_seller_id = productDTO.getProduct_seller_id(); // 판매자

      // 컨디션이 없다면 - 전체 상품 보기
      if (productDTO.getProduct_condition() == null || productDTO.getProduct_condition().isEmpty()) {
         Object[] args = { search_criteria, search_criteria, search_criteria, search_criteria, searchKeyword,
               product_location, product_category, page_num, page_num };
         datas = jdbcTemplate.query(PRODUCT_SELECTALL, args, new ProductRowMapper_all());
      }
      // 컨디션이 'PRODUCT_SELECTALL_OWNER'라면 - 사장님 본인 상품 전체 보기
      else if (productDTO.getProduct_condition().equals("PRODUCT_SELECTALL_OWNER")) {
         Object[] args = { product_seller_id, page_num, page_num };
         datas = jdbcTemplate.query(PRODUCT_SELECTALL_OWNER,args, new ProductRowMapper_all());
      }
      // 컨디션이 'PRODUCT_SELECTALL_CRAWLING'라면 - 크롤링을 위한 전체 번호 보기
      else if (productDTO.getProduct_condition().equals("PRODUCT_SELECTALL_CRAWLING")) {
         datas = jdbcTemplate.query(PRODUCT_SELECTALL_CRAWLING, new ProductRowMapper_all_crawling());
      }
      // 컨디션이 'PRODUCT_TOTAL_CNT'라면 - 상품 개수 보기
      else if (productDTO.getProduct_condition().equals("PRODUCT_TOTAL_CNT")) {
         datas = jdbcTemplate.query(PRODUCT_SELECTALL_TOTAL_CNT, new ProductRowMapper_one_product_cnt());
      }
      //11.05 취합
      // 컨디션이 "PRODUCT_BY_LOCATION"라면 - location별 상품 수
      else if(productDTO.getProduct_condition().equals("PRODUCT_BY_LOCATION")) {
         datas = jdbcTemplate.query(PRODUCT_BY_LOCATION, new ProductRowMapper_location_by_cnt());
      }
      //11.05 취합
      // 컨디션이 "PRODUCT_LOCATION_BY_CATEGORY"라면 - location의 카테고리 별 상품 수
      else if(productDTO.getProduct_condition().equals("PRODUCT_LOCATION_BY_CATEGORY")) {
         datas = jdbcTemplate.query(PRODUCT_LOCATION_BY_CATEGORY, new ProductRowMapper_location_by_category_cnt());
      }
      // 컨디션이 'PRODUCT_SELECTALL_SEARCH'라면 - 상품 개수 보기
      else if (productDTO.getProduct_condition().equals("PRODUCT_SELECTALL_SEARCH")) {
         System.out.println("model.ProductDAO.selectAll_PRODUCT_SELECTALL_SEARCH 시작");

         StringBuilder sql = new StringBuilder(PRODUCT_SELECTALL_FILTER_SEARCH); // 기본 쿼리 초기화
         List<Object> params = new ArrayList<>(); // 쿼리 파라미터를 저장할 리스트

         // 유형 필터링
         if (productDTO.getProduct_types() != null && !productDTO.getProduct_types().isEmpty()) {
            System.out.println("유형 필터링 조건 추가: " + productDTO.getProduct_types());
            String placeholders = String.join(", ", Collections.nCopies(productDTO.getProduct_types().size(), "?"));
            sql.append(String.format(TYPE_FILTER, placeholders));
            params.addAll(productDTO.getProduct_types());
         }

         // 카테고리 필터링
         if (productDTO.getProduct_categories() != null && !productDTO.getProduct_categories().isEmpty()) {
            System.out.println("카테고리 필터링 조건 추가: " + productDTO.getProduct_categories());
            String placeholders = String.join(", ",
                  Collections.nCopies(productDTO.getProduct_categories().size(), "?"));
            sql.append(String.format(CATEGORY_FILTER, placeholders));
            params.addAll(productDTO.getProduct_categories());
         } else {
            // 카테고리 필터가 비어 있을 경우 조건 추가를 생략
            System.out.println("카테고리 필터가 비어있어 조건을 추가하지 않습니다.");
         }

         // 검색 쿼리 필터링
         if (productDTO.getProduct_searchKeyword() != null && !productDTO.getProduct_searchKeyword().isEmpty()) {
            System.out.println("검색어 조건 추가: " + productDTO.getProduct_searchKeyword());
            sql.append(NAME_SEARCH_FILTER);
            params.add("%" + productDTO.getProduct_searchKeyword() + "%");
         }

         sql.append(GROUP_BY); // GROUP BY 절 추가

         // 페이지네이션
         int pageNum = productDTO.getProduct_page_num() > 0 ? productDTO.getProduct_page_num() : 1;
         int itemsPerPage = 9;
         sql.append(FILTER_PAGE);
         params.add(pageNum); // 시작 번호
         params.add(pageNum); // 끝 번호

         // 생성된 쿼리 출력
         System.out.println("Generated SQL: " + sql.toString());
         System.out.println("Parameters: " + params);

         if (productDTO.getProduct_total_page() == 0) {
            // 카운트 쿼리 초기화
            String countQuery = "SELECT COUNT(*) FROM PRODUCT P WHERE 1=1 "; // 기본 조건

            // 카운트 쿼리 조건 추가
            List<Object> countParams = new ArrayList<>(); // 카운트 파라미터 리스트 초기화

            // 만약 타입이 없다면, 동적쿼리를 수행한다.
            if (productDTO.getProduct_types() != null && !productDTO.getProduct_types().isEmpty()) {
               String placeholders = String.join(", ",
                     Collections.nCopies(productDTO.getProduct_types().size(), "?"));
               countQuery += String.format(TYPE_FILTER, placeholders);
               countParams.addAll(productDTO.getProduct_types());
            }
            // 만약 카테고리가 없다면, 동적쿼리를 수행한다.
            if (productDTO.getProduct_categories() != null && !productDTO.getProduct_categories().isEmpty()) {
               String placeholders = String.join(", ",
                     Collections.nCopies(productDTO.getProduct_categories().size(), "?"));
               countQuery += String.format(CATEGORY_FILTER, placeholders);
               countParams.addAll(productDTO.getProduct_categories());
            }
            // 검색어가 없다면, 동적쿼리를 수행한다.
            if (productDTO.getProduct_searchKeyword() != null && !productDTO.getProduct_searchKeyword().isEmpty()) {
               countQuery += NAME_SEARCH_FILTER;
               countParams.add("%" + productDTO.getProduct_searchKeyword() + "%");
            }

            System.out.println("Count Query: " + countQuery);
            System.out.println("Count Parameters: " + countParams);

            // 카운트 쿼리 실행 및 결과 반환한다.
            int totalCount = 0;
            try {
               totalCount = jdbcTemplate.queryForObject(countQuery, Integer.class, countParams.toArray());
            } catch (Exception e) {
               System.err.println("카운트 쿼리 실행 중 오류 발생: " + e.getMessage());
               return null; // null을 반환한다.
            }

            // 총 페이지 수 계산한다.
            int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);

            // productDTO에 값 설정한다.
            productDTO.setProduct_total_page(totalPages); // 총 페이지 수 설정

         }

         // 실제 쿼리 실행
         try {
            datas = jdbcTemplate.query(sql.toString(), new ProductRowMapper_all(), params.toArray());
         } catch (Exception e) {
            System.err.println("쿼리 실행 중 오류 발생: " + e.getMessage());
            return null; // 빈 리스트 반환
         }

         System.out.println("model.ProductDAO.selectAll 결과: " + datas);
      }
      return datas; // 최종 결과 반환
   }
}

//======================================================RowMapper=================================================================
// 전체 상품 목록의 데이터를 반환하는 RowMapper
class ProductRowMapper_all implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.ProductDAO2.ProductRowMapper_all 실행");

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

      System.out.println("====model.ProductDAO2.ProductRowMapper_all 반환");
      return data;
   }
}


// 크롤링을 위한 전체 상품 번호를 반환하는 RowMapper
class ProductRowMapper_all_crawling implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====model.ProductDAO2.ProductRowMapper_all_crawling 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호

      System.out.println("====model.ProductDAO2.ProductRowMapper_all_crawling 반환");

      return data;
   }
}
// 11.05 취합
class ProductRowMapper_location_by_cnt implements RowMapper<ProductDTO>{

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      ProductDTO data = new ProductDTO();

      data.setProduct_location(rs.getString("PRODUCT_LOCATION"));
      data.setProduct_location_by_cnt(rs.getInt("PRODUCT_LOCATION_BY_CNT"));
      return data;
   }
}
// 11.05 취합
class ProductRowMapper_location_by_category_cnt implements RowMapper<ProductDTO>{

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      ProductDTO data = new ProductDTO();
      
      data.setProduct_location(rs.getString("PRODUCT_LOCATION"));
      data.setProduct_category(rs.getString("PRODUCT_CATEGORY"));
      data.setProduct_location_by_category_cnt(rs.getInt("PRODUCT_LOCATION_BY_CATEGORY"));
      return data;
   }
}
// 상품 상세보기 정보를 반환하는 RowMapper
class ProductRowMapper_one_by_info implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_one_by_info 실행");

      ProductDTO data = new ProductDTO();
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

      System.out.println("model.ProductDAO.ProductRowMapper_one_by_info 반환");
      return data;
   }
}

// 사용자가 선택한 일자의 재고를 반환하는 RowMapper
class ProductRowMapper_one_current_stock implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_one_current_stock 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
      data.setProduct_cnt(rs.getInt("CURRENT_STOCK")); // 상품의 재고

      System.out.println("model.ProductDAO.ProductRowMapper_one_current_stock 반환");
      return data;
   }
}

// 크롤링을 위해 가장 최근 등록된 상품의 pk를 반환하는 RowMapper
class ProductRowMapper_one_num implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_one_num 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_num(rs.getInt("MAX_NUM")); // 상품 번호

      System.out.println("model.ProductDAO.ProductRowMapper_one_num 반환");
      return data;
   }
}

// 상품 테이블의 전체 개수를 반환하는 RowMapper
class ProductRowMapper_one_page_count implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_one_page_count 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_total_page(rs.getInt("PRODUCT_TOTAL_PAGE")); // 상품 개수

      System.out.println("model.ProductDAO.ProductRowMapper_one_page_count 반환");
      return data;
   }
}

// 상품 총 개수를 반환하는 RowMapper
class ProductRowMapper_one_product_cnt implements RowMapper<ProductDTO> {

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_one_product_cnt 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 카테고리
      data.setProduct_total_cnt(rs.getInt("PRODUCT_TOTAL_CNT")); // 상품 개수

      System.out.println("model.ProductDAO.ProductRowMapper_one_product_cnt 반환");
      return data;
   }
}
class ProductRowMapper_total_cnt implements RowMapper<ProductDTO>{

   @Override
   public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("model.ProductDAO.ProductRowMapper_total_cnt 실행");

      ProductDTO data = new ProductDTO();
      data.setProduct_total_cnt(rs.getInt("PRODUCT_TOTAL_COUNT"));
      return data;
   }
}