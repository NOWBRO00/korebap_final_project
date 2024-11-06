//비밀번호 중복검사를 위한 js
$(document).ready(function() {
	// 비밀번호와 비밀번호 확인 필드의 값이 일치하는지 확인하는 함수
	function checkPassword() {
		var password = document.getElementById("password").value;
		var confirmPassword = document.getElementById("confirmPassword").value;
		// 비밀번호가 일치하지 않는 경우
		if (password !== confirmPassword) {
			// 경고 메시지를 표시하고, 폼 제출을 막음
			return false; // 폼이 제출되지 않도록 함
		}
		// 비밀번호가 일치하는 경우
		return true; // 폼이 정상적으로 제출되도록 함
	}

	// 비밀번호 또는 비밀번호 확인 필드에서 입력이 변경될 때마다 중복 검사를 실행
	$('#confirmPassword').on('input', function() {
		var passwordMsg = document.getElementById("passwordMsg");
		if (!checkPassword()) {
			passwordMsg.textContent = "비밀번호가 일치하지 않습니다.";
			passwordMsg.style.color = "red";
		} else {
			passwordMsg.textContent = "비밀번호 확인이 완료되었습니다.";
			passwordMsg.style.color = "green";
		}
	});
	
	$('#submitBtn').on('submit', function(event){
		if (!checkPassword()) {
		    event.preventDefault(); // 폼 제출을 막음
		    alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
		}
	})

})
