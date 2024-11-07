<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />

<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script defer type="text/javascript" src="js/product/payment.js"></script>
<style type="text/css">
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
               <h2>예약 하기</h2>
            </div>
            <hr>
            <div class="row g-0">
               <div class="col-md-4" style="width: 50rem;">
                  <div class="card-body">
                     <!-- 상품 썸네일 -->
                     <c:if test="${not empty fileList}">
                        <c:set var="file" value="${fileList[1]}" />
                        <img src="${file.file_dir}" class="rounded mx-auto d-block"
                           alt="상품 사진" style="margin-left: 10px;">
                     </c:if>
                     <br>
                  </div>
               </div>
               <div class="col-md-8" style="width: 100rem; ">
                  <div class="card-body">
                     <!-- 상품명, 카테고리 -->
                     <input type="hidden" id="product_num"
                        value="${product.product_num}" readonly />
                     <div class="input-group mb-3"
                        style="display: flex; align-items: flex-end; height: 40px;">
                        <h3 id="product_name">${product.product_name}</h3>
                        <div class="bd-hero-text" style="margin-right: 3px;">
                           <span>${product.product_category}</span>
                        </div>
                     </div>
                     <!-- 사용 예정일 -->
                     <div class="input-group mb-3">
                        <h5>사용 예정일</h5>
                        <input type="text" id="reservation_date" class="form-control"
                           value="${reservation_date}" readonly>
                     </div>
                     <span id="nameMsg"></span>
                     <!-- 예약자명 -->
                     <input type="hidden" id="member_id" value="${member.member_id}" />
                     <div class="input-group mb-3">
                        <h5>예약자명</h5>
                        <input type="text" id="member_name" class="form-control"
                           value="${member.member_name}" readonly>
                     </div>
                     <span id="nicknameMsg"></span>
                     <!-- 예약자 전화번호 -->
                     <div class="input-group mb-3">
                        <h5>전화번호</h5>
                        <input type="text" id="member_phone" class="form-control"
                           value="${member.member_phone}" readonly>
                     </div>
                     <!-- 결제금액 -->
                     <div class="input-group mb-3">
                        <h5>결제금액</h5>
                        <input type="text" id="amount" class="form-control"
                           value="${product.product_price}" readonly>
                     </div>
                     <!-- 결제하기 버튼 -->
                     <button type="button" id="payBtn"
                        class="btn btn-outline-secondary" style="width: 140px"
                        onclick="requestPay()">결제하기</button>
                     <!-- 뒤로가기 버튼 -->
                     <button type="button" id="backBtn"
                        onclick="location.href='productDetail.do?product_num=${product.product_num}'"
                        class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
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
</body>
</html>