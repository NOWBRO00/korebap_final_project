<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
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

<!-- jQuery 연결 -->
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<style>
.search-container { /* 검색 컨테이너 스타일 */
	display: flex;
	justify-content: flex-start; /* 부모 요소에서 왼쪽 정렬 */
	text-align: center; /* 중앙 정렬 */
	padding-left: 20px; /* 왼쪽 여백 조절 */
	max-width: 1200px; /* 페이지와 정렬 위치 동일하게 설정 */
	margin: 50px auto 0; /* 위쪽 여백 50px, 아래쪽 여백 0, 가운데 정렬 */
}

.sort-options {
	display: flex;
	gap: 10px; /* 각 정렬 버튼 사이에 간격 */
	align-items: center;
}

.search-box { /* 검색 박스 스타일 */
	display: flex; /* Flexbox 사용 */
	justify-content: center; /* 중앙 정렬 */
	align-items: center; /* 수직 중앙 정렬 */
}

.search-input { /* 검색 입력창 스타일 */
	padding: 10px; /* 패딩 */
	font-size: 16px; /* 글자 크기 */
	border: 2px solid #ccc; /* 테두리 */
	border-radius: 5px 0 0 5px; /* 테두리 반경 */
	width: 300px; /* 너비 */
	outline: none; /* 아웃라인 제거 */
}

.search-button { /* 검색 버튼 스타일 */
	padding: 10px 20px; /* 패딩 */
	font-size: 16px; /* 글자 크기 */
	border: 2px solid #ccc; /* 테두리 */
	border-left: none; /* 왼쪽 테두리 제거 */
	border-radius: 0 5px 5px 0; /* 테두리 반경 */
	background-color: #1F3BB3; /* 배경색 */
	color: white; /* 글자 색상 */
	cursor: pointer; /* 커서 포인터 변경 */
}

.search-button:hover { /* 검색 버튼에 마우스 오버 시 */
	background-color: #e0f7fa; /* 배경색 변경 */
}

.file-display { /* 파일 표시 영역 스타일 */
	position: relative; /* 상대 위치 */
	z-index: 1; /* z-인덱스 */
	background-color: #f8f8f8; /* 배경색 */
	border: 1px solid #ccc; /* 테두리 */
	border-radius: 4px; /* 테두리 반경 */
	padding: 10px; /* 패딩 */
	min-height: 30px; /* 최소 높이 */
	line-height: 30px; /* 줄 높이 */
	overflow: hidden; /* 오버플로우 숨김 */
}

.nice-select { /* Nice Select 스타일 */
	margin-right: 10px; /* 오른쪽 여백 */
}

.bi-text .b-tag:last-child {
	display: block;
	margin-bottom: 20px; /* 원하는 공백 크기로 설정 */
}
</style>
</head>
<body>
	<!-- 헤더 연결 -->
	<c:import url="header.jsp"></c:import>

	<input type="hidden" id="product_location"
		data-product_location="${product_location}"
		value="${product_location}">
	<input type="hidden" id="product_category"
		data-product_category="${product_category}"
		value="${product_category}">
	<input type="hidden" id="productList" data-productList="${productList}"
		value="${productList}">
	<input type="hidden" id="productTypes"
		data-product_types="${productTypes}" value="${productTypes}">
	<input type="hidden" id="productCategories"
		data-product_categories="${productCategories}"
		value="${productCategories}">
	<input type="hidden" id="product_searchKeyword"
		data-product_searchKeyword="${product_searchKeyword}"
		value="${product_searchKeyword}">
	
	<input type="hidden" id="product_total_page"
		data-product_page_count="${product_page_count}"
		value="${product_page_count}">
	<input type="hidden" id="currentPage"
		data-product-current-page="${currentPage}" value="${currentPage}">



	<!-- 상품 검색창 섹션 시작 -->
	<div class="search-container">
		<div class="search-box">
			<!-- 정렬 기준 버튼 -->
			<div class="sort-options">
				<label><input type="radio" name="searchOption"
					value="newest" checked> 최신순 </label> <label><input
					type="radio" name="searchOption" value="rating"> 별점순 </label> <label><input
					type="radio" name="searchOption" value="wish"> 찜 많은 순 </label> <label><input
					type="radio" name="searchOption" value="payment"> 결제 많은 순 </label>
			</div>
		</div>
	</div>
	<br>
	<!-- 상품 검색창 섹션 시작 -->

	<!-- 상품 목록 섹션 시작 -->
	<section class="blog-section blog-page spad">
		<div class="container">
			<div class="row">
				<!-- 현재 페이지 설정 -->
				<c:set var="currentPage"
					value="${param.currentPage != null ? param.currentPage : 1}" />

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
				<div class="row" id="productList">
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
											src="img/product/${product.product_file_dir}"
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
	<!-- 상품 목록 섹션 종료 -->

	<!-- 페이지네이션 섹션 시작 -->
	<div class="col-lg-12">
		<div class="pagination">
			<c:if test="${currentPage > 1}">
				<a href="#" class="page-link" data-page="${currentPage - 1}">이전</a>
			</c:if>
			<c:forEach var="i" begin="1" end="${product_page_count}">
				<c:if test="${i == currentPage}">
					<strong>${i}</strong>
				</c:if>
				<c:if test="${i != currentPage}">
					<a href="#" class="page-link" data-page="${i}">${i}</a>
				</c:if>
			</c:forEach>
			<c:if test="${currentPage < product_page_count}">
				<a href="#" class="page-link" data-page="${currentPage + 1}">다음</a>
			</c:if>
		</div>
	</div>
	<!-- 페이지네이션 섹션 종료 -->



	<!-- 푸터 연결 -->
	<c:import url="footer.jsp"></c:import>

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/product/productList.js"></script>
	<!--  <script src="js/main.js"></script>-->
</body>
</html>