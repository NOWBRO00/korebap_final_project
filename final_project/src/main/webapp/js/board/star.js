// 별점 플러그인 코드
// DOMContentLoaded 이벤트는 HTML 문서의 초기 로딩이 완료되었을 때 발생합니다.
document.addEventListener('DOMContentLoaded', () => {
    // '.star-rating input[type="radio"]' 선택자를 사용하여 모든 별점 라디오 버튼을 선택합니다.
    // 이 선택자는 클래스를 '.star-rating'으로 가진 요소 안의 모든 라디오 버튼을 의미합니다.
    const stars = document.querySelectorAll('.star-rating input[type="radio"]');
    
    // 선택된 모든 별점 라디오 버튼에 대해 반복 작업을 수행합니다.
    stars.forEach(star => {
        // 각 별점 라디오 버튼에 'change' 이벤트 리스너를 추가합니다.
        // 'change' 이벤트는 사용자가 라디오 버튼을 선택했을 때 발생합니다.
        star.addEventListener('change', () => {
            // 현재 체크된 라디오 버튼의 값을 가져옵니다.
            // 'input[name="rating"]:checked'는 name 속성이 'rating'인 체크된 라디오 버튼을 의미합니다.
            const rating = document.querySelector('input[name="rating"]:checked').value;
            
            // 선택된 별점 값을 콘솔에 출력합니다.
            // 선택된 값은 사용자가 선택한 별점의 숫자입니다.
            console.log(`Selected rating: ${rating}`);
        });
    });
});
