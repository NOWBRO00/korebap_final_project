package com.korebap.app.view.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.common.CrawlingSelenium;
import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component // Spring의 리스너 구동
public class SampleListener{

    @Autowired //의존성 주입 어노테이션
    private ProductService productService; // 상품 서비스

    @Autowired
    private BoardService boardService; // 게시판 서비스

    @Autowired
    private ImageFileService fileService; // 파일 서비스

    @Autowired
    private CrawlingSelenium crawling; // 크롤링 객체

    // Bean의 초기화 메서드: Bean이 생성된 후 자동으로 호출
    @PostConstruct
    public void start() {
        System.out.println("웹 서버 구동(실행)을 감지함");

        // 상품 데이터 처리
        processProducts();

        // 게시판 데이터 처리
        processBoards();

        System.out.println("웹 서버 구동(실행)을 완료함");
    }

    // 종료 메서드: Bean이 파괴되기전 호출되는 메서드
    @PreDestroy
    public void end() {
        System.out.println("서버 종료 감지, 자원 해제 중...");
        if (crawling != null) {
            crawling.CrawlingSeleniumDown(); // 크롤링 종료
            System.out.println("SampleListener: 크롤링 종료");
        }
        System.out.println("자원 해제 완료, 서버 종료");
    }

    // 상품 처리 메서드
    private void processProducts() {
        ProductDTO productDTO = new ProductDTO();// 객체 생성
        productDTO.setProduct_condition("PRODUCT_SELECTALL_CRAWLING"); //컨디션
        
        // 상품 데이터가 없을 경우 크롤링 수행
        if (productService.selectAll(productDTO).isEmpty()) {
            System.out.println("SampleListener.processProducts 로그: 상품 데이터가 없음. 크롤링 시작");

            // 각 상품 종류별 데이터 삽입 시작
            System.out.println("SampleListener.processProducts 로그: 바다 낚시배 데이터 삽입 시작");
            insertProducts(crawling.makeSampleProductSeaBoat(), "PRODUCT_CRAWLING_SEA_BOAR_INSERT");

            System.out.println("SampleListener.processProducts 로그: 바다 낚시터 데이터 삽입 시작");
            insertProducts(crawling.makeSampleProductSeaFishing(), "PRODUCT_CRAWLING_SEA_FISHING_INSERT");

            System.out.println("SampleListener.processProducts 로그: 민물 낚시터 데이터 삽입 시작");
            insertProducts(crawling.makeSampleProductFreshWaterFishing(), "PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT");

            System.out.println("SampleListener.processProducts 로그: 민물 낚시터 카페 데이터 삽입 시작");
            insertProducts(crawling.makeSampleProductFreshWaterFishingFishingCafe(), "PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT");

        } else {
            System.out.println("SampleListener.processProducts 로그: product 데이터 있음");
        }
    }

    // 상품 삽입 메서드
    private void insertProducts(List<ProductDTO> products, String condition) {
        System.out.println("SampleListener.insertProducts 로그: 상품 삽입 시작");
        for (ProductDTO data : products) {// 상품 리스트 만큼 반복
            System.out.println("SampleListener.insertProducts 로그: 제품 삽입 시작 - 상품명: [" + data.getProduct_name() + "]");
            data.setProduct_condition(condition);

            // 상품 삽입
            boolean isInserted = productService.insert(data);
            System.out.println("insert 종료");
           
            if (isInserted) {// insert가 true면
                data.setProduct_condition("PRODUCT_NUM_SELECT");
                ProductDTO product = productService.selectOne(data); // 상품 정보 조회

                System.out.println("product :"+product);
                System.out.println("product num"+product.getProduct_num());
                // 이미지 URL 처리
                if (data.getUrl() != null && !data.getUrl().isEmpty()) { //이미지가 있으면
                    for (String imageUrl : data.getUrl()) { // 배열만큼 반복
                        ImageFileDTO imageFileDTO = new ImageFileDTO();
                        imageFileDTO.setFile_condition("PRODUCT_FILE_INSERT");
                        imageFileDTO.setFile_product_num(product.getProduct_num()); // 상품 번호
                        imageFileDTO.setFile_dir(imageUrl); // 이미지 url

                        // 이미지 DB에 삽입
                        fileService.insert(imageFileDTO);
                        System.out.println("SampleListener.insertProducts 로그: 이미지 삽입 완료");
                    }
                }
            } else {
                System.out.println("SampleListener.insertProducts 로그: 상품 삽입 실패 - 제품명: [" + data.getProduct_name() + "]");
            }
        }
    }

    // 게시판 처리 메서드
    private void processBoards() {
        System.out.println("SampleListener.processBoards 로그: 게시판 데이터 처리 시작");
        BoardDTO boardDTO = new BoardDTO();//객체 생성
        boardDTO.setBoard_condition("BOARD_SELECTALL_CRAWLING");
    
        if (boardService.selectAll(boardDTO).isEmpty()) { // 게시판 데이터가 없을 경우
            List<BoardDTO> boards = crawling.makeSampleBoard(); // 게시판 샘플 데이터 생성
            insertBoards(boards); // 게시판 데이터 삽입
            System.out.println("SampleListener.processBoards 로그: 게시판 데이터 처리 완료");
        } else {
            System.out.println("SampleListener.processBoards 로그: board 데이터 있음");
        }
    }

    // 게시판 삽입 메서드
    private void insertBoards(List<BoardDTO> boards) {
        for (BoardDTO data : boards) {//배열만큼 반복
            System.out.println("SampleListener.insertBoards 로그: 게시판 삽입 시작 - 게시판 제목: " + data.getBoard_title());
//            data.setBoard_condition("BOARD_INSERT");

            // 게시판 삽입
            boolean isInserted = boardService.insert(data);
            if (isInserted) {
                System.out.println("SampleListener.insertBoards 로그: 게시판 삽입 완료 - 게시판 번호: " + data.getBoard_num());
                data.setBoard_condition("BOARD_NUM_SELECTONE");
                BoardDTO board = boardService.selectOne(data);

                if (data.getUrl() != null && !data.getUrl().isEmpty()) {
                    for (String imageUrl : data.getUrl()) { // 배열만큼 반복
                        ImageFileDTO imageFileDTO = new ImageFileDTO();
                        imageFileDTO.setFile_condition("BOARD_FILE_INSERT");
                        imageFileDTO.setFile_board_num(board.getBoard_num());// 게시판 번호
                        imageFileDTO.setFile_dir(imageUrl); // 이미지 url

                        // 이미지 DB에 삽입
                        fileService.insert(imageFileDTO);
                        System.out.println("SampleListener.insertBoards 로그: 이미지 삽입 완료");
                    }
                }
            } else {
                System.out.println("SampleListener.insertBoards 로그: 게시판 삽입 실패 - 게시판 제목: [" + data.getBoard_title() + "]");
            }
        }
    }
}
