$(document).ready(function() {
	console.log("문서 로드 완료");

	var totalPage = $('#claimList_total_page').val(); // 전체 페이지 수
	console.log(totalPage);
	// 페이지네이션 클릭 이벤트   
	$('.pagination').on('click', '.page-link', function() {
		console.log("페이지네이션 클릭 이벤트");

		var currentPage = $(this).data('page');
		console.log("currentPage [" + currentPage + "]");
		loadClaim(currentPage); // 클릭한 페이지 번호로 로드
	});

	// 상품 로드 함수
	function loadClaim(currentPage) {
		console.log("totalPage [" + totalPage + "]");
		console.log("loadClaim 함수 실행 후 currentPage [" + currentPage + "]");

		// AJAX 요청
		$.ajax({
		    url: 'claimList.do',
		    type: 'GET',
		    data: {
		        "totalPage": totalPage,
		        "currentPage": currentPage,
		    },
		    dataType: 'json',
		    success: function(data) {
		        console.log("ajax요청 성공 반환");
		        console.log("AJAX 요청 성공, 반환된 데이터: ", data);
		        const claimList = data.claimList; // 리스트 형태의 데이터
		        let claimHTML = '';
		        if (!claimList || claimList.length === 0) {
		            console.log("더 이상 불러올 사장님 회원이 없습니다.");
		            claimHTML += '<tr><td colspan="5">사장님 사용자가 없습니다.</td></tr>';
		            updatePagination(currentPage, 1); // 페이지네이션 초기화
		        } else {
		            claimList.forEach((claim) => {
		                console.log("forEach 시작: ", claim);
		                claimHTML += 						`
						                    <tr>
						                        <td style="vertical-align: middle;">${claim.claim_num}</td>
						                        <td style="vertical-align: middle;">${claim.claim_reporter_id}</td>
						                        
						                        ${claim.claim_board_num || claim.claim_reply_num ? `
						                            <td style="vertical-align: middle;">
						                                ${claim.claim_board_num ? `
						                                    <a href="boardDetail.do?board_num=${claim.claim_board_num}">게시글 ${claim.claim_board_num}</a>
						                                ` : ''}
						                                ${claim.claim_reply_num ? `
						                                    <a href="selectReply.do?reply_num=${claim.claim_reply_num}">댓글 ${claim.claim_reply_num}</a>
						                                ` : ''}
						                            </td>
						                        ` : ''}
						                        
						                        <td style="vertical-align: middle;" class="center">${claim.claim_target_member_id}</td>
						                        <td style="vertical-align: middle;" class="center">
						                            <div>
						                                <button style="margin-right: 2rem;" onclick="location.href='accessClaim.do?claim_num=${claim.claim_num}'" class="accessButton" id="accessButton" value="${claim.claim_num}">승인</button>
						                                <button onclick="location.href='returnClaim.do?claim_num=${claim.claim_num}'" class="returnButton" id="returnButton" value="${claim.claim_num}">반려</button>
						                            </div>
						                        </td>
						                    </tr>`;
		            });
		            updatePagination(currentPage, data.claim_total_page); // 페이지네이션 업데이트
		        }

		        // tbody 내용 업데이트
		        $('.container .claimList').empty().append(claimHTML); // 기존 내용을 지우고 새 내용 추가
		    },
		});
	}

	// 페이지 버튼 상태 업데이트 함수
	function updatePagination(currentPage, totalPages) {
		console.log("버튼 상태 없데이트 시작");
		console.log(currentPage);
		console.log(totalPages);
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
	loadClaim(1); // 페이지가 로드될 때 첫 페이지 상품 로드
});
