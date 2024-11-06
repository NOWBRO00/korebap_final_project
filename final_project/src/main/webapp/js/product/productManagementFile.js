$(document).ready(function() {
	function displayFileNames() {
		const fileInput = document.getElementById('product_files');
		const fileListDisplay = document.getElementById('fileList');
		fileListDisplay.innerHTML = ""; // 기존에 있던 내용 지우기

		const files = fileInput.files;

		for (let i = 0; i < files.length; i++) {
			const file = files[i];

			// 이미지 파일인지 확인
			if (file && file.type.match('image.*')) {
				// 파일의 URL 생성
				const imgURL = URL.createObjectURL(file);

				// 이미지 미리보기 생성
				const img = document.createElement('img');
				img.src = imgURL;
				img.style.width = '150px';
				img.style.margin = '10px';
				fileListDisplay.appendChild(img);

				// URL을 로그에 출력
				console.log("Image URL:", imgURL);

				// URL 해제 (필요할 때)
				// URL.revokeObjectURL(imgURL);
			} else {
				const listItem = document.createElement('div');
				listItem.textContent = file.name; // 이미지 파일이 아닌 경우 파일 이름만 표시
				fileListDisplay.appendChild(listItem);
			}
		}
	}

	// 파일 및 폼 데이터를 서버로 전송하는 함수
	function submitForm() {
		const form = document.getElementById('productForm');
		const formData = new FormData(form); // FormData 객체 생성

		const fileInput = document.getElementById('product_files');
		const files = fileInput.files;

		// 파일을 FormData에 추가
		for (let i = 0; i < files.length; i++) {
			formData.append('product_files', files[i]);
		}

		// 서버로 데이터 전송
		fetch('/your-server-endpoint', {
			method: 'POST',
			body: formData,
		})
			.then(response => response.json())
			.then(data => {
				console.log('Success:', data);
				alert('파일 업로드 성공');
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	}
})