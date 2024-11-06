// 선택한 파일의 이름을 표시하는 함수
function displayFileNames() {
    // 파일 입력 요소를 ID로 가져옴
    var fileInput = document.getElementById('files');
    
    // 파일 이름이 표시될 요소를 ID로 가져옴
    var fileList = document.getElementById('fileList');
    
    // 기존 파일 이름 내용을 지움
    fileList.innerHTML = ''; 

	for (let i = 0; i < fileInput.files.length; i++) {
	                fileList.innerHTML += '<div>' + fileInput.files[i].name + '</div>'; // 파일 이름 표시
	            }
}
