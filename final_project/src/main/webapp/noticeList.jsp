<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Google Font -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">

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
<title>공지 사항 목록</title>
</head>
<body>
<!-- 헤더 연결 -->
<c:import url="header.jsp"></c:import>

<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row pt-5 pb-2">
				<div class="col-12">
					<h1 class="text-center">공지 사항</h1>
					<c:if test="${member_role eq 'ADMIN'}">
						<a href="writeNotice.do" class="search-button" style="float:inline-end; border:none; border-radius: 5px">공지사항 작성</a>
					</c:if>
				</div>
			</div>
			<c:if test="${empty noticeList}">
				<div>공지 사항 없음</div>
			</c:if>
			<c:if test="${not empty noticeList}">
				<div id="notice_total_cnt" style="display: none;">${total_page}</div>
				<c:forEach var="notice" items="${noticeList}">
					<div class="row pt-5">
						<div class="col-12">
							<div class="card card-stats card-round mb-0">
								<div class="card-body p-5 d-flex justify-content-between">
									<h3 class="card-title">
										<a href="noticeDetail.do?board_num=${notice.board_num}"
											class="link-dark"> ${notice.board_title}</a>
									</h3>
									<div class="info-user">
										<div class="username">작성자 : ${notice.board_writer_nickname}</div>
										<div>작성일 : ${notice.board_registration_date}</div>
										<div style="display:none">${notice.board_writer_id}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
       		<div class="container">
           		<div class="row">
           			<div class="col-sm-12">
           				<div class="dataTables_paginate paging_simple_numberscenter justify-content-center align-items-center" id="dataTables-example_paginate">
           					<ul class="pagination justify-content-center align-items-center">
           						<li id="prevBtn" class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"></li>
	         					<li id="pageBtns" class="paginate_button active" aria-controls="dataTables-example" tabindex="0"></li>
	         					<li id="nextBtn" class="paginate_button next disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"></li>
         					</ul>
        				</div>
    				</div>
           		</div>
         	</div>
		</div>
	</div>		<!-- container end -->

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

<script type="text/javascript">
$(document).ready(function() {
    const page_total_cnt = $('#notice_total_cnt').text();
    console.log(page_total_cnt);

    const prev_button = $('#prevBtn');
    const page_buttons = $('#pageBtns');
    const next_button = $('#nextBtn');
    //let currentPage = 1; // 현재 페이지 번호
    
 	// URL에서 현재 페이지 번호 읽기, 없으면 기본값 1
    const urlParams = new URLSearchParams(window.location.search);
    let currentPage = parseInt(urlParams.get('board_page_num')) || 1;

    // 페이지 버튼 렌더링 함수
    function renderPageButtons() {
        // 페이지 버튼 초기화
        page_buttons.empty();
        prev_button.empty(); // Prev 버튼 초기화
        next_button.empty(); // Next 버튼 초기화

        // 시작 페이지와 끝 페이지 계산
        const startPage = Math.floor((currentPage - 1) / 10) * 10 + 1; // 시작 페이지
        const endPage = Math.min(startPage + 9, page_total_cnt); // 끝 페이지

        // 페이지 버튼 추가
        for (let i = startPage; i <= endPage; i++) {
            const pageLink = $('<a>')
                .attr('href', 'noticeList.do?board_page_num=' + i) // 수정된 부분
                .text(i)
                .on('click', function(event) {
                    event.preventDefault(); // 기본 링크 동작 방지
                    currentPage = i; // 현재 페이지 업데이트
                    renderPageButtons(); // 페이지 버튼 다시 렌더링
                    window.location.href = $(this).attr('href'); // 페이지 이동
                });
            // 현재 페이지인 경우 특별한 클래스를 추가
            if (i === currentPage) {
                pageLink.addClass('pageActive'); // 'pageActive' 클래스를 추가
            }

            page_buttons.append(pageLink); // 페이지 버튼 추가
        }

        // "Prev" 버튼 추가
        if (currentPage > 1) {
            const prevLink = $('<a>')
                .attr('href', 'noticeList.do?board_page_num=' + (currentPage - 1)) // 수정된 부분
                .text('Prev')
                .on('click', function(event) {
                    event.preventDefault(); // 기본 링크 동작 방지
                    currentPage--; // 현재 페이지 감소
                    renderPageButtons(); // 페이지 버튼 다시 렌더링
                    window.location.href = 'noticeList.do?board_page_num=' + currentPage; // 수정된 부분
                });
            prev_button.append(prevLink); // "Prev" 버튼 추가
        }

        // "Next" 버튼 추가
        if (currentPage < page_total_cnt) {
            const nextLink = $('<a>')
                .attr('href', 'noticeList.do?board_page_num=' + (currentPage + 1)) // 수정된 부분
                .text('Next')
                .on('click', function(event) {
                    event.preventDefault(); // 기본 링크 동작 방지
                    currentPage++; // 현재 페이지 증가
                    renderPageButtons(); // 페이지 버튼 다시 렌더링
                    window.location.href = 'noticeList.do?board_page_num=' + currentPage; // 수정된 부분
                });
            next_button.append(nextLink); // "Next" 버튼 추가
        }
    }

    renderPageButtons(); // 페이지 버튼 초기 렌더링
});
</script>
</body>
</html>