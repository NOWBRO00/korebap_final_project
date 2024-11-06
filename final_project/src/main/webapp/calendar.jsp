<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>예약 시스템</title>
    <style>
        /* 전체 캘린더 스타일 */
        #calendar {
            border: 1px solid #007bff; /* 테두리 색상 및 두께 */
            border-radius: 10px; /* 모서리 둥글게 */
            padding: 20px; /* 내부 여백 */
            background-color: #ffffff; /* 배경 색상 */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
            height: 600px; /* 높이 설정 */
            display: flex; /* flexbox 사용 */
            flex-direction: column; /* 수직 방향으로 정렬 */
            justify-content: space-between; /* 버튼을 아래로 정렬 */
        }

        /* 월과 연도 표시 스타일 */
        #monthYear {
            font-size: 24px; /* 폰트 크기 */
            text-align: center; /* 가운데 정렬 */
            margin-bottom: 10px; /* 하단 여백 */
        }

        /* 날짜를 표시하는 부분 스타일 */
        #dates {
            display: grid; /* 그리드 레이아웃 사용 */
            grid-template-columns: repeat(7, 1fr); /* 7개의 열로 구성 */
            gap: 5px; /* 날짜 사이 간격 */
            height: calc(100% - 100px); /* 버튼 공간을 고려하여 높이 설정 */
            overflow-y: auto; /* 수직 스크롤 허용 */
        }

        /* 날짜 스타일 */
        .date {
            width: 60px; /* 너비 설정 */
            height: 60px; /* 높이 설정 */
            display: flex; /* flexbox 사용 */
            align-items: center; /* 수직 가운데 정렬 */
            justify-content: center; /* 수평 가운데 정렬 */
            border: 1px solid #007bff; /* 테두리 색상 및 두께 */
            border-radius: 5px; /* 모서리 둥글게 */
            cursor: pointer; /* 커서 포인터로 변경 */
            transition: background-color 0.3s; /* 배경색 변경에 부드러운 애니메이션 추가 */
        }

        /* 날짜에 마우스를 올렸을 때의 스타일 */
        .date:hover {
            background-color: #e7f0ff; /* 배경색 변경 */
        }

        /* 선택된 날짜 스타일 */
        .selected {
            background-color: #007bff; /* 선택된 날짜 배경색 */
            color: white; /* 선택된 날짜 글자 색상 */
        }

        /* 버튼 스타일 */
        button {
            padding: 5px 10px; /* 내부 여백을 줄임 */
            font-size: 14px; /* 폰트 크기를 줄임 */
            background-color: #007bff; /* 배경색 변경 */
            color: white; /* 글자 색상 흰색으로 변경 */
            border: none; /* 테두리 없음 */
            border-radius: 5px; /* 모서리 둥글게 */
            cursor: pointer; /* 커서 포인터로 변경 */
            transition: background-color 0.3s; /* 배경색 변경에 부드러운 애니메이션 추가 */
            align-self: center; /* 버튼을 중앙으로 정렬 */
        }

        /* 버튼에 마우스를 올렸을 때의 스타일 */
        button:hover {
            background-color: #0056b3; /* 버튼 배경색 변경 */
        }
    </style>
</head>
<body>
<!-- 달력 섹션 시작 -->
<div id="calendar">
    <!-- 월과 연도를 표시하는 부분 -->
    <div id="monthYear">
        <button onclick="changeMonth(-1)">이전</button> <!-- 이전 달로 변경 -->
        <span id="currentMonthYear"></span> <!-- 현재 월과 연도 표시 -->
        <button onclick="changeMonth(1)">다음</button> <!-- 다음 달로 변경 -->
    </div>
    <div id="dates"></div> <!-- 날짜를 표시하는 부분 -->
    <form id="reservationForm" action="paymentInfo.do" method="POST">
        <input type="hidden" id="selectedDate" name="reservation_date"> <!-- 선택된 날짜를 저장하는 숨겨진 입력 필드 -->
        <input name="product_num" value="${product.product_num}" style="visibility: hidden;"> <!-- 상품 번호 -->
        <div style="display:flex; justify-content: space-between;">
           <span style="font-size:30px; color:blue;">${product.product_price}원</span> <!-- 상품 가격 표시 -->
           <button type="submit">예약하기</button> <!-- 예약하기 버튼 -->
        </div>
    </form>
    
</div>
<!-- 달력 섹션 종료 -->
<script>
    // DOM 요소를 변수에 저장
    const datesContainer = document.getElementById("dates");
    const monthYear = document.getElementById("currentMonthYear");
    const selectedDateInput = document.getElementById("selectedDate");

    let selectedDate = null; // 선택된 날짜를 저장하는 변수
    let currentYear = new Date().getFullYear(); // 현재 연도
    let currentMonth = new Date().getMonth(); // 현재 월

    // 캘린더 생성 함수
    function generateCalendar(year, month) {
        datesContainer.innerHTML = ""; // 기존 날짜 내용 지우기
        monthYear.innerText = year + "년 " + (month + 1) + "월"; // 월과 연도 표시

        const firstDay = new Date(year, month, 1).getDay(); // 월의 첫 날의 요일
        const lastDate = new Date(year, month + 1, 0).getDate(); // 월의 마지막 날짜
        const today = new Date(); // 오늘 날짜

        // 빈 날짜를 채워 넣기 (첫 날의 요일에 따라)
        for (let i = 0; i < firstDay; i++) {
            const emptyDiv = document.createElement("div");
            emptyDiv.className = "date"; // 날짜 스타일 적용
            datesContainer.appendChild(emptyDiv); // 빈 날짜 추가
        }

        // 현재 월의 실제 날짜를 채워 넣기
        for (let date = 1; date <= lastDate; date++) {
            const dateDiv = document.createElement("div");
            dateDiv.className = "date"; // 날짜 스타일 적용
            dateDiv.innerText = date; // 날짜 숫자 삽입

            const currentDate = new Date(year, month, date); // 날짜 객체 생성
            if (currentDate < today) {
                // 과거 날짜는 비활성화
                dateDiv.classList.add("disabled");
                dateDiv.style.pointerEvents = "none"; // 클릭 비활성화
                dateDiv.style.color = "#ccc"; // 색상 회색으로 변경
            } else {
                dateDiv.onclick = () => selectDate(dateDiv, date, year, month); // 날짜 클릭 시 선택 함수 호출
            }

            datesContainer.appendChild(dateDiv); // 날짜 추가
        }
    }

    // 날짜 선택 함수
    function selectDate(dateDiv, date, year, month) {
        resetSelection(); // 기존 선택 해제
        selectedDate = new Date(year, month, ++date); // 선택된 날짜 저장
        dateDiv.classList.add("selected"); // 선택된 날짜 스타일 적용
        selectedDateInput.value = selectedDate.toISOString().split('T')[0]; // 날짜를 'yyyy-mm-dd' 형식으로 변환하여 숨겨진 입력 필드에 저장
    }

    // 선택된 날짜 초기화 함수
    function resetSelection() {
        const allDates = datesContainer.children; // 모든 날짜 요소 가져오기
        for (let i = 0; i < allDates.length; i++) {
            allDates[i].classList.remove("selected"); // 선택된 스타일 제거
        }
    }

    // 월 변경 함수
    function changeMonth(direction) {
        currentMonth += direction; // 방향에 따라 현재 월 변경
        if (currentMonth < 0) {
            currentMonth = 11; // 12월로 변경
            currentYear--; // 연도 감소
        } else if (currentMonth > 11) {
            currentMonth = 0; // 1월로 변경
            currentYear++; // 연도 증가
        }
        generateCalendar(currentYear, currentMonth); // 변경된 월로 캘린더 생성
    }

    // 현재 년도와 월로 캘린더 생성
    generateCalendar(currentYear, currentMonth); 
</script>
<script>
    // 폼의 제출 버튼에 이벤트 리스너 추가
    document.getElementById('reservationForm').addEventListener('submit', function(event) {
        // 선택된 날짜가 없다면
        if (!document.getElementById('selectedDate').value) {
            alert('날짜를 선택해주세요!');
            event.preventDefault();  // 폼 제출을 막음
        }
    });
</script>

</body>
</html>
