$(document).ready(function() {
	// 전화번호 유효성 검사 함수
	function checkPhone(phone) {
		// 기본 전화번호 형식 (예: 123-456-7890 또는 1234567890)
		// ^: 문자열의 시작
		// 010: 전화번호의 시작 부분
		// -?: 하이픈(-)이 있을 수도 있고 없을 수도 있음
		// \d{4}: 4개의 숫자
		var phonePattern = /^010-?\d{4}-?\d{4}$/;
		var phone = document.getElementById("phone").value;
		// 전화번호 형식이 맞는지 확인하고 boolean 타입으로 반환
		return phonePattern.test(phone);
	}

	// 전화번호 형식을 확인
	$('#phone').on('blur', function() {
		var phoneMsg = document.getElementById("phoneMsg");
		// 전화번호 형식이 맞지 않으면
		if (!checkPhone(phone)) {
			// 안내문 출력
			phoneMsg.textContent = "01012341234의 형식으로 입력해주세요.";
			phoneMsg.style.color = "red";
		} else {
			// 형식이 맞으면 메세지가 사라짐
			phoneMsg.textContent = "";
		}
	});

	// 폼 제출 시 전화번호 유효성 검사를 완료했는지 확인
	$('#submitBtn').on('submit', function(event) {
		var phoneMsg = document.getElementById("phoneMsg");
		// 전화번호 형식이 맞는지 확인 후에 메세지 출력
		if (!checkPhone(phone)) {
			phoneMsg.textContent = "01012341234의 형식으로 입력해주세요.";
			phoneMsg.style.color = "red";
			event.preventDefault(); // 폼 제출 방지
		}
		else {
			phoneMsg.style.color = "green";
		}
	});
});