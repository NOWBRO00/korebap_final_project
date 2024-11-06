<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Sona Template">
    <meta name="keywords" content="Sona, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>고래밥</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">
    
	<!-- CKEditor cdn -->
	<script src="https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.js"></script>
	<!-- CKEditor css -->
	<link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.css">
	<!-- CKEditor Js Import -->
	<script type="importmap">
	{
			"imports": {
			"CKEditor": "https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.js",
			"CKEditor/": "https://cdn.ckeditor.com/ckeditor5/43.1.0/",
			"editorConfig": "./js/board/CKEditorConfig.js"
			}
	}
	</script>
	<script type="text/javascript">
		//외부 js 파일에서 사용하기 위해 전역변수로 설정하여 데이터를 불러옵니다.
		var contentData = '${notice.board_content}';
	</script>
	<script type="module" src="js/board/CKEditorRead.js"></script>

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/flaticon.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <style type="text/css">
    .file-display {
            position: relative;
            z-index: 1;
            background-color: #f8f8f8;
            border: 1px solid #ccc;
            border-radius: 4px;
            padding: 10px;
            min-height: 30px;
            line-height: 30px;
            overflow: hidden;
        }
    .ck.ck-editor{
		width: 100%;
		max-width: 1500px;
	}
	.ck-editor__editable {
		min-height: 350px;
	}
	.ck-content{
		height: 41rem !important;
	}
	</style>
</head>

<body>
	<!-- 헤더 연결 -->
    <c:import url="header.jsp"></c:import>

    <!-- 글 작성페이지 시작섹션 시작 -->
    <section class="blog-details-hero set-bg" data-setbg="img/board/boardBasic.png">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="bd-hero-text">
                    	 <!-- 페이지 제목 및 설명 -->
                        <span>공지 사항</span>
                        <h2>고래밥 공지 사항</h2>
                        
                    </div>
                </div>
            </div>
        </div>
    </section>
<!-- 글 작성페이지 시작섹션 종료 -->

<!-- 글 작성페이지 메인섹션 시작 -->
    <section class="blog-details-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="blog-details-text">
                        <div class="leave-comment ">
                            <!-- 글 작성 폼 -->
                            <h2 class="d-flex justify-content-center mb-5">공지 사항 수정</h2>
                             <form action="updateNotice.do" method="POST" name="editing" enctype="multipart/form-data">
                             	<input type="hidden" name="board_num" value="${notice.board_num}" readonly/>
				                <div class="row">
				                    <div class="col-md-12">
				                        <div class="form-group">
				                        <!-- 제목 바이트 제한 : 100자 -->
				                            <input type="text" class="form-control" id="title" name="board_title" required placeholder="글의 제목을 입력해주세요 ( 제한 : 100자 )" maxlength="100" value="${notice.board_title}"/>
				                            <!-- 바이트 제한을 넘어서면 작게 안내문구 보이도록 설정 -->
				                            <div id="titleError" class="byte-error">제목은 100자를 넘을 수 없습니다.</div>
				                        </div>
				                    </div>
				                </div>
				                <div class="row">
				                    <div class="col-md-12">
				                        <div class="form-group">
				                            <div class="input-group">
				                                <textarea id="readContent" class="form-control" name="board_content">
				                                	<!-- 수정할 게시글을 불러올 영역 -->
				                                </textarea>
				                            </div>
				                            <hr>
				                            <div class="text-group" style="text-align:right; height: 40px !important;">
					                           현재 이미지 개수 : <span class="img-length">0장</span> <br>
					                           현재 글자 수 : <span class="text-length">0자</span>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				                <div class="row pt-5">
				                    <div class="col-12 d-flex justify-content-center">
				                        <button type="button" class="btn btn-black me-4" onclick="cancelEditing()">취소</button>
				                        <button type="submit" class="btn btn-primary" onclick="return validateForm()">작성</button>
				                    </div>
				                </div>
				                <!-- End of Form -->
				            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
<!-- 글 작성페이지 시작섹션 종료 -->


<!-- 푸터 연결 -->
    <c:import url="footer.jsp"></c:import>

<!-- Js Plugins -->
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.magnific-popup.min.js"></script>
    <script src="js/jquery.nice-select.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/jquery.slicknav.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/myJs/displayFileNames.js"></script>
    
        <script type="text/javascript">

    // 바이트 계산 함수
    function getByteLength(str) {
       // TextEncoder를 사용하여 문자열의 바이트 길이 계산
        return new TextEncoder().encode(str).length; // 바이트 길이 반환
    }

    // 제목과 내용의 입력 체크 함수
    function updateByteCount() {
       // 제목과 내용 입력 필드 값 가져오기
        const title = document.getElementById('title').value;

        // 가져온 입력 값들의 바이트 길이 계산
        const titleByteCount = getByteLength(title);

        // 오류 메시지를 표시할 요소를 가져옴
        const titleError = document.getElementById('titleError');

        // 제목 바이트가 100을 넘었다면
        if (titleByteCount > 100) {
           // 바이트 초과 오류 메시지 표시
            titleError.style.display = 'block';
           // 초과된 문자를 제거하여 제목의 길이를 100 이하로 강제로 설정
            document.getElementById('title').value = title.slice(0, 100); 
        } else {
           // 기본값 : none, 오류메시지 숨김
            titleError.style.display = 'none';
        }
    }

 // 제목 입력 필드와 내용 입력 필드에 대한 'input' 이벤트 리스너
    // 실시간 바이트 체크
    document.getElementById('title').addEventListener('input', updateByteCount);

    // 폼 검증 함수
    function validateForm() {
       // 폼 제출 전에 바이트 수를 업데이트하여 현재 상태를 반영
        updateByteCount(); 
     // 제목과 내용의 오류 메시지 표시 상태를 확인하여 폼 제출을 허용할지 결정
        const titleError = document.getElementById('titleError').style.display === 'block';
     
        if(titleError){ // 제목과 길이 제한을 초과한 채로 제출한다면
           alert('제목과 내용의 길이 제한을 다시 확인해주세요!'); // 경고창 띄우기
        }
        return !titleError; // 에러가 없으면 폼 제출
    }

    function cancelEditing() {
        window.location.href = 'noticeList.do';
    }

    // 페이지 로드 시 초기 바이트 수 업데이트
    window.onload = updateByteCount;
    </script>
    
</body>
</html>