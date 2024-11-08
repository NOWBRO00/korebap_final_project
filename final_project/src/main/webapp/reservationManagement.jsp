<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>예약 관리</title>

<!-- CSS 스타일 및 jQuery -->
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="shortcut icon" href="img/favicon.png" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>

<!-- 페이지 내 스타일 -->
<style type="text/css">
.card {
	margin-bottom: 20px;
}

.card img {
	width: 100%;
	height: auto;
	object-fit: cover;
	max-height: 250px;
}

.card-body {
	text-align: left;
}

.card-body h3 {
	font-size: 1.5rem;
	margin-bottom: 10px;
}

.card-body p {
	margin: 0;
}

.btn {
	margin-top: 10px;
	width: 120px;
}

.btn-danger {
	background-color: red;
	color: white;
}

.btn-outline-secondary {
	margin-top: 10px;
}

.container {
	margin-top: 20px;
}
</style>
</head>
<body>
 	<!-- 헤더를 포함하는 부분 -->
   <c:import url="header.jsp"></c:import>

	<!-- 예약 목록 표시 -->
	<div class="container text-center">
		<h2>예약 관리</h2>
		<hr>

		<!-- 예약 목록이 있는지 확인 -->
		<div class="row">
			<c:if test="${not empty reservationList}">
				<c:forEach var="reservation" items="${reservationList}">
					<div class="col-md-6">
						<div class="card">
							<!-- 예약 상세 정보 -->
							<div class="card-body">
								<h3>
									<a
										href="ownerReservationDetail.do?reservation_num=${reservation.reservation_num}">
										${reservation.reservation_product_name} </a>
								</h3>
								<p>예약자: ${reservation.reservation_member_name}</p>
								<p>예약 날짜: ${reservation.reservation_registration_date}</p>
								<p>예약 상태: ${reservation.reservation_status}</p>

								<!-- 상세보기 버튼 -->
								<a
									href="ownerReservationDetail.do?reservation_num=${reservation.reservation_num}"
									class="btn btn-outline-secondary">상세보기</a>

								<!-- 예약 취소 버튼 -->
								<c:if test="${reservation.reservation_status == '예약완료'}">
									<form action="ownerReservationCancle.do" method="POST"
										style="display: inline;">
										<input type="hidden" name="reservation_num"
											value="${reservation.reservation_num}">
										<button type="submit" class="btn btn-danger"
											onclick="return confirm('정말 취소하시겠습니까?');">취소</button>
									</form>
								</c:if>

								<!-- 예약 취소 불가능 상태 -->
								<c:if
									test="${reservation.reservation_status == '예약취소'}">
									<span class="text-danger"></span>
								</c:if>

							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>

			<!-- 예약이 없을 때 메시지 -->
			<c:if test="${empty reservationList}">
				<div class="col-md-12">
					<p>등록된 예약이 없습니다. 상품을 등록하고 예약을 확인해 주세요.</p>
				</div>
			</c:if>
		</div>

		<!-- 뒤로가기 버튼 -->
		<a href="main.do" class="btn btn-outline-secondary">뒤로가기</a>
	</div>

	<br>
	<br>
	<br>

	<!-- 푸터 연결 -->
	<jsp:include page="footer.jsp" />
</body>
</html>
