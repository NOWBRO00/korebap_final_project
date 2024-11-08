$(document).ready(function() {
	console.log("문서 로드 완료");

	var totalPage = $('#bossList_total_page').data('boss_page_count'); // 전체 페이지 수
	let searchTerm2 = $('#serchTerm2').val();
	console.log(totalPage);
	// 페이지네이션 클릭 이벤트   
	$('.pagination').on('click', '.page-link', function() {
		console.log("페이지네이션 클릭 이벤트");

		var currentPage = $(this).data('page');
		console.log("currentPage [" + currentPage + "]");
		loadBoss(currentPage, searchTerm2); // 클릭한 페이지 번호로 로드
	});
	
	$('.search-button').on('click', function() {
			searchTerm2='';
	        var searchTerm = $('#searchTerm').val().trim(); // 입력된 검색어
	        console.log("검색어: " + searchTerm);
	        loadBoss(1, searchTerm); // 첫 페이지를 로드하고 검색어 전달
	    });

	// 상품 로드 함수
	function loadBoss(currentPage, searchTerm) {
		console.log("totalPage [" + totalPage + "]");
		console.log("loadBoss 함수 실행 후 currentPage [" + currentPage + "]");
		console.log("searchKeyword : ["+searchTerm+"]");

		// AJAX 요청
		$.ajax({
		    url: 'bossList.do',
		    type: 'GET',
		    data: {
		        "totalPage": totalPage,
		        "currentPage": currentPage,
				"member_searchkeyword": searchTerm
		    },
		    dataType: 'json',
		    success: function(data) {
		        console.log("ajax요청 성공 반환");
		        console.log("AJAX 요청 성공, 반환된 데이터: ", data);
		        const bossList = data.bossList; // 리스트 형태의 데이터
				searchTerm2 = data.searchTerm;
		        let bossHTML = '';
				console.log(searchTerm2);
		        if (!bossList || bossList.length === 0) {
		            console.log("더 이상 불러올 사장님 회원이 없습니다.");
		            bossHTML += '<tr><td colspan="5">사장님 사용자가 없습니다.</td></tr>';
		            updatePagination(currentPage, 1); // 페이지네이션 초기화
		        } else {
		            bossList.forEach((boss) => {
		                console.log("forEach 시작: ", boss);
		                bossHTML += '<tr style="text-align: center;">' +
		                    '<td style="vertical-align: middle;"><a href="mypage.do?member_id=' + boss.member_id + '">' + boss.member_id + '</a></td>' +
		                    '<td style="vertical-align: middle;">' + boss.member_name + '</td>' +
		                    '<td style="vertical-align: middle;">' + boss.member_phone + '</td>' +
		                    '<td style="vertical-align: middle;">' + boss.member_registration_date + '</td>' +
		                    '<td style="vertical-align: middle;">' +
		                    '<div>' +
		                    '<a style="margin-right: 2rem;" href="MemberBan.do?member_id=' + boss.member_id + '" class="btn btn-primary" role="button">정지</a>' +
		                    '<a href="deleteMember.do?member_id=' + boss.member_id + '" class="btn btn-primary" role="button">탈퇴</a>' +
		                    '</div>' +
		                    '</td>' +
		                    '</tr>';
		            });
		            updatePagination(currentPage, data.boss_page_count); // 페이지네이션 업데이트
		        }

		        // tbody 내용 업데이트
		        $('.container .bossList').empty().append(bossHTML); // 기존 내용을 지우고 새 내용 추가
		    },
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
	loadBoss(1); // 페이지가 로드될 때 첫 페이지 상품 로드
});
