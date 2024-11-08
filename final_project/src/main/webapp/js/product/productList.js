$(document).ready(function() {
    console.log("문서 로드 완료");
   
    var totalPage = $('#product_total_page').data('product_page_count'); // 전체 페이지 수
    var productTaypes = $('#productTypes').data('product_types');
    var productCategories = $('#productCategories').data('product_categories');

    // 페이지네이션 클릭 이벤트   
    $('.pagination').on('click', '.page-link', function() {
        console.log("페이지네이션 클릭 이벤트");

        var currentPage = $(this).data('page');
        var searchOption = $('input[type="radio"][name="searchOption"]:checked').val(); // 선택된 정렬 옵션

        console.log("currentPage [" + currentPage + "]");
        loadProducts(currentPage, searchOption); // 클릭한 페이지 번호로 로드
    });

    // 정렬 옵션 라디오 버튼 클릭 이벤트
    $('input[type="radio"][name="searchOption"]').on('change', function() {
        var searchOption = $(this).val(); // 선택된 정렬 기준 가져오기
        loadProducts(1, searchOption); // 첫 페이지에서 정렬 적용
    });

    // 검색 버튼 클릭 이벤트 처리
    $('.search-button').on('click', function(e) {
        e.preventDefault();
        loadProducts(1); // 첫 페이지에서 시작
    });

    // 상품 로드 함수
    function loadProducts(currentPage, searchOption) {
      var searchKeyword = $('#product_searchKeyword').val(); 
        var productLocation = $('#product_location').val();
        var productCategory = $('#product_category').val();

        console.log("totalPage [" + totalPage + "]");
        console.log("searchKeyword [" + searchKeyword + "]");
        console.log("productLocation [" + productLocation + "]");
        console.log("productCategory [" + productCategory + "]");
        console.log("productTaypes [" + productTaypes + "]");
        console.log("productCategories [" + productCategories + "]");

        // AJAX 요청
        $.ajax({
            url: 'productList.do',
            type: 'GET',
            data: {
                "totalPage": totalPage,
                "currentPage": currentPage,
                "product_searchKeyword": searchKeyword,
                "product_search_criteria": searchOption,
                "product_location": productLocation,
                "product_category": productCategory,
                "product_types": productTaypes,
                "product_categories": productCategories
            },
            dataType: 'json',
            success: function(data) {
                console.log("ajax요청 성공 반환");
                console.log("AJAX 요청 성공, 반환된 데이터: ", data);
                let productHTML = '';

                if (data.product) {
                    // 일반 상품 처리
                    const product = data.product;
                    const imgSrc = (product.product_file_dir && product.product_file_dir.startsWith("http")) 
                        ? product.product_file_dir  // 이미 http로 시작하는 절대경로일 경우 그대로 사용
                        : (product.product_file_dir && product.product_file_dir !== "null" && product.product_file_dir !== "") 
                            ? "img/product/" + product.product_file_dir  
                            : 'img/board/boardBasic.png';  // null 또는 빈 값일 경우 기본 이미지

                    productHTML += `
                        <div class="col-md-4">
                            <img alt="상품사진" class="blog-item set-bg" src="${imgSrc}">
                            <div class="bi-text">
                                <span class="b-tag">${product.product_location}</span>🌊
                                <span class="b-tag">${product.product_category}</span>
                                <span>⭐${product.product_avg_rating}⭐</span>
                                <h4><a href="productDetail.do?product_num=${product.product_num}">${product.product_name}</a></h4>
                                <span class="b-tag">상품가격 : ${product.product_price}￦</span>
                            </div>
                        </div>
                    `;
                    updatePagination(currentPage, 1); // 단일 상품의 경우 페이지네이션 필요 없음
                } else if (data.productList) {
                    // 필터 검색 결과 처리
                    const productList = data.productList;
                    if (!productList || productList.length === 0) {
                        console.log("더 이상 불러올 게시글이 없습니다.");
                        productHTML += '<p>데이터 없음</p>';
                        updatePagination(currentPage, 1); // 페이지네이션 초기화
                    } else {
                        productList.forEach((product) => {
                            const imgSrc = (product.product_file_dir && product.product_file_dir.startsWith("http"))
                                ? product.product_file_dir
                                : (product.product_file_dir && product.product_file_dir !== "null" && product.product_file_dir !== "") 
                                    ? "img/product/" + product.product_file_dir
                                    : 'img/board/boardBasic.png';
                            productHTML += `
                                <div class="col-md-4">
                                    <img alt="상품사진" class="blog-item set-bg" src="${imgSrc}">
                                    <div class="bi-text">
                                        <span class="b-tag">${product.product_location}</span>🌊
                                        <span class="b-tag">${product.product_category}</span>
                                        <span>⭐${product.product_avg_rating}⭐</span>
                                        <h4><a href="productDetail.do?product_num=${product.product_num}">${product.product_name}</a></h4>
                                        <span class="b-tag">상품가격 : ${product.product_price}￦</span>
                                    </div>
                                </div>
                            `;
                        });
                        updatePagination(currentPage, data.product_page_count); // 페이지네이션 업데이트
                    }
                }
                $('.blog-section .row').empty().append(productHTML); // 새로운 상품 목록 추가
            }
        });
    }
   
    // 페이지 버튼 상태 업데이트 함수
    function updatePagination(currentPage, totalPages) {
        var paginationHtml = '';

        if (currentPage > 1) {
            paginationHtml += '<a href="#" class="page-link" data-page="' + (currentPage - 1) + '">이전</a>';
        }
        for (var i = 1; i <= totalPages; i++) {
            if (i === currentPage) {
                paginationHtml += '<strong class="page-link active">' + i + '</strong>';
            } else {
                paginationHtml += '<a href="#" class="page-link" data-page="' + i + '">' + i + '</a>';
            }
        }
        if (currentPage < totalPages) {
            paginationHtml += '<a href="#" class="page-link" data-page="' + (currentPage + 1) + '">다음</a>';
        }
        $('.pagination').html(paginationHtml);
    }

    // 초기 로드
    loadProducts(1); // 페이지가 로드될 때 첫 페이지 상품 로드
});
