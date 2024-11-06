<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<!-- 주소 API를 사용하기 위한 연결 -->
<script
   src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 분리한 js파일 연결 -->
<script src="js/member/addressAPI.js"></script>
<script src="js/member/checkEmail.js"></script>
<script src="js/member/checkName.js"></script>
<script src="js/member/checkNickname.js"></script>
<script src="js/member/checkPassword.js"></script>
<script src="js/member/checkPhone.js"></script>
</head>

<body>
   <!-- 헤더 -->
   <jsp:include page="header.jsp" />
   <br>
   <!-- 내용 -->
   <div class="container text-center">
      <div class="row">
         <div class="col-2"></div>
         <div class="col-8 card" style="background-color: #ffffff">
         <br><div><h2>회원가입</h2></div><hr>
            <!-- 회원 가입 form -->
            <form action="join.do" method="POST">
               <!-- 개인 회원/ 사장님 선택 -->
               <input type="radio" name="member_role" class="btn-check" checked
                  autocomplete="off" value="USER" required> <label class="btn"
                  for="btn-check-5">개인</label> <input type="radio"
                  name="member_role" class="btn-check" autocomplete="off" value="OWNER"> <label
                  class="btn" for="btn-check-5">사장님</label>

               <!-- 이메일 중복검사, 이메일 인증 -->
               <div class="input-group mb-3">
                  <input type="text" name="member_id" id="email"
                     class="form-control" placeholder="이메일 주소를 입력해주세요."
                     autocomplete="email" required>
                  <button type="button" id="sendVeriBtn"
                     class="btn btn-outline-secondary" style="width: 140px">이메일
                     발송</button>
               </div>
               <span id="emailMsg"></span>
               <div class="input-group mb-3">
                  <input type="text" name="varicationrNumber" id="veriNum"
                     class="form-control" placeholder="인증번호를 입력해주세요."
                     autocomplete="veriNum" required>
                  <button type="button" id="checkVeriBtn"
                     class="btn btn-outline-secondary" style="width: 140px">인증
                     완료</button>
               </div>

               <!-- 비밀번호 일치 확인 -->
               <!-- autocomplete="new-password": 새 비밀번호를 입력할 때 사용한다. 
               브라우저가 이 필드에 기존 비밀번호를 자동으로 채우지 않도록한다. -->
               <div class="input-group mb-3">
                  <input type="password" name="member_password" id="password"
                     class="form-control" placeholder="비밀번호를 입력해주세요."
                     autocomplete="new-password" required>
               </div>
               <div class="input-group mb-3">
                  <input type="password" id="confirmPassword" class="form-control"
                     placeholder="비밀번호를 한번 더 입력해주세요." autocomplete="new-password"
                     required>
               </div>
               <span id="passwordMsg"></span>

               <!-- 이름 입력 -->
               <!-- autocomplete="username" : 브라우저에게 이 필드가 사용자 이름을 위한 것임을 알려준다. -->
               <div class="input-group mb-3">
                  <input type="text" name="member_name" id="name"
                     class="form-control" placeholder="이름을 입력해주세요."
                     autocomplete="username" required>
               </div>
               <span id="nameMsg"></span>

               <!-- 닉네임 입력 -->
               <div class="input-group mb-3">
                  <input type="text" name="member_nickname" id="nickname"
                     class="form-control" placeholder="닉네임을 입력해주세요." required>
               </div>
               <span id="nicknameMsg"></span>

               <!-- 전화번호 입력 -->
               <div class="input-group mb-3">
                  <input type="text" name="member_phone" id="phone"
                     class="form-control" placeholder="-을 제외한 전화번호 11자리를 입력해주세요."
                     required>
               </div>
               <span id="phoneMsg"></span>

               <!-- 주소 입력 -->
               <!-- 우편 번호 -->
               <div class="input-group mb-3">
                  <input type="text" name="postcode" id="postcode"
                     class="form-control" placeholder="우편 번호" readonly required>
                  <button class="btn btn-outline-secondary" type="button"
                     id="addressBtn" style="width: 140px">주소 찾기</button>
               </div>
               <!-- 기본 주소 -->
               <div class="input-group mb-3">
                  <input type="text" name="address" id="address"
                     class="form-control" placeholder="주소" readonly required>
               </div>
               <!-- 추가 주소 -->
               <div class="input-group mb-3">
                  <input type="text" name="extraAddress" id="extraAddress"
                     class="form-control" placeholder="추가 주소" readonly required>
               </div>
               <!-- 상세 주소 -->
               <div class="input-group mb-3">
                  <input type="text" name="detailAddress" id="detailAddress"
                     class="form-control" placeholder="상세 주소" required>
               </div>

               <!-- 제출 -->
               <div class="d-grid gap-2 col-6 mx-auto">
                  <input type="submit" id="submitBtn" class="btn btn-primary"
                     value="회원가입"><br><br>
               </div>
            </form>
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