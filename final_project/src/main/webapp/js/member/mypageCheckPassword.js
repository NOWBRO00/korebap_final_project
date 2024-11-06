$(document).ready(function() {
   var flag = false;
   // 비밀번호 확인 창에 비밀번호를 입력하면 
   // DB에 저장된 비밀번호와 확인
   $('.checkPassword').on('input', function() {
      console.log("비밀번호 비동기 체크");
      // jQuery로 비밀번호 값 가져오기
      var password = this.value;
      console.log("password : " + password);
      // AJAX로 서버에 현재 비밀번호 전송
      $.ajax({
         type: "POST",
         url: "checkPassword.do", // 요청을 보낼 URL
		 contentType:'application/json',
         data: JSON.stringify({password: password }), // 전송할 데이터
		 datatype:'json',
         success: function(response) {
            // 서버에서 비밀번호가 맞는다는 응답을 받으면
            if (response === "true") {
               $('.errorMsg').text('');
               flag = true; // 비밀번호 확인 성공
            } 
		   else {
	           $('.errorMsg').text('현재 비밀번호가 일치하지 않습니다.');// 에러 메시지 출력
	           flag = false; // 비밀번호 확인 실패
            }
         },
         error: function() {
            // 서버 오류 메시지 출력
            $('.errorMsg').text('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
         }
      });
   });
   
   // 비밀번호가 일치하고 삭제 버튼을 누르면,
    $('#deleteMemberBtn').on('click', function(event) {
       console.log("삭제 버튼 클릭");
       if (!flag) { // 비밀번호가 일치하지 않는다면,
          event.preventDefault(); // 폼 제출 방지
		  console.log("삭제 불가: 비밀번호 불일치");
	    } 
		else {
	        console.log("삭제 가능: 비밀번호 일치");
	    }
    })
	
	$('#checkPasswordBtn').on('click', function(event) {
		event.preventDefault();
		if (flag) {
		    console.log("변경 가능: 비밀번호 일치");
		    $('#newPasswordModal').style.display='block'; // 새 비밀번호 입력창 표시
		}
		else{
			console.log("변경 불가: 비밀번호 불일치");
		}
	});
   


})