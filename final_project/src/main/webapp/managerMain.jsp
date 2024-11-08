<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

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

<!-- chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="js/claim/Claim.js"></script>
</head>
<body>
	<c:import url="header.jsp"></c:import>
	
	<div class="container">
		<div id="page-wrapper">
			<div class="header">
				<h1 class="page-header">고래밥 현황</h1>
			</div>
			<div class="dashboard-cards mt-5">
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-3">
						<a href="memberListPage.do" class="manager-main-a">
							<div
								class="manager-card horizontal cardIcon waves-effect waves-dark ">
								<div class="card-image red">
									<span class="material-symbols-outlined manager-main-icon">person</span>
								</div>
								<div class="card-stacked red">
									<div class="card-content">
										<h3 id="userTotal">${user_memberCnt}</h3>
									</div>
									<div class="card-action">
										<strong>사용자</strong>
									</div>
								</div>
							</div>
						</a>
					</div>

					<div class="col-xs-12 col-sm-6 col-md-3">
						<a href="bossListPage.do" class="manager-main-a">
							<div
								class="manager-card horizontal cardIcon waves-effect waves-dark">
								<div class="card-image orange">
									<span class="material-symbols-outlined manager-main-icon">sailing</span>
								</div>
								<div class="card-stacked orange">
									<div class="card-content">
										<h3 id="bossTotal">${owner_memberCnt}</h3>
									</div>
									<div class="card-action">
										<strong>사장님 사용자</strong>
									</div>
								</div>
							</div>
						</a>
					</div>

					<div class="col-xs-12 col-sm-6 col-md-3">
						<a href="boardListPage.do" class="manager-main-a">
							<div
								class="manager-card horizontal cardIcon waves-effect waves-dark">
								<div class="card-image green">
									<span class="material-symbols-outlined manager-main-icon">library_books</span>
								</div>
								<div class="card-stacked green">
									<div class="card-content">
										<h3 id="boardTotal">${all_boardCnt}</h3>
									</div>
									<div class="card-action">
										<strong>게시판</strong>
									</div>
								</div>
							</div>
						</a>
					</div>

					<div class="col-xs-12 col-sm-6 col-md-3">
						<a href="productListPage.do" class="manager-main-a">
							<div
								class="manager-card horizontal cardIcon waves-effect waves-dark">
								<div class="card-image blue">
									<span class="material-symbols-outlined manager-main-icon">set_meal</span>
								</div>
								<div class="card-stacked blue">
									<div class="card-content">
										<h3 id="productTotal">${product_total_cnt}</h3>
									</div>
									<div class="card-action">
										<strong>상품</strong>
									</div>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<div class="container mt-5">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-7">
						<div class="cirStats">
							<div class="row">
								<div class="col-xs-12 col-sm-6 col-md-6">
									<div class="card-panel text-center manager-main-chart-div">
										<h4>사용자</h4>
										<div class="easypiechart" id="easypiechart-red"
											data-percent="82">
											<span class="percent"></span>
											<canvas id="userChart" class="chartCanvas" height="110"
												width="110"></canvas>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6">
									<div class="card-panel text-center manager-main-chart-div">
										<h4>사장님 사용자</h4>
										<div class="easypiechart" id="easypiechart-orange"
											data-percent="46">
											<span class="percent"></span>
											<canvas id="bossChart" class="chartCanvas" height="110"
												width="110"></canvas>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 mt-5">
									<div class="card-panel text-center manager-main-chart-div">
										<h4>게시판</h4>
										<div class="easypiechart" id="easypiechart-teal"
											data-percent="55">
											<span class="percent"></span>
											<canvas id="boardChart" class="chartCanvas" height="110"
												width="110"></canvas>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 mt-5">
									<div class="card-panel text-center manager-main-chart-div">
										<h4>상품</h4>
										<div class="easypiechart" id="easypiechart-blue"
											data-percent="84">
											<span class="percent"></span>
											<canvas id="productChart" class="chartCanvas" height="110"
												width="110"></canvas>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-5"
						style="align-content: center">
						<div class="row">
							<div class="col-xs-12">
								<div class="card manager-main-card manager-main-chart-div">
									<div class="card-image donutpad">
										<h3 style="text-align: center">카테고리별 상품 수</h3>
										<div id="morris-donut-chart">
											<canvas id="donutChart" class="chartCanvas" width="440"
												height="550"></canvas>
										</div>
									</div>
									<div style="display:none" id="ocean_cnt">${ocean_cnt}</div>
									<div style="display:none" id="freshwater">${freshwater}</div>
									<div style="display:none" id="ocean_boat">${ocean_boat}</div>
									<div style="display:none" id="ocean_spot">${ocean_spot}</div>
									<div style="display:none" id="fresh_cafe">${fresh_cafe}</div>
									<div style="display:none" id="fresh_onWater">${fresh_onWater}</div>
									<!-- <div class="card-action">
			                          <b>카테고리별 상품 수</b>
			                        </div> -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<input type="hidden" id="claimList" data-productList="${claimList}" value="${bossList}">
			<input type="hidden" id="claimList_total_page" data-claimList_page_count="${claimList_total_page}" value="${claimList_total_page}">
			<input type="hidden"
				id="currentPage" data-currentPage="${currentPage}"
				value="${currentPage}">

			<div class="container mt-5 mb-5">
				<div class="row">
					<div class="col-md-12">
						<!-- Advanced Tables -->
						<div class="card manager-main-card">
							<div class="card-action"></div>
							<div class="card-content">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover"
										id="dataTables-example">
										<thead>
											<tr style="text-align: center;">
												<th>신고 번호</th>
												<th>신고한 아이디</th>
												<th>신고 컨텐츠</th>
												<th>신고 받은 아이디</th>
												<th>승인 / 반려</th>
											</tr>
										</thead>
										<tbody class="claimList" style="text-align: center;">
											<c:if test="${empty claimList}">
												<tr>
													<td colspan=5>처리되지 않은 신고가 없습니다.</td>
												</tr>
											</c:if>
											
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
				</div>
			</div>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
	<script src="js/member/claimListPageNation.js"></script>
	<script src="js/chartJs.js"></script>
<script>
     
    // 'returnButton' 클릭 이벤트 핸들러
    $('#returnButton').on('click', function() {
        // 'returnButton'의 값(신고 번호)을 가져옴
        var claim_num = document.getElementById('returnButton').value;
        console.log("returnButton 신고버튼 :" + claim_num); // 신고 번호 로그 출력

        // AJAX 요청을 통해 신고 반려 처리
        $.ajax({
            url: 'returnClaim.do', // 요청할 URL
            type: 'GET', // HTTP 메서드
            data: { claim_num: claim_num }, // 서버로 보낼 데이터
            success: function(result) { // 요청 성공 시 실행
                console.log(typeof result); // 결과의 타입 로그 출력
                console.log(result); // 결과 로그 출력
                if (result == "true") { // 결과가 'true'일 경우
                    alert("신고가 반려되었습니다."); // 반려 메시지 알림
                    location.reload(); // 페이지 새로고침
                } else {
                    alert("반려 처리 중 오류가 발생했습니다."); // 오류 메시지 알림
                }
            },
            error: function(error) { // 요청 실패 시 실행
                console.log("error : " + error); // 오류 로그 출력
            }
        });
    });
</script>
</html>