//주소 API
$(document).ready(function() {
    $('#addressBtn').on('click', function() {
        // Daum 주소 API의 Postcode 객체를 사용하여 주소 검색 팝업을 엽니다.
        new daum.Postcode({
			// 사용자가 주소를 선택하면 실행되는 콜백함수
            oncomplete: function(data) {
                var addr = ''; // 선택한 주소를 담을 변수
                var extraAddr = ''; // 참고항목 (예: 동 이름, 건물명 등)을 담을 변수

                // 사용자가 선택한 주소의 타입에 따라 도로명 주소 또는 지번 주소를 설정
                if (data.userSelectedType === 'R') { // 도로명 주소를 선택한 경우
                    addr = data.roadAddress;
                } else { // 지번 주소를 선택한 경우
                    addr = data.jibunAddress;
                }

                // 선택한 우편번호와 주소를 해당 폼 필드에 입력
                document.getElementById("postcode").value = data.zonecode;
                document.getElementById("address").value = addr;

                // 만약 도로명 주소를 선택했을 경우, 참고 항목이 있는지 확인 후 추가
                if (data.userSelectedType === 'R') {
					// 동 이름이 있을 경우, 동 이름을 참고 항목으로 추가
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
					// 아파트명 등이 있을 경우, 해당 정보를 참고 항목으로 추가
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
					// 참고 항목이 있으면 괄호로 묶어서 추가 주소 필드에 넣음
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    document.getElementById("extraAddress").value = extraAddr;
                } else {
					// 참고 항목이 없을 경우, 추가 주소 필드를 빈칸으로 설정
                    document.getElementById("extraAddress").value = '';
                }

                // 상세 주소 입력 필드에 자동으로 포커스 이동
                document.getElementById("detailAddress").focus();
            }
        }).open(); // 주소 검색 팝업을 염
    });

    // "회원가입" 버튼 클릭 시, 주소 필드의 유효성 검사를 실행하는 이벤트 핸들러
    $('#submitBtn').on('click', function(event) {
        // 우편번호, 기본 주소, 상세 주소의 입력 값을 가져와서 공백을 제거
        var postcode = document.getElementById("postcode").value.trim(); // 우편번호
        var address = document.getElementById("address").value.trim();   // 기본 주소
        var detailAddress = document.getElementById("detailAddress").value.trim(); // 상세 주소

        // 우편번호, 기본 주소, 상세 주소 중 하나라도 비어있으면 폼 제출을 막고 alert 메시지 출력
        if (postcode === '') {
            alert("우편번호를 입력해주세요.");
            event.preventDefault(); // 폼 제출 방지
        } else if (address === '') {
            alert("기본 주소를 입력해주세요.");
            event.preventDefault(); // 폼 제출 방지
        } else if (detailAddress === '') {
            alert("상세 주소를 입력해주세요.");
            event.preventDefault(); // 폼 제출 방지
        }
    });
});

