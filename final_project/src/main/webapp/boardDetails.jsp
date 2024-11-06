<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>고래밥</title>

<!-- Google Font -->
<link
   href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
   rel="stylesheet">
<link
   href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
   rel="stylesheet">
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
<style type="text/css">

/* 공통 버튼 스타일 */
#likeBtn, .likeButton {
   background-color: #87CEEB; /* 하늘색 */
   border: none;
   color: white;
   padding: 10px 20px;
   text-align: center;
   text-decoration: none;
   display: inline-block;
   font-size: 16px;
   margin: 5px;
   cursor: pointer;
   border-radius: 5px;
   transition: background-color 0.3s ease, transform 0.2s ease;
}

/* 좋아요 버튼 */
#likeBtn {
   font-size: 14px;
}

#likeBtn:hover {
   background-color: #00BFFF; /* 더 밝은 하늘색 */
   transform: scale(1.05);
}

/* 삭제 버튼 */
#editButton {
   background-color: #87CEEB; /* 하늘색 */
}

#editButton:hover {
   background-color: #00BFFF; /* 더 밝은 하늘색 */
   transform: scale(1.05);
}

/* 수정 버튼 */
#reportButton {
   background-color: #87CEEB; /* 하늘색 */
}

#reportButton:hover {
   background-color: #00BFFF; /* 더 밝은 하늘색 */
   transform: scale(1.05);
}

/* 아이콘 스타일 */
.icon_thumb_up, .icon_warning {
   margin-right: 5px;
}

input{
display:block;
border: none;
width:500px;
}
</style>

</head>

<body>
   <!-- 헤더 연결 -->
   <c:import url="header.jsp"></c:import>

   <!-- 글 상세페이지 글정보섹션 시작 -->
   <!-- data-set-bg : 게시판 기본 이미지 -->
   <section class="blog-details-hero set-bg"
      data-setbg="img/board/boardBasic.png">
      <div class="container">
         <div class="row">
            <div class="col-lg-10 offset-lg-1">
               <div class="bd-hero-text">
                  <!-- 카테고리(바다,민물) -->
                  <span>자유 게시판</span>
                  <!-- 게시글 제목 -->
                  <h2>${board.board_title}</h2>
                  <ul>
                     <!-- 게시글 작성 날짜 -->
                     <li class="b-time"><i class="icon_clock_alt"></i>
                        ${board.board_registration_date}</li>
                     <!-- 게시글 작성자 -->
                     <li><i class="icon_profile"></i>${board.board_writer_id}</li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </section>
   <!-- 글 상세페이지 글정보섹션 종료 -->

   <!-- 글 상세페이지 파일출력섹션 시작 -->
   <section class="testimonial-section spad">
      <div class="container">
         <div class="row">
            <!-- 사용자가 넣은 파일 확인유무(사진) -->
            <c:if test="${empty fileList}">
               <div>사진 없음</div>
            </c:if>
            <c:if test="${not empty fileList}">
               <div class="col-lg-8 offset-lg-2">
                  <div class="testimonial-slider owl-carousel">
                     <!-- 데이터로부터 사용자가 넣은 파일 반복 -->
                     <c:forEach var="file" items="${fileList}">
                        <div class="ts-item">
                           <div class="bd-pic">
                              <c:choose>
                                 <c:when test="${fn:startsWith(file.file_dir, 'http')}">
                                    <img src="${file.file_dir}" alt="상품 이미지입니다.">
                                 </c:when>
                                 <c:otherwise>
                                    <img
                                       src="http://localhost:8088/teem_project/img/board/${file.file_dir}"
                                       alt="게시글에서 사용자가 입력한 사진파일입니다.">
                                 </c:otherwise>
                              </c:choose>
                           </div>
                        </div>
                     </c:forEach>
                  </div>
               </div>
            </c:if>
         </div>
      </div>
   </section>
   <!-- 글 상세페이지 파일출력섹션 종료 -->

   <!-- 글 상세페이지 메인섹션 시작 -->
   <section class="blog-details-section">
      <div class="container">
         <div class="row">
            <div class="col-lg-10 offset-lg-1">
               <div class="blog-details-text">
                  <div class="bd-title">
                     <!-- 블로그 내용 -->
                     <p>${board.board_content}</p>
                  </div>
                  <div class="tag-share"
                     style="display: flex; justify-content: space-between;">
                     <div class="tags">
                        <div class="button-container">
                           <!-- 좋아요 버튼 -->
                           <div>
                              <button id="likeBtn" class="likeButton"
                                 data-post-id="${board.board_num}"
                                 style="background-color: ${like_member == 'true' ? 'red' : '#87CEEB'};">
                                 👍 좋아요 (<span id="likeCount">${board.board_like_cnt}</span>)
                              </button>
                           </div>
                           <div>
                              <input id="like_member" value="${member_id}"
                                 style="visibility: hidden;">
                              <!-- 로그인한 사용자와 작성자가 같은 경우 -->
                           </div>
                        </div>
                     </div>
                     <div class="tags">
                        <div class="button-container">
                           <div style="display: flex">
                              <c:if
                                 test="${member_id == board.board_writer_id or member_role eq 'ADMIN'}">
                                 <!-- 글삭제 버튼 -->
                                 <div>
                                    <button class="deleteButton likeButton"
                                       data-board-num="${board.board_num}">❌삭제</button>
                                 </div>
                              </c:if>
                              <c:if test="${member_id == board.board_writer_id }"> 
                              <button class="likeButton" onclick="location.href='updateBoard.do?board_num=${board.board_num}'">📝게시글 수정</button>
                                    </c:if>
                              <c:if
                                 test="${member_id != board.board_writer_id && member_role != 'ADMIN'}">
                                 <div>
                                    <button class="likeButton"
                                       onclick="location.href='claim.do?claim_board_num=${board.board_num}&claim_target_member_id=${board.board_writer_id}'">🚨신고</button>
                                 </div>
                              </c:if>
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="comment-option">
                     <h4>댓글</h4>
                     <c:if test="${empty replyList}">
                        <p>작성된 댓글이 없습니다.</p>
                     </c:if>
                     <!-- 댓글 반복 -->
                     <c:forEach var="reply" items="${replyList}">
                        <div class="single-comment-item"
                           style="display: flex; justify-content: space-between;">
                           <div>
                              <div class="sc-author">
                                 <!-- 댓글 작성자 프로필 이미지 -->
                                 <img src="img/profile/${reply.reply_member_profile}"
                                    alt="댓글 작성자 프로필 이미지">
                              </div>
                              <div class="sc-text">
                                 <input type="text" id="replyNum" value="${reply.reply_num}" style="display:none;"/>
                                 <!-- 댓글 작성일자 -->
                                 <input type="text" value="${reply.reply_registration_date}" readonly/>
                                 <!-- 댓글 작성자 -->
                                 <input type="text" value="${reply.reply_writer_id}" readonly/>
                                 <!-- 댓글 내용 -->
                                 <input type="text" id="editReplyInput"value="${reply.reply_content}" style="font-size:25px" readonly/>
                                 <!-- 댓글 삭제 (비동기X)-->
                              </div>
                           </div>
                           <div style="display: flex; justify-content: end;">
                              <c:if test="${empty member_id}">

                              </c:if>
                              <c:if test="${not empty member_id}">
                                 <div>
                                    <c:if
                                       test="${member_id == reply.reply_writer_id or member_role eq 'ADMIN'}">
                                       <div>
                                          <button class="likeButton comment-btn"
                                             data-board-num="${board.board_num}"
                                             data-reply-num="${reply.reply_num}">❌댓글삭제</button>
                                       </div>
                                       <c:if test="${member_id == reply.reply_writer_id }"> 
                                    <button id="editButton" class="likeButton" >📝댓글수정</button>
                                    <button id="submitEditButton" class="likeButton" style="display:none;">📝수정완료</button>
                                    
                                    </c:if>
                                    </c:if>
                                    
                                       <c:if
                                          test="${member_id != reply.reply_writer_id && member_role != 'ADMIN'}">
                                          <div>
                                             <button class="likeButton"
                                                onclick="location.href='claim.do?claim_reply_num=${reply.reply_num}&claim_target_member_id=${reply.reply_writer_id}';">🚨댓글신고</button>
                                          </div>
                                       </c:if>
                                 </div>
                              </c:if>
                           </div>
                        </div>
                     </c:forEach>
                  </div>
                  <div class="leave-comment">
                     <h4>댓글 작성</h4>
                     <form action="writeReply.do" class="comment-form" method="POST">
                        <div class="row">
                           <div class="col-lg-12 text-center">
                              <input type="hidden" name="board_num"
                                 value="${board.board_num}" />
                              <!-- 댓글 내용 입력 -->
                              <textarea name="reply_content" placeholder="댓글 내용"></textarea>
                              <!-- 댓글 작성 버튼 -->
                              <button type="submit" class="site-btn">댓글 작성</button>
                           </div>
                        </div>
                     </form>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </section>
   <!-- 글 상세페이지 메인섹션 종료 -->



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

   <!-- 추가 Js 플러그인 -->
   <script src="js/board/like.js"></script>
   <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
   <script>
$(document).ready( function() {
         console.log("문서가 준비되었습니다.");

         // .editButton 클래스를 가진 모든 버튼에 클릭 이벤트 추가
         $('.deleteButton').on( 'click',function() {
         console.log("삭제 버튼 눌림");
         var flag = confirm("정말로 이 게시글을 삭제하시겠습니까?");
         if (flag) {
          // 확인을 클릭한 경우, 삭제 요청을 서버로 전송
          var boardNum = $(this).data('board-num'); // data-board-num에서 가져오기
          window.location.href = "deleteBoard.do?board_num="+boardNum;
         }
         });
         
    // .editButton 클래스를 가진 모든 버튼에 클릭 이벤트 추가
   // jQuery를 사용하여 이벤트 처리
   $('#editButton').on('click',  function () {
       console.log("수정 버튼 눌림");
       // 읽기 전용 해제 및 테두리 스타일 변경
       document.getElementById("editReplyInput").readOnly = false;
       document.getElementById("editReplyInput").style.border = "1px solid #ccc";
       document.getElementById("editButton").style.display = "none";
       document.getElementById("submitEditButton").style.display = "block";
   });
   
   // '수정완료' 버튼 클릭 시 데이터 전송
   $('#submitEditButton').on('click', function () {
    const editReplyInputValue = document.getElementById("editReplyInput").value;
    const replyNum = document.getElementById("replyNum").value;
    
       $.ajax({
           type: "POST",
           url: "updateReply.do",
           contentType: 'application/json',
           data: JSON.stringify({ "reply_num": replyNum, "reply_content": editReplyInputValue }),
           dataType: 'json',
           success: function(flag) {
               console.log("Response data:", flag);
               console.log("Response typeof data:", typeof flag);
               
               // Boolean 응답을 직접 확인합니다.
               if (flag === true) { 
                   location.reload(); // 페이지 새로고침
               } else {
                   alert("수정에 실패했습니다.");
               }
           },
           error: function(error) {
               console.log("error:", error);
           }
       });
   });


         $('.comment-btn').on('click',
            function() {
               console.log("삭제 버튼 눌림");
               var flag = confirm("정말로 이 댓글을 삭제하시겠습니까?");
               if (flag) {
                  // 확인을 클릭한 경우, 삭제 요청을 서버로 전송
                  var board_num = $(this)
                        .data('board-num'); // data-board-num에서 가져오기
                  var reply_num = $(this)
                        .data('reply-num'); // data-reply-num에서 가져오기
                  window.location.href = "deleteReply.do?reply_num="
                        + reply_num
                        + "&board_num="
                        + board_num;
         }
            });
      });
   </script>

</body>

</html>