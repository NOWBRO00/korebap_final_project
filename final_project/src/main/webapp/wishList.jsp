<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고래밥</title>

<!-- Google Font 추가 -->
<link
   href="https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400;700&display=swap"
   rel="stylesheet">
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
<link rel="shortcut icon" href="img/favicon.png" />

<script src="js/jquery-3.3.1.min.js"></script>

<!-- 스타일 추가 -->
<style type="text/css">
#wishlist_count {
   font-family: 'Noto Sans', sans-serif;
   font-size: 16px;
   color: #09022b;
   display: block;
   margin-bottom: 20px;
}

.count-bold {
   font-size: 16px;
   color: #09022b;
   font-weight: bold; /* 숫자에만 bold 적용 */
}
</style>

</head>
<body>

   <!-- 헤더 연결 -->
   <c:import url="header.jsp" />

   <!-- 탐색경로 섹션 시작 -->
   <div class="breadcrumb-section">
      <div class="container">
         <div class="row">
            <div class="col-lg-12">
               <div class="breadcrumb-text">
                  <h2>위시리스트</h2>
                  <div class="bt-option">
                     <span>나의 찜 목록</span>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- 탐색경로 섹션 종료 -->

   <!-- 위시리스트 목록 섹션 시작 -->

   <!-- 빈 위시리스트 요소는 항상 존재하지만, wishlist가 비어있지 않다면 숨김 처리 -->
   <div class="container">
      <div class="empty-wishlist"   style="<c:if test='${not empty wishlist}'>display:none;</c:if>">
         <p>찜한 상품이 없습니다.</p>
         <a href="productListPage.do" class="btn-primary">상품 목록 보기</a>
      </div>
   </div>

   <!-- 만약 위시리스트 파일이 비어있지 않다면 -->
   <c:if test="${not empty wishlist}">
      <section class="wishlist-section blog-page spad">
         <div class="container">
            <div class="row">
               <div class="col-lg-12">
                  <!-- 전체 위시리스트 개수 표시 -->
                  <span id="wishlist_count">전체 <span class="count-bold">${wishlist_count}</span>개
                  </span>
               </div>
               <%-- forEach 사용하여 위시리스트 항목 출력해준다. --%>
               <c:forEach var="datas" items="${wishlist}">
                  <div class="col-md-4">
                     <!-- 게시판 태그 -->
                     <mytag:wishlist wishlist="${datas}" wishlist_page_count="${wishlist_page_count}"/>
                  </div>
               </c:forEach>
            </div>
         </div>
      </section>
   </c:if>

   <!-- 위시리스트 목록 섹션 종료 -->


   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

   <!-- Js Plugins -->
   <!-- <script src="js/jquery-3.3.1.min.js"></script>-->
   <script src="js/bootstrap.min.js"></script>
   <script src="js/jquery.magnific-popup.min.js"></script>
   <script src="js/jquery.nice-select.min.js"></script>
   <script src="js/jquery-ui.min.js"></script>
   <script src="js/jquery.slicknav.js"></script>
   <script src="js/owl.carousel.min.js"></script>
   <script src="js/main.js"></script>
   <script src="js/wishlist/deleteWishlist.js"></script>

</body>
</html>