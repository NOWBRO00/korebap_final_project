$(document).ready(function() {
	var login_member = $('#like_member').val();
	console.log(login_member);
	// var flag = like_member; // 회원이 좋아요를 눌렀는지 여부를 나타내는 상태값

	// 페이지가 로드되면 서버로부터 게시판 ID 및 좋아요 카운트를 가져온다.
	var board_num = $('#likeBtn').data('post-id'); // 버튼에서 게시글 ID 가져오기
	var likeCount = $('#likeCount'); // 좋아요 카운트를 나타내기 위한 span 태그

	console.log("board_num :" + board_num);

	// 좋아요 버튼 클릭하면,
	$('#likeBtn').on('click', function() {
		console.log("좋아요 버튼 누름");
		//flag = !flag; // 현재 상태 반전

		// AJAX 요청
		$.ajax({
			url: 'boardLike.do',
			method: 'POST',
			data: JSON.stringify({
				goodLike_board_num: board_num,
				goodLike_member_id: login_member
			}),
			dataType: 'json',
			contentType: 'application/json',  // JSON 형식으로 전송 : 
			success: function(response) {
				console.log(response);
				
				if (response.message) {
					alert(response.message); // 안내 메시지 표시
				}
				if (response.redirect) {
					window.location.href = response.redirect; // 로그인 페이지로 리디렉션
				}
				else { // 로그인 상태인 경우

					// 서버로부터 성공 응답을 받으면 좋아요 수를 업데이트
					if (response.flag != null) { // 성공 플래그에 따라 조건 변경
						likeCount.text(response.likeCnt);
						// 좋아요를 눌렀으면, 하늘색/ 안눌렀으면 빨간색 버튼으로 변경
						$('#likeBtn').css('backgroundColor', response.flag ? 'red' : '#87CEEB');
						console.log("성공");
					} else {
						console.log("실패");
						alert(response.message); // 오류 메시지 표시
					}

				}

			},
			error: function() {
				alert('좋아요 처리 중 오류가 발생했습니다.'); // 오류 발생 시 경고
			}


		});
	});
})
