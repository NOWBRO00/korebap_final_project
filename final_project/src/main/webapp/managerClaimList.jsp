<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 내역</title>
<!-- Google Font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
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
</head>
<body>
	<c:import url="header.jsp"></c:import>

	<input type="hidden" id="claimList" data-productList="${claimList}"
		value="${bossList}">
	<input type="hidden" id="claimList_total_page"
		data-claimList_page_count="${claimList_total_page}"
		value="${claimList_total_page}">
	<input type="hidden" id="currentPage" data-currentPage="${currentPage}"
		value="${currentPage}">

	<div class="container mb-5">
		<div id="page-wrapper">
			<div class="header">
				<h1 class="page-header">지난 신고</h1>
			</div>

			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<!-- Advanced Tables -->
						<div class="card">
							<div class="card-action"></div>
							<div class="card-content">
								<div class="table-responsive">
									<div id="dataTables-example_wrapper"
										class="dataTables_wrapper form-inline" role="grid">
										<table class="table table-striped table-bordered table-hover"
											id="dataTables-example">
											<thead>
												<tr>
													<th>신고 번호</th>
													<th>신고한 아이디</th>
													<th>신고 컨텐츠</th>
													<th>신고 받은 아이디</th>
													<th>승인 / 반려</th>
												</tr>
											</thead>
											<tbody class="claimList">
												<!-- 실 데이터 반복 -->
												<c:if test="${empty claimList}">
													<tr>
														<td colspan=5>처리된 신고가 없습니다.</td>
													</tr>
												</c:if>
												
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
													<c:forEach var="i" begin="1" end="${claimList_total_page}">
														<c:if test="${i == currentPage}">
															<strong>${i}</strong>
														</c:if>
														<c:if test="${i != currentPage}">
															<a href="#" class="page-link" data-page="${i}">${i}</a>
														</c:if>
													</c:forEach>
													<c:if test="${currentPage < claimList_total_page}">
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
	<script src="js/member/afterClaimListPageNation.js"></script>
</body>
</html>