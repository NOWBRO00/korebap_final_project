<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>고래밥</title>

<!-- Google Font -->


<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">
<!-- Css Styles -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/flaticon.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />

<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fecf06794382c055ec106573ef0aa278&libraries=services"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fecf06794382c055ec106573ef0aa278"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>
<style>
.bi-text .b-tag:last-child {
	display: block;
	margin-bottom: 20px; /* 원하는 공백 크기로 설정 */
}
</style>
</head>

<body>
	<c:import url="header.jsp"></c:import>

	<!-- Hero Section Begin -->
	<section class="hero-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<script type="text/javascript"
						src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fecf06794382c055ec106573ef0aa278"></script>

					<c:import url="kakaoMap.jsp"></c:import>

					<div>
						<h1 style="color: white;">고래밥</h1>
						<a href="./contact.html" class="primary-btn">Discover Now</a>
					</div>
				</div>
				<div class="col-xl-4 col-lg-5 offset-xl-2 offset-lg-1">
					<div class="booking-form">
						<h3>명소 검색</h3>
						<form action="#">
							<div class="check-date">
								<label for="date-in">예약 날짜</label> <input type="text"
									class="date-input" id="date-in"> <i
									class="icon_calendar"></i>
							</div>
							<div class="check-date">
								<label for="date-in">예약 인원</label><input type="number" min="1"
									id="date-in">
							</div>
							<div class="select-option">
								<label for="room">낚시장소</label> <select id="room">
									<option value="">선택없음</option>
									<option value="">바다</option>
									<option value="">민물</option>
								</select>
							</div>
							<div class="select-option">
								<label for="room">낚시유형</label> <select id="room">
									<option value="">선택없음</option>
									<option value="">낚시터</option>
									<option value="">낚시배</option>
								</select>
							</div>
							<button type="submit">예약하기</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="hero-slider owl-carousel">
			<div class="hs-item set-bg" data-setbg="img/common/common_1.png"></div>
			<div class="hs-item set-bg" data-setbg="img/common/common_3.png"></div>
		</div>
	</section>
	<!-- Hero Section End -->

	<!-- Services Section Begin -->
	<section class="services-section spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="section-title">
						<span>카테고리</span>
						<h2>메인 메뉴${product.product_address}</h2>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 col-sm-6">
					<a href="productListPage.do?product_location=바다">
						<div class="service-item">
							<i class="flaticon-017-sunbed"></i>
							<h4>바다</h4>
							<p>바다라는 키워드로 검색한 결과가 나오는 페이지</p>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-sm-6">
					<a href="productListPage.do?product_location=민물">
						<div class="service-item">
							<i class="flaticon-007-swimming-pool"></i>
							<h4>민물</h4>
							<p>민물이라는 키워드로 검색한 결과가 나오는 페이지</p>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-sm-6">
					<a href="productListPage.do?product_category=낚시터">
						<div class="service-item">
							<i class="flaticon-049-power-socket"></i>
							<h4>낚시터</h4>
							<p>낚시터라는 키워드로 검색한 결과가 나오는 페이지</p>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-sm-6">
					<a href="productListPage.do?product_category=낚시배">
						<div class="service-item">
							<i class="flaticon-030-bathtub"></i>
							<h4>낚시배</h4>
							<p>낚시배라는 키워드로 검색한 결과가 나오는 페이지</p>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-sm-6">
					<a href="myReservationListPage.do">
						<div class="service-item">
							<i class="flaticon-047-calendar"></i>
							<h4>내 예약</h4>
							<p>예약 페이지로 이동</p>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-sm-6">
					<a href="boardListPage.do">
						<div class="service-item">
							<i class="flaticon-038-five-stars"></i>
							<h4>게시판</h4>
							<p>게시판 페이지로 이동</p>
						</div>
					</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Services Section End -->

	<!-- Home Room Section Begin -->
	<section class="hp-room-section">
		<div class="container">
			<div class="hp-room-items">
				<div class="row">
					<!-- 검색한 상품이 없는 경우 -->
					<c:if test="${empty productList}">
						<!-- productList가 비어있는지 검사 -->
						<div class="col-md-4">
							<!-- 3열 그리드 -->
							<p>검색 결과가 없습니다.</p>
							<!-- 검색 결과가 없을 때 메시지 -->
						</div>
					</c:if>
					<!-- c:forEach를 사용하여 상품 항목 반복 시작 -->
					<c:forEach var="product" items="${productList}">
						<!-- productList를 반복 -->
						<div class="col-md-4">
							<!-- 3열 그리드 -->
							<c:if test="${not empty product.product_file_dir}">
								<c:choose>
									<c:when
										test="${fn:startsWith(product.product_file_dir, 'http')}">
										<img src="${product.product_file_dir}" alt="상품 이미지"
											class="blog-item set-bg">
									</c:when>
									<c:otherwise>
										<!-- 절대경로로 변경 -->
										<img
											src="http://localhost:8088/teem_project/img/product/${product.product_file_dir}"
											alt="상품 이미지" class="blog-item set-bg">
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${empty product.product_file_dir}">
								<img alt="상품사진입니다" class="blog-item set-bg"
									src="img/board/boardBasic.png">
							</c:if>
							<div class="bi-text">
								<!-- 상품 카테고리 -->
								<span class="b-tag">${product.product_location}</span> 🌊<span
									class="b-tag">${product.product_category}</span>
								<!-- 상품 별점 평균 -->
								<span>⭐${product.product_avg_rating}⭐</span>
								<!-- 상품명 및 링크 -->
								<!-- 상품의 PK값과 함께 이동 -->
								<h4>
									<a href="productDetail.do?product_num=${product.product_num}">${product.product_name}</a>
								</h4>
								<span class="b-tag">상품가격 : ${product.product_price}￦</span> <br>
								<br> <br>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
	<!-- Home Room Section End -->

	<!-- Blog Section Begin -->
	<section class="blog-section spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="section-title">
						<span>당신의 낚시 이야기를 들려주세요</span>
						<h2>게시판</h2>
					</div>
				</div>
			</div>
			<c:if test="${empty boardList}">
				<div>게시글 없음</div>
			</c:if>
			<!-- 게시글이 있는 경우 게시글 목록 출력 -->
			<div class="row" id="boardList">
				<c:forEach var="board" items="${boardList}">
					<div class="col-md-4">
						<!-- AJAX로 데이터를 받아서 이곳에 추가 -->
						<c:if test="${not empty board.board_file_dir}">
							<c:choose>
								<c:when test="${fn:startsWith(board.board_file_dir, 'http')}">
									<img src="${board.board_file_dir}" alt="게시글 이미지"
										class="blog-item set-bg">
								</c:when>
								<c:otherwise>
									<img src="img/board/${board.board_file_dir}" alt="게시글 이미지"
										class="blog-item set-bg">
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${empty board.board_file_dir}">
							<img src="img/board/boardBasic.png" alt="기본 이미지"
								class="blog-item set-bg">
						</c:if>
						<div class="blog-item scroll_counter" style="height: 120px;">
							<div class="bi-text"
								style="padding-left: 0px; bottom: 2px; position: static;">
								<span class="b-tag">자유게시판</span> <span class="b-tag">${board.board_like_cnt}</span>
							</div>
							<h4>
								<a class="text-dark"
									href="boardDetail.do?member_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a>
							</h4>
							<div class="b-time">
								<div class="text-dark">
									<i class="icon_clock_alt"></i>${board.board_registration_date}
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	<!-- Blog Section End -->

	<!-- 푸터 연결 -->
	<c:import url="footer.jsp"></c:import>

	<!-- Search model Begin -->
	<div class="search-model">
		<div class="h-100 d-flex align-items-center justify-content-center">
			<div class="search-close-switch">
				<i class="icon_close"></i>
			</div>
			<form class="search-model-form">
				<input type="text" id="search-input" placeholder="Search here.....">
			</form>
		</div>
	</div>
	<!-- Search model end -->

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>


	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/kakaoMap.js"></script>
</body>

</html>