<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="wishlist" type="java.lang.Object" %>
<%@ attribute name="wishlist_page_count" type="java.lang.Integer" %>

<!-- 사용자 입력 파일이 비어있지 않다면 -->
<c:if test="${not empty wishlist.wishlist_file_dir}">
	<div class="wishlist blog-item" style="position: relative;">
    	 <!-- 상품 이미지 -->
        <a href="productDetail.do?meber_id=${member_id}&product_num=${wishlist.wishlist_product_num}">
            <div class="wishlist-image">
                <img src="${wishlist.wishlist_file_dir}" alt="상품설명을 위해 사장님이 넣은 사진파일입니다.">
            	<!-- 상품 정보 박스 (사진 위에 표시) -->
		        <div class="wishlist-info-overlay">
		            <!-- 상품 장소(바다,민물) -->
		            <div class="pl-tag">${wishlist.wishlist_product_location}</div>
		            <!-- 상품 유형(낚시배, 낚시터,낚시카페,수상) -->
		            <div class="pc-tag">${wishlist.wishlist_product_category}</div>
		        </div>
            </div>
        </a>
    	
    	<!-- 상품 정보 박스 (사진 아래에 표시) -->
    	<div>
	    	<div class="pi-text">
				<!-- 상품명 및 링크 -->
				<h4><a href="productDetail.do?meber_id=${member_id}&product_num=${wishlist.wishlist_product_num}">${wishlist.wishlist_product_name}</a></h4>	
				<!-- 상품가격 -->
				<div class="p-price">	
					<fmt:formatNumber value="${wishlist.wishlist_product_price}" type="number" groupingUsed="true"/>원
				</div>
				<!-- 상품 주소 -->
				<div class="p-address">${wishlist.wishlist_product_address}</div>
	        </div>
	        <!-- 이미지 위에 삭제 버튼 -->
	        <button class="btn-remove" data-num="${wishlist.wishlist_num}"><i class="icon_heart"></i></button>
		</div>
	</div>
</c:if>

<!-- 사용자 입력 파일이 비어있다면 -->
<c:if test="${empty wishlist.wishlist_file_dir}">
	<div class="wishlist blog-item" style="position: relative;">
    	<!-- 상품 이미지 (이미지를 클릭하면 링크로 이동) -->
        <a href="productDetail.do?meber_id=${member_id}&product_num=${wishlist.wishlist_product_num}">
            <div class="wishlist-image">
                <img src="img/blog/boardBagic.png" alt="기본 상품 이미지입니다.">
	            <!-- 상품 정보 박스 (사진 위에 표시) -->
		    	<div class="wishlist-info-overlay">
		    	    <!-- 상품 장소(바다,민물) -->
		    	    <div class="pl-tag">${wishlist.wishlist_product_location}</div>
		    	    <!-- 상품 유형(낚시배, 낚시터,낚시카페,수상) -->
		    	    <div class="pc-tag">${wishlist.wishlist_product_category}</div>
		    	</div>
            </div>
        </a>
    	
    	<!-- 상품 정보 박스 (사진 아래에 표시) -->
    	<div>
	    	<div class="pi-text">
				<!-- 상품명 및 링크 -->
				<h4><a href="productDetail.do?meber_id=${member_id}&product_num=${wishlist.wishlist_product_num}">${wishlist.wishlist_product_name}</a></h4>	
				<!-- 상품가격 -->
				<div class="p-price">	
					<fmt:formatNumber value="${wishlist.wishlist_product_price}" type="number" groupingUsed="true"/>원
				</div>
				<!-- 상품 주소 -->
				<div class="p-address">${wishlist.wishlist_product_address}</div>
	        </div>
	        <!-- 이미지 위에 삭제 버튼 -->
	        <button class="btn-remove" data-num="${wishlist.wishlist_num}"><i class="icon_heart"></i></button>
		</div>
	</div>
</c:if>


<input type="hidden" id="page_count" data-wishlist-page-count="${wishlist_page_count}" >

