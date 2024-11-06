// 이미지 삭제 모달에서 선택된 이미지 삭제 처리
$('#deleteImageModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // 버튼의 data-image 속성으로 이미지 경로 받기
    var image = button.data('image'); // 'data-image'에서 이미지 URL을 가져옴
    var modal = $(this);

    $('#confirmDeleteImage').off('click').on('click', function() {
        if (image) { // 이미지 경로가 유효한지 확인
            $.ajax({
                type: 'POST',
                url: 'deleteImage.do',
                data: { imagePath: image },
                success: function(response) {
                    alert('이미지가 삭제되었습니다.');
                    modal.modal('hide');
                    location.reload(); // 삭제 후 새로고침
                },
                error: function() {
                    alert('이미지 삭제 중 오류가 발생했습니다.');
                }
            });
        } else {
            alert('이미지를 선택하지 않았습니다.');
        }
    });
});