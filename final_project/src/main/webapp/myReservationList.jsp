<%@ page isErrorPage="false" contentType="text/html;charset=UTF-8"
   language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 목록 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="shortcut icon" href="img/favicon.png" />
<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<style type="text/css">
input {
   border: none !important;
}

h5 {
   width: 120px;
   align-content: center !important;
}
.reservationDetail {
    color: blue; /* 기본 색상: 파랑 */
    text-decoration: none; /* 밑줄 없애기 */
}

.reservationDetail:hover {
    color: red; /* 마우스 올렸을 때 색상: 빨강 */
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
         <div class="col-12 card" style="background-color: #ffffff">
            <div class="card-body">
               <br>
               <div>
                  <h2>예약 목록</h2>
               </div>
               <br>
               <!-- 
               <c:forEach var="data" items=""><!--${boardList}-->
               <div class="col-md-4">
                  <!-- 게시판 태그 -->
                  <mytag:smallBoard />
               </div>
               </c:forEach>
               -->
               <!-- 예시 데이터 -->
               <table class="table">
                  <thead>
                     <tr>
                        <th scope="col">상품명</th>
                        <th scope="col">금액</th>
                        <th scope="col">예약상태</th>
                     </tr>
                  </thead>
                  <tbody>
                     <c:if
                        test="${myReservationList == null || empty myReservationList}">
                        <th colspan="3">예약 내역이 없습니다.</th>
                     </c:if>
                     <c:if
                        test="${not (myReservationList == null || empty myReservationList)}">
                        <!-- 예약 내역이 있을 때의 내용 -->
                        <c:forEach var="reservation" items="${myReservationList}">
							<tr>
								<td><a class="reservationDetail" href="reservationDetail.do?reservation_num=${reservation.reservation_num}">${reservation.reservation_product_name}</a></td>
								<td>${reservation.reservation_price}</td>
								<td>${reservation.reservation_status}</td>
							</tr>
                        </c:forEach>
                     </c:if>

                  </tbody>
               </table>


               <!-- 뒤로가기 버튼 -->
               <button type="button" id="backBtn" onclick="location.href='main.do'"
                  class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
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