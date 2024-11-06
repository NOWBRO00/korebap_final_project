// 지도 생성 및 옵션 설정
    		        var container = document.getElementById('map'); // 지도를 담을 영역의 DOM 레퍼런스
    		        var options = { // 지도를 생성할 때 필요한 기본 옵션
    		            center: new kakao.maps.LatLng(37.5665, 127.961), // 한반도의 중심 좌표
    		            level: 17 // 지도의 레벨(확대, 축소 정도). 7은 한반도의 전체적인 모습을 보여줍니다.
    		        };

    		        var map = new kakao.maps.Map(container, options); // 지도 생성 및 객체 리턴
    		        
    				// GPS API
    				// 브라우저가 지오로케이션을 지원하는지 확인
    				if (navigator.geolocation) {
    				    // 사용자의 현재 위치를 가져오기
    				    navigator.geolocation.getCurrentPosition(
    				        (position) => {
    				            // 사용자의 현재 위치 정보를 사용하여 kakao.maps.LatLng 객체 생성
    				            const userLocation = new kakao.maps.LatLng(
    				                position.coords.latitude, // 위도
    				                position.coords.longitude // 경도
    				            );

    				            // 지도 중심을 사용자 위치로 이동
    				            map.setCenter(userLocation);

    				            // 사용자 위치에 물고기 아이콘 마커 추가
    				            new kakao.maps.Marker({
    				                position: userLocation, // 마커의 위치
    				                map: map, // 마커를 추가할 지도 객체
    				                title: 'You are here!', // 마커의 제목 (마우스를 올렸을 때 표시됨)
    				                image: new kakao.maps.MarkerImage(
    				                    'img/favicon.png', // 물고기 아이콘 URL
    				                    new kakao.maps.Size(40, 40) // 아이콘의 크기
    				                )
    				            });
    				        },
    				        (error) => {
    				            // 위치 정보 가져오기 실패 시 호출되는 함수
    				            handleLocationError(true, error.message); // 오류 메시지 처리 함수 호출
    				        }
    				    );
    				} else {
    				    // 브라우저가 지오로케이션을 지원하지 않는 경우
    				    handleLocationError(false); // 지오로케이션 미지원 메시지 처리 함수 호출
    				}
    		        // 장소에 대한 정보
    		        var places = [
    		            {
    		                position: new kakao.maps.LatLng(37.5665, 126.978), // 서울의 위도, 경도
    		                title: '서울 낚시터'
    		            },
    		            {
    		                position: new kakao.maps.LatLng(35.1796, 129.0756), // 부산의 위도, 경도
    		                title: '부산 낚시터'
    		            }
    		            // 추가 장소를 여기에 넣을 수 있습니다.
    		        ];

    		        // 마커와 정보 창을 추가하는 함수
    		        function addMarkersAndInfoWindows() {
    		            places.forEach(function(place) {
    		                // 마커 생성
    		                var marker = new kakao.maps.Marker({
    		                    position: place.position,
    		                    map: map
    		                });

    		                // 정보 창 생성
    		                var infoWindow = new kakao.maps.InfoWindow({
    		                    content: '<div style="padding:5px;">' + place.title + '</div>'
    		                });

    		                // 마커 클릭 시 정보 창 표시
    		                kakao.maps.event.addListener(marker, 'click', function() {
    		                    infoWindow.open(map, marker);
    		                });
    		            });
    		        }

    		        // 마커와 정보 창 추가
    		        addMarkersAndInfoWindows();

    		        // 위치 오류 처리 함수
    		        function handleLocationError(browserHasGeolocation, errorMessage) {
    		            var message = browserHasGeolocation ?
    		                'Error: ' + errorMessage :
    		                'Error: Your browser does not support geolocation.';
    		            alert(message);
    		        }
