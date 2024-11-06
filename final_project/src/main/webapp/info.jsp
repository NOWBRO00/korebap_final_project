<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alert with Modal</title>
    <style>
        /* 기본 스타일 설정 */
        body { font-family: Arial, sans-serif; }
        .modal {
            display: none; /* 기본적으로 숨김 상태 */
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5); /* 반투명 배경 */
        }
        .modal-content {
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            text-align: center;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            background-color: #007BFF; /* 파란색 버튼 */
            color: white;
            border: none;
            border-radius: 5px;
            margin-top: 20px;
        }
        .button:hover {
            background-color: #0056b3; /* 버튼 호버 시 색상 변화 */
        }
    </style>
</head>
<body>

    <!-- 모달 구조 -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p id="modalMessage"></p>
            <button class="button" id="redirectButton">확인</button>
        </div>
    </div>

    <script>
        // 모달 관련 변수
        var modal = document.getElementById("myModal");
        var span = document.getElementsByClassName("close")[0];
        var redirectButton = document.getElementById("redirectButton");

        // 오류 메시지와 경로를 변수로 설정
        var msg = '${msg}';
        var path = '${path}';

        // 모달을 열고 메시지와 버튼 설정
        function showModal(message, redirectUrl) {
            document.getElementById("modalMessage").innerText = message;
            redirectButton.onclick = function() {
                window.location.href = redirectUrl;
            };
            modal.style.display = "block";
        }

        // 모달을 닫는 기능
        span.onclick = function() {
            modal.style.display = "none";
        };

        // 모달 외부 클릭 시 모달 닫기
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        };

        // 페이지 로드 시 모달 표시
        document.addEventListener('DOMContentLoaded', function() {
            showModal(msg, path);
        });
    </script>
</body>
</html>
