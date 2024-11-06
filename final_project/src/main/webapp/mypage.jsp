<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<!-- 주소 API를 사용하기 위한 연결 -->
<script
   src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 분리한 js파일 연결 -->
<script src="js/member/checkName.js"></script>
<script src="js/member/checkNickname.js"></script>
<script src="js/member/checkPassword.js"></script>
<script src="js/member/mypageCheckPassword.js"></script>
<script src="js/member/mypageCheckImage.js"></script>
<script src="js/member/mypage.js"></script>

<style type="text/css">
/* 회원탈퇴, 비밀번호 변경 모달팝업 스타일 */
#passwordModal, #passwordCheckModal, #passwordChangeModal,
   #newPasswordModal {
   display: none;
   position: fixed;
   z-index: 1;
   left: 0;
   top: 0;
   width: 100%;
   height: 100%;
   background-color: rgba(0, 0, 0, 0.4);
}

#modalContent {
   background-color: #fefefe;
   margin: 15% auto;
   padding: 20px;
   border: 1px solid #888;
   width: 500px;
}
</style>
</head>
<body>
   <!-- 헤더 -->
   <c:import url="header.jsp"></c:import>
   <br>
   <!-- 내용 -->
   <div class="container text-center">
      <div class="row">
         <div class="card">
            <div class="card-body">
               <br>
               <div>
                  <h2>마이페이지</h2>
               </div>
               <hr>
               <div class="row g-0">
                  <div class="col-md-4" style="width: 50rem;">
                     <div class="card-body">
                        <form id="imageChangeForm" action="updateProfile.do"
                           method="POST" enctype="multipart/form-data">
                           <img src="img/profile/${member.member_profile}"
                              class="rounded-circle mx-auto d-block" alt="프로필 사진" ><br>
                           <img id="thumbnail" alt="미리보기" style="display: none"> <span
                              id="fileErrorMsg" style="color: red;"></span><br> <input
                              type=file name="member_profile" id="profile"
                              style="display: none"> <span id="fileMsg"
                              style="color: red;"></span> <br>

                           <button type="button" class="btn btn-outline-secondary"
                              style="width: 140px"
                              onclick="document.getElementById('profile').click()">사진
                              변경</button>
                           <input type=submit id="profileSubmitBtn" style="display: none">
                           <button id="imageSubmitBtn" type="button"
                              class="btn btn-outline-secondary" style="width: 140px"
                              onclick="document.getElementById('profileSubmitBtn').click()">사진
                              선택 완료</button>
                        </form>
                     </div>
                  </div>
                  <div class="col-md-8" style="width: 100rem; ">
                     <div class="card-body">
                        <div class="input-group mb-3"
                           style="display: flex; justify-content: center; align-items: center; height: 50px;">
                           <h3>${member.member_id}</h3>
                        </div>
                        <!-- 이름 변경 -->
                        <form action="updateMypage.do" method="POST" id="MypageForm">
                           <div class="input-group mb-3">
                              <input type="text" name="member_name" id="name"
                                 class="form-control" placeholder="${member.member_name}">
                           </div>
                           <span id="nameMsg"></span>
                           <!-- 닉네임 변경 -->
                           <div class="input-group mb-3">
                              <input type="text" name="member_nickname" id="nickname"
                                 class="form-control" placeholder="${member.member_nickname}">
                           </div>
                           <span id="nicknameMsg"></span>
                        </form>
                        <!-- 전화번호 변경 -->
                        <div class="input-group mb-3">
                           <input type="text" name="member_phone" id="phone"
                              class="form-control" placeholder="${member.member_phone}"
                              readonly>
                        </div>
                        <!-- 주소 변경 -->
                        <div class="input-group mb-3">
                           <input type="text" name="member_postcode" id="postcode"
                              class="form-control" placeholder="${member.member_postcode}"
                              readonly>
                        </div>
                        <!-- 기본 주소 -->
                        <div class="input-group mb-3">
                           <input type="text" name="member_address" id="address"
                              class="form-control" placeholder="${member.member_address}"
                              readonly>
                        </div>
                        <!-- 추가 주소 -->
                        <div class="input-group mb-3">
                           <input type="text" name="member_extraAddress" id="extraAddress"
                              class="form-control"
                              placeholder="${member.member_extraAddress}" readonly>
                        </div>
                        <!-- 상세 주소 -->
                        <div class="input-group mb-3">
                           <input type="text" name="member_detailAddress"
                              id="detailAddress" class="form-control"
                              placeholder="${member.member_detailAddress}" readonly>
                        </div>
                        <!-- 이름, 닉네임 변경 -->
                        <button type="submit" id="submitBtn"
                           class="btn btn-outline-secondary" style="width: 140px">변경
                           완료</button>
                        <!-- 비밀번호 변경 -->
                        <button type="button" class="btn btn-outline-secondary"
                           style="width: 140px"
                           onclick="document.getElementById('passwordCheckModal').style.display='block'">비밀번호
                           변경</button>
                        <!-- 회원탈퇴 -->
                        <button type="button" class="btn btn-outline-secondary"
                           style="width: 140px"
                           onclick="document.getElementById('passwordModal').style.display='block'">회원
                           탈퇴</button>
                     </div>
                  </div>
               </div>




               <!-- 회원 탈퇴 비밀번호 확인 팝업 (모달창) -->
               <div id="passwordModal">
                  <div id="modalContent">
                     <form id="deleteMemberForm" method="POST"
                        action="deleteMember.do">
                        비밀번호를 입력하세요.<br> <input type="password"
                           class="checkPassword" name="member_password" autoComplete="off"><br>
                        <span class="errorMsg"></span><br>
                        <button id="deleteMemberBtn" type="submit"
                           class="btn btn-outline-secondary"
                           style="width: 140px; display: inline;">확인</button>
                        <button type="button" class="btn btn-outline-secondary"
                           style="width: 140px; display: inline;"
                           onclick="document.getElementById('passwordModal').style.display='none'">취소</button>
                        <br>
                     </form>
                  </div>
               </div>



               <!-- 비밀번호 변경 비밀번호 확인 팝업 (모달창) -->
               <div id="passwordCheckModal">
                  <!-- 비밀번호 확인 -->
                  <div id="modalContent">
                     <form>
                        비밀번호를 입력하세요.<br> <input type="password"
                           class="checkPassword" name="member_password" autoComplete="off"><br>
                        <span class="errorMsg"></span><br>
                        <button id="checkPasswordBtn" type="button"
                           class="btn btn-outline-secondary"
                           style="width: 140px; display: inline;"
                           onclick="document.getElementById('newPasswordModal').style.display='block'">확인</button>
                        <button type="button" class="btn btn-outline-secondary"
                           style="width: 140px; display: inline;"
                           onclick="document.getElementById('passwordCheckModal').style.display='none'">취소</button>
                     </form>
                  </div>
               </div>


               <!-- 비밀번호 변경 (모달창) -->
               <div id="newPasswordModal">
                  <div id="modalContent">
                     <form id="passwordChangeForm" method="POST"
                        action="updatePassword.do">
                        변경할 비밀번호: <input type="password" id="password"
                           name="member_password" autoComplete="off"><br>
                        비밀번호 확인: <input type="password" id="confirmPassword"
                           autoComplete="off"><br> <span id="passwordMsg"></span><br>
                        <button type="submit" class="btn btn-outline-secondary"
                           style="width: 140px;">변경</button>
                        <button type="button" class="btn btn-outline-secondary"
                           style="width: 140px; display: inline;"
                           onclick="document.getElementById('newPasswordModal').style.display='none'; document.getElementById('passwordCheckModal').style.display='none';">취소</button>
                     </form>
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