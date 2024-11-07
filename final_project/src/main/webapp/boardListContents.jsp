<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고래밥</title>
<link rel="shortcut icon" href="img/favicon.png" />

</head>
<body>
	<p> 총 페이지 수 : <c:out value="${totalPage}" /></p>
	<p> 현재 페이지 수 : <c:out value="${currentPage}" /></p>
	
<!-- 게시글이 없는 경우 메시지 출력 -->

	<!-- 게시판 목록 섹션 시작 -->
	<!-- 게시판 목록이 비어있지 않다면 -->
							<c:if test="${empty boardList}">
					<div class="empty-boardList">
						<p>게시글이 없습니다.</p>
						<a href="main.do" class="btn-primary">메인으로 돌아가기</a>
					</div>
				</c:if>

				<!-- 게시글이 있는 경우 게시글 목록 출력 -->
				<div class="row" id="boardList"> <!-- AJAX로 데이터를 받아서 이곳에 추가 -->
					<c:if test="${not empty boardList}">
						<c:forEach var="board" items="${boardList}">
							<div class="col-md-4">
								<div class="blog-item scroll_counter">
									<c:if test="${not empty board.board_file_dir}">
										<img src="${board.board_file_dir}" alt="게시글 이미지"
											class="img-fluid">
									</c:if>
									<c:if test="${empty board.board_file_dir}">
										<img src="img/board/boardBasic.png" alt="기본 이미지"
											class="img-fluid">
									</c:if>
									<div class="bi-text">
										<span class="b-tag">자유게시판</span>
										<div class="b-tag">${board.board_like_cnt}</div>
										<h4>
											<a class="text-dark"
												href="boardDetail.do?member_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a>
										</h4>
										<div class="b-time">
											<div class="text-dark">
												<i class="icon_clock_alt"></i>${board.board_registration_date}
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
				
<input type="hidden" id="totalPage" data-board-total-page="${totalPage}">
<input type="hidden" id="currentPage" data-board-current-Page="${currentPage}">

<% 
//System.out.println("JSP에서 totalPage 값: " + request.getAttribute("totalPage"));
%>


<script type="text/javascript">



/*
무한스크롤 구현하는 JS

// 데이터가 모두 로드 된 후 실행

	$(document).ready(function(){
		console.log("페이지 로드 완료");
		
	
			var totalPage = $('#totalPage').data('board-total-page'); 
			
			console.log("totalPage 확인 : "+totalPage);
			// hidden input 요소 선택
			var totalPageElement = document.getElementById('totalPage');
			console.log("totalPageElement 확인 : "+totalPageElement);

			console.log("총 페이지 수: " + totalPage);
		var isLoading = false; // 현재 페이지가 로딩중인지 여부
		var currentPage = 1; // 페이지 초기 값
							
		console.log("총 페이지 수: " + totalPage);

		
		//윈도우 스크롤 기능 동작
		$(window).scroll(function(){
			console.log("스크롤 이벤트 발생"); // 스크롤 이벤트 로그 추가
			
			var scrollTop = $(window).scrollTop(); // 위로 스크롤된 길이
			var windowsHeight = $(window).height(); //웹 브라우저 창의 높이
			var documentHeight =$(document).height(); //문서 전체의 높이
			var isBottom = scrollTop + windowsHeight +10 > documentHeight; // 바닥에 갔는지 여부
			
			
					
			
			if (isBottom && !isLoading) {
					// 만약 현재 페이지가 로딩 중이 아니고, 스크롤이 문서의 끝에 도달한 경우
					console.log("스크롤 임계점에 도달"); // 스크롤 조건이 충족될 때 로그 추가
					isLoading =true; 
					$("#load").show(); // 로딩바 표시
					
					// 만약 현재 페이지가 전체 페이지 수 보다 크다면
					if(currentPage >= totalPage){
						isLoading = false; // 로딩 상태를 false로 변경
						$("#load").hide(); // 로딩바 숨김
						return; // 함수 종료
					}
			
						currentPage++; // 현재 페이지 1 증가
						getList(currentPage); // 추가로 받을 리스트 ajax 처리
				
								
			}
		});
	
		// 리스트 불러오기 함수
		function getList(currentPage){
			$.ajax({
				type : "GET",
				url : "boardListScroll", // 비동기 요청을 보낼 URL
				data : {"currentPage" : currentPage}, // 서버에 페이지 정보 전달
				success : function(data){
					if (data.trim() === "") {
					        console.log("더 이상 불러올 게시글이 없습니다.");
							$("#load").hide();
							isLoading = false;
					        return;  // 빈 응답일 경우 추가 요청을 막음
					}
					// 서버에서 전송된 데이터를 boardList에 추가하기		
					$('#boardList').append(data);
					$('#load').hide(); // 로딩바 숨기기
					isLoading = false; // 로딩여부 false로 변경
				},
				error: function(xhr, status, error) {
					console.error("AJAX 요청 실패:", error);
					$("#load").hide(); // 로딩바 숨기기
					isLoading = false; // 로딩여부 false로 변경
				}
			});
		}
});
	
*/	
</script>


<!-- 
	

 -->
<script src="js/board/boardListScroll.js"></script>

</body>
</html>