$(document).ready(function() {
    // URL의 쿼리 파라미터에서 commentId를 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const reply_num = urlParams.get('reply_num');

    // 해당 댓글이 있을 경우 포커스 주기
    if (reply_num) {
        const targetReply = document.getElementById(`reply${reply_num}`);
		// 댓글이 존재하는지 확인
        if (targetReply) {
            targetReply.focus();
        } else {
            // 댓글이 존재하지 않을 경우 사용자에게 알림
            alert('해당 댓글은 삭제되었거나 존재하지 않습니다.');
        }
    }
});