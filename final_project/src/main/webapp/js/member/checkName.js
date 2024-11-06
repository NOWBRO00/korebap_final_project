$(document).ready(function() {
	function checkName() {
		// 한글 2~5글자 정규 표현식
		var namePattern = /^[가-힣]{2,5}$/;
		var name = document.getElementById("name").value;
		var nameMsg = document.getElementById("nameMsg");
		console.log("이름이 한글 2-5자리인지 확인 : "+namePattern.test(name));
		return namePattern.test(name);
	}
	
	// 이름이 2-5자리 숫자인지 확인
	$('#name').on('input', function() {
		// 이름 형식에 맞는지 확인 후 메세지 출력
		if (!checkName()) {
			console.log("이름 형식 유효성 검사 실패");
			nameMsg.textContent = "이름을 정확히 입력해주세요.";
			nameMsg.style.color = "red";
		}
		// 이름 형식에 안맞는 입력을 했다가 고쳤을 때, 
		// 텍스트, 컬러 초기화
		else {
			console.log("이름 형식 유효성 검사 성공");
			nameMsg.textContent = "";
			nameMsg.style.color = "none";
		}
	})

	// 폼 제출 시 이름 유효성 검사
	$('#submitBtn').on('submit', function(event) {
		// 이름 형식에 맞는지 확인 후 메세지 출력, 폼 제출 방지
		if (!checkName()) {
			console.log("이름 형식 유효성 검사 실패");
			nameMsg.textContent = "이름을 정확히 입력해주세요.";
			nameMsg.style.color = "red";
			event.preventDefault(); // 폼 제출 방지
		}
		else {
			console.log("이름 형식 유효성 검사 통과");
			nameMsg.textContent = "";
		}
	});
});