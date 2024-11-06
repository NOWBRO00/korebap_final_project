/**
 * 위시리스트 삭제 기능
 * 사용자가 위시리스트에서 찜 해제 버튼(삭제)을 누르면 ajax를 이용해 C에게 요청
 * 비동기처리
 */

// 페이지가 완전히 로드된 후 코드를 실행하게 함
$(document).ready(function(){ 
	// 버튼 클릭시 실행될 함수
	$(".wishlist").on('click','.btn-remove',function(){
		// 클릭한 버튼을 jQuery 객체로 만들기
		var button = $(this); 
		// 버튼의 data-num 속성값을 가지고 온다.
		var wishlist_num = button.data("num");
		
		//ajax
		$.ajax({
			type : "POST", // 요청 방식
			url : "deleteWishList", // 요청할 URL
			data : {wishlist_num : wishlist_num}, // key-value
			success : function(data){ // 성공시 콜백함수
				console.log("위시리스트 삭제에 대한 data : " + data);
				// 삭제 성공시 true, 아니라면 false 반환
				if(data=='true'){ // 삭제 성공시 (true 반환시)
					// 해당 위시리스트 삭제
					button.closest(".col-md-4").remove();
					// 현재 개수(wishlist_count 요소에서 선택)를 가져와서 숫자만 추출한다.
					var current_count =  parseInt($("#wishlist_count").text().replace(/\D/g, ''));
					console.log(current_count);
					// 가져온 개수에서 -1 한다.
					var new_count = current_count - 1; 
					
					// 모든 위시리스트가 삭제되었다면 위시리스트 목록을 숨기고 empty wishlist를 보여준다.
					if(new_count<=0){
						$(".wishlist-section").hide() // 위시리스트 목록 숨김
						$(".empty-wishlist").show(); // 빈 위시리스트 상태의 메세지 표시
					}
					else{						
						// 개수를 "전체 개수 : N개" 형식으로 업데이트
						$("#wishlist_count").html('전체 <span class="count-bold">' + new_count + '</span>개');
					}
					// 삭제 안내
					alert("위시리스트에서 삭제되었습니다.");
				}
				else{ // 실패시 (false 반환시)
					// 실패 안내
					alert("삭제에 실패했습니다.");
				}
			},
			error : function(){ // 실패시 콜백함수
				console.log("error : " + error);
				alert("에러가 발생했습니다. 다시 시도해주세요.");
			}
		});
	});
});