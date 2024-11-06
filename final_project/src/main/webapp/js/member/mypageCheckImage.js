$(document).ready(function() {
    function previewImage(event) {
        const thumbnail = document.getElementById('thumbnail');
        const fileInput = document.getElementById('profile');
        const file = fileInput.files[0];
        const fileMsg = document.getElementById('fileMsg');

        // 파일 확장자 검사
        if (file) {
            const fileName = file.name.toLowerCase();
            const validExtensions = ['jpg', 'jpeg', 'png'];
            const fileExtension = fileName.split('.').pop();

            // 유효한 확장자인지 확인
            if (!validExtensions.includes(fileExtension)) {
                fileMsg.innerText = "jpg, jpeg, png 파일만 업로드할 수 있습니다.";
                fileInput.value = ""; // 잘못된 파일은 초기화
                thumbnail.style.display = 'none'; // 썸네일 숨기기
                document.getElementById('imageSubmitBtn').disabled = true; // 버튼 비활성화
            } else {
                const reader = new FileReader();
                reader.onload = function(e) {
                    thumbnail.src = e.target.result; // 미리보기 이미지에 파일의 데이터 URL 설정
                    thumbnail.style.display = 'block'; // 썸네일을 보이도록 설정
                };
                reader.readAsDataURL(file); // 파일을 읽어서 데이터 URL로 변환
                fileMsg.innerText = fileName; // 선택한 파일 이름 보여주기
                fileMsg.style.color = "black"; // 파일 메시지 색상 변경
                document.getElementById('imageSubmitBtn').disabled = false; // 버튼 활성화
            }
        } else {
            thumbnail.src = '#'; // 파일이 없을 경우 기본 값
            thumbnail.style.display = 'none'; // 썸네일 숨기기
            fileMsg.innerText = ""; // 메시지 초기화
            document.getElementById('imageSubmitBtn').disabled = true; // 버튼 비활성화
        }
    }

    $('#profile').on('change', function(event) {
        previewImage(event); // 파일 변경 시 미리보기 함수 호출
    });
});
