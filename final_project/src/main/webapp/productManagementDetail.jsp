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
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css/">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/flaticon.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />

<style>
.search-container { /* 검색 컨테이너 스타일 */
	text-align: center; /* 중앙 정렬 */
	margin-top: 50px; /* 위쪽 여백 */
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
/*모달*/
.container {
	margin-top: 30px;
}

.modal-content {
	font-family: 'Lora', serif;
}

.modal-title {
	font-size: 20px;
	font-weight: bold;
}

.modal-body {
	font-size: 18px;
}
</style>
</head>
<body>
	<!-- 샘플 데이터 설정 
   <%
//   Map<String, Object> product = new HashMap<>();
//   product.put("product_name", "샘플 상품");
/*
   product.put("product_num", 101);
   product.put("product_price", 10000);
   product.put("product_details", "이것은 샘플 상품 설명입니다.");
   product.put("product_cnt", 5);
   product.put("product_location", "민물");
   product.put("product_category", "낚시배");
   product.put("product_address", "서울시 강동구 강변로 123");

   request.setAttribute("product", product);
   */
%> -->

	<div class="container">
		<!-- 헤더 섹션 -->
		<c:import url="header.jsp"></c:import>
		<!-- 본문 내용 -->
		<div class="container">
			<!-- 상품 상세페이지 파일출력섹션 시작 -->
			<h2>${product.product_name}상세보기</h2>
			<section class="testimonial-section spad">
				<div class="pi-text"></div>
				<!-- 사장님이 넣은 파일 확인유무(사진) -->
				<c:if test="${not empty fileList}">
					<div class="col-lg-8 offset-lg-2">
						<div class="testimonial-slider owl-carousel">
							<!-- 데이터로부터 사장님이 넣은 사진 파일 반복 -->
							<c:forEach var="file" items="${fileList}">
								<div class="ts-item">
									<div class="bd-pic">
										<c:choose>
											<c:when test="${fn:startsWith(file.file_dir, 'http')}">
												<img src="${file.file_dir}" alt="상품 이미지입니다.">
											</c:when>
											<c:otherwise>
												<img src="img/product/${file.file_dir}"
													alt="상품설명을 위해 사장님이 넣은 사진파일입니다.">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</section>
			<p>설명: ${product.product_details}</p>
			<p>가격: ${product.product_price}</p>
			<p>장소: ${product.product_location}</p>
			<p>카테고리: ${product.product_category}</p>
			<p>주소: ${product.product_address}</p>

			<!-- 이미지 슬라이드 및 삭제 버튼 -->
			<div id="productImageCarousel" class="carousel slide"
				data-ride="carousel">
				<div class="carousel-inner">
					<c:forEach var="product_urls" items="${product.url}">
						<div
							class="carousel-item ${product_urls == product.url[0] ? 'active' : ''}">
							<img class="d-block w-100" src="${product_urls}"
								alt="Product Image">
							<!-- 각 이미지에 삭제 버튼 추가 (모달창 열기) -->
							<button type="button" class="btn btn-danger btn-sm"
								data-toggle="modal" data-target="#deleteImageModal"
								data-image="${product_urls}">이미지 삭제</button>
						</div>
					</c:forEach>
				</div>
			</div>

			<!-- 이미지 수정 및 추가 버튼 (모달창 열기)  -->
			<button type="button" class="btn btn-primary" data-toggle="modal"
				data-target="#addImageModal"
				data-product-num="${product.product_num}">이미지 추가</button>

			<!-- 상품 수정 및 삭제 -->
			<a href="ownerProductUpdate.do?product_num=${product.product_num}"
				class="btn btn-primary">수정</a>

			<form action="ownerProductDelete.do" method="POST"
				style="display: inline;">
				<input type="hidden" name="product_num"
					value="${product.product_num}">
				<button type="submit" class="btn btn-danger"
					onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
			</form>

			<a href="ownerProductList.do" class="btn btn-primary">목록으로 돌아가기</a>
		</div>
		
		<!-- 이미지 추가 모달  -->
	<div class="modal fade" id="addImageModal" tabindex="-1" role="dialog"
		aria-labelledby="addImageModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addImageModalLabel">이미지 추가</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="ownerProductImageAdd.do" method="POST"
						enctype="multipart/form-data">
						<div class="form-group">
							<label for="product_url">이미지 파일 선택</label> <input type="file"
								name="files" multiple class="form-control-file" required>
						</div>
						<input type="hidden" name="product_num" value="${product.product_num}">
						<button type="submit" class="btn btn-primary">이미지 추가</button>
					</form>
				</div>
			</div>
		</div>
	</div>
		
		
		
	</div>

	<!-- 이미지 삭제(수정) 모달 -->
	<div class="modal fade" id="deleteImageModal" tabindex="-1"
		role="dialog" aria-labelledby="deleteImageModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteImageModalLabel">이미지 삭제</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">이 이미지를 정말 삭제하시겠습니까?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger"
						id="confirmDeleteImage">삭제</button>
				</div>
			</div>
		</div>
	</div>



	<!-- JS 코드 -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/product.productManagementDetail.js"></script>

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
	<script src="js/main.js"></script>

</body>
</html>
