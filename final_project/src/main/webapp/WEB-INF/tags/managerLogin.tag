<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<link rel="stylesheet" href="css/style.css" type="text/css">

<header class="header-section">
	<div class="top-nav">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<ul class="tn-left">
						<img class="weatherIcon" src="icon_url_here" alt="Weather Icon"style="display: inline-block; block-size: 50px;">
						<span class="weatherInfo">Weather Info</span>
					</ul>
				</div>
				<div class="col-lg-6">
					<div class="tn-right">
						<div class="top-social">
							<!-- 소셜 미디어 링크 -->
							<a href="https://koreaisacademy.com/"><i
								class="fa fa-facebook"></i></a> <a
								href="https://koreaisacademy.com/"><i class="fa fa-twitter"></i></a>
							<a href="https://koreaisacademy.com/"><i
								class="fa fa-instagram"></i></a>
						</div>
						<!-- 사용자 로그인 태그 -->
						<mytag:login />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="menu-item">
		<div class="container">
			<div class="row">
				<div class="col-lg-2">
					<div class="logo">
						<!-- 사이트 로고 -->
						<a href="main.do"> <img src="img/logo.png" alt="로고입니다."></a>
					</div>
				</div>
				<div class="col-lg-10">
					<div class="nav-menu">
						<nav class="mainmenu">
							<ul>
								<li><a href="kosime.do">고심이</a></li>
								<li><a
									href="productListPage.do?product_searchContent=null&searchKeyword=null">상품검색</a></li>
								<li><a>관리자 메뉴</a>
									<ul class="dropdown">
										<li><a href="managerClaimListPage.do">지난 신고 내역</a></li>
									</ul></li>
								<li><a href="boardListPage.do">게시판</a>
									<ul class="dropdown">
										<li><a href="noticeList.do">공지사항</a></li>
										<li><a href="boardListPage.do">자유 게시판</a></li>
									</ul></li>
								<li>
									<!-- 검색창 포함 -->
									<div class="search-container">
										<!-- 검색 버튼 -->
										<button type="button" class="btn btn-primary"
											data-bs-toggle="modal" data-bs-target="#filterModal">
											상품 검색</button>
									</div>
								</li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
</header>
<body>

	<!-- 모달 -->
	<div class="modal fade" id="filterModal" tabindex="-1"
		aria-labelledby="filterModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="filterModalLabel">상품 검색</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="filterForm" action="searchProduct.do" method="GET">
						<div class="mb-3">
							<label>유형</label><br> <input type="checkbox"
								name="product_types" value="바다"> 바다 <input
								type="checkbox" name="product_types" value="민물"> 민물
						</div>
						<div class="mb-3">
							<label>카테고리</label><br> <input type="checkbox"
								name="product_categories" value="낚시터"> 낚시터 <input
								type="checkbox" name="product_categories" value="낚시배">
							낚시배 <input type="checkbox" name="product_categories" value="낚시카페">
							낚시카페 <input type="checkbox" name="product_categories" value="수상">
							수상
						</div>
						<div class="mb-3">
							<label>상품명</label><br> <input type="text"
								name="product_searchKeyword" placeholder="검색어 입력">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary"
						onclick="submitSearch()">검색</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 헤더 섹션 종료 -->
	<!-- Bootstrap JS (필수) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function submitSearch() {
			var form = document.getElementById('filterForm');
			var formData = new FormData(form);
			var queryParams = new URLSearchParams();

			console.log("form 문서 로드 후 [" + form + "]");
			console.log("formData 문서 로드 후 [" + formData + "]");
			console.log("queryParams 문서 로드 후 [" + queryParams + "]");

			formData.forEach(function(value, key) {
				// 체크박스 값이 여러 개일 경우 배열로 전달
				if (Array.isArray(queryParams.getAll(key))) {
					queryParams.append(key, value);
				} else {
					queryParams.set(key, value); // 마지막 값을 덮어씌우지 않도록
				}
				console.log("key 문서 로드 후 [" + key + "]");
				console.log("value 문서 로드 후 [" + value + "]");
			});

			// GET 방식으로 서버에 검색 요청 보내기 (페이지 이동)
			window.location.href = "searchProduct.do?" + queryParams.toString();
		}
	</script>
	<script src="js/weather.js"></script>
</body>