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
<link rel="shortcut icon" href="img/favicon.png" />
<style>
.container {
	margin-top: 30px;
}

.product-img {
	max-width: 100%;
	height: auto;
	margin-bottom: 20px;
}

.details {
	text-align: left;
}

.details h3 {
	margin-bottom: 20px;
}

.details p {
	margin: 5px 0;
}

.btn {
	margin-top: 20px;
}
</style>
</head>
<body>

	<%
	/*
	// 샘플 데이터 설정
	Map<String, Object> reservation = new HashMap<>();
	reservation.put("reservation_product_file_dir", "https://via.placeholder.com/400");
	reservation.put("reservation_product_name", "상품 A");
	reservation.put("reservation_member_name", "홍길동");
	reservation.put("reservation_member_phone", "010-1234-5678");
	reservation.put("reservation_registration_date", "2024-11-10");
	reservation.put("booking_price", 30000);
	reservation.put("reservation_status", "예약완료");
	reservation.put("reservation_num", 101);
	request.setAttribute("reservation", reservation);
	*/
	%>

	<!-- 헤더 연결 -->
	<jsp:include page="header.jsp" />
	<br>
	<div class="container">
		<h2>예약 상세보기</h2>
		<hr>

		<div class="row">
			<div class="col-md-6">
				<!-- 상품 이미지 -->
				<img src="${reservation.product_file_dir}" alt="상품 이미지"
					class="product-img img-thumbnail">
			</div>

			<div class="col-md-6 details">
				<!-- 상품 정보 및 예약 정보 -->
				<h3>${reservation.reservation_product_name}</h3>
				<p>예약자: ${reservation.reservation_member_name}</p>
				<p>예약자 연락처: ${reservation.reservation_member_phone}</p>
				<p>예약 날짜: ${reservation.reservation_registration_date}</p>
				<p>가격: ${reservation.booking_price}원</p>
				<p>예약 상태: ${reservation.reservation_status}</p>

				<!-- 예약 취소 버튼 -->
				<c:if test="${reservation.reservation_status == '예약완료'}">
					<form action="ownerReservationCancle.do" method="POST">
						<input type="hidden" name="reservation_num"
							value="${reservation.reservation_num}">
						<button type="submit" class="btn btn-danger"
							onclick="return confirm('예약을 취소하시겠습니까?');">예약 취소</button>
					</form>
				</c:if>

				<!-- 뒤로가기 버튼 -->
				<a href="ownerReservationList.do" class="btn btn-outline-secondary">뒤로가기</a>
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
