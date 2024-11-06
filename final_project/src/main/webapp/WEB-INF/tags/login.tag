<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/style.css" type="text/css">

<!-- member_id가 비어있지 않다면 -->
<!-- member_role이 'USER'일 때 -->
<!-- member_role이 'ADMIN'일 때 -->
<!-- member_id가 비어있다면 -->
<c:choose>
    <c:when test="${not empty member_id}">
        <c:choose>
            <c:when test="${member_role eq 'USER'or member_role eq'OWNER'}">
                <a href="mypage.do" class="bk-btn">마이페이지</a>
                <a href="logout.do" class="bk-btn">로그아웃</a>
            </c:when>

            <c:when test="${member_role eq 'ADMIN'}">
                <a href="managerMain.do" class="bk-btn">관리자 홈</a>
                <a href="logout.do" class="bk-btn">로그아웃</a>
            </c:when>
        </c:choose>
    </c:when>

    <c:otherwise>
        <a href="join.do" class="bk-btn">회원가입</a>
        <a href="login.do" class="bk-btn"> 로그인 </a>
    </c:otherwise>
</c:choose>

