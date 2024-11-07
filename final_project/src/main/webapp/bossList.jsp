<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Google Font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap"
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
<link rel="stylesheet" href="css/starPlugin.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />
<title>사장님 사용자 목록</title>

<!-- jQuery 연결 -->
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>

</head>
<body>
	<c:import url="header.jsp"></c:import>

	<input type="hidden" id="bossList" data-productList="${bossList}"
		value="${bossList}">
	<input type="hidden" id="bossList_total_page"
		data-bossList_page_count="${bossList_total_page}"
		value="${bossList_total_page}">
	<input type="hidden" id="currentPage" data-currentPage="${currentPage}"
		value="${currentPage}">
	<input type="hidden" id="searchTerm2" value="${searchTerm}">

	<div class="container mb-5">
		<div id="page-wrapper">
			<div class="header">
				<h1 class="page-header">사장님 사용자 목록</h1>
			</div>

			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<!-- Advanced Tables -->
						<div class="card-action mb-5 mt-3">
							<div class="d-flex justify-content-center">
								<input class="search-input" id="searchTerm" type="text" name="member_id"
										placeholder="회원 검색..." required />
										<button class="search-button" type="submit"/>검색</button>
							</div>
						</div>
						<div class="card manager-main-card">
							<div class="card-content">
								<div class="table-responsive">
									<div id="dataTables-example_wrapper"
										class="dataTables_wrapper form-inline" role="grid">
										<table class="table table-striped table-bordered table-hover"
											id="dataTables-example">
											<thead>
												<tr>
													<th>ID</th>
													<th>이름</th>
													<th>전화번호</th>
													<th>가입날짜</th>
													<th>비고</th>
												</tr>
											</thead>
											<tbody class="bossList">
												<!-- 실 데이터 반복 -->
												<c:if test="${empty bossList}">
													<tr>
														<td colspan=5>사장님 사용자가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach var="boss" items="${bossList}">
													<a href="mypage.do?member_id=${boss.member_id}">
														<tr>
															<td>${boss.member_id}</td>
															<td>${boss.member_name}</td>
															<td>${boss.member_phone}</td>
															<td>${boss.member_registration_date}</td>
															<td>
																<div class="d-flex justify-content-between">
																	<a href="MemberBan.do?member_id=${boss.member_id}"
																		class="btn btn-primary disabled" role="button"
																		aria-disabled="true">정지</a> 
																		<a
																		href="deleteMember.do?member_id=${boss.member_id}"
																		class="btn btn-primary disabled" role="button"
																		aria-disabled="true">탈퇴</a>
																</div>
															</td>
														</tr>
													</a>
												</c:forEach>
												<!-- 실 데이터 반복 -->
											</tbody>
										</table>
									</div>
									<div class="container">
										<div class="row">
											<div class="col-sm-12">
												<div class="pagination">
													<c:if test="${currentPage > 1}">
														<a href="#" class="page-link"
															data-page="${currentPage - 1}">이전</a>
													</c:if>
													<c:forEach var="i" begin="1" end="${bossList_total_page}">
														<c:if test="${i == currentPage}">
															<strong>${i}</strong>
														</c:if>
														<c:if test="${i != currentPage}">
															<a href="#" class="page-link" data-page="${i}">${i}</a>
														</c:if>
													</c:forEach>
													<c:if test="${currentPage < bossList_total_page}">
														<a href="#" class="page-link"
															data-page="${currentPage + 1}">다음</a>
													</c:if>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--End Advanced Tables -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>

	<script src="js/member/bossListPageNation.js"></script>
</body>
</html>