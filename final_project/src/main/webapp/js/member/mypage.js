$(document).ready(function() {

	// '변경 완료'버튼을 누르면 이름, 닉네임을 담은 form이 submit
	$('#submitBtn').on('click', function(event) {
		$('#MypageForm').submit(); // jQuery로 폼 제출
	});
})

