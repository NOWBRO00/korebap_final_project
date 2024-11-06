$(document).ready(function() {
document.getElementById('files').addEventListener('change', function(event) {
      // 파일 미리보기
     
         const fileList = document.getElementById('files').files;
         const fileDisplay = document.getElementById('fileList');
         fileDisplay.innerHTML = ''; // 기존 파일 목록 초기화
         for (let i = 0; i < fileList.length; i++) {
            let img = document.createElement('img');
            img.src = URL.createObjectURL(fileList[i]);
            img.onload = function() {
               URL.revokeObjectURL(this.src); // 메모리 해제
            }
            fileDisplay.appendChild(img);
         }
      
         // 페이지 로드 시 초기 이미지 표시
      displayFileNames();
   });
});