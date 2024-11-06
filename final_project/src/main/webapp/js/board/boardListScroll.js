
$(document).ready(function() {
   console.log("페이지 로드 완료");

   var isLoading = false; // 현재 페이지가 로딩 중인지 여부

   var totalPage = $('#totalPage').data('board-total-page');  // 전체 페이지 수
   var currentPage = $('#currentPage').data('board-current-page'); // 현재 페이지 수
   var board_search_criteria = $("select[name='board_search_criteria']").val(); // 정렬 기준
   var board_searchKeyword = $('input[name="board_searchKeyword"]').val(); // 검색어



   // 만약 현재 페이지가 undefined 혹은 null이어서 값이 없는 경우
   if (currentPage === undefined || currentPage === null) {
      // 1로 설정
      currentPage = 1;
   }


   // 윈도우 스크롤 기능 동작
   $(window).scroll(debounce(function() {
      console.log("스크롤 이벤트 발생");

      var scrollTop = $(window).scrollTop(); // 위로 스크롤된 길이
      var windowsHeight = $(window).height(); // 웹 브라우저 창의 높이
      var documentHeight = $(document).height(); // 문서 전체의 높이
      var isBottom = scrollTop + windowsHeight > documentHeight - 400; // 바닥에 갔는지 여부


      // 데이터 로그
      console.log("totalPage 확인 : " + totalPage);
      console.log("currentPage 확인 : " + currentPage);
      console.log("board_search_criteria 확인 : " + board_search_criteria);
      console.log("board_searchKeyword 확인 : " + board_searchKeyword);



      // 스크롤 계산된 데이터라면
      if (isBottom) {
         // 현재페이지보다 전체페이지 수가 작을 때
         if (currentPage < totalPage) {
            isLoading = true; // 로딩 상태로 변경
            currentPage++; // 페이지 번호 증가
            console.log("스크롤 이벤트 발생, 데이터 요청: 페이지 번호 ", currentPage);
            // 다음 페이지 요청
            
            $.ajax({
               type: "GET",
               url: "boardListScroll.do", // 비동기 요청을 보낼 URL
               data: {
                  "currentPage": currentPage, // 현재 페이지 번호
                  "board_search_criteria": board_search_criteria, // 정렬 기준
                  "board_searchKeyword": board_searchKeyword // 검색어
               }, // 서버에 페이지 정보 전달
               dataType: 'json',
               success: function(data) {

                  const boardList = data.boardList; // data의 boardList 가져오기

                  if (!data) {
                     console.log("더 이상 불러올 게시글이 없습니다.");
                     isLoading = false;
                     return;
                  }


                     
                  // 서버에서 전송된 데이터를 boardList에 추가하기
                  // 반복문을 돌려 json 타입의 데이터를 html 요소에 반영한다.      
                  boardList.forEach((board, i) => {
                     console.log("forEach 시작 i:" + i);
                     // imgSrc 변수 설정
                                const imgSrc = (board.board_file_dir && board.board_file_dir.startsWith('http')) 
                                    ? board.board_file_dir 
                                    : 'img/board/' + (board.board_file_dir || 'boardBasic.png');
                     //const postHtml = ' <div class="col-md-4"> <div class="blog-item scroll_counter"> <img src="' + (board.board_file_dir ? 'img/board/' + board.board_file_dir : 'img/board/boardBasic.png') + '" alt="게시글 이미지" class="img-fluid"> <div class="bi-text"><span class="b-tag">자유게시판</span><div class="b-tag">' + board.board_like_cnt + '</div><h4><a class="text-dark" href="boardDetail.do?member_id=' + board.member_id + '&board_num=' + board.board_num + '">' + board.board_title + '</a></h4> <div class="b-time"><div class="text-dark"> <i class="icon_clock_alt"></i>' + board.board_registration_date + '</div></div></div></div></div>';
                     const postHtml = `
                         <div class="col-md-4">
                             <div class="blog-item scroll_counter">
                                 <img src="${imgSrc}" alt="게시글 이미지" class="img-fluid">
                                 <div class="bi-text">
                                     <span class="b-tag">자유게시판</span>
                                     <div class="b-tag">${board.board_like_cnt}</div>
                                     <h4>
                                         <a class="text-dark" href="boardDetail.do?member_id=${board.member_id}&board_num=${board.board_num}">${board.board_title}</a>
                                     </h4>
                                     <div class="b-time">
                                         <div class="text-dark">
                                             <i class="icon_clock_alt"></i>${board.board_registration_date}
                                         </div>
                                     </div>
                                 </div>
                             </div>
                         </div>
                     `;
                     // boardList에 추가
                     $('#boardList').append(postHtml);
                  });

                  console.log("append 실행!!");
                  isLoading = false; // 로딩여부 false로 변경
               },
               error: function(xhr, status, error) {
                  console.error("AJAX 요청 실패:", error);
                  isLoading = false; // 로딩 중 오류가 나면 로딩 상태 초기화
               }
            });

         }
         else {
            // 현재페이지 < 전체페이지가 아닌 경우
            return false;
         }
      }
   }, 250));


   // debounce 함수 정의
   function debounce(func, delay) {
      let timeout;
      return function() {
         const context = this, args = arguments;
         clearTimeout(timeout);
         timeout = setTimeout(() => func.apply(context, args), delay);
      };
   }

})