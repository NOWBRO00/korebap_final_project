<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>고래밥</title>

<!-- Google Font -->
<link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">

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
<style type="text/css">
   .file-display img {
   border: 1px solid #ddd;
   border-radius: 4px;
   padding: 5px;
   margin-right: 10px;
   max-width: 150px; 
   max-height: 150px; 
   display: inline-block;
}</style>
</head>

<body>
	<!-- 헤더 연결 -->
    <c:import url="header.jsp"></c:import>

    <!-- 글 작성페이지 시작섹션 시작 -->
    <section class="blog-details-hero set-bg" data-setbg="img/board/boardBasic.png">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="bd-hero-text">
                    	 <!-- 페이지 제목 및 설명 -->
                        <span>자유 게시판</span>
                        <h2>당신의 낚시를 알려주세요</h2>
                        
                    </div>
                </div>
            </div>
        </div>
    </section>
<!-- 글 작성페이지 시작섹션 종료 -->

<!-- 글 작성페이지 메인섹션 시작 -->
    <section class="blog-details-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="blog-details-text">
                        <div class="leave-comment">
                            <!-- 글 작성 폼 -->
                            <h4>글 작성</h4>
                            <form action="writeBoard.do" method="POST" class="comment-form" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-lg-12 text-center">
                                    	<!-- 글 제목 입력 필드 -->
                                    	<input type="text" id="title" name="board_title" required placeholder="글제목을 작성해주세요" maxlength="40">
                                    	<!-- 파일 업로드 입력 필드 (사진 파일만 허용) -->
                                    	<input type="file" id="files" name="files" multiple accept="image/*"/>
                                    	<!-- 선택된 파일 이름을 표시할 영역 -->
                                    	<div class="file-display" id="fileList"></div><br>
                                    	<!-- 글 내용 입력 필드 -->
                                    	<textarea id="content" name="board_content" rows="10" required placeholder="글내용을 작성해주세요" maxlength="500"></textarea>
                                    	<!-- 제출 버튼 -->
                                    	<input type="submit" value="글작성" class="site-btn"/>
                                    	
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div><br>
                </div>
            </div>
        </div>
    </section>
<!-- 글 작성페이지 시작섹션 종료 -->


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
    <script src="js/myJs/displayFileNames.js"></script>
    <script src="js/previewFile.js"></script>
</body>
</html>