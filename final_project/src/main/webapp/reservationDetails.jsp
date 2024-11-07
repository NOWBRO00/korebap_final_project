<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 내역 상세 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/starPlugin.css" type="text/css">

<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<script
   src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style type="text/css">
textarea {
   width: 100%;
   height: 150px;
   resize: vertical;
   padding: 10px;
   font-size: 1rem;
}

/*별점 플러그인*/
.star-rating {
   display: inline-block;
   font-size: 0;
   position: relative;
   cursor: pointer;
   direction: rtl;
}

.star-rating input {
   display: none;
}

.star-rating label {
   color: #ccc;
   font-size: 2rem;
   padding: 0;
   margin: 0;
   cursor: pointer;
   display: inline-block;
}

.star-rating label:before {
   content: '★';
}

.star-rating input:checked ~ label {
   color: gold;
}

.star-rating input:checked ~ label:hover, .star-rating input:checked ~
   label:hover ~ label {
   color: gold;
}

.star-rating input:hover ~ label {
   color: gold;
}

#result {
   margin-top: 10px;
}

input {
   border: none !important;
}

h5 {
   width: 120px;
   align-content: center !important;
}
</style>
</head>
<body>
   <!-- 헤더 -->
   <jsp:include page="header.jsp" />
   <br>
   <!-- 예약 내역 상세보기 -->
   <div class="container text-center">
      <div class="row">
         <div class="card">
            <br>
            <div>
               <h2>예약 정보</h2>
            </div>
            <hr>
            <div class="row g-0">
               <div class="col-md-4" style="width: 50rem;">
                  <div class="card-body">
                     <!-- 상품 썸네일 -->
                     <img
                        src="http://localhost:8088/teem_project/img/product/${reservation.reservation_product_file_dir}"
                        class="rounded mx-auto d-block" alt="상품 사진"
                        style="margin-left: 10px;"> <br>
                  </div>
               </div>
               <div class="col-md-8" style="width: 100rem;">
                  <div class="card-body">
                     <!-- 상품명, 예약상태, 결제상태 -->
                     <div class="input-group mb-3"
                        style="display: flex; align-items: flex-end; height: 40px;">
                        <a
                           href="productDetail.do?product_num=${reservation.reservation_product_num}">
                           <h3>${reservation.reservation_product_name}</h3>
                        </a>
                        <div class="bd-hero-text" style="margin-right: 3px;">
                           <span>${reservation.reservation_status}</span>
                        </div>
                        <div class="bd-hero-text">
                           <span>${reservation.reservation_payment_status}</span>
                        </div>
                     </div>
                     <!-- 예약번호 -->
                     <div class="input-group mb-3">
                        <h5>예약번호</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_num}" readonly>
                     </div>
                     <span id="nameMsg"></span>
                     <!-- 예약일자 -->
                     <div class="input-group mb-3">
                        <h5>사용 예정일</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_registration_date}" readonly>
                     </div>
                     <span id="nicknameMsg"></span>
                     <!-- 예약자명 -->
                     <div class="input-group mb-3">
                        <h5>예약자명</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_member_name}" readonly>
                     </div>
                     <!-- 예약자 전화번호 -->
                     <div class="input-group mb-3">
                        <h5>전화번호</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_member_phone}" readonly>
                     </div>
                     <!-- 결제금액, 결제방법 -->
                     <div class="input-group mb-3">
                        <h5>결제금액</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_price}" readonly>
                        <div class="bd-hero-text"
                           style="margin-left: 3px; margin-top: 0.75rem;">
                           <span>${reservation.reservation_payment_method}</span>
                        </div>
                     </div>
                     <!-- 뒤로가기 버튼 -->
                     <button type="button"
                        onclick="location.href='myReservationListPage.do'"
                        class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
                     <button type="button"
                        onclick="location.href='cancelReservation.do?reservation_num=${reservation.reservation_num}'"
                        class="btn btn-outline-secondary" style="width: 140px">취소하기</button>
                     <c:if test="${reservation.reservation_status == '사용완료'}">
                        <button type="button" data-bs-toggle="modal"
                           data-bs-target="#modal" class="btn btn-outline-secondary"
                           style="width: 140px">리뷰쓰기</button>
                     </c:if>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <br>
   <br>
   <br>
   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

   <!-- 모달 -->
   <div class="modal fade" id="modal" tabindex="-1"
      aria-labelledby="filterModalLabel" aria-hidden="true">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <h5 class="modal-title">리뷰 작성</h5>
               <button type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
            </div>
            <div class="modal-body">
               <!-- 아이디, 상품번호, 별점, 내용 -->
               <form action="writeReview.do" method="POST" class="comment-form">
                  <div class="star-rating">
                     <input type="radio" id="star5" name="review_star" value="5"
                        required /> <label for="star5" title="5개 별"></label> <input
                        type="radio" id="star4" name="review_star" value="4" /> <label
                        for="star4" title="4개 별"></label> <input type="radio" id="star3"
                        name="review_star" value="3" /> <label for="star3" title="3개 별"></label>
                     <input type="radio" id="star2" name="review_star" value="2" /> <label
                        for="star2" title="2개 별"></label> <input type="radio" id="star1"
                        name="review_star" value="1" /> <label for="star1" title="1개 별"></label>
                  </div>
                  <hr>
                  <!-- 글 내용 입력 필드 -->
                  <textarea id="content" name="review_content" required
                     placeholder="리뷰를 작성해주세요"></textarea>
                  <!-- 글번호 -->
                  <input type="hidden" name="review_product_num"
                     value="${reservation.reservation_product_num}" />
                  <!-- 제출 버튼 -->
                  <input type="submit" value="리뷰작성" class="btn btn-primary" />
               </form>
            </div>
         </div>
      </div>
   </div>

</body>
</html>