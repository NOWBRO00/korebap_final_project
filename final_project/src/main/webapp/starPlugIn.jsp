<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>별점 평가 플러그인</title>
    <style>
        .star-rating {
            display: inline-block;
            font-size: 0;
            position: relative;
            cursor: pointer;
            direction: rtl;
        }
        .star-rating input {
            display: none;
        }
        .star-rating label {
            color: #ccc;
            font-size: 2rem;
            padding: 0;
            margin: 0;
            cursor: pointer;
            display: inline-block;
        }
        .star-rating label:before {
            content: '★';
        }
        .star-rating input:checked ~ label {
            color: gold;
        }
        .star-rating input:checked ~ label:hover,
        .star-rating input:checked ~ label:hover ~ label {
            color: gold;
        }
        .star-rating input:hover ~ label {
            color: gold;
        }

        #result {
            margin-top: 10px;
        }

        #averageRating {
            font-size: 1.5rem;
            margin-top: 20px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="star-rating">
        <input type="radio" id="star5" name="rating" value="5" /><label for="star5" title="5개 별"></label>
        <input type="radio" id="star4" name="rating" value="4" /><label for="star4" title="4개 별"></label>
        <input type="radio" id="star3" name="rating" value="3" /><label for="star3" title="3개 별"></label>
        <input type="radio" id="star2" name="rating" value="2" /><label for="star2" title="2개 별"></label>
        <input type="radio" id="star1" name="rating" value="1" /><label for="star1" title="1개 별"></label>
    </div>
    
    <div id="result"></div>
    <div id="averageRating"></div>
    
    <script type="text/javascript">
        $(document).ready(function() {
            // 별점 선택 시 서버에 데이터 전송
            $('.star-rating input').change(function() {
                var rating = $(this).val();
                console.log('선택한 별점: ' + rating + '개 별');

                // AJAX 요청을 보내 서버로 별점 정보를 전달
                $.ajax({
                    type: 'POST',
                    url: 'submitRating.do', // 요청할 URL
                    data: JSON.stringify({ rating: rating }), // 서버로 보낼 데이터
                    contentType: 'application/json',
                    dataType: 'text',
                    success: function(data) {
                        console.log('서버 응답 데이터:', data);
                        if (data === 'success') {
                            $("#result").text("별점이 성공적으로 제출되었습니다.").css("color", "green");
                        } else if (data === 'error') {
                            $("#result").text("별점 제출에 오류가 발생했습니다.").css("color", "red");
                        } else {
                            $("#result").text("알 수 없는 오류가 발생했습니다.").css("color", "orange");
                        }

                        // 별점 제출 후 평균 별점 요청
                        fetchAverageRating();
                    },
                    error: function(xhr, status, error) {
                        console.log('서버 응답 실패...');
                        console.log(xhr);
                        $("#result").text("서버에 요청을 보낼 수 없습니다.").css("color", "red");
                    }
                });
            });

            // 페이지 로드 시 평균 별점 가져오기
            function fetchAverageRating() {
                $.ajax({
                    type: 'GET',
                    url: 'getAverageRating.do', // 평균 별점을 가져올 URL
                    dataType: 'json',
                    success: function(data) {
                        if (data && data.averageRating) {
                            $("#averageRating").text("현재 평균 별점: " + data.averageRating.toFixed(1) + "개 별");
                        } else {
                            $("#averageRating").text("평균 별점을 가져오는 데 실패했습니다.");
                        }
                    },
                    error: function(xhr, status, error) {
                        console.log('서버 응답 실패...');
                        console.log(xhr);
                        $("#averageRating").text("서버에 요청을 보낼 수 없습니다.");
                    }
                });
            }

            // 초기 평균 별점 로드
            fetchAverageRating();
        });
    </script>
</body>
</html>
