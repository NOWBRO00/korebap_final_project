// 결제 관련 JavaScript 코드

var IMP = window.IMP; // 결제 API 초기화
IMP.init("상점 번호"); // 상점 고유의 IMP 키로 초기화

function requestPay() {
    let uuid = self.crypto.randomUUID(); // 고유 UUID 생성
    const cleanUuid = uuid.replace(/-/g, ""); // UUID에서 하이픈 제거
    const currentDate = new Date().toISOString().slice(0, 10).replace(/-/g, ''); // 현재 날짜 (YYYYMMDD 형식으로)
    const merchant_uid = cleanUuid + currentDate; // 주문 번호 (UUID + 날짜)
    console.log("merchant_uid : " + merchant_uid); // 주문 번호 출력
    
    let amount = $('#amount').val(); // 결제 금액
    console.log("가격 : " + amount); // 금액 출력
    
    let product_num = $('#product_num').val(); // 상품 번호
    console.log("상품번호 : " + product_num); // 상품 번호 출력
    
    let reservationDate = $('#reservation_date').val(); // 예약 날짜
    console.log("예약일 : " + reservationDate); // 예약 날짜 출력
    
    const product_name = $('#product_name').text(); // 상품 이름
    console.log("상품 이름 : " + product_name); // 상품 이름 출력
    
    const member_id = $('#member_id').val(); // 회원 ID
    console.log("멤버 아이디 : " + member_id); // 회원 ID 출력
    
    const member_phone = $('#member_phone').val(); // 회원 전화번호
    console.log("멤버 전화번호 : " + member_phone); // 회원 전화번호 출력
    
    const member_name = $('#member_name').val(); // 회원 이름
    console.log("멤버 이름 : " + member_name); // 회원 이름 출력
    
    let amountRes; // 결제 금액을 담을 변수

    // 사전 검증 등록 요청
    $.ajax({
        url: "payment/prepare.do", // 결제 사전 검증을 위한 API
        method: "POST",
        contentType: 'application/json', // JSON 형식으로 요청
        data: JSON.stringify({
            merchant_uid: merchant_uid, // 주문 고유번호
            amount: amount, // 결제 금액
            product_num: product_num // 상품 번호
        })
    }).done(function(data) {
        console.log("첫 번째 응답: " + data); // 첫 번째 응답 출력
        if (data === "true") {
            console.log("사전 검증 등록 완료"); // 사전 검증 완료

            // 사전 검증 등록 조회 요청
            $.ajax({
                url: "payment/prepared.do", // 사전 검증 등록 상태 조회 API
                method: "POST",
                contentType: 'application/json', // JSON 형식으로 요청
                data: JSON.stringify({
                    merchant_uid: merchant_uid // 주문 고유번호
                }),
                dataType: "json",
            }).done(function(data) {
                console.log("두 번째 응답:" + data); // 두 번째 응답 출력

                if (!isNaN(data) && data > 0) {
                    console.log("사전 검증 등록 조회 성공"); // 사전 검증 등록 조회 성공
                    amountRes = data; // 응답에서 받은 금액
                    console.log(amountRes); // 금액 출력
                } else {
                    console.log("오류 발생: 반환된 값이 유효하지 않음"); // 오류 발생 시
                }
                
                // 결제 요청
                IMP.request_pay({
                    pg: 'html5_inicis.INIpayTest', // 결제 대행사 코드
                    pay_method: 'card', // 결제 방법 (카드)
                    merchant_uid: merchant_uid, // 주문 고유번호
                    name: product_name, // 상품명
                    amount: amountRes, // 결제 금액
                    // 구매자 정보
                    buyer_email: member_id, // 구매자 이메일
                    buyer_name: member_name, // 구매자 이름
                    buyer_tel: member_phone // 구매자 전화번호
                },
                function(rsp) {
                    if (rsp.success) {
                        // 결제 성공 시
                        // 결제 승인 또는 가상계좌 발급에 성공한 경우
                        jQuery.ajax({
                            url: "payment/vaildate.do", // 결제 검증 API
                            method: "POST",
                            contentType: 'application/json', // JSON 형식으로 요청
                            dataType: "json",
                            data: JSON.stringify({
                                imp_uid: rsp.imp_uid, // 포트원 결제 고유번호
                                product_num: product_num, // 상품 번호
                                merchant_uid: rsp.merchant_uid // 주문 번호
                            })
                        }).done(function(data) {
                            if (rsp.paid_amount == data) {
                                // 결제 검증 성공 시
                                alert("결제 및 결제검증완료");
                                location.href = "makeReservation.do?merchant_uid=" + merchant_uid + "&reservation_registrarion_date=" + reservationDate; // 예약 페이지로 이동
                            } else {
                                alert("검증 실패");
                                // 결제 취소
                                location.href = "cancelPayment.do?merchant_uid=" + merchant_uid; // 결제 취소 페이지로 이동
                            }
                        }).fail(function(error) {
                            console.error('Error:', error); // 오류 처리
                        });
                    } else {
                        alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg); // 결제 실패 시
                    }
                });
            }).fail(function(error) {
                console.log("사전 검증 조회 AJAX 오류:", error); // 사전 검증 조회 오류 처리
            });

        } else {
            console.log("사전 검증 등록 실패"); // 사전 검증 등록 실패 시
        }
    }).fail(function(error) {
        console.log("사전 검증 등록 AJAX 오류:", error); // 사전 검증 등록 오류 처리
    });
};
