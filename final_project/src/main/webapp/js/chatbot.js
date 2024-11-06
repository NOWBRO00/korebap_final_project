$(document).ready(function() {
    const chatMessages = $('#chat-messages');
    const userInput = $('#user-input input');
    const sendButton = $('#user-input button');
    
    let isFetching = false; // 요청 진행 상태 관리

    function addMessage(sender, message) {
        const messageElement = $('<div class="message userMessage"></div>').text(`${sender}: ${message}`);
        chatMessages.prepend(messageElement);
    }

    function fetchAIResponse(prompt) {
		console.log(prompt);
        return $.ajax({
            url: 'goOpenAI.do', // 서버 컨트롤러의 URL로 변경
            method: 'POST',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify({userMessage: prompt}), // 사용자 메시지를 JSON 형식으로 전송
			dataType: 'json'
		});
    }

    sendButton.on('click', function() {
        const message = userInput.val().trim();
        if (message.length === 0 || isFetching) return; // 사용자 메세지가 비어있다면 반려

        addMessage('나', message);
        userInput.val(''); // 입력 필드 비우기
		
		// "응답 대기 중" 메시지 div 생성
		setTimeout(function() {
			aiMessageDiv = $('<div class="message aiMessage loading">고심이 : 답변 생성중</div>');
			chatMessages.prepend(aiMessageDiv); // 메시지 영역에 추가
		}, 500); // 500ms(0.5초) 텀을 주기

        isFetching = true; // 요청 중
        sendButton.prop('disabled', true); // 버튼 비활성화

        fetchAIResponse(message)
            .done(function(data) {
				console.log('서버응답 : '+data);
				console.log(typeof data);
                const aiResponse = data || '응답이 없습니다.'; // 서버에서 반환된 응답을 사용
                aiMessageDiv.text('고심이 : '+ aiResponse); // 고심이 응답 div set
				aiMessageDiv.removeClass('loading'); // loading 클래스 삭제
            })
			.fail(function(jqXHR) {
			    // API 호출이 실패했을 때 실행되는 콜백 함수
			    // jqXHR 객체는 jQuery Ajax 요청의 결과로 반환되는 객체

			    // 오류 정보 출력
			    console.error('API 오류:', jqXHR.responseJSON);
			    
			    // 실패 시 사용자에게 표시할 메시지를 설정
			    aiMessageDiv.text('고심이 : API 호출 중 오류가 발생했습니다.');
			})
            .always(function() {
                isFetching = false; // 요청 완료
                sendButton.prop('disabled', false); // 버튼 재활성화
            });
    });

    userInput.on('keydown', function(event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });
	
	
	
	/*
	const divElement = document.getElementById('background');

	// 현재 시간 가져오기
	const currentHour = new Date().getHours();

	// 배경 이미지 URL을 담을 변수
	let backgroundUrl = '';

	// 시간대에 따라 배경 이미지 변경
	if (currentHour >= 5 && currentHour < 11) {
	    // 아침
	    backgroundUrl = 'https://wrtn-image-user-output.s3.ap-northeast-2.amazonaws.com/66ab84c962630a39208f1b38/7c469b32-7669-4fb9-81d4-fd8ecc4cbc0c.png'; // 아침 이미지 경로
	} else if (currentHour >= 11 && currentHour < 17) {
	    // 점심
	    backgroundUrl = 'https://wrtn-image-user-output.s3.ap-northeast-2.amazonaws.com/66ab84c962630a39208f1b38/062081d1-0197-4bb9-98a2-fbf7bb7adac2.png'; // 점심 이미지 경로
	} else {
	    // 저녁
	    backgroundUrl = 'https://wrtn-image-user-output.s3.ap-northeast-2.amazonaws.com/66ab84c962630a39208f1b38/2a91bf23-2f37-46f6-830e-1e7a52677a8d.png'; // 저녁 이미지 경로
	}

	// 배경 이미지 설정
	divElement.style.backgroundImage = `url(${backgroundUrl})`;
	*/
});