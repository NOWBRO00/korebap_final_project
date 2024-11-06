<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>글 수정</title>

<!-- Google Fonts 링크 -->
<link
   href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
   rel="stylesheet">
<link
   href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
   rel="stylesheet">

<!-- 스타일 시트 링크 -->
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
}
/* 삭제 버튼의 스타일 */
.delete-button {
   color: red; /* 텍스트 색상을 빨간색으로 설정 */
   border: none; /* 테두리 제거 */
   background: none; /* 배경 제거 */
   cursor: pointer; /* 커서를 포인터로 변경 */
   font-size: 16px; /* 폰트 크기 설정 */
}
</style>
</head>
<body>
   <!-- 헤더를 포함하는 부분 -->
   <c:import url="header.jsp"></c:import>

   <!-- 글 수정 페이지 시작 섹션 -->
   <section class="blog-details-hero set-bg"
      data-setbg="img/board/boardBasic.png">
      <div class="container">
         <div class="row">
            <div class="col-lg-10 offset-lg-1">
               <div class="bd-hero-text">
                  <!-- 게시판 카테고리 & 기능 -->
                  <span>자유 게시판</span>
                  <!-- 게시판 카테고리 표시 -->
                  <h2>게시물 수정하기</h2>
                  <!-- 페이지 제목 -->
               </div>
            </div>
         </div>
      </div>
   </section>
   <!-- 글 수정 페이지 시작 섹션 종료 -->

   <!-- 글 수정 페이지의 메인 섹션 시작 -->
   <section class="blog-details-section">
      <div class="container">
         <div class="row">
            <div class="col-lg-10 offset-lg-1">
               <div class="blog-details-text">
                  <div class="leave-comment">
                     <!-- 글 수정 폼 시작 -->
                     <h4>글 수정</h4>
                     <form action="updateBoard.do" method="post"
                        enctype="multipart/form-data" class="comment-form">
                        <!-- 게시물 번호를 숨겨서 전송 (수정할 게시물을 식별하기 위해 사용) -->
                        <input type="hidden" name="board_num"
                           value="${boardDTO.board_num}">
                        <div class="row">
                           <div class="col-lg-12 text-center">
                              <!-- 글 제목 입력 필드 -->
                              <input type="text" id="title" name="board_title" required
                                 placeholder="글제목을 작성해주세요" value="${board.board_title}">
                              <!-- 파일 업로드 입력 필드 (여러 개의 이미지 파일을 허용) -->
                              <input type="file" id="files" name="files" multiple
                                 accept="image/*" />
                              <!-- 선택된 파일 이름을 표시할 영역 -->
                              <div class="file-display" id="fileList"></div>
                              <br>
                              <!-- 기존 이미지 표시 및 삭제 버튼 추가 -->
                              <div class="existing-images">
                                 <!-- 기존 이미지 목록을 표시 -->
                                 <div class="file-display">
                                    <div class="image-container"
                                       style="position: relative; display: inline-block; margin-right: 10px;">
                                       <!-- 기존 이미지 표시 -->
                                       <c:forEach var="file" items="${fileList}">
                                          <div class="ts-item">
                                             <div class="bd-pic">
                                                <c:choose>
                                                   <c:when test="${fn:startsWith(file.file_dir, 'http')}">
                                                      <img src="${file.file_dir}" alt="상품 이미지입니다.">
                                                   </c:when>
                                                   <c:otherwise>
                                                      <img src="img/product/${file.file_dir}"
                                                         alt="상품설명을 위해 사장님이 넣은 사진파일입니다.">
                                                   </c:otherwise>
                                                </c:choose>
                                             </div>
                                          </div>
                                       </c:forEach>
                                       <c:if test="${not empty file.file_dir}">
                                          <img src="${file.file_dir}" alt="기존 이미지"
                                             style="width: 100px; height: auto;">
                                          <!-- 이미지 삭제 버튼 (삭제 요청 시 이미지 URL 전송) -->
                                          <button type="button" class="delete-button"
                                             data-image="${file.file_dir}">삭제</button>
                                       </c:if>
                                    </div>
                                 </div>
                              </div>
                              <br>
                              <!-- 글 내용 입력 필드 -->
                              <textarea id="content" name="board_content" rows="10" required
                                 placeholder="글내용을 작성해주세요">${board.board_content}</textarea>
                              <!-- 제출 버튼 -->
                              <input type="submit" value="글수정" class="site-btn" />
                           </div>
                        </div>
                     </form>
                     <!-- 글 수정 폼 종료 -->
                  </div>
               </div>
               <br>
            </div>
         </div>
      </div>
   </section>
   <!-- 글 수정 페이지의 메인 섹션 종료 -->

   <!-- 푸터를 포함하는 부분 -->
   <c:import url="footer.jsp"></c:import>

   <!-- Js 플러그인 및 스크립트 파일 링크 -->
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
   <script>
        // 페이지가 로드된 후 실행되는 코드
        document.addEventListener('DOMContentLoaded', function() {
            const deleteButtons = document.querySelectorAll('.delete-button'); // 삭제 버튼(CSS포함)들 가져오기
            deleteButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const imageUrl = button.getAttribute('data-image'); // 삭제할 이미지 URL 가져오기
                    // 이미지 삭제 확인 대화상자
                    if (confirm('정말로 이 이미지를 삭제하시겠습니까?')) {
                        // 이미지 삭제 요청을 서버로 전송
                        fetch('deleteImage.do', {
                            method: 'POST', // HTTP POST 메서드 사용
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded' // 요청 본문 형식 설정
                            },
                            body: new URLSearchParams({
                                'imageUrl': imageUrl, // 삭제할 이미지 URL
                                'boardNum': document.querySelector('input[name="board_num"]').value // 게시물 번호
                            })
                        })
                        .then(response => response.json()) // 응답을 JSON으로 변환
                        .then(data => {
                            if (data.success) { // 삭제가 성공했는지 확인
                                button.parentElement.remove(); // 성공하면 해당 이미지 요소 삭제
                            } else {
                                // 삭제 실패 시 알림
                                alert('이미지 삭제에 실패했습니다.');
                            }
                        });
                    }
                });
            });
        });
    </script>
</body>
</html>
