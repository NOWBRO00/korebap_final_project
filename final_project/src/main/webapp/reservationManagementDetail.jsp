<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>예약 상세보기</title>

<!-- CSS 및 스타일 -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
input {
	border: none !important;
}

h5 {
	width: 120px;
	align-content: center !important;
}
</style>
</head>
<body>

	<!-- 헤더 연결 -->
	<jsp:include page="header.jsp" />
	<br>
	<div class="container">
		<div class="row">
			<div class="card">
				<br>

				<h2>예약 상세보기</h2>

				<hr>
				<div class="row g-0">
					<div class="col-md-4" style="width: 50rem;">
						<div class="card-body">
							<!-- 상품 이미지 -->
							<img
								src="img/product/${reservation.reservation_product_file_dir}"
								alt="상품 이미지" class="rounded mx-auto d-block"
								style="margin-left: 10px;">
						</div>
					</div>

					<div class="col-md-8" style="width: 100rem;">
						<div class="card-body">
							<!-- 상품 정보 및 예약 정보 -->
							<div class="input-group mb-3"
								style="display: flex; align-items: flex-end; height: 40px;">
								<h3>${reservation.reservation_product_name}</h3>
								<div class="bd-hero-text" style="margin-right: 3px;">
									<span>${reservation.reservation_status}</span>
								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<h5>예약자명</h5>
							<input type="text" class="form-control"
								value="${reservation.reservation_member_name}" readonly>
						</div>
						<div class="input-group mb-3">
							<h5>전화번호</h5>
							<input type="text" class="form-control"
								value="${reservation.reservation_member_phone}" readonly>
						</div>
						<div class="input-group mb-3">
							<h5>사용 예정일</h5>
							<input type="text" class="form-control"
								value="${reservation.reservation_registration_date}" readonly>
						</div>
						<div class="input-group mb-3">
							<h5>결제금액</h5>
							<input type="text" class="form-control"
								value="${reservation.reservation_price}" readonly>
						</div>

						<!-- 예약 취소 버튼 -->
						<c:if test="${reservation.reservation_status == '예약완료'}">
							<form action="ownerReservationCancle.do" method="POST">
								<input type="hidden" name="reservation_num"
									value="${reservation.reservation_num}">
								<button type="submit" class="btn btn-outline-secondary" style="width: 140px"
									onclick="return confirm('예약을 취소하시겠습니까?');">예약 취소</button>
							</form>
						</c:if>

						<!-- 뒤로가기 버튼 -->
						<a href="ownerReservationList.do"
							class="btn btn-outline-secondary">뒤로가기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<br>
	<br>
	<br>

	<!-- 푸터 연결 -->
	<jsp:include page="footer.jsp" />
	<!-- 부트스트랩 JS -->
	<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>
