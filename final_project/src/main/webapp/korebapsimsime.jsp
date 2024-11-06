<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="js/jquery-3.3.1.min.js"></script>

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">

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
<link rel="stylesheet" href="css/kosimeCss.css" type="text/css">
<title>고래밥 심심이</title>

</head>
<body>
<c:import url="header.jsp"></c:import>

<div id="background" class="kosimePage">
	<div class="container kosimePageContainer">
		<div class="row simsimeHeaderRow">
			<div class="col-lg-10 offset-lg-1">
				<div class="bd-hero-text">
					<h1>⚓낚시 천재 고심이⚓</h1>
				</div>
			</div>
		</div>
		<div class="row simsimeRow">
			<div class="col-lg-10 offset-lg-1">
				<div class="bd-hero-text">
					<h3>천재 낚시꾼 고심이(고래밥 심심이)에게 낚시 꿀팁을 물어보세요.</h3>
					<p class="mt-2">고심이와의 대화 내용은 저장되지 않습니다.</p>
				</div>
			</div>
		</div>
		<div id="chat-container" class="container simsimeContainer">
		    <div id="chat-messages"></div>
		    <div id="user-input">
		        <input type="text" placeholder="메시지를 입력하세요..." />
		        <button>전송</button>
		    </div>
		</div>
	</div>
</div>
<c:import url="footer.jsp"></c:import>

<script src="js/chatbot.js"></script>
</body>
</html>