$(document).ready(function() {
    // URL의 쿼리 파라미터에서 commentId를 가져오기
	console.log("포커스 준비 ㅇ완료")
    const urlParams = new URLSearchParams(window.location.search);
    const reply_num = urlParams.get('reply_num');
	console.log("dkssud"+reply_num);

    // 해당 댓글이 있을 경우 포커스 주기
    if (reply_num) {
        const targetReply = document.getElementById(`${reply_num}`);
		console.log(targetReply);
		// 댓글이 존재하는지 확인
        if (targetReply) {
			console.log("포커스");
			
            targetReply.setAttribute('tabindex', '0');
			//targetReply.style.backgroundColor = 'rgba(128, 128, 128, 0.5)';
            targetReply.focus();
						
			alert("신고된 댓글이 표시됩니다.");
        } else {
            // 댓글이 존재하지 않을 경우 사용자에게 알림
            alert('해당 댓글은 삭제되었거나 존재하지 않습니다.');
        }
    }
});