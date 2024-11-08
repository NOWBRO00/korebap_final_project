<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 사항 상세보기</title>
<!-- Google Font -->
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
<link rel="shortcut icon" href="img/favicon.png" />
</head>
<body>
<!-- 헤더 연결 -->
<c:import url="header.jsp"></c:import>

<!-- 글 상세페이지 글정보섹션 시작 -->
<section class="blog-details-hero set-bg" data-setbg="img/board/boardBasic.png">
    <div class="container">
        <div class="row">
            <div class="col-lg-10 offset-lg-1">
                <div class="bd-hero-text">
                    <span>공지 사항</span>
                    <h2>${notice.board_title}</h2>
                    <ul>
                        <li class="b-time"><i class="icon_clock_alt"></i>${notice.board_registration_date}</li>
                        <li><i class="icon_profile"></i> 
                            <c:if test="${not empty member_id}">
                                <c:import url="role.jsp"></c:import>
                            </c:if> 
                            ${notice.board_writer_id}
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- 글 상세페이지 메인섹션 시작 -->
<section class="blog-details-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-10 offset-lg-1">
                <div class="blog-details-text">
                    <div class="col-12 d-flex justify-content-center">
						<div class="w-75">
							<p class="text-start">${notice.board_content}</p>
						</div>
					</div>
                    <div class="tag-share">
                        <div class="tags">
                            <div class="button-container">
                                <c:if test="${member_id eq notice.board_writer_id or member_role=='ADMIN'}">
                                    <button class="editButton" onclick="location.href='updateNotice.do?board_num=${notice.board_num}';">✏️수정</button>
                                    <button class="editButton" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='deleteNotice.do?board_num=${notice.board_num}';">❌삭제</button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- 푸터 연결 -->
<c:import url="footer.jsp"></c:import>

<!-- 템플릿 Js 플러그인 -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
<script src="js/starPlugin.js"></script>
<script>src="js/board/replyFocus.js"</script>
</body>
</html>