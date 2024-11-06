<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>레벨에 따른 이모지 표시</title>
    <script>
        // 페이지가 로드될 때 이모지를 설정합니다.
        function setEmoji(level) {
            // 해양 이모지 배열을 정의합니다.
            var emojis = ["🐚", "🦐", "🦞", "🐠", "🐙", "🐬", "🦈", "🐳"];
            
            // 레벨에 따라 이모지를 설정합니다.
            var emoji;
            
            // 레벨을 5로 나눈 몫을 사용하여 이모지를 선택합니다.
            var index = Math.floor((level - 1) / 5);
            
            // 이모지 배열의 범위를 벗어나지 않도록 확인합니다.
            if (index < emojis.length) {
                emoji = emojis[index];
            } else {
                emoji = "😕"; // 레벨이 배열의 범위를 벗어나는 경우
            }
            
            // 이모지를 페이지에 표시합니다.
            document.getElementById('emojiDisplay').innerText = emoji;
            document.getElementById('levelDisplay').innerText = level;
        }
        
        // 페이지가 로드될 때 호출됩니다.
        window.onload = function() {
            // JSP에서 전달받은 member_level 값을 JavaScript 변수로 설정합니다.
            //var userLevel = ${member.member_level}; // JSP 표현식을 사용하여 서버에서 전달된 값을 삽입
            //setEmoji(userLevel);
            
            var userLevel = 28; // 예시로 하드코딩된 값
            setEmoji(userLevel);
        };
    </script>
</head>
<body>
	<c:if test="${not empty member.member_level}">
	    Level: <span id="levelDisplay"></span>
	    <span id="emojiDisplay"></span>
    </c:if>
</body>
</html>
