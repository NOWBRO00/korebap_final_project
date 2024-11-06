// 결제






var IMP = window.IMP;
IMP.init("IMP");

function requestPay() {
	let uuid = self.crypto.randomUUID();
	const cleanUuid = uuid.replace(/-/g, "");
	//new Date().getTime()+'_'+
	const currentDate = new Date().toISOString().slice(0, 10).replace(/-/g, '');
	const merchant_uid = cleanUuid + currentDate; // 주문 번호 날짜-랜덤값
	console.log("merchant_uid : " + merchant_uid);

	let amount = $('#amount').val(); // 가격
	console.log("가격 : " + amount);
	let product_num = $('#product_num').val();
	console.log("상품번호 : " + product_num);
	let reservationDate = $('#reservation_date').val();
	console.log("예약일 : " + reservationDate);
	const product_name = $('#product_name').text();
	console.log("상품 이름 : " + product_name);
	const member_id = $('#member_id').val();
	console.log("멤버 아이디 : " + member_id);
	const member_phone = $('#member_phone').val();
	console.log("멤버 전화번호 : " + member_phone);
	const member_name = $('#member_name').val();
	console.log("멤버 이름 : " + member_name);


	// 사전 검증 등록
	$.ajax({
		url: "payment/prepare.do",
		method: "POST",
		contentType:'application/json',
		 data: JSON.stringify({
			merchant_uid: merchant_uid,
			amount: amount
		})
	}).done(function(data) {
		console.log("첫 번째 응답: " + data);
		if (data === "true") {
			console.log("사전 검증 등록 완료");

			// 사전 검증 등록 조회 요청
			$.ajax({
				url: "payment/prepared.do",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify({ // 데이터 객체를 JSON 문자열로 변환
					merchant_uid: merchant_uid
				}),
				dataType: "json",
			}).done(function(data) {
				console.log("두 번째 응답:" + data); // 응답 전체 확인
				console.log(typeof data);
				console.log("두 번째 응답:" + data); // 확인

				if (!isNaN(data) && data > 0) {
					console.log("사전 검증 등록 조회 성공");
					//amount = data.amountRes;
					//console.log(amount);
				} else {
					console.log("오류 발생: 반환된 값이 유효하지 않음");
				}
			}).fail(function(error) {
				console.log("사전 검증 조회 AJAX 오류:", error);
			});

		} else {
			console.log("사전 검증 등록 실패");
		}
	}).fail(function(error) {
		console.log("사전 검증 등록 AJAX 오류:", error);
	});






	IMP.request_pay({
		pg: 'html5_inicis.INIpayTest', // 결제 대행사 코드.
		pay_method: 'card', // 결제 방법
		merchant_uid: merchant_uid, // 주문 고유번호 
		name: product_name, // 상품명
		amount: amount, // 상품 가격
		// 구매자 정보
		buyer_email: member_id,
		buyer_name: member_name,
		buyer_tel: member_phone
	},
		function(rsp) {
			if (rsp.success) {
			  // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
			  // jQuery로 HTTP 요청
			  jQuery.ajax({
				  url: "payment/vaildate.do", 
				  method: "POST",
				  contentType:'application/json',
				  // JSON 형식으로 전송
				  dataType: "json",
				  data: JSON.stringify({ 
					  imp_uid: rsp.imp_uid,  // 포트원 결제 고유번호
					  product_num: product_num,
					  merchant_uid: rsp.merchant_uid  // 주문번호
				  })
			  }).done(function (data) {
				  if(rsp.paid_amount == data){
					  // 가맹점 서버 결제 API 성공시 로직
					  alert("결제 및 결제검증완료");
					  location.href="makeReservation.do?merchant_uid="+merchant_uid+"&reservation_registrarion_date="+reservationDate;
				  } else {
					 alert("검증 실패");
					 // 결제 취소
					 location.href="cancelPayment.do?merchant_uid="+merchant_uid;  
					  }
				  }).fail(function(error) {
				  console.error('Error:', error);
			  });
			} else {
			  alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		});
	};