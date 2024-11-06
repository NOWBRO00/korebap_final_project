<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>로그인 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">

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
			<br><div><h2>로그인</h2></div><hr>
				<!-- 로그인 form -->
				<form action="login.do" method="POST">
					<!-- 이메일(아이디) 입력 -->
					<div class="input-group mb-3">
						<input type="email" name="member_id" class="form-control"
							placeholder="이메일을 입력해주세요." required>
					</div>

					<!-- 비밀번호 입력 -->
					<div class="input-group mb-3">
						<input type="password" name="member_password" class="form-control"
							placeholder="비밀번호를 입력해주세요." autocomplete="password" required>
					</div>

					<!-- 로그인 버튼 -->
					<div class="d-grid gap-2 col-6 mx-auto">
						<input type="submit" class="btn btn-primary" value="로그인">
					</div>
				</form>

				<!-- 회원가입 페이지로 이동 -->
				<div class="text-center mt-4 fw-light">
					회원이 아니신가요?<a href="joinPage.do" class="text-primary">회원가입</a>
				</div>
				<br><br>
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
