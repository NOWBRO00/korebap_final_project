// 닉네임 중복 검사
$(document).ready(function() {
	var isVerified = false;
	
	function checkNickname() {
		// 한글, 영어, 숫자만 입력 가능
		var namePattern = /^[가-힣a-zA-Z0-9]+$/;
		var nickname = document.getElementById("nickname").value;
		var nicknameMsg = document.getElementById("nicknameMsg");
		console.log("checkNickname() 확인 : "+namePattern.test(nickname));
		return namePattern.test(nickname);
	}
	// 닉네임 형식 유효성 검사
	// 닉네임에 변동이 감지되면 아래의 코드도 같이 수행됨
	// 한번 더 확인해봐야할듯
	// 이거 되는거같은디
	$('#nickname').on('input', function() {
		console.log("닉네임 중복 검사 시작");
		var nickname = document.getElementById('nickname').value;
		var nicknameMsg = document.getElementById("nicknameMsg");
		console.log("nickname : " + nickname);
		// 닉네임 형식에 맞는지 확인 후 메세지 출력
		if (!checkNickname()) {
			console.log("닉네임 정규식 검사 실패");
			nicknameMsg.textContent = "닉네임은 한글, 영문으로만 설정할 수 있습니다. 다시 입력해주세요.";
			nicknameMsg.style.color = "red";
		}
		// 닉네임 형식에 안맞는 입력을 했다가 고쳤을 때, 
		// 텍스트, 컬러 초기화
		else {
			console.log("닉네임 정규식 검사 성공");
			nicknameMsg.textContent = "";
			nicknameMsg.style.color = "none";
			
			$.ajax({
				type: "POST",
				url: "checkNickName.do",
				contentType:'application/json',
				data: JSON.stringify({nickname: nickname }),
				datatype:'json',
				success: function(data) {
					console.log("닉네임 ajax로 받아온 data : "+data);
					// 데이터가 들어오면, nicknameMsg에 "닉네임 사용이 불가능 안내"
					// data를 true, false로 받음
					if (data == 'true') {
						console.log("닉네임 중복검사 true");
						nicknameMsg.textContent = "이미 사용 중인 닉네임입니다.";
						nicknameMsg.style.color = "red";
						return false; // 폼이 제출되지 않도록 함
					}
					else if (data == 'false') {
						console.log("닉네임 중복검사 false");
						nicknameMsg.textContent = "사용 가능한 닉네임입니다.";
						nicknameMsg.style.color = "green";
						// 데이터가 안들어오면, nicknameMsg에 "닉네임 사용 가능 안내"
						isVerified = true;
						return true; // 폼이 정상적으로 제출되도록 함
					}
				},
				error: function(error) {
					console.log("error : " + error);
				}
			})
		}
	})
	/*
	// 닉네임 중복검사
	$('#nickname').on('input', function() {
		console.log("닉네임 중복 검사 시작");
		var nickname = document.getElementById('nickname').value;
		var nicknameMsg = document.getElementById("nicknameMsg");
		console.log("nickname : " + nickname);

		$.ajax({
			type: "POST",
			url: "checkNickName",
			data: { nickname: nickname },
			// dataType 생략시 text가 default
			success: function(data) {
				console.log("닉네임 ajax로 받아온 data : "+data);
				// 데이터가 들어오면, nicknameMsg에 "닉네임 사용이 불가능 안내"
				// data를 true, false로 받음
				if (data == 'true') {
					console.log("닉네임 중복검사 true");
					nicknameMsg.textContent = "이미 사용 중인 닉네임입니다.";
					nicknameMsg.style.color = "red";
					return false; // 폼이 제출되지 않도록 함
				}
				else if (data == 'false') {
					console.log("닉네임 중복검사 false");
					nicknameMsg.textContent = "사용 가능한 닉네임입니다.";
					nicknameMsg.style.color = "green";
					// 데이터가 안들어오면, nicknameMsg에 "닉네임 사용 가능 안내"
					isVerified = true;
					return true; // 폼이 정상적으로 제출되도록 함
				}
			},
			error: function(error) {
				console.log("error : " + error);
			}
		})
	})*/

	// 폼 제출 시 닉네임 유효성 검사
	$('#submitBtn').on('submit', function(event) {
		// 이름 형식에 맞는지 확인 후 메세지 출력, 폼 제출 방지
		if (!checkNickname()) {// 닉네임 형식에 맞지 않을 때
			console.log("닉네임 정규식 검사 실패");
			nicknameMsg.textContent = "닉네임에 특수문자를 사용할 수 없습니다. 다시 입력해주세요.";
			nicknameMsg.style.color = "red";
			event.preventDefault(); // 폼 제출 방지
		}
		/*
		else if(){ // 사용 중인 닉네임이 있을 때
			console.log("닉네임 중복 검사 실패");
			nicknameMsg.textContent = "이미 사용 중인 닉네임입니다.";
			nicknameMsg.style.color = "red";
			event.preventDefault(); // 폼 제출 방지
		}*/
		else {
			console.log("닉네임 유효성 검사 성공");
			nicknameMsg.textContent = "";
		}
	});
})

