<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!-- 여기에서 사용자 경험치 값을 설정합니다. -->
    <c:set var="member_level" value="${board}" />

    <!-- 경험치에 따라 이모지를 표시하는 부분 -->
    <div class="emoji">
        <c:choose>
            <!-- 레 8 이상인 경우 -->
            <c:when test="${member_level >= 8}">
                &#x1F433; ${board.member_nickname} &#x1F433;
            </c:when>
            <!-- 레벨이 7 이상 6 미만인 경우 -->
            <c:when test="${member_level >= 7}">
                &#x1F988; ${board.member_nickname} &#x1F988;
            </c:when>
            <!-- 레벨이 6 이상 5 미만인 경우 -->
            <c:when test="${member_level >= 6}">
                &#x1F42C; ${board.member_nickname} &#x1F42C;
            </c:when>
            <!-- 레벨이 5 이상 4 미만인 경우 -->
            <c:when test="${member_level >= 5}">
                &#x1F419; ${board.member_nickname} &#x1F419;
            </c:when>
            <!-- 레벨이 4 이상 3 미만인 경우 -->
            <c:when test="${member_level >= 4}">
                &#x1F420; ${board.member_nickname} &#x1F420;
            </c:when>
            <!-- 레벨이 3 이상 2 미만인 경우 -->
            <c:when test="${member_level >= 3}">
                &#x1F99E; ${board.member_nickname} &#x1F99E;
            </c:when>
            <!-- 레벨이 2 이상 1 미만인 경우 -->
            <c:when test="${member_level >= 2}">
                &#x1F990; ${board.member_nickname} &#x1F990;
            </c:when>
            <!-- 레벨이 1 미만인 경우 -->
            <c:otherwise>
                &#x1F41A; ${board.member_nickname} &#x1F41A;
            </c:otherwise>
        </c:choose>
    </div>
<!-- 🐚🦐🦞🐠🐙🐬🦈🐳👷‍♂️⚓ -->    