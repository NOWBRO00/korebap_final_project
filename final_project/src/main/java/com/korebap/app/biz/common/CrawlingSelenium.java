package com.korebap.app.biz.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.biz.product.ProductDTO;

@Component
public class CrawlingSelenium {
	//WebDriver 인스턴스를 정의
	private static WebDriver driver;
	
	@Autowired
	private MemberService memberService;
	
	public CrawlingSelenium() { // 셀레니움 옵션 설정
		// 크롬 옵션 설정
		ChromeOptions options = new ChromeOptions();
		//헤드리스 모드 추가 (코드 실행시 크롬창이 뜨지않게 함)
		options.addArguments("--headless");
		//팝업창 제거 옵션 추가
		options.addArguments("--disable-popup-blocking");
		//GPU 가속 비활성화
		options.addArguments("--disable-gpu");
		//샌드박스 비활성화
		options.addArguments("--no-sandbox");

		// 옵션설정한 ChromeDriver 인스턴스 생성
		driver = new ChromeDriver(options);
	}

	public void CrawlingSeleniumDown() {// 웹 드라이버 종료
		// WebDriver를 종료 메서드
		if (driver != null) {
			// 드라이버 브라우저 연결 끊기
			System.out.println("com.korebap.app.biz.common.CrawlingSelenium 종료");
			driver.quit();
			// 브라우처 창 닫기
			//driver.close()
		}
	}
	public WebDriver getDriver() { // 웹 드라이버 사용
		// WebDriver 사용 
		return driver;
	}

	//-----------------------------------------------------------------------
	public List<ProductDTO> makeSampleProductSeaBoat() { // 바다 낚시배
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaBoat 시작");

		// 요소 및 변수 설정

		// 쿠키 팝업 닫기 요소
		final String COOKIE_ELEMENT = "body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close";
		// 바다 페이지 요소
		final String PRODUCT_SEA_ELEMENT = "div.header_area > header > section > div > a:nth-child(2)";
		// 상품 찾기 요소
		final String PRODUCT_ELEMENT_LIST = "#category_list > a:";

		// 상품 세부 요소 모음    
		// 남은 인원
		final String PRODUCT_PEOPLE_ELEMENT = "a > div > span";
		// 가격
		final String PRODUCT_PRICE_ELEMENT = "a > div > div.reserve_price > p";
		// 상품명
		final String PRODUCT_NAME_ELEMENT = "div.profile_info > dl > dt > h1";
		// 이용정보 클릭 요소
		final String PRODUCT_DETAILS_ELEMENT = "div.view_tab_area > section > ul > li:nth-child(3)";
		// 주소
		final String PRODUCT_ADDRESS_ELEMENT = "#view_info > section:nth-child(1) > div.view_box.view_map > div.map_link > h2 > a";
		// 내용
		final String PRODUCT_DETAILS_CONTENT_ELEMENT = "#view_info > section:nth-child(1) > div:nth-child(2) > div > h2";
		// 이미지
		final String PRODUCT_IMGS_ELEMENT = "section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper";
		// 주소 설정(메인 페이지)
		String URL = "https://www.moolban.com/";
		//---------------------------------------------------------------------------------------------------	
		//상품 샘플을 저장할 ArrayList 생성
		List<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 웹 페이지 접속
		driver.get(URL);
		System.out.println("makeSampleProductSeaBoat 로그 url :["+URL+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COOKIE_ELEMENT)));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		//cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행

		// 상품 카테고리
		System.out.println("makeSampleProductSeaBoat 로그: 상품 카테고리 클릭 시작");
		// 바다 페이지
		WebElement product_sea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_SEA_ELEMENT)));
		// 클릭
		product_sea.click();

		// 상품 요소 저장 변수
		WebElement product_element = null;
		// 상품 링크를 저장 변수
		String href="";
		for(int i = 2; i < 8; i++) {           
			try {
				for(int a=2; a<=i;a++) {
					try {
						// 상품 요소 찾기
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_ELEMENT_LIST+"nth-child("+a+")")));
						// 상품 링크를 저장 변수
						href = product_element.getAttribute("href"); // 링크 href 추출

						// 상품 링크 확인
						System.out.println("makeSampleProductSeaBoat 로그 상품 링크 :["+href+"]");
					}catch (Exception e) {
						System.out.println("makeSampleProductSeaBoat 로그 : 상품 요소를 찾지 못함.");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("makeSampleProductSeaBoat 상품 요소 :["+product_element+"]");
					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
					// executeScript() :  JavaScript 코드를 실행
					// arguments[0] : arguments 배열의 첫 번째 요소를 참조(product_element 배열의 첫 번째 요소로 전달)
					// scrollIntoView(true) : 요소가 화면에 표시되도록 스크롤
					// > true: 요소가 화면의 상단에 맞추어 스크롤/ false: 요소가 화면의 하단에 맞추어 스크롤
				}
				// 상품 페이지로 이동
				System.out.println("makeSampleProductSeaBoat 로그: 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();
				
				try {
					// 작성자 넣을 맴버 객체 생성
					MemberDTO memberDTO=new MemberDTO();
					//MemberDAO2 memberDAO2=new MemberDAO2();
					// 맴버의 pk인 작성자를 받아오기위해 select 한다.
					memberDTO.setMember_condition("RANDOM_MEMBER_OWNER_ID");
					memberDTO = memberService.selectOne(memberDTO);
					//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
					//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
					//MemberDTO randomMember = members.get(randomIndex);
					//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
					productDTO.setProduct_seller_id(memberDTO.getMember_id());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 :사장님 찾기 실패");
					productDTO.setProduct_seller_id("하윙@naver.com");
				}
				try {
					//남은 인원
					List<WebElement> people_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PEOPLE_ELEMENT)));
					System.out.println("makeSampleProductSeaBoat 로그:"+people_element +" : [" + people_element.size()+"]");
					//Selenium WebDriver를 사용 
					//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
					//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행

					// 남은 인원 객체에 저장
					System.out.println("makeSampleProductSeaBoat 로그: ["+people_element.get(0).getText()+"]");
					productDTO.setProduct_cnt(Integer.parseInt(people_element.get(0).getText().replace("남은수", "").replace("명", "")));;
					// Integer.parseInt : 문자열을 정수로 변환 메서드
					// get(0) : 리스트 첫 번째 요소
					// getText() : 요소의 택스트를 가져옴
					// replace() : 문자열 특정 부분을 다른 문자열로 교체 메서드
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 : 남은 인원 요소를 찾지 못함.");
					productDTO.setProduct_cnt(15);
				}

				// 가격
				try {
					List<WebElement> price_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PRICE_ELEMENT)));
					System.out.println("makeSampleProductSeaBoat 로그:"+price_element + " : [" + price_element.size()+"]");
					// 가격 객체에 저장
					System.out.println("makeSampleProductSeaBoat 로그: ["+price_element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_element.get(0).getText().replace(",", "").replace(" 원", "")));;
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 : 가격 요소를 찾지 못함.");
					productDTO.setProduct_price(10000);
				}

				// 상품명
				try {
					List<WebElement> name_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_NAME_ELEMENT)));
					System.out.println("makeSampleProductSeaBoat 로그:"+name_element +" : [" + name_element.size()+"]");

					// 상품명 객체에 저장
					System.out.println("makeSampleProductSeaBoat 로그: ["+name_element.get(0).getText()+"]");
					productDTO.setProduct_name(name_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 : 상품명 요소를 찾지 못함.");
					productDTO.setProduct_name("고래밥");
				}

				// 이용정보 클릭
				WebElement pruduct_details = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_DETAILS_ELEMENT)));
				// 클릭
				pruduct_details.click();

				// 상품 주소			
				try {
					List<WebElement> address_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_ADDRESS_ELEMENT)));
					System.out.println("makeSampleProductSeaBoat 로그: "+address_element + " : [" + address_element.size()+"]");
					// 상품 주소 객체에 저장
					System.out.println("makeSampleProductSeaBoat 로그: ["+address_element.get(0).getText()+"]");
					productDTO.setProduct_address(address_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 : 상품 주소 요소를 찾지 못함.");
					productDTO.setProduct_address("강원도 동해시 목포항");
				}

				// 상품 내용
				try {
					List<WebElement> details_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_DETAILS_CONTENT_ELEMENT)));
					System.out.println("makeSampleProductSeaBoat 로그: "+details_element + " : [" + details_element.size()+"]");

					// 상품 내용 객체에 저장
					System.out.println("makeSampleProductSeaBoat 로그: ["+details_element.get(0).getText()+"]");
					productDTO.setProduct_details(details_element.get(0).getText());}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 : 상품 내용 요소를 찾지 못함.");
					productDTO.setProduct_details("즐거운 낚시~!");
				}

				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector(PRODUCT_IMGS_ELEMENT);

				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_Selector = By.cssSelector("img");

				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_Container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));

				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_Container.findElements(image_Selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("makeSampleProductSeaBoat 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("makeSampleProductSeaBoat 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("makeSampleProductSeaBoat 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("297 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
				// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
				// 브라우저의 이전 페이지로 이동
			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("makeSampleProductSeaBoat 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaBoat 종료");

		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;			
	}
	//-----------------------------------------------------------------------
	public List<ProductDTO> makeSampleProductSeaFishing() { // 바다 낚시터
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaFishing 시작");
		//요소 모음

		// 바다 페이지
		final String PUDUCT_SEA_ELEMENT="div.wrap_area > div.header_area > header > section > div > a:nth-child(2)";	
		// 낚시터 페이지
		final String PRODUCT_SEA_FISHING_ELEMENT = "/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a";
		// 상품 요소 찾기
		final String PRODUCT_ELEMENT_LIST="#category_list > a:";
		// 가격
		final String PRODUCT_PRICE_ELEMENT = "a > div > dl:nth-child(1) > dt > strong";
		// 상품명
		final String PRODUCT_NAME_ELEMENT = "div.profile_info > dl > dt > h1";
		// 주소
		final String PRODUCT_ADDRESS_ELEMENT = "#view_goods > section:nth-child(2) > div.view_box.view_map > div.map_link > h2 > a";
		// 이미지
		final String PRODUCT_IMGS_SELECTOR = "section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper";
		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		//---------------------------------------------------------------------------------------------------	
		// 상품 샘플을 저장할 ArrayList 생성
		List<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성

		// 웹 페이지 접속
		driver.get(url);
		System.out.println("makeSampleProductSeaFishing 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));

		// 쿠키 클릭
		//cookie.click();

		// 상품 카테고리
		System.out.println("makeSampleProductSeaFishing 로그: 상품 카테고리 클릭 시작");
		// 바다 페이지 
		WebElement pruduct_sea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PUDUCT_SEA_ELEMENT)));
		// 클릭
		pruduct_sea.click();
		System.out.println("makeSampleProductSeaFishing 로그: 낚시터 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} 
		catch (InterruptedException e) {
			System.err.println("makeSampleProductSeaFishing 로그: 로딩 실패");
			e.printStackTrace();
		}
		// 낚시터 클릭
		WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_SEA_FISHING_ELEMENT)));
		System.out.println("makeSampleProductSeaFishing 로그 낚시터 요소: ["+pruduct_sea_fishing+"]");
		// 클릭
		pruduct_sea_fishing.click();
		System.out.println("makeSampleProductSeaFishing 로그 낚시터: ["+pruduct_sea_fishing.getText()+"]");
		System.out.println("makeSampleProductSeaFishing 로그 : 낚시터 검색완료");

		// 상품 요소 저장 변수
		WebElement product_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for(int i = 14; i < 20; i++) {           
			try {
				for(int a=14; a<=i;a++) {
					try {
						// 상품 요소 찾기
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_ELEMENT_LIST+"nth-child("+a+")"))); 
						href = product_element.getAttribute("href"); // 링크 href 추출
						// 상품 링크 확인
						System.out.println("makeSampleProductSeaFishing 상품 링크 :"+href);
					}
					catch (Exception e) {
						System.out.println("makeSampleProductSeaFishing 로그 : 요소 찾기 실패");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("makeSampleProductSeaFishing 상품 요소 :["+product_element+"]");
					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
				}
				// 상품 페이지로 이동
				System.out.println("makeSampleProductSeaFishing 로그: 상품 페이지 이동");
				driver.get(href);

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();
				
				try {
					// 작성자 넣을 맴버 객체 생성
					MemberDTO memberDTO=new MemberDTO();
					

					// 맴버의 pk인 작성자를 받아오기위해 select 한다.
					memberDTO.setMember_condition("RANDOM_MEMBER_OWNER_ID");
					memberDTO = memberService.selectOne(memberDTO);
					//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
					//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
					//MemberDTO randomMember = members.get(randomIndex);
					//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
					productDTO.setProduct_seller_id(memberDTO.getMember_id());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 :사장님 찾기 실패");
					productDTO.setProduct_seller_id("하윙@naver.com");
				}
				// 가격
				try {
					List<WebElement> price_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PRICE_ELEMENT)));
					System.out.println("makeSampleProductSeaFishing 로그 가격 요소: "+price_element + " : [" + price_element.size()+"]");
					// 가격 설정
					System.out.println("makeSampleProductSeaFishing 로그 가격: ["+price_element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_element.get(0).getText().replace(",", "").replace("원전화예약","")));;
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaFishing 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(25000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				try {
					List<WebElement> name_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_NAME_ELEMENT))); //body > div.wrap_area > div.view_area.container > section > div > div.profile_info > dl > dt > h1
					System.out.println("makeSampleProductSeaFishing 로그 상품명 요소: "+name_element +" : [" + name_element.size()+"]");
					// 상품명 설정
					System.out.println("makeSampleProductSeaFishing 로그 상품명:["+name_element.get(0).getText()+"]");
					productDTO.setProduct_name(name_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaFishing 로그 :상품명 요소 찾기 실패");
					productDTO.setProduct_name("고래밥");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				//"#view_goods > section:nth-child(2) > div.view_box.view_map > div.map_link > h2 > a"
				try {	
					List<WebElement> address_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_ADDRESS_ELEMENT)));
					//WebElement element =address_Element.get(0);
					System.out.println("makeSampleProductSeaFishing 로그 주소 요소: "+address_element + " : [" + address_element.size()+"]");
					// 주소 설정
					System.out.println("makeSampleProductSeaFishing 로그 주소: ["+address_element.get(0).getText()+"]");
					productDTO.setProduct_address(address_element.get(0).getText());		
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaFishing 로그 :상품 주소 요소 찾기 실패");
					productDTO.setProduct_address("인천 부둣가");	// 요소를 찾지 못하면 계속 진행
				}

				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector(PRODUCT_IMGS_SELECTOR);
				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_selector = By.cssSelector("img");
				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));
				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_container.findElements(image_selector);
				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("makeSampleProductSeaFishing 로그: 상품 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("makeSampleProductSeaFishing 로그: 상품 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("makeSampleProductSeaFishing 로그: 상품 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				product.add(productDTO); // 배열리스트에 객체 추가
				driver.navigate().back(); // 브라우저의 이전 페이지로 이동 

			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("makeSampleProductSeaFishing 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaFishing 종료");

		//드라이버 종료
		//driver.quit();
		return product;			
	}
	//-----------------------------------------------------------------------	
	public List<ProductDTO> makeSampleProductFreshWaterFishing() { // 민물 낚시터
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWater 시작");
		// 요소 모음
		//민물 페이지
		final String PRODUCT_FRESH_WATER_ELEMENT = "div.wrap_area > div.header_area > header > section > div > a:nth-child(3)";
		// 수상 페이지
		final String PRODUCT_FRESHWATER_FISHING_ELEMENT = "/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a";
		// 상품 요소 찾기
		final String PRODUCT_ELEMENT = "#category_list > a:";
		// 남은 인원
		final String PRODUCT_PEOPLE_ELEMENT = "a:nth-child(1) > div > span";
		// 가격
		final String PRODUCT_PRICE_ELEMENT = "a:nth-child(1) > div > div.reserve_price > p";
		// 상품명
		final String PRODUCT_NAME_ELEMENT_SELECTOR = "div.profile_info > dl > dt > h1";
		// 상품 주소
		final String PRODUCT_ADDRESS_ELEMENT= "section > div > div.profile_info > dl > dd:nth-child(2) > h2";
		//이미지
		final String PRODUCT_IMGS="section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper";
		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		//---------------------------------------------------------------------------------------------------	
		//상품 샘플을 저장할 ArrayList 생성
		List<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성

		// 웹 페이지 접속
		driver.get(url);
		System.out.println("makeSampleProductFreshWaterFishing 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));

		// 쿠키 클릭
		//cookie.click();

		// 상품 카테고리
		System.out.println("makeSampleProductFreshWaterFishing 로그: 상품 카테고리 클릭 시작");
		// 민물 페이지
		WebElement product_fresh_water = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_FRESH_WATER_ELEMENT)));
		// 클릭
		product_fresh_water.click();

		System.out.println("makeSampleProductFreshWaterFishing 로그: 수상 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} catch (InterruptedException e) {
			System.err.println("makeSampleProductFreshWaterFishing 로그 : 로딩 실패");
			e.printStackTrace();
		}

		// 수상 클릭
		WebElement pruduct_freshwater_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_FRESHWATER_FISHING_ELEMENT)));
		System.out.println("makeSampleProductFreshWaterFishing 로그 수상 요소:["+pruduct_freshwater_fishing+"]");
		// 클릭
		pruduct_freshwater_fishing.click();	
		System.out.println("makeSampleProductFreshWaterFishing 로그 수상 요소명: ["+pruduct_freshwater_fishing.getText()+"]");
		System.out.println("makeSampleProductFreshWaterFishing 로그 : 낚시터 검색완료");

		// 상품 요소 저장 변수
		WebElement pruduct_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for(int i = 4; i < 10; i++) {           
			try {
				for(int a=4; a<=i;a++) {
					try {
						// 상품 요소 찾기
						pruduct_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_ELEMENT+"nth-child("+a+")")));
						href = pruduct_element.getAttribute("href"); // 링크 href 추출

						// 상품 링크 확인
						System.out.println("makeSampleProductFreshWaterFishing 로그 상품 링크 : ["+href+"]");

					}catch (Exception e) {
						System.err.println("makeSampleProductFreshWaterFishing 로그 상품 요소를 찾지 못함.");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("makeSampleProductFreshWaterFishing 로그 상품 요소 : ["+pruduct_element+"]");
					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", pruduct_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
				}
				// 상품 페이지로 이동
				System.out.println("makeSampleProductFreshWaterFishing 로그: 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();
				
				try {
					// 작성자 넣을 맴버 객체 생성
					MemberDTO memberDTO=new MemberDTO();
					

					// 맴버의 pk인 작성자를 받아오기위해 select 한다.
					memberDTO.setMember_condition("RANDOM_MEMBER_OWNER_ID");
					memberDTO = memberService.selectOne(memberDTO);
					//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
					//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
					//MemberDTO randomMember = members.get(randomIndex);
					//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
					productDTO.setProduct_seller_id(memberDTO.getMember_id());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 :사장님 찾기 실패");
					productDTO.setProduct_seller_id("하윙@naver.com");
				}
				
				// 남은 인원 요소 
				try {
					List<WebElement> people_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PEOPLE_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishing 로그 남은인원 요소:"+people_element +" : [" + people_element.size()+"]");
					//Selenium WebDriver를 사용 
					//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
					//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행

					// 남은 인원 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishing 로그 남은 인원:["+people_element.get(0).getText()+"]");
					productDTO.setProduct_cnt(Integer.parseInt(people_element.get(0).getText().replace("남은수", "").replace("동", "").replace("명","")));
				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishing 로그 :남은 인원 요소 찾기 실패");
					productDTO.setProduct_cnt(5);	// 요소를 찾지 못하면 계속 진행
				}
				// 가격
				try {
					List<WebElement> price_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PRICE_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishing 로그 가격 요소:"+price_element + " : [" + price_element.size()+"]");
					// 가격 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishing 로그 가격:["+price_element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_element.get(0).getText().replace(",", "").replace(" 원", "").replace("현장결제", "").replace("원","")));

				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishing 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(50000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				try {
					List<WebElement> name_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_NAME_ELEMENT_SELECTOR)));
					System.out.println("makeSampleProductFreshWaterFishing 로그 상품명 요소:"+name_element +" : [" + name_element.size()+"]");
					// 상품명 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishing 로그 상품명:["+name_element.get(0).getText()+"]");
					productDTO.setProduct_name(name_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishing 로그 :상품명 요소 찾기 실패");
					productDTO.setProduct_name("행복 낚시");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				try {
					List<WebElement> address_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_ADDRESS_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishing 로그 상품 주소 요소: "+address_element + " : [" + address_element.size()+"]");
					// 상품 주소 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishing 로그 상품 주소:["+address_element.get(0).getText()+"]");
					productDTO.setProduct_address(address_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishing 로그 :상품 주소 요소 찾기 실패");
					productDTO.setProduct_address("연안 부두");	// 요소를 찾지 못하면 계속 진행
				}
				//상품 이미지
				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector(PRODUCT_IMGS);
				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_selector = By.cssSelector("img");
				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));
				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_container.findElements(image_selector);
				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("makeSampleProductFreshWaterFishing 로그: 상품 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("makeSampleProductFreshWaterFishing 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("makeSampleProductFreshWaterFishing 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("109 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// 브라우저의 이전 페이지로 이동

			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("makeSampleProductFreshWaterFishing 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWater 종료");
		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;		
	}
	//-----------------------------------------------------------------------	
	public List<ProductDTO> makeSampleProductFreshWaterFishingFishingCafe() { // 낚시 카페
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWaterFishingFishingCafe 시작");
		// 요소 모음
		// 민물 페이지
		final String PRODUCT_FRESH_WATER_ELEMENT= "div.wrap_area > div.header_area > header > section > div > a:nth-child(3)";
		// 낚시 카페 
		final String PRODUCT_FRESHWATER_FISHING_ELEMENT = "/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[4]/a";
		// 상품 요소 찾기
		final String PRODUCT_ELEMENT_LIST = "#category_list > a:";
		// 이용정보 페이지
		final String PRODUCT_FRESH_UTILIZATION = "body > div.wrap_area > div.view_area.container > div.view_tab_area > section > ul > li:nth-child(3) > a";
		// 가격
		final String PRODUCT_PRICE_ELEMENT = "div.view_box.info_view_sty5 > table > tbody > tr:nth-child(2) > td:nth-child(1) > strong";
		// 상품명
		final String PRODUCT_NAME_ELEMENT = "section > div > div.profile_info > dl > dt > h1";
		// 상품 주소
		final String PRODUCT_ADDRESS_ELEMENT = "div.view_box.view_map > div.map_link > h2 > a";
		//이미지
		final String PRODUCT_IMGS="section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper";

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		//---------------------------------------------------------------------------------------------------	
		//상품 샘플을 저장할 ArrayList 생성
		List<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성


		// 웹 페이지 접속
		driver.get(url);
		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));

		// 쿠키 클릭
		//cookie.click();

		// 상품 카테고리
		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 상품 카테고리 클릭 시작");
		// 민물 페이지

		WebElement pruduct_fresh_water = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_FRESH_WATER_ELEMENT)));
		// 클릭
		pruduct_fresh_water.click();

		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 수상 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} catch (InterruptedException e) {
			System.err.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 로딩 실패");
			e.printStackTrace();
		}

		// 낚시 카페 클릭

		WebElement product_freshwater_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_FRESHWATER_FISHING_ELEMENT)));
		//WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > div > div.list_header > div.list_menu_area > section > ol > li.cc_key_2 > a")));
		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 낚시 카페 요소:["+product_freshwater_fishing+"]");

		// 클릭
		//jsExecutor.executeScript("arguments[0].click()", pruduct_sea_fishing);
		product_freshwater_fishing.click();
		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 낚시 카페: ["+product_freshwater_fishing.getText()+"]");
		System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 낚시카페 검색완료");
		// 상품 요소 저장 변수
		WebElement product_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for (int i = 4; i < 10; i++) {   
			try {				
				for(int a=4; a<=i;a++) {
					try {
						// 상품 요소 찾기
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_ELEMENT_LIST+"nth-child("+a+")")));
						href = product_element.getAttribute("href"); // 링크 href 추출
						// 상품 링크 확인
						System.out.println("makeSampleProductFreshWaterFishingFishingCafe 상품 링크 :["+href+"]");
					}
					catch (Exception e) {
						System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 요소 찾기 실패");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 상품 요소 :["+product_element+"]");

					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
				}
				// 상품 페이지로 이동
				System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 이용정보 페이지 이동");
				// 이용정보 페이지
				WebElement pruduct_fresh_utilization = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_FRESH_UTILIZATION)));
				// 클릭
				pruduct_fresh_utilization.click();

				System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 이용정보 페이지 이동 완료");

				try { 
					Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
				} catch (InterruptedException e) {
					System.err.println("makeSampleProductFreshWaterFishingFishingCafe 로그 : 로딩 실패");
					e.printStackTrace();
				}

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();
				
				try {
					// 작성자 넣을 맴버 객체 생성
					MemberDTO memberDTO=new MemberDTO();
					

					// 맴버의 pk인 작성자를 받아오기위해 select 한다.
					memberDTO.setMember_condition("RANDOM_MEMBER_OWNER_ID");
					memberDTO = memberService.selectOne(memberDTO);
					//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
					//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
					//MemberDTO randomMember = members.get(randomIndex);
					//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
					productDTO.setProduct_seller_id(memberDTO.getMember_id());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductSeaBoat 로그 :사장님 찾기 실패");
					productDTO.setProduct_seller_id("하윙@naver.com");
				}
				// 가격
				try {
					List<WebElement> price_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_PRICE_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 가격 요소:"+price_element + " : [" + price_element.size()+"]");
					// 가격 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 가격:["+price_element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_element.get(0).getText().replace(",", "").replace("원", "")));;

				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(50000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				try {
					List<WebElement> name_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_NAME_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 상품명 요소:"+name_element +" : [" + name_element.size()+"]");
					// 상품명 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 상품명:["+name_element.get(0).getText()+"]");
					productDTO.setProduct_name(name_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 :상품명 찾기 실패");
					productDTO.setProduct_name("고래밥");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				try {
					List<WebElement> address_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(PRODUCT_ADDRESS_ELEMENT)));
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 주소 요소: "+address_element + " : [" + address_element.size()+"]");

					// 상품 주소 객체에 저장
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 주소:["+address_element.get(0).getText()+"]");
					productDTO.setProduct_address(address_element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그 :상품명 찾기 실패");
					productDTO.setProduct_address("부산 앞바다");	// 요소를 찾지 못하면 계속 진행
				}
				//상품 이미지
				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector(PRODUCT_IMGS);
				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_selector = By.cssSelector("img");
				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));
				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_container.findElements(image_selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("makeSampleProductFreshWaterFishingFishingCafe 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("952 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// 브라우저의 이전 페이지로 이동
			}
			catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("makeSampleProductFreshWaterFishingFishingCafe 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWaterFishingFishingCafe 종료");

		//드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;			
	}
	//-----------------------------------------------------------------------	
	public List<BoardDTO> makeSampleBoard() { // 게시판
		System.out.println(" model.common.CrawlingSelenium.makeSampleBoard 시작");
		//요소 모음
		// 게시판 페이지
		final String NOTICE_BOARD_ELEMENTS = "div.header_area > header > section > div > a:nth-child(6)";
		// 자유게시판 클릭
		final String FREE_BOARD_ELEMENTS = "html/body/div[1]/div[39]/div/div[2]/div[1]/section/ul/li[3]/a";
		// 게시판 요소
		final String BOARD_ELEMENT = "div.talk_list > #talk_detail_list > div.talk_box_area > a.talk_view_btn";
		//제목
		final String BOARD_TITLE_ELEMENT = "div > div.detail_title > section > div > dl > dt";
		//내용 요소
		final String BOARD_CONTENT_ELEMENT = "section > div > div.manage_detail > p";
		// 이미지 저장
		final String BOARD_IMAGE_ELEMENT = "section > div > div.manage_detail img";

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		//---------------------------------------------------------------------------------------------------	
		//게시판 샘플을 저장할 ArrayList 생성
		List<BoardDTO> board = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성

		// 웹 페이지 접속
		driver.get(url);
		System.out.println("991 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));

		// 쿠키 클릭
		//cookie.click();

		// 게시판
		System.out.println("makeSampleBoard 로그: 게시판 클릭 시작");
		try {
			// 게시판 페이지			
			WebElement notice_board = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(NOTICE_BOARD_ELEMENTS)));
			// 클릭
			System.out.println("makeSampleBoard 로그 게시판 요소:["+notice_board+"]");
			notice_board.click();
			
			try { 
				Thread.sleep(4000); // 메뉴 선택간 로딩 시간 4초
			} catch (InterruptedException e) {
				System.err.println("makeSampleBoard 로그 : 게시판 로딩 실패");
				e.printStackTrace();
			}
			System.out.println("makeSampleBoard 로그: 자유 게시판 검색");
			
			// 자유게시판 클릭
			WebElement free_board = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FREE_BOARD_ELEMENTS)));
			System.out.println("makeSampleBoard 로그 자유게시판 요소:["+free_board+"]");
			free_board.click();// 클릭
			System.out.println("makeSampleBoard 로그 자유게시판 텍스트:["+free_board.getText()+"]");
			System.out.println("makeSampleBoard 로그 : 자유 게시판 검색 완료");

			try { 
				Thread.sleep(4000); // 메뉴 선택간 로딩 시간 4초
			} catch (InterruptedException e) {
				System.err.println("makeSampleBoard 로그 :자유게시판 로딩 실패");
				e.printStackTrace();
			}

			// 게시판들 요소 찾기 변수
			List<WebElement> board_elements = null;
			String href="";	
			for(int i = 1; i <= 10; i++) {   
				try {	
					//게시판 요소 찾기
					board_elements = driver.findElements(By.cssSelector(BOARD_ELEMENT));
					if (i >= board_elements.size()) {
						System.err.println("makeSampleBoard 로그 요소가 부족 인덱스 [" + i + "]에 접근 불가");
						break;
					}
					WebElement board_element = board_elements.get(i);
					href = board_element.getAttribute("href"); // 링크 href 추출

					// 게시판 링크 확인
					System.out.println("makeSampleBoard 로그 게시판 링크 :["+href+"]");

					//게시판 요소 확인
					System.out.println("makeSampleBoard 로그 게시판 요소 :"+board_elements);
					// 게시판 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", board_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행

					// 게시판 페이지로 이동
					System.out.println("makeSampleBoard 로그: 게시판 페이지 이동");
					System.out.println("여기!!!!!!!!!");
					driver.get(href);
					System.out.println("1!!!!!!!!!!여기!!!!!!!!!");
					//웹 브라우저를 특정 URL(href)로 이동

					// 객체에 저장
					BoardDTO boardDTO=new BoardDTO();

					//제목
					try {
						System.out.println("들엉옴!!");
						List<WebElement> board_title_element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(BOARD_TITLE_ELEMENT)));
						System.out.println("makeSampleBoard 로그 제목 요소 개수:"+board_title_element +" : [" + board_title_element.size()+"]");
						//Selenium WebDriver를 사용 
						//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
						//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행

						// 제목 객체에 저장
						System.out.println("makeSampleBoard 로그 제목 :["+board_title_element.get(0).getText()+"]");
						if(!board_title_element.isEmpty()) {
							boardDTO.setBoard_title(board_title_element.get(0).getText());
							// Integer.parseInt : 문자열을 정수로 변환 메서드
							// get(0) : 리스트 첫 번째 요소
							// getText() : 요소의 택스트를 가져옴
							// replace() : 문자열 특정 부분을 다른 문자열로 교체 메서드
						}				
					}
					catch (Exception e) {
						System.out.println("makeSampleBoard 로그 :제목 찾기 실패");
						boardDTO.setBoard_title("제목이 없습니다.");
					}
					try {
						// 작성자 넣을 맴버 객체 생성
						MemberDTO memberDTO=new MemberDTO();
						

						// 맴버의 pk인 작성자를 받아오기위해 select 한다.
						memberDTO.setMember_condition("RANDOM_MEMBER_ID");
						memberDTO = memberService.selectOne(memberDTO);
						//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
						//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
						//MemberDTO randomMember = members.get(randomIndex);
						//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
						boardDTO.setBoard_writer_id(memberDTO.getMember_id());
					}
					catch (Exception e) {
						System.out.println("makeSampleBoard 로그 :작성자 찾기 실패");
						boardDTO.setBoard_writer_id("MOOMOO@naver.com");
					}
					//내용 요소
					try {
						List<WebElement> board_content_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(BOARD_CONTENT_ELEMENT)));
						// 요소가 비어 있는 경우, 즉 `board_content_Element` 리스트가 빈 경우 `NoSuchElementException`을 발생.
						// 이는 요소가 예상대로 로드되지 않았음을 명시적으로 표시하기 위함.

						if (board_content_Element.isEmpty()) { // 요소가 없다면
							throw new NoSuchElementException("1177 로그: 요소를 찾지 못함");
							// throw: 특정 조건 예외 발생
							// NoSuchElementException : 요소를 찾지 못할때 발생하는 예외 클래스	
						}
						// 로드된 내용 요소들의 개수를 출력.
						System.out.println("makeSampleBoard 로그 내용 요소 개수: [" + board_content_Element.size()+"]");

						// 첫 번째 내용 요소의 텍스트를 출력.
						System.out.println("makeSampleBoard 로그 내용 : [" + board_content_Element.get(0).getText()+"]");

						// 첫 번째 내용 요소의 텍스트를 `data` 객체의 `board_content` 속성에 저장.
						boardDTO.setBoard_content(board_content_Element.get(0).getText());
					} 
					catch (NoSuchElementException e) {
						System.out.println("makeSampleBoard 로그 에러: [" + e.getMessage()+"]");
						boardDTO.setBoard_content("없는 내용입니다.");
					} catch (Exception e) {
						System.out.println("1196 로그 에러: [" + e.getMessage()+"]");
						boardDTO.setBoard_content("없는 내용입니다.");
					}
					// 이미지 저장					
					try {
						List<WebElement> board_imag_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(BOARD_IMAGE_ELEMENT)));
						System.out.println("makeSampleBoard 로그 이미지 요소 개수: [" + board_imag_Element.size()+"]");

						List<String> imageUrls = new ArrayList<>();
						// 이미지 저장
						for (WebElement img : board_imag_Element) {
							//image=new ImageFileDTO();
							// 이미지 요소 가져오기
							String imageUrl = img.getAttribute("src");
							System.out.println("makeSampleBoard 로그 이미지 src: ["+ imageUrl+"]");
							// 이미지src DTO 저장
							imageUrls.add(imageUrl);
							boardDTO.setUrl(imageUrls);                
						}	
					}
					catch (Exception e) { 
						// 이미지가 없는경우
						System.out.println("makeSampleBoard 로그:이미지 없음");
					}

					// 결과 추출
					//System.out.println("1302 결과"+data);
					board.add(boardDTO); //배열리스트에 객체 추가

					// 뒤로 가기
					driver.navigate().back();
					// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
					// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
					// 브라우저의 이전 페이지로 이동

					// 페이지가 완전히 로드될 때까지 대기
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BOARD_ELEMENT)));

				}catch (Exception e) {
					// 오류가 발생시 다음으로 넘어감
					System.err.println("makeSampleBoard 로그 오류 발생: [" +i+"]");
				}
			}
		}catch (Exception e) {
			System.err.println("makeSampleBoard 로그 게시판 클릭 오류 발생: [" + e.getMessage()+"]");
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleBoard 종료");
		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return board;			
	}

	//	메인 메서드로 테스트
	//		public static void main(String[] args) {
	//			CrawlingSelenium.makeSampleProductSeaBoat();
	//		}
}

