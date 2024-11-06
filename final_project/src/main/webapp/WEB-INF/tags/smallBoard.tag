<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 사용자 입력 파일이 비어있지 않다면 -->
<!--<c:if test="${not empty file.file_images}">-->
	<div class="blog-item set-bg" data-setbg="${board.board_images}">
    	<div class="bi-text">
    		<!-- 게시판 종류 -->
            <span class="b-tag">자유게시판</span>
            <!-- 게시판 좋아요 개수 -->
            <div class="b-tag">${board.board_like_cnt}</div>
            <!-- 게시판 제목 및 링크 -->
            <h4><a href="boardDetail.do?meber_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a></h4>
            <!-- 게시판 작성 날짜 -->
            <div class="b-time"><i class="icon_clock_alt"></i>${board.board_registration_date}</div>
        </div>
	</div>
<!--</c:if>-->

<!-- 사용자 입력 파일이 비어있다면 -->
<!--<c:if test="${empty file.file_images}">-->
	<div class="blog-item set-bg" data-setbg="img/blog/boardBagic.png">
		<div class="bi-text">
			<!-- 게시판 종류 -->
			<span class="b-tag">자유게시판</span>
			<!-- 게시판 좋아요 개수 -->
			<div class="b-tag">${board.board_like_cnt}</div>
			<!-- 게시판 제목 및 링크 -->
			<h4><a href="boardDetail.do?meber_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a></h4>
			<!-- 게시판 작성 날짜 -->
			<div class="b-time"><i class="icon_clock_alt"></i>${board.board_registration_date}</div>
		</div>
	</div>
<!--</c:if>-->